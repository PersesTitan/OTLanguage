package etc.music.start;

import etc.music.exception.MusicException;
import etc.music.paly.MusicRepository;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

public class PlaySoundTest extends StartWorkV3 implements MusicRepository {
    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        for(String param : params) {
            if (!musicRepository.containsKey(param)) throw new MusicException().noDefine();
            musicRepository.get(param).start();
        }
    }
}
