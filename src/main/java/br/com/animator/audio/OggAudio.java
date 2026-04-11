package br.com.animator.audio;

import static org.lwjgl.openal.AL10.*;

/**
 * Class for OGG audio file support (Music and SFX).
 */
public class OggAudio {

    public enum AudioType {
        SFX, MUSIC
    }

    private AudioType type;
    private int bufferId;
    private int sourceId;
    private float volume = 1.0f;
    private boolean isMuted = false;

    public OggAudio(String resourcePath, AudioType type) {
        AudioManager.init(); // Garante que o contexto existe
        this.type = type;
        
        this.bufferId = AudioManager.getBuffer(resourcePath);
        this.sourceId = alGenSources();
        alSourcei(sourceId, AL_BUFFER, bufferId);
        updateVolume();
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
        // O Buffer não é deletado aqui, pois é gerenciado pelo AudioManager (Cache)
    }

    public AudioType getType() { return type; }
    public void setType(AudioType type) { this.type = type; }
}
