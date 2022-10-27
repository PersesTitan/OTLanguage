package bin.orign.variable.origin.put;

import bin.apply.Repository;
import bin.exception.VariableException;
import bin.orign.variable.list.get.GetList;
import bin.orign.variable.map.get.GetMap;
import bin.orign.variable.set.get.GetSet;
import work.StartWork;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PutVariable implements StartWork, SetVariableValue, GetSet, GetList, GetMap {
    private final String patternText = startMerge(VARIABLE_SET);
    private final Matcher matcher = Pattern.compile(patternText).matcher("");

    @Override
    public boolean check(String line) {
        return matcher.reset(line).find();
    }

    @Override
    public void start(String line, String origen,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        // value = 변수명, 새로운 값
        String[] values = matchSplitError(line.strip(), VARIABLE_PUT, 2);
        int accessCount = accessCount(values[0], repositoryArray.size());
        if (accessCount == -1) throw VariableException.localNoVariable();
        var repository = repositoryArray.get(accessCount);
        if (!Repository.getSet(repository).contains(values[0])) throw VariableException.noDefine();
        String varType = getVariableType(repository, values[0]); // ㅇㅅㅇ, ㅇㅈㅇ
        repository.get(varType).put(values[0], values[1]);
    }

    @Override
    public void first() {

    }

    private String getVariableType(Map<String, Map<String, Object>> repository, String variableName) {
        for (var variableType : repository.entrySet()) {
            if (variableType.getValue().containsKey(variableName)) return variableType.getKey();
        }
        throw VariableException.sameVariable();
    }
}
