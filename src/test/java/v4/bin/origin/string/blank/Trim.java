package v4.bin.origin.string.blank;

import v4.bin.apply.sys.item.TypeMap;
import v4.item.ReturnItem;
import v4.work.ReturnWork;

import java.util.LinkedList;

public class Trim extends ReturnWork {
    // 1: str
    public Trim(ReturnItem... workItems) {
        super(workItems);
    }

    @Override
    public String start(String line, Object[] params, LinkedList<TypeMap> repositoryArray) {
        return params[0].toString().strip();
    }
}
