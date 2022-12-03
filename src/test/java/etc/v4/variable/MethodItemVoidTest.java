package etc.v4.variable;

import bin.apply.sys.item.HpMap;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static bin.apply.Repository.repositoryItems;
import static bin.apply.sys.make.StartLine.startStartLine;
import static etc.v4.variable.MethodItemToolTest.resetRepository;
import static etc.v4.variable.MethodItemToolTest.setParams;

public class MethodItemVoidTest {
    public final Map<String, Map<String, Object>> repository = new HashMap<>() {{
        repositoryItems.forEach(v -> put(v, new HpMap(v)));
    }};
    private final String[][] params;
    private final String finalTotal;
    private final String fileName;

    public MethodItemVoidTest(String[][] params, String finalTotal, String fileName) {
        this.params = params;
        this.finalTotal = finalTotal;
        this.fileName = fileName;
    }

    public void start(Object[] params, LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        setParams(this.params, params, repository);
        repositoryArray.addFirst(repository);
        try {
            startStartLine(finalTotal, fileName, repositoryArray);
        } finally {
            repositoryArray.removeFirst();
            resetRepository(repository);
        }
    }

    public int getParams() {
        return this.params.length;
    }
}

