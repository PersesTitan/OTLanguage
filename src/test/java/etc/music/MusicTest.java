package etc.music;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class MusicTest {
    public static void main(String[] args) {
        new MusicTest();
//        new MusicTest();
    }

    public MusicTest(String line) {
        try {
            startMusic(new File(line));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private final HashMap<String, File> map = new HashMap<>();
    public MusicTest() {
        new File("test").mkdirs();
        List<File> fileList = new ArrayList<>(Arrays.asList(Objects.requireNonNull(new File("music").listFiles())));
        int i = 0;
        for (File file : fileList) {
            if (file.isFile() && file.getName().endsWith(".wav")) map.put(Integer.toString(i++), file);
        }

//            for (File ais : map.values()) {
//                startMusic(ais);
//            }
        System.out.println(map);
    }

    private void startMusic(File file)
            throws LineUnavailableException, IOException, InterruptedException, UnsupportedAudioFileException {
        Clip clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(file));
        clip.start();
        Thread.sleep(clip.getMicrosecondLength() / 1000);
    }
}