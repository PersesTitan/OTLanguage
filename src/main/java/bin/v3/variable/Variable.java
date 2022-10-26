package bin.v3.variable;

import bin.orign.variable.both.ContainsTool;
import bin.token.MergeToken;
import bin.token.VariableToken;
import work.v3.ReturnWorkV3;

import java.util.*;

import static bin.check.VariableCheck.isInteger;
import static bin.token.VariableToken.*;
import static bin.token.cal.BoolToken.FALSE;
import static bin.token.cal.BoolToken.TRUE;

public class Variable extends ReturnWorkV3 implements MergeToken, ContainsTool {
    public Variable() {
        super(1);
    }

    private final String SET_ISEMPTY = VariableToken.SET_ISEMPTY.replace("\\", "");
    private final String SET_SUM = VariableToken.SET_SUM.replace("\\", "");
    private final String SET_GET = VariableToken.SET_GET.replace("\\", "");
    private final String SET_CONTAINS = VariableToken.SET_CONTAINS.replace("\\", "");

    private final String LIST_ISEMPTY = VariableToken.LIST_ISEMPTY.replace("\\", "");
    private final String LIST_SUM = VariableToken.LIST_SUM.replace("\\", "");
    private final String LIST_GET = VariableToken.LIST_GET.replace("\\", "");
    private final String LIST_CONTAINS = VariableToken.LIST_CONTAINS.replace("\\", "");

    private final String MAP_ISEMPTY = VariableToken.MAP_ISEMPTY.replace("\\", "");
    private final String MAP_GET = VariableToken.MAP_GET.replace("\\", "");
    private final String MAP_CONTAINS_KEY = VariableToken.MAP_CONTAINS.replace("\\", "");

    @Override
    public String start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        int count = accessCount(line, repositoryArray.size());
        if (count == -1) return null;
        line = line.substring(count);
        Map.Entry<String, Map<String, Object>> repository = getRepository(line, repositoryArray.get(count));
        if (repository == null) return null;

        String token = params[0];
        // 변수 가져오기 // :변수명_
        if (token.isEmpty()) return repository.getValue().get(line).toString();
        // 리스트, 셋, 맵
        // SET
        else if (SET_LIST.contains(repository.getKey())) {
            LinkedHashSet<Object> set = (LinkedHashSet<Object>) repository.getValue().get(line);
            if (token.equals(SET_ISEMPTY)) return set.isEmpty() ? TRUE : FALSE;
            else if (token.equals(SET_SUM)) return collectionSum(set, repository.getKey());
            else if (token.startsWith(SET_GET) && isInteger(token.substring(SET_GET.length())))
                return set.stream().toList().get(Integer.parseInt(token.substring(SET_GET.length()))).toString();
            else if (token.startsWith(SET_CONTAINS))
                return collectionCheck(set, repository.getKey(), token.substring(SET_CONTAINS.length()));
        }
        // LIST
        else if (LIST_LIST.contains(repository.getKey())) {
            LinkedList<Object> list = (LinkedList<Object>) repository.getValue().get(line);
            if (token.equals(LIST_ISEMPTY)) return list.isEmpty() ? TRUE : FALSE;
            else if (token.equals(LIST_SUM)) return collectionSum(list, repository.getKey());
            else if (token.startsWith(LIST_GET) && isInteger(token.substring(LIST_GET.length())))
                return list.get(Integer.parseInt(token.substring(LIST_GET.length()))).toString();
            else if (token.startsWith(LIST_CONTAINS))
                return collectionCheck(list, repository.getKey(), token.substring(LIST_CONTAINS.length()));
        }
        // MAP
        else if (MAP_LIST.contains(repository.getKey())) {
            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) repository.getValue().get(line);
            if (token.equals(MAP_ISEMPTY)) return map.isEmpty() ? TRUE : FALSE;
            else if (token.startsWith(MAP_GET) && map.containsKey(token.substring(MAP_GET.length())))
                return map.get(token.substring(MAP_GET.length())).toString();
            else if (token.startsWith(MAP_CONTAINS_KEY))
                return collectionCheck(map.keySet(), repository.getKey(), token.substring(MAP_CONTAINS_KEY.length()));
        }
        return null;
    }

    // 저장소 가져오는 로직
    private Map.Entry<String, Map<String, Object>> getRepository(
            String variableName, Map<String, Map<String, Object>> repository) {
        for (Map.Entry<String, Map<String, Object>> reps : repository.entrySet()) {
            if (reps.getValue().containsKey(variableName)) return reps;
        } return null;
    }
}
