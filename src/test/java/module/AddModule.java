package module;

import v4.bin.apply.sys.item.Type;
import v4.bin.origin.console.Print;
import v4.item.StartItem;
import v4.work.StartWork;

import static bin.apply.sys.item.SystemSetting.*;

public class AddModule {
    public static void main(String[] args) {
        new AddModule();
    }

    private AddModule() {

    }

    private final String STRING = Type.STRING_VARIABLE.getType();
    private final String INT = Type.INT_VARIABLE.getType();
    private final String LONG = Type.LONG_VARIABLE.getType();
    private final String BOOL = Type.BOOL_VARIABLE.getType();
    private final String CHARACTER = Type.CHARACTER_VARIABLE.getType();
    private final String FLOAT = Type.FLOAT_VARIABLE.getType();
    private final String DOUBLE = Type.DOUBLE_VARIABLE.getType();
    private final String SET_INTEGER = Type.SET_INTEGER.getType();
    private final String SET_LONG = Type.SET_LONG.getType();
    private final String SET_BOOLEAN = Type.SET_BOOLEAN.getType();
    private final String SET_STRING = Type.SET_STRING.getType();
    private final String SET_CHARACTER = Type.SET_CHARACTER.getType();
    private final String SET_FLOAT = Type.SET_FLOAT.getType();
    private final String SET_DOUBLE = Type.SET_DOUBLE.getType();
    private final String LIST_INTEGER = Type.LIST_INTEGER.getType();
    private final String LIST_LONG = Type.LIST_LONG.getType();
    private final String LIST_BOOLEAN = Type.LIST_BOOLEAN.getType();
    private final String LIST_STRING = Type.LIST_STRING.getType();
    private final String LIST_CHARACTER = Type.LIST_CHARACTER.getType();
    private final String LIST_FLOAT = Type.LIST_FLOAT.getType();
    private final String LIST_DOUBLE = Type.LIST_DOUBLE.getType();
    private final String MAP_INTEGER = Type.MAP_INTEGER.getType();
    private final String MAP_LONG = Type.MAP_LONG.getType();
    private final String MAP_BOOLEAN = Type.MAP_BOOLEAN.getType();
    private final String MAP_STRING = Type.MAP_STRING.getType();
    private final String MAP_CHARACTER = Type.MAP_CHARACTER.getType();
    private final String MAP_FLOAT = Type.MAP_FLOAT.getType();
    private final String MAP_DOUBLE = Type.MAP_DOUBLE.getType();

    private void start1() {
        StartItem str = new StartItem(false, STRING);
        StartWork print = new Print(new );
        priorityCreateStartWorks()
        createStartWorks()
//        createReturnWorks()
    }
}
