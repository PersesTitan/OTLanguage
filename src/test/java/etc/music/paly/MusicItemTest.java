package etc.music.paly;

import etc.music.SonicTest;
import etc.music.exception.MusicException;
import lombok.Getter;
import lombok.Setter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

@Getter
public class MusicItemTest {
    private final File file;
    private int loop = 1;
    private float speed = 1.0f;
    private float pitch = 1.0f;
    private float rate = 1.0f;
    private float volume = 1.0f;
    @Setter private boolean emulateChordPitch = false;
    @Setter private int quality = 0;

    public MusicItemTest(String fileName) {
        this.file = new File(fileName);
    }

    public MusicItemTest(File file) {
        this.file = file;
    }

    public void start() {
        try {
            SonicTest.getInstance().start(this);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new MusicException().doNotReadMusicFile();
        }
    }

    public MusicItemTest setSpeed(float speed) {
        this.speed = speed < 0 ? 1.0f / (-speed) : speed;
        return this;
    }

    public MusicItemTest setVolume(float volume) {
        this.volume = volume < 0 ? 1.0f / (-volume) : volume;
        return this;
    }

    public MusicItemTest setPitch(float pitch) {
        this.pitch = pitch < 0 ? 1.0f / (-pitch) : pitch;
        return this;
    }

    public MusicItemTest setRate(float rate) {
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

