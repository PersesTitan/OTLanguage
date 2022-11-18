package bin.define.method;

import bin.define.item.MethodItemVoid;
import bin.exception.VariableException;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.token.LoopToken.METHOD;

public class MethodVoid extends StartWorkV3 {
    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        int position = line.indexOf('[');
        String variableType = line.substring(0, position);
        Object startItem = repositoryArray.get(0).get(METHOD).get(variableType);

        if (startItem == null) throw new VariableException().noDefineMethod();
        else if (startItem instanceof MethodItemVoid methodItemTest) {
            if (methodItemTest.getParams() == 0) {
                if (!(params.length == 1 && params[0].isEmpty())) throw new VariableException().methodParamsError();
            } else if (methodItemTest.getParams() != params.length)
                throw new VariableException().methodParamsError();
            methodItemTest.start(params, repositoryArray);
        } else throw new VariableException().methodTypeMatch();
    }
}
