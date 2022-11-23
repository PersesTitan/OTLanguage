package cos.music.start;

import bin.calculator.tool.Calculator;
import bin.exception.MatchException;
import cos.music.exception.MusicException;
import cos.music.items.MusicItem;
import cos.music.items.MusicRepository;
import work.v3.StartWorkV3;

import java.io.File;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;

import static bin.apply.sys.item.Separator.MODULE_PATH;
import static bin.apply.sys.item.Separator.getPath;
import static bin.check.VariableCheck.getFloat;
import static bin.token.Token.ACCESS;
import static bin.token.VariableToken.VARIABLE_HTML;

public class CreateMusic extends StartWorkV3 implements MusicRepository, Calculator {
    // 1
    public CreateMusic(int... counts) {
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
            MusicItem item = create(methodName)
                    .setSpeed(speed)
                    .setVolume(volume);
            musicRepository.put(params[0], item);
        } else if (params.length == 4) {
            float speed = getFloat(getNumberStr(params[1], repositoryArray));
            float volume = getFloat(getNumberStr(params[2], repositoryArray));
            float pitch = getFloat(getNumberStr(params[3], repositoryArray));
            MusicItem item = create(methodName)
                    .setSpeed(speed)
                    .setVolume(volume)
                    .setPitch(pitch);
            musicRepository.put(params[0], item);
        } else throw new MatchException().grammarError();
    }

    private final File A = new File(getPath(MODULE_PATH, "music", "A.wav"));
    private final File B = new File(getPath(MODULE_PATH, "music", "B.wav"));
    private final File C = new File(getPath(MODULE_PATH, "music", "C.wav"));
    private final File D = new File(getPath(MODULE_PATH, "music", "D.wav"));
    private final File E = new File(getPath(MODULE_PATH, "music", "E.wav"));
    private final File F = new File(getPath(MODULE_PATH, "music", "F.wav"));
    private final File G = new File(getPath(MODULE_PATH, "music", "G.wav"));
    private MusicItem create(String methodName) {
        return switch (methodName) {
            case 도 -> new MusicItem(A);
            case 레 -> new MusicItem(B);
            case 미 -> new MusicItem(C);
            case 파 -> new MusicItem(D);
            case 솔 -> new MusicItem(E);
            case 라 -> new MusicItem(F);
            case 시 -> new MusicItem(G);
            default -> throw new MusicException().methodTypeError();
        };
    }
}
