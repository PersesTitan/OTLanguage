package cos.music.items;

import cos.music.exception.MusicException;
import lombok.Getter;
import lombok.Setter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

@Getter
public class MusicItem {
    private final File file;
    private int loop = 1;
    private float speed = 1.0f;
    private float pitch = 1.0f;
    private float rate = 1.0f;
    private float volume = 1.0f;
    @Setter private boolean emulateChordPitch = false;
    @Setter private int quality = 0;

    public MusicItem(String fileName) {
        this.file = new File(fileName);
    }

    public MusicItem(File file) {
        this.file = file;
    }

    public void start() {
        try {
            Sonic.getInstance().start(this);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new MusicException().doNotReadMusicFile();
        }
    }

    public MusicItem setSpeed(float speed) {
        this.speed = speed < 0 ? 1.0f / (-speed) : speed;
        return this;
    }

    public MusicItem setVolume(float volume) {
        this.volume = volume < 0 ? 1.0f / (-volume) : volume;
        return this;
    }

    public MusicItem setPitch(float pitch) {
        this.pitch = pitch < 0 ? 1.0f / (-pitch) : pitch;
        return this;
    }

    public MusicItem setRate(float rate) {
        this.rate = rate < 0 ? 1.0f / (-rate) : rate;
        return this;
    }

    public void setLoop(int loop) {
        if (loop < 1) throw new MusicException().loopCountError();
        this.loop = loop;
    }

    public AudioInputStream getStream() throws UnsupportedAudioFileException, IOException {
        return AudioSystem.getAudioInputStream(file);
    }
}
