package v4.work;

import bin.exception.MatchException;
import v4.bin.apply.sys.item.TypeMap;
import v4.item.ReturnItem;

import java.util.LinkedList;

public abstract class ReturnWork {
    private final ReturnItem[] workItems;
    public ReturnWork(ReturnItem...workItems) {
        this.workItems = workItems;
    }

    public abstract String start(String line, Object[] params, LinkedList<TypeMap> repositoryArray);

    public Object[] paramsCheck(int size, String[] params, LinkedList<TypeMap> repositoryArray) {
        if (params.length == 1 && params[0].isEmpty() && size == 0) return new Object[0];
        return check(size, params, repositoryArray);
    }

    private Object[] check(int size, String[] params, LinkedList<TypeMap> repositoryArray) {
        for (ReturnItem item : workItems) {
            if (size == item.types().length) return item.checkType(params, repositoryArray);
        }
        throw new MatchException().grammarError();
    }
}
