package bin.orign;

import bin.exception.VariableException;
import bin.orign.variable.GetList;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import static bin.check.VariableCheck.listCheck;
import static bin.token.VariableToken.LIST_LIST;
import static bin.token.VariableToken.SET_LIST;

public interface GetOnlyChangeList extends GetList {
    // value : list 변수명
    default LinkedList<String> getList(String value,
                                       Map<String, Map<String, Object>> repositoryArray) {
        if (listCheck(value)) {     // List, Set 변수명
            for (var list : LIST_LIST) {
                var repository = repositoryArray.get(list);
                if (repository.containsKey(value)) {
                    return ((LinkedList<Object>) repository.get(value))
                            .stream()
                            .map(Object::toString)
                            .collect(Collectors.toCollection(LinkedList::new));
                }
            }
            for (var set : SET_LIST) {
                var repository = repositoryArray.get(set);
                if (repository.containsKey(value)) {
                    return ((LinkedHashSet<Object>) repository.get(value))
                            .stream()
                            .map(Object::toString)
                            .collect(Collectors.toCollection(LinkedList::new));
                }
            }
        } else return setStringList(new LinkedList<>(), value);
        // [1, 2, 3, 4, 5]
        throw new VariableException().typeMatch();
    }
}
