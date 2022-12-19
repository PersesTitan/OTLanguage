package v4.bin.origin.string;

import v4.bin.apply.sys.item.Type;
import v4.bin.apply.sys.item.TypeMap;
import v4.item.ReturnItem;
import v4.work.ReturnWork;

import java.util.LinkedList;

public class Repeat extends ReturnWork {
    // str, int
    public Repeat(ReturnItem... workItems) {
        super(workItems);
    }

    @Override
    public String start(String line, Object[] params, LinkedList<TypeMap> repositoryArray) {
        return params[0].toString().repeat(Type.casting(Integer.class, params[1]));
    }
}
