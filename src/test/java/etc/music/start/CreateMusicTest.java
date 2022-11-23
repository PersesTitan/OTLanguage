package etc.music.start;

import bin.calculator.tool.Calculator;
import bin.exception.MatchException;
import etc.music.exception.MusicException;
import etc.music.paly.MusicItemTest;
import etc.music.paly.MusicRepository;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;

import static bin.check.VariableCheck.getFloat;
import static bin.token.Token.ACCESS;
import static bin.token.VariableToken.VARIABLE_HTML;

public class CreateMusicTest extends StartWorkV3 implements MusicRepository, Calculator {
    // 1
    public CreateMusicTest(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        StringTokenizer tokenizer = new StringTokenizer(line.split("(\\s+|\\[)")[0], ACCESS);
        tokenizer.nextToken();
        String methodName = tokenizer.nextToken();
        if (musicRepository.containsKey(methodName)) throw new MusicException().defineName();
        else if (!methodName.matches(VARIABLE_HTML)) throw new MusicException().nameMathError();

        if (params.length == 1) musicRepository.put(params[0], create(methodName));
        else if (params.length == 2) {
            float speed = getFloat(getNumberStr(params[1], repositoryArray));
            musicRepository.put(params[0], create(methodName).setSpeed(speed));
        } else if (params.length == 3) {
            float speed = getFloat(getNumberStr(params[1], repositoryArray));
            float volume = getFloat(getNumberStr(params[2], repositoryArray));
            MusicItemTest musicItemTest = create(methodName)
                    .setSpeed(speed)
                    .setVolume(volume);
            musicRepository.put(params[0], musicItemTest);
        } else if (params.length == 4) {
            float speed = getFloat(getNumberStr(params[1], repositoryArray));
            float volume = getFloat(getNumberStr(params[2], repositoryArray));
            float pitch = getFloat(getNumberStr(params[3], repositoryArray));
            MusicItemTest musicItemTest = create(methodName)
                    .setSpeed(speed)
                    .setVolume(volume)
                    .setPitch(pitch);
            musicRepository.put(params[0], musicItemTest);
        } else throw new MatchException().grammarError();
    }

    private MusicItemTest create(String methodName) {
        return switch (methodName) {
            case 도 -> new MusicItemTest("music/FX_piano01.wav");
            case 레 -> new MusicItemTest("music/FX_piano03.wav");
            case 미 -> new MusicItemTest("music/FX_piano05.wav");
            case 파 -> new MusicItemTest("music/FX_piano06.wav");
            case 솔 -> new MusicItemTest("music/FX_piano08.wav");
            case 라 -> new MusicItemTest("music/FX_piano10.wav");
            case 시 -> new MusicItemTest("music/FX_piano12.wav");
            default -> throw new MusicException().methodTypeError();
        };
    }
}
