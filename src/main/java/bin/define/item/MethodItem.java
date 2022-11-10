package bin.define.item;

import bin.apply.Repository;
import bin.apply.Setting;
import bin.exception.VariableException;
import bin.token.LoopToken;
import work.StartWork;

import java.util.HashMap;
import java.util.LinkedList;
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
    public void startVoid(String[] params, LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        getVariable(params, repositoryArray);
    }

    // ================================================== //
    // ===================== RETURN ===================== //
    // ================================================== //
    public String startReturn(String[] params, LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        getVariable(params, repositoryArray);
        return getVariable(returnVariable);
    }

    // ============================= TOOL ============================= //
    private static final Map<String, Map<String, Object>> repository = (Map<String, Map<String, Object>>) COPY_REPOSITORY.clone();
    private void getRepository(String[] params, LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (params.length != this.params.length) throw new VariableException().methodParamsCount();
        repository.values().forEach(Map::clear);
        try {
            repositoryArray.addFirst(repository);
            PASS:
            for (int i = 0; i<params.length; i++) {
                for (StartWork work : startWorks) {
                    String line = String.format("%s %s:%s", this.params[i][0], this.params[i][1], params[i]);
                    if (work.check(line)) {work.start(line, line, repositoryArray); break PASS;}
                }
                Setting.warringMessage(String.format("매개변수 %s 추가에 실패하였습니다.", this.params[i][1]));
            }
        } finally {
            repositoryArray.remove(repository);
        }
    }

    private void getVariable(String[] params, LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        getRepository(params, repositoryArray);
        String total = getTotal();
        startStartLine(getFinalTotal(false, total, fileName), total, repositoryArray);
    }

    // 변수값을 가져오는 작업
    private String getVariable(String variable) {
        for (var rep : repository.entrySet()) {
            var value = rep.getValue();
            if (value.containsKey(variable)) return value.get(variable).toString();
        } return null;
    }
}
