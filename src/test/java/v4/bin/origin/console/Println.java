package v4.bin.origin.console;

import v4.bin.apply.sys.item.TypeMap;
import v4.item.StartItem;
import v4.work.StartWork;

import java.util.LinkedList;

public class Println extends StartWork {
    public Println(StartItem... workItems) {
        super(workItems);
    }

    @Override
    public void start(String line, String loop, Object[] params, LinkedList<TypeMap> repositoryArray) {
        System.out.println(params[0]);
    }
}
