package bin.string.list;

import bin.exception.VariableException;
import bin.orign.variable.GetList;
import bin.token.MergeToken;

import java.util.LinkedList;
import java.util.Map;

import static bin.token.VariableToken.*;

public class AggregationList implements MergeToken, GetList {
    public LinkedList<Object> getList2(String list, String variableType,
                                       LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (list.startsWith("[") && list.endsWith("]")) {
            LinkedList<?> list1 = switch (variableType) {
                case LIST_INTEGER -> setIntegerList(new LinkedList<>(), list);
                case LIST_LONG -> setLongList(new LinkedList<>(), list);
                case LIST_BOOLEAN -> setBoolList(new LinkedList<>(), list);
                case LIST_STRING -> setStringList(new LinkedList<>(), list);
                case LIST_CHARACTER -> setCharacterList(new LinkedList<>(), list);
                case LIST_FLOAT -> setFlotList(new LinkedList<>(), list);
                case LIST_DOUBLE -> setDoubleList(new LinkedList<>(), list);
                default -> throw new VariableException().typeMatch();
            };
            return (LinkedList<Object>) list1;
        } else return getList1(list, variableType, repositoryArray);
    }

    public LinkedList<Object> getList1(String list, String variableType,
                                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        int accessCount = accessCount(list, repositoryArray.size());
        if (accessCount == -1) throw new VariableException().localNoVariable();
        list = list.substring(accessCount);
        var repository = repositoryArray.get(accessCount).get(variableType);
        if (repository == null) throw new VariableException().noDefine();
        var rep = repository.get(list);
        if (rep == null) throw new VariableException().variableNameMatch();
        return switch (variableType) {
            case LIST_INTEGER, LIST_LONG, LIST_BOOLEAN, LIST_STRING, LIST_CHARACTER, LIST_FLOAT, LIST_DOUBLE -> (LinkedList<Object>) rep;
            default -> throw new VariableException().typeMatch();
        };
    }
}
