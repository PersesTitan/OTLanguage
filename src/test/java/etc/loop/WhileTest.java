package etc.loop;

import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

public class WhileTest extends StartWorkV3 {
    public static void main(String[] args) {
        new WhileTest();
    }

    public WhileTest(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {

    }
}
