package bin.define.method;

import bin.define.item.MethodItemReturn;
import bin.exception.VariableException;
import work.v3.ReturnWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.token.LoopToken.METHOD;

public class MethodReturn extends ReturnWorkV3 {
    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        int position = line.indexOf('[');
        String variableType = line.substring(0, position);
        Object startItem = repositoryArray.get(0).get(METHOD).get(variableType);

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
