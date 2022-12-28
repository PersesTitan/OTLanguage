package v4.bin.origin.console;

import v4.bin.apply.sys.item.TypeMap;
import v4.item.StartItem;
import v4.work.StartWork;

import java.util.LinkedList;

public class Print extends StartWork {
    public Print(StartItem... workItems) {
        super(workItems);
    }

    @Override
    public void start(String line, String loop, Object[] params, LinkedList<TypeMap> repositoryArray) {
        System.out.print(params[0]);
    }
}
