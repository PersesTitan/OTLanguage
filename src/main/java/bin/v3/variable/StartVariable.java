package bin.v3.variable;

import bin.apply.Setting;
import bin.exception.VariableException;
import bin.orign.variable.both.ContainsTool;
import bin.token.MergeToken;
import bin.token.VariableToken;
import work.v3.StartWorkV3;

import java.util.*;

import static bin.token.VariableToken.*;

public class StartVariable extends StartWorkV3 implements MergeToken, ContainsTool {
    public StartVariable(int... counts) {
        super(counts);
    }

    private final String SET_CLEAR = VariableToken.SET_CLEAR.replace("\\", "");
    private final String SET_SORT = VariableToken.SET_SORT.replace("\\", "");
    private final String SET_DELETE = VariableToken.SET_DELETE.replace("\\", "");
    private final String SET_ADD = VariableToken.SET_ADD.replace("\\", "");

    private final String LIST_CLEAR = VariableToken.LIST_CLEAR.replace("\\", "");
    private final String LIST_SORT = VariableToken.LIST_SORT.replace("\\", "");
    private final String LIST_DELETE = VariableToken.LIST_DELETE.replace("\\", "");
    private final String LIST_ADD = VariableToken.LIST_ADD.replace("\\", "");

    private final String MAP_CLEAR = VariableToken.MAP_CLEAR.replace("\\", "");
    private final String MAP_DELETE = VariableToken.MAP_DELETE.replace("\\", "");
    private final String MAP_ADD = VariableToken.MAP_ADD.replace("\\", "");

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        String variableName = params[0];
        int count = accessCount(variableName, repositoryArray.size());
        if (count == -1) throw VariableException.localNoVariable();
        variableName = variableName.substring(count);
        Map.Entry<String, Map<String, Object>> repository = getRepository(variableName, repositoryArray.get(count));
        if (repository == null) throw VariableException.variableNameMatch();
        String token = params[1];

        if (token.startsWith(VARIABLE_PUT)) repository.getValue().put(variableName, token);
        // SET
        else if (SET_LIST.contains(repository.getKey())) {
            LinkedHashSet<Object> set = (LinkedHashSet<Object>) repository.getValue().get(variableName);
            if (token.equals(SET_CLEAR)) set.clear();
            else if (token.equals(SET_SORT)) sortSet(set);
            else if (token.startsWith(SET_DELETE)) delete(set, repository.getKey(), token.substring(SET_DELETE.length()));
            else if (token.startsWith(SET_ADD)) repository.getValue().put(variableName, token);
            else Setting.warringMessage(line);
        }
        // LIST
        else if (LIST_LIST.contains(repository.getKey())) {
            LinkedList<Object> list = (LinkedList<Object>) repository.getValue().get(variableName);
            if (token.equals(LIST_CLEAR)) list.clear();
            else if (token.equals(LIST_SORT)) sortList(repository.getKey(), repository.getValue().get(variableName));
            else if (token.startsWith(LIST_DELETE)) delete(list, repository.getKey(), token.substring(LIST_DELETE.length()));
            else if (token.startsWith(LIST_ADD)) repository.getValue().put(variableName, token);
            else Setting.warringMessage(line);
        }
        // MAP
        else if (MAP_LIST.contains(repository.getKey())) {
            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) repository.getValue().get(variableName);
            if (token.equals(MAP_CLEAR)) map.clear();
            else if (token.startsWith(MAP_DELETE)) map.remove(token.substring(MAP_DELETE.length()));
            else if (token.startsWith(MAP_ADD)) repository.getValue().put(variableName, token);
            else if (token.contains(MAP_ADD)) addMap(map, token);
            else Setting.warringMessage(line);
        }
        else Setting.warringMessage(line);
    }

    // 저장소 가져오는 로직
    private Map.Entry<String, Map<String, Object>> getRepository(
            String variableName, Map<String, Map<String, Object>> repository) {
        for (Map.Entry<String, Map<String, Object>> reps : repository.entrySet()) {
            if (reps.getValue().containsKey(variableName)) return reps;
        } return null;
    }
}
