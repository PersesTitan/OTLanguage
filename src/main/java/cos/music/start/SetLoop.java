package cos.music.start;

import bin.calculator.tool.Calculator;
import cos.music.exception.MusicException;
import cos.music.items.MusicRepository;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.check.VariableCheck.getInteger;

public class SetLoop extends StartWorkV3 implements MusicRepository, Calculator {
    // 2
    public SetLoop(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (!musicRepository.containsKey(params[0])) throw new MusicException().noDefine();
        int count = getInteger(getNumberStr(params[1], repositoryArray));
        musicRepository.get(params[0]).setLoop(count);
    }
}
