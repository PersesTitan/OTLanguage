package etc.v4.variable;

import bin.apply.Repository;
import bin.define.item.MethodItemVoid;
import bin.exception.VariableException;
import work.v4.StartWork;

import static bin.token.LoopToken.METHOD;

public class MethodVoidTest extends StartWork {
    @Override
    public void start(String line, Object[] params, String loop) {
        int position = line.indexOf('[');
        String variableType = line.substring(0, position);
        Object startItem = Repository.repository.get(0).get(METHOD).get(variableType);

        if (startItem == null) throw new VariableException().noDefineMethod();
        else if (startItem instanceof MethodItemVoidTest methodItemTest) {
            if (methodItemTest.getParams() == 0) {
                if (!(params.length == 1 && params[0].toString().isEmpty()))
                    throw new VariableException().methodParamsError();
            } else if (methodItemTest.getParams() != params.length)
                throw new VariableException().methodParamsError();
            methodItemTest.start(params, Repository.repository);
        } else throw new VariableException().methodTypeMatch();
    }
}
