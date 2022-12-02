package cos.gui.items;

import cos.gui.setting.GUIRepository;
import work.v3.StartWorkV3;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Map;

public class OFrame extends StartWorkV3 implements GUIRepository {
    // 1
    public OFrame(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        item.put(params[0], new JFrame());
    }
}
