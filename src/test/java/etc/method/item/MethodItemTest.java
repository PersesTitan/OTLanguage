package etc.method.item;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static bin.apply.Repository.COPY_REPOSITORY;
import static bin.apply.sys.make.StartLine.startStartLine;
import static etc.method.MethodItemTool.resetRepository;
import static etc.method.MethodItemTool.setParams;

public class MethodItemTest {
    public final Map<String, Map<String, Object>> repository = new HashMap<>(COPY_REPOSITORY);
    private final String[][] params;
    private final String finalTotal;
    private final String fileName;

    public MethodItemTest(String[][] params, String finalTotal, String fileName) {
        this.params = params;
        this.finalTotal = finalTotal;
        this.fileName = fileName;
    }

    public void start(String[] params, LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        setParams(this.params, params, repository);
        repositoryArray.addFirst(repository);
        try {
            startStartLine(finalTotal, fileName, repositoryArray);
        } finally {
            resetRepository(repository);
            repositoryArray.removeFirst();
        }
    }
}
