package etc.method.item;

import bin.CreateReturnWorks;
import bin.apply.Repository;
import bin.define.method.MethodReturn;
import work.v3.ReturnWorkV3;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static bin.apply.Repository.COPY_REPOSITORY;
import static bin.apply.sys.make.StartLine.startStartLine;
import static etc.method.MethodItemTool.resetRepository;
import static etc.method.MethodItemTool.setParams;

public class MethodItemReturnTest {
    public final Map<String, Map<String, Object>> repository = new HashMap<>(COPY_REPOSITORY);
    private final String[][] params;
    private final String finalTotal;
    private final String fileName;
    private final String returnValue;

    public MethodItemReturnTest(String[][] params, String finalTotal, String fileName, String returnValue) {
        this.params = params;
        this.finalTotal = finalTotal;
        this.fileName = fileName;
        this.returnValue = returnValue;
    }

    public String start(String[] params, LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        repositoryArray.addFirst(repository);
        setParams(this.params, params, repository);
        try {
            startStartLine(finalTotal, fileName, repositoryArray);
            return CreateReturnWorks.sub(returnValue, null, repositoryArray);
        } finally {
            resetRepository(repository);
            repositoryArray.removeFirst();
        }
    }
}
