package bin.orign;

import bin.exception.VariableException;
import bin.orign.variable.list.get.GetList;
import bin.token.VariableToken;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import static bin.check.VariableCheck.listCheck;

public class GetOnlyChangeList implements GetList, VariableToken {
    public LinkedList<String> getList(String value,
                                       Map<String, Map<String, Object>> repositoryArray) {
        if (listCheck(value)) {     // List, Set 변수명
            for (var list : LIST_LIST) {
                var repository = repositoryArray.get(list);
                if (repository.containsKey(value)) {
                    LinkedList<Object> objects = (LinkedList<Object>) repository.get(value);
                    return objects
                            .stream()
                            .map(Object::toString)
                            .collect(Collectors.toCollection(LinkedList::new));
                }
            }
            for (var set : SET_LIST) {
                var repository = repositoryArray.get(set);
                if (repository.containsKey(value)) {
                    LinkedHashSet<Object> objects = (LinkedHashSet<Object>) repository.get(value);
                    return objects
                            .stream()
                            .map(Object::toString)
                            .collect(Collectors.toCollection(LinkedList::new));
                }
            }
        } else return getStringList(value);
        // [1, 2, 3, 4, 5]
        throw VariableException.typeMatch();
    }
}
