package v4.bin.origin.string.blank;

import bin.apply.sys.item.HpMap;
import bin.exception.VariableException;
import bin.token.MergeToken;
import v4.bin.apply.sys.item.Type;
import v4.bin.apply.sys.item.TypeMap;
import v4.bin.apply.sys.item.VariableTypeName;
import v4.item.StartItem;
import v4.work.StartWork;

import java.util.LinkedList;
import java.util.Objects;

public class TrimPoint extends StartWork implements MergeToken {
    // str: variableName
    public TrimPoint(StartItem... workItems) {
        super(workItems);
    }

    @Override
    public void start(String line, String loop, Object[] params, LinkedList<TypeMap> repositoryArray) {
        VariableTypeName variableTypeName = TypeMap.getTypeName(params[0].toString(), repositoryArray);
        final String variableType = variableTypeName.type();
        final String variableName = variableTypeName.name();
        HpMap map = variableTypeName.hpMap();

        if (Objects.equals(variableType, Type.STRING_VARIABLE.getType())) {
            String value = map.get(variableName).toString().strip();
            map.put(variableName, value);
        } else throw new VariableException().typeMatch();
    }
}
