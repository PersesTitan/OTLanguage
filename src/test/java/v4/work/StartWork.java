package v4.work;

import bin.exception.MatchException;
import v4.bin.apply.sys.item.TypeMap;
import v4.item.StartItem;

import java.util.LinkedList;

public abstract class StartWork {
    private final StartItem[] workItems;

    public StartWork(StartItem...workItems) {
        this.workItems = workItems;
    }
    public abstract void start(String line, String loop, Object[] params, LinkedList<TypeMap> repositoryArray);

    public Object[] paramsCheck(int size, String[] params, LinkedList<TypeMap> repositoryArray) {
        if (params.length == 1 && params[0].isEmpty() && size == 0) return new Object[0];
        return check(size, params, repositoryArray);
    }

    public boolean isLoop() {
        return workItems[0].isLoop();
    }

    private Object[] check(int size, String[] params, LinkedList<TypeMap> repositoryArray) {
        for (StartItem item : workItems) {
            if (size == item.types().length) return item.checkType(params, repositoryArray);
        }
        throw new MatchException().grammarError();
    }
}
