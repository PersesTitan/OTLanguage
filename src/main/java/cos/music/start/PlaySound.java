package cos.music.start;

import cos.music.exception.MusicException;
import cos.music.items.MusicRepository;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

public class PlaySound extends StartWorkV3 implements MusicRepository {
    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        for(String param : params) {
            if (!musicRepository.containsKey(param)) throw new MusicException().noDefine();
            musicRepository.get(param).start();
        }
    }
}