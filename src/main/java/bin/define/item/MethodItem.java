package bin.define.item;

import bin.apply.Setting;
import bin.exception.VariableException;
import bin.token.LoopToken;
import work.StartWork;

import java.util.HashMap;
import java.util.Map;

import static bin.apply.Repository.startWorks;
import static bin.apply.Setting.COPY_REPOSITORY;
import static bin.apply.sys.make.StartLine.getFinalTotal;
import static bin.apply.sys.make.StartLine.startStartLine;

public record MethodItem(String[][] params, MethodType methodType, String returnVariable,
                         String fileName, int start, int end) implements LoopToken {

    private String getTotal() {
        return LOOP_TOKEN.get(fileName).substring(start, end);
    }

    // ================================================== //
    // ====================== VOID ====================== //
    // ================================================== //
    public void startVoid(String[] params, Map<String, Map<String, Object>> repositoryArray) {
        getVariable(params, repositoryArray);
    }

    public void startVoid(String[] params,
                          Map<String, Map<String, Object>> repositoryArray1,
                          Map<String, Map<String, Object>> repositoryArray2) {
        getVariable(params, repositoryArray1, repositoryArray2);
    }

    // ================================================== //
    // ===================== RETURN ===================== //
    // ================================================== //
    public String startReturn(String[] params, Map<String, Map<String, Object>> repositoryArray) {
        getVariable(params, repositoryArray);
        return getVariable(returnVariable);
    }

    public String startReturn(String[] params,
                              Map<String, Map<String, Object>> repositoryArray1,
                              Map<String, Map<String, Object>> repositoryArray2) {
        getVariable(params, repositoryArray1, repositoryArray2);
        return getVariable(returnVariable);
    }

    // ============================= TOOL ============================= //
    private static final Map<String, Map<String, Object>> repository = (Map<String, Map<String, Object>>) COPY_REPOSITORY.clone();
    private void getRepository(String[] params) {
        if (params.length != this.params.length) throw VariableException.methodParamsCount();
        repository.values().forEach(Map::clear);
        PASS:
        for (int i = 0; i<params.length; i++) {
            for (StartWork work : startWorks) {
                String line = String.format("%s %s:%s", this.params[i][0], this.params[i][1], params[i]);
                if (work.check(line)) {work.start(line, line, getRepository(repository)); break PASS;}
            }
            Setting.warringMessage(String.format("매개변수 %s 추가에 실패하였습니다.", this.params[i][1]));
        }
    }

    // 저장소 1개
    private void getVariable(String[] params, Map<String, Map<String, Object>> repositoryArray) {
        getRepository(params);
        String total = getTotal();
        startStartLine(getFinalTotal(false, total, fileName), total, repository, repositoryArray);
    }

    // 저장소 2개
    private void getVariable(String[] params,
                             Map<String, Map<String, Object>> repositoryArray1,
                             Map<String, Map<String, Object>> repositoryArray2) {
        getRepository(params);
        String total = getTotal();
        startStartLine(getFinalTotal(false, total, fileName), total, repository, repositoryArray1, repositoryArray2);
    }

    @SafeVarargs
    private Map<String, Map<String, Object>>[] getRepository(Map<String, Map<String, Object>>...repository) {
        return repository;
    }

    // 변수값을 가져오는 작업
    private String getVariable(String variable) {
        for (var rep : repository.entrySet()) {
            var value = rep.getValue();
            if (value.containsKey(variable)) return value.get(variable).toString();
        } return null;
    }
}
