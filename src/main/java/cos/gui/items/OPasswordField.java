package cos.gui.items;

import cos.gui.setting.GUIRepository;
import work.v3.StartWorkV3;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Map;

public class OPasswordField extends StartWorkV3 implements GUIRepository {
    // 1 = 이름
    // 1    // 2
    public OPasswordField(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (params.length == 1) item.put(params[0], new JPasswordField());
        else item.put(params[0], new JPasswordField(params[1]));
    }
}
