package bin.define.item;

import bin.exception.VariableException;

import java.util.HashMap;
import java.util.Map;

import static bin.apply.Setting.COPY_REPOSITORY;
import static bin.apply.sys.make.StartLine.getFinalTotal;
import static bin.apply.sys.make.StartLine.startStartLine;
import static bin.token.LoopToken.LOOP_TOKEN;

public record MethodItem(String[][] params, MethodType methodType, String returnVariable,
                         String fileName, int start, int end) {

    private String getTotal() {
        return LOOP_TOKEN.get(fileName).substring(start, end);
    }

    public void startVoid(String[] params, Map<String, Map<String, Object>> repositoryArray) {
        if (params.length != this.params.length) throw VariableException.methodParamsCount();
        Map<String, Map<String, Object>> repository = (HashMap<String, Map<String, Object>>) COPY_REPOSITORY.clone();
        for (int i = 0; i<params.length; i++) repository.get(this.params[i][0]).put(this.params[i][1], params[i]);
        String total = getTotal();
        startStartLine(getFinalTotal(false, total, fileName), total, repository, repositoryArray);
    }

    public void startVoid(String[] params,
                          Map<String, Map<String, Object>> repositoryArray1,
                          Map<String, Map<String, Object>> repositoryArray2) {
        if (params.length != this.params.length) throw VariableException.methodParamsCount();
        Map<String, Map<String, Object>> repository = (HashMap<String, Map<String, Object>>) COPY_REPOSITORY.clone();
        for (int i = 0; i<params.length; i++) repository.get(this.params[i][0]).put(this.params[i][1], params[i]);
        String total = getTotal();
        startStartLine(getFinalTotal(false, total, fileName), total, repository, repositoryArray1, repositoryArray2);
    }

    public String startReturn(String[] params, Map<String, Map<String, Object>> repositoryArray) {
        if (params.length != this.params.length) throw VariableException.methodParamsCount();
        Map<String, Map<String, Object>> repository = (HashMap<String, Map<String, Object>>) COPY_REPOSITORY.clone();
        for (int i = 0; i<params.length; i++) repository.get(this.params[i][0]).put(this.params[i][1], params[i]);
        String total = getTotal();
        startStartLine(getFinalTotal(false, total, fileName), total, repository, repositoryArray);
        return getVariable(returnVariable, repository);
    }

    public String startReturn(String[] params,
                              Map<String, Map<String, Object>> repositoryArray1,
                              Map<String, Map<String, Object>> repositoryArray2) {
        if (params.length != this.params.length) throw VariableException.methodParamsCount();
        Map<String, Map<String, Object>> repository = (HashMap<String, Map<String, Object>>) COPY_REPOSITORY.clone();
        for (int i = 0; i<params.length; i++) repository.get(this.params[i][0]).put(this.params[i][1], params[i]);
        String total = getTotal();
        startStartLine(getFinalTotal(false, total, fileName), total, repository, repositoryArray1, repositoryArray2);
        return getVariable(returnVariable, repository);
    }

    private String  getVariable(String variable, Map<String, Map<String, Object>> repository) {
        for (var rep : repository.entrySet()) {
            var value = rep.getValue();
            if (value.containsKey(variable)) return value.get(variable).toString();
        }
        return null;
    }
}
