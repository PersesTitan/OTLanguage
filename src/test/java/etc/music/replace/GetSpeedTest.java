package etc.music.replace;

import etc.music.exception.MusicException;
import etc.music.paly.MusicRepository;
import work.v3.ReturnWorkV3;

import java.util.LinkedList;
import java.util.Map;

public class GetSpeedTest extends ReturnWorkV3 implements MusicRepository {
    // 1
    public GetSpeedTest(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (!musicRepository.containsKey(params[0])) throw new MusicException().noDefine();
        return String.valueOf(musicRepository.get(params[0]).getSpeed());
    }
}