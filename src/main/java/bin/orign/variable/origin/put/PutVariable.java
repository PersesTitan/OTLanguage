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

public class PutVariable implements
        StartWork, SetVariableValue, GetSet, GetList, GetMap {
    private final String patternText = startMerge(VARIABLE_SET);
    private final Pattern pattern = Pattern.compile(patternText);
    private final Pattern accessPattern = Pattern.compile(ACCESS);

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line, String origen, Map<String, Map<String, Object>>[] repositoryArray) {
        line = line.strip();
        String value = line.replaceFirst(patternText, ""); //넣을 값
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String group = matcher.group();
            int accessCount = accessCount(group);
            if (accessCount > repositoryArray.length) throw VariableException.localNoVariable();
            String variableName = group.substring(accessCount, group.length()-1); // 변수명
            var repository = repositoryArray[accessCount];
            if (Repository.noUse.contains(variableName)) throw VariableException.reservedWorks();
            else if (!Repository.getSet(repository).contains(variableName)) throw VariableException.noDefine();
            String varType = getVariableType(repository, variableName); // ㅇㅅㅇ, ㅇㅈㅇ
            set(varType, variableName, value, repository);
        }
    }

    private String getVariableType(Map<String, Map<String, Object>> repository, String variableName) {
        for (var variableType : repository.entrySet()) {
            if (variableType.getValue().containsKey(variableName)) return variableType.getKey();
        }
        throw VariableException.sameVariable();
    }
}
