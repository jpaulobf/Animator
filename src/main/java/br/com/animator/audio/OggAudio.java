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
import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_memory;
import static org.lwjgl.system.MemoryStack.stackPush;

/**
 * Class for OGG audio file support (Music and SFX).
 */
public class OggAudio {

    private static long device;
    private static long context;

    public enum AudioType {
        SFX, MUSIC
    }

    private AudioType type;
    private int bufferId;
    private int sourceId;
    private float volume = 1.0f;
    private boolean isMuted = false;

    public OggAudio(String resourcePath, AudioType type) {
        initOpenAL();
        this.type = type;
        loadAudio(resourcePath);
    }

    private static void initOpenAL() {
        if (context != 0) return;
        device = alcOpenDevice((ByteBuffer) null);
        ALCCapabilities alcCapabilities = ALC.createCapabilities(device);
        context = alcCreateContext(device, (IntBuffer) null);
        alcMakeContextCurrent(context);
        AL.createCapabilities(alcCapabilities);
    }

    private void loadAudio(String resourcePath) {
        try (MemoryStack stack = stackPush()) {
            ByteBuffer encodedBuffer = ioResourceToByteBuffer(resourcePath, 32 * 1024);

            IntBuffer channelsBuffer = stack.mallocInt(1);
            IntBuffer sampleRateBuffer = stack.mallocInt(1);

            ShortBuffer rawAudioBuffer = stb_vorbis_decode_memory(encodedBuffer, channelsBuffer, sampleRateBuffer);
            if (rawAudioBuffer == null) {
                throw new RuntimeException("Failed to decode OGG: " + resourcePath);
            }

            int channels = channelsBuffer.get(0);
            int sampleRate = sampleRateBuffer.get(0);

            int format = -1;
            if (channels == 1) {
                format = AL_FORMAT_MONO16;
            } else if (channels == 2) {
                format = AL_FORMAT_STEREO16;
            }

            bufferId = alGenBuffers();
            alBufferData(bufferId, format, rawAudioBuffer, sampleRate);

            sourceId = alGenSources();
            alSourcei(sourceId, AL_BUFFER, bufferId);

            MemoryUtil.memFree(rawAudioBuffer);
            updateVolume();
        } catch (Exception e) {
            System.err.println("Error loading OGG [" + resourcePath + "]: " + e.getMessage());
        }
    }

    private ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws Exception {
        ByteBuffer buffer;
        try (InputStream source = getClass().getResourceAsStream(resource);
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

    public void play() {
        alSourcePlay(sourceId);
    }

    public void stop() {
        alSourceStop(sourceId);
    }

    public void loop() {
        alSourcei(sourceId, AL_LOOPING, AL_TRUE);
        alSourcePlay(sourceId);
    }

    public void volumeUp() {
        this.volume = Math.min(1.0f, this.volume + 0.1f);
        if (this.volume < 0.1f) this.volume = 0.1f;
        updateVolume();
    }

    public void volumeDown() {
        this.volume = Math.max(0.1f, this.volume - 0.1f);
        updateVolume();
    }

    public void muted() {
        this.isMuted = !this.isMuted;
        updateVolume();
    }

    public void volumeTo(float percent) {
        this.volume = Math.max(0.0f, Math.min(100.0f, percent)) / 100.0f;
        updateVolume();
    }

    private void updateVolume() {
        float appliedVolume = isMuted ? 0.0f : volume;
        alSourcef(sourceId, AL_GAIN, appliedVolume);
    }

    public void cleanup() {
        stop();
        alDeleteSources(sourceId);
        alDeleteBuffers(bufferId);
    }

    public AudioType getType() { return type; }
    public void setType(AudioType type) { this.type = type; }
}
