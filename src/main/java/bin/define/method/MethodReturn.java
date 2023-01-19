package bin.define.method;

import bin.define.item.MethodItemReturn;
import bin.exception.VariableException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import work.v3.ReturnWorkV3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

import static bin.token.LoopToken.METHOD;
import static bin.token.MergeToken.access;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MethodReturn extends ReturnWorkV3 {
    private static MethodReturn methodReturn;
    public static MethodReturn getInstance() {
        if (methodReturn == null) {
            synchronized (MethodReturn.class) {
                methodReturn = new MethodReturn();
            }
        }
        return methodReturn;
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        int position = line.indexOf('[');
        String variableType = line.substring(0, position);

        int count = access(variableType, repositoryArray.size());
        if (count == -1) return null;
        variableType = variableType.substring(count);

        Object startItem = repositoryArray.get(count).get(METHOD).get(variableType);

        if (startItem == null) throw new VariableException().noDefineMethod();
        else if (startItem instanceof MethodItemReturn methodItemReturn) {
            if (methodItemReturn.getParams() == 0) {
                if (!(params.length == 1 && params[0].isEmpty())) throw new VariableException().methodParamsError();
            } else if (methodItemReturn.getParams() != params.length)
                throw new VariableException().methodParamsError();
            return methodItemReturn.start(params, repositoryArray);
        } else throw new VariableException().methodTypeMatch();
    }
}
