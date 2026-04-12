package br.com.animator.audio;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.HashMap;
import java.util.Map;
import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_memory;
import static org.lwjgl.system.MemoryStack.stackPush;

/**
 * Singleton Manager for OpenAL context and audio buffer caching.
 */
public class AudioManager {

    private static long device;
    private static long context;
    private static final Map<String, Integer> bufferCache = new HashMap<>();
    private static final Map<String, String> keyPathMap = new HashMap<>();
    private static final Map<String, OggAudio.AudioType> keyTypeMap = new HashMap<>();
    private static final Map<String, OggAudio> audioCache = new HashMap<>();

    public static void init() {
        if (context != 0) return;
        device = alcOpenDevice((ByteBuffer) null);
        ALCCapabilities alcCapabilities = ALC.createCapabilities(device);
        context = alcCreateContext(device, (IntBuffer) null);
        alcMakeContextCurrent(context);
        AL.createCapabilities(alcCapabilities);
        System.out.println("OpenAL Initialized");
    }

    public static void loadSFX(String key, String path) {
        init();
        keyPathMap.put(key, path);
        keyTypeMap.put(key, OggAudio.AudioType.SFX);
        getBuffer(path); // Pré-carrega o buffer
    }

    public static void loadMusic(String key, String path) {
        init();
        keyPathMap.put(key, path);
        keyTypeMap.put(key, OggAudio.AudioType.MUSIC);
        getBuffer(path); // Pré-carrega o buffer
    }

    public static OggAudio getAudio(String key) {
        return audioCache.computeIfAbsent(key, k -> new OggAudio(k));
    }

    public static String getAudioPath(String key) {
        return keyPathMap.get(key);
    }

    public static OggAudio.AudioType getAudioType(String key) {
        return keyTypeMap.get(key);
    }

    public static int getBuffer(String resourcePath) {
        if (bufferCache.containsKey(resourcePath)) {
            return bufferCache.get(resourcePath);
        }

        int bufferId = loadAudioToBuffer(resourcePath);
        bufferCache.put(resourcePath, bufferId);
        return bufferId;
    }

    private static int loadAudioToBuffer(String resourcePath) {
        ByteBuffer encodedBuffer = null;
        try (MemoryStack stack = stackPush()) {
            encodedBuffer = ioResourceToByteBuffer(resourcePath, 32 * 1024);

            IntBuffer channelsBuffer = stack.mallocInt(1);
            IntBuffer sampleRateBuffer = stack.mallocInt(1);

            ShortBuffer rawAudioBuffer = stb_vorbis_decode_memory(encodedBuffer, channelsBuffer, sampleRateBuffer);
            if (rawAudioBuffer == null) {
                throw new RuntimeException("Failed to decode OGG: " + resourcePath);
            }

            int channels = channelsBuffer.get(0);
            int sampleRate = sampleRateBuffer.get(0);
            int format = (channels == 1) ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16;

            int bufferId = alGenBuffers();
            alBufferData(bufferId, format, rawAudioBuffer, sampleRate);

            MemoryUtil.memFree(rawAudioBuffer);
            return bufferId;
        } catch (Exception e) {
            throw new RuntimeException("Error loading OGG to buffer [" + resourcePath + "]: " + e.getMessage());
        } finally {
            if (encodedBuffer != null) MemoryUtil.memFree(encodedBuffer);
        }
    }

    private static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws Exception {
        ByteBuffer buffer;
        try (InputStream source = AudioManager.class.getResourceAsStream(resource);
             ReadableByteChannel rbc = Channels.newChannel(source)) {
            if (source == null) throw new RuntimeException("Resource not found: " + resource);
            buffer = MemoryUtil.memAlloc(bufferSize);
            while (true) {
                int bytes = rbc.read(buffer);
                if (bytes == -1) break;
                if (buffer.remaining() == 0) {
                    buffer = MemoryUtil.memRealloc(buffer, buffer.capacity() * 2);
                }
            }
            buffer.flip();
        }
        return buffer;
    }

    public static void cleanup() {
        if (context == 0) return;
        
        // Cleanup OggAudio instances (Sources)
        for (OggAudio audio : audioCache.values()) {
            audio.cleanup();
        }
        audioCache.clear();
        keyPathMap.clear();
        keyTypeMap.clear();

        // Clear all cached buffers
        for (int bufferId : bufferCache.values()) {
            alDeleteBuffers(bufferId);
        }
        bufferCache.clear();

        // Shutdown context
        alcMakeContextCurrent(0);
        alcDestroyContext(context);
        alcCloseDevice(device);
        context = 0;
        device = 0;
    }
}