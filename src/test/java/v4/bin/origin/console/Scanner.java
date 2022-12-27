package v4.bin.origin.console;

import v4.bin.apply.sys.item.TypeMap;
import v4.item.ReturnItem;
import v4.work.ReturnWork;

import java.util.LinkedList;

import static bin.apply.Controller.scanner;

public class Scanner extends ReturnWork {
    public Scanner(ReturnItem... workItems) {
        super(workItems);
    }

    @Override
    public String start(String line, Object[] params, LinkedList<TypeMap> repositoryArray) {
        return scanner();
    }
}
