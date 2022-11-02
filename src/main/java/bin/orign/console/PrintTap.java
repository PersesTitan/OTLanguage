package bin.orign.console;

import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

public class PrintTap extends StartWorkV3 {
    public PrintTap(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        System.out.print(params[0] + "\t");
    }
}
