package etc.music;

import etc.music.paly.Sonic;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PlaybackParamsTest {
    private static Map<String, File> map = new HashMap<>(Map.of(
            "도", new File("music/FX_piano01.wav"),
            "레", new File("music/FX_piano03.wav"),
            "미", new File("music/FX_piano05.wav"),
            "파", new File("music/FX_piano06.wav"),
            "솔", new File("music/FX_piano08.wav"),
            "라", new File("music/FX_piano10.wav"),
            "시", new File("music/FX_piano12.wav"),
            "도1", new File("music/FX_piano13.wav"),
            "도#", new File("music/FX_piano02.wav"),
            "레#", new File("music/FX_piano04.wav")
    )) {{
        putAll(Map.of(
                "미#", new File("music/FX_piano05.wav"),
                "파#", new File("music/FX_piano07.wav"),
                "솔#", new File("music/FX_piano09.wav"),
                "라#", new File("music/FX_piano11.wav")));
    }};
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
//        for (int i = 1; i<13; i++) {
//            if (i<10) Sonic.getInstance().start(new File("music/FX_piano0" + i + ".wav"), 1.0f, 1.0f, 1.0f, 1.0f);
//            else Sonic.getInstance().start(new File("music/FX_piano" + i + ".wav"), 1.0f, 1.0f, 1.0f, 1.0f);
//        }
//        Sound.도.start();
//        Clip clip = AudioSystem.getClip();
//        try {
//            Clip clip = AudioSystem.getClip();
//            clip.open(AudioSystem.getAudioInputStream(new File("music/FX_piano01.wav")));
//            clip.start();
//            TimeUnit.MICROSECONDS.sleep(clip.getMicrosecondLength());
//            clip.stop();
//            clip.close();
//            TimeUnit.MICROSECONDS.sleep(clip.getMicrosecondLength());
//        } catch (InterruptedException ignored) {}

        for (String word : Arrays.asList("미", "레", "도", "레", "미", "미", "미", "레", "레", "레", "미", "미", "미")) {
            Sonic.getInstance().start(map.get(word), 1.0f, 1.0f, 1.0f, 1.0f);
        }


//        for (int i = 0; i<100; i++) {
//            Sonic.getInstance().start(new File("music/FX_piano01.wav"), 10.0f, 1.0f, 0.1f * i);
//        }
    }

    public enum Sound {
        도(new File("music/FX_piano01.wav")),
        레(new File("music/FX_piano03.wav")),
        미(new File("music/FX_piano05.wav")),
        파(new File("music/FX_piano06.wav")),
        솔(new File("music/FX_piano08.wav")),
        라(new File("music/FX_piano10.wav")),
        시(new File("music/FX_piano12.wav")),
        도1(new File("music/FX_piano13.wav")),
        도샵(new File("music/FX_piano02.wav")),
        레샵(new File("music/FX_piano04.wav")),
        미샵(new File("music/FX_piano05.wav")),
        파샵(new File("music/FX_piano07.wav")),
        솔샵(new File("music/FX_piano09.wav")),
        라샵(new File("music/FX_piano11.wav"));

        private final File file;
        Sound(File file) {
            this.file = file;
        }

        public void start() {
            try {
//                Clip clip = AudioSystem.getClip();
//                clip.open(AudioSystem.getAudioInputStream(file));
//                clip.start();
//                TimeUnit.MICROSECONDS.sleep(clip.getMicrosecondLength());
//                clip.stop();
//                clip.close();

                Sonic.getInstance().start(file, 1.0f, 1.0f, 1.0f, 1.0f);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }
}
