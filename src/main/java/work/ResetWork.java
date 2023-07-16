package work;

import bin.apply.Repository;
import bin.apply.item.CodeItem;
import bin.apply.item.Item;
import bin.exception.SystemException;
import bin.token.KlassToken;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface ResetWork {
    String s = KlassToken.STRING_VARIABLE;
    String b = KlassToken.BOOL_VARIABLE;
    String i = KlassToken.INT_VARIABLE;
    String f = KlassToken.FLOAT_VARIABLE;
    String l = KlassToken.LONG_VARIABLE;
    String c = KlassToken.CHARACTER_VARIABLE;
    String d = KlassToken.DOUBLE_VARIABLE;

    String li = KlassToken.LIST_INTEGER;
    String ll = KlassToken.LIST_LONG;
    String lb = KlassToken.LIST_BOOLEAN;
    String ls = KlassToken.LIST_STRING;
    String lc = KlassToken.LIST_CHARACTER;
    String lf = KlassToken.LIST_FLOAT;
    String ld = KlassToken.LIST_DOUBLE;

    String si = KlassToken.SET_INTEGER;
    String sl = KlassToken.SET_LONG;
    String sb = KlassToken.SET_BOOLEAN;
    String ss = KlassToken.SET_STRING;
    String sc = KlassToken.SET_CHARACTER;
    String sf = KlassToken.SET_FLOAT;
    String sd = KlassToken.SET_DOUBLE;

    String mi = KlassToken.MAP_INTEGER;
    String ml = KlassToken.MAP_LONG;
    String mb = KlassToken.MAP_BOOLEAN;
    String ms = KlassToken.MAP_STRING;
    String mc = KlassToken.MAP_CHARACTER;
    String mf = KlassToken.MAP_FLOAT;
    String md = KlassToken.MAP_DOUBLE;

    String version();
    void reset();

    default <T extends Item> void create(String name, Class<T> klass, Function<Object[], T> function, String... types) {
        Repository.createWorks.put(name, new CreateWork<>(klass, types) {
            @Override
            protected T createItem(Object[] params) {
                return function.apply(params);
            }
        });
    }

    default <T, R> void add(String klass, String method, BiFunction<T, Object[], R> function, String r, String... types) {
        Repository.methodWorks.put(klass, method, new MethodWork(r, klass, false, types) {
            @Override
            protected Object methodItem(Object klassItem, Object[] params) {
                return function.apply((T) klassItem, params);
            }
        });
    }

    default <T> void add(String klass, String method, BiConsumer<T, Object[]> consumer, String... types) {
        if (types.length == 0) throw SystemException.SYSTEM_ERROR.getThrow(types.length);
        Repository.methodWorks.put(klass, method, new StartWork(klass, false, types) {
            @Override
            protected void startItem(Object klassItem, Object[] params) {
                consumer.accept((T) klassItem, params);
            }
        });
    }

    private boolean checkModule(String module) {
        try {
            Class.forName(String.format("cos.%s.Reset", module));
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    default void checkModuleError(String... modules) {
        List<String> list = new ArrayList<>();
        for (String module : modules) {
            if (!(checkModule(module) && CodeItem.contains(module))) list.add(module);
        }
        if (!list.isEmpty()) throw SystemException.IMPORT_ERROR.getThrow(String.join(", ", list));
    }
}
