package work.v4;

import bin.exception.MatchException;

import java.io.Serializable;

public abstract class StartWork implements Serializable {
    private final WorkItem[] workItems;
    public StartWork(WorkItem...workItems) {
        this.workItems = workItems;
    }

    public abstract void start(String line, Object[] params, String loop);

    public Object[] paramsCheck(int size, String[] params) {
        if (params.length == 1 && params[0].isEmpty() && size == 0) return new Object[0];
        return check(size, params);
    }

    private Object[] check(int size, String[] params) {
        for (WorkItem item : workItems) {
            if (size == item.count()) return item.getParams(params);
        }
        throw new MatchException().grammarError();
    }
}
