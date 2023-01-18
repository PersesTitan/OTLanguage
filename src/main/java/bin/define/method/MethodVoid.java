package bin.define.method;

import bin.define.item.MethodItemVoid;
import bin.exception.VariableException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.token.LoopToken.METHOD;
import static bin.token.MergeToken.access;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MethodVoid extends StartWorkV3 {
    private static MethodVoid methodVoid;
    public static MethodVoid getInstance() {
        if (methodVoid == null) {
            synchronized (MethodVoid.class) {
                methodVoid = new MethodVoid();
            }
        }
        return methodVoid;
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        int position = line.indexOf('[');
        String variableType = line.substring(0, position);

        int count = access(variableType, repositoryArray.size());
        if (count == -1) throw new VariableException().localNoVariable();
        variableType = variableType.substring(count);

        Object startItem = repositoryArray.get(count).get(METHOD).get(variableType);

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
