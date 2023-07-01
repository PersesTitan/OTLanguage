package bin.apply.repository.method;

import bin.apply.Repository;
import bin.exception.VariableException;
import bin.parser.param.ParamToken;
import bin.token.check.CheckToken;
import bin.token.KlassToken;
import work.CreateWork;
import work.MethodWork;

import java.util.HashMap;
import java.util.function.*;

public class KlassMap extends HashMap<String, MethodMap> implements FunctionTool {
    public void put(String klass, String method, MethodWork work) {
        if (super.containsKey(klass)) super.get(klass).put(method, work);
        else super.put(klass, new MethodMap(method, work));
    }

    public boolean contains(String klass, String method, MethodWork work) {
        return super.containsKey(klass) && super.get(klass).contains(method, work);
    }

    public boolean hasStatic(String name) {
        String type = KlassToken.DEFAULT_KLASS.get();
        return super.containsKey(type) && super.get(type).containsKey(name);
    }

    public MethodWork get(String klass, String method, int size) {
        if (klass == null) throw VariableException.NO_RETURN_TYPE.getThrow(null);
        if (super.containsKey(klass)) return super.get(klass).get(method, size);
        else throw VariableException.NO_CLASS.getThrow(klass);
    }

    public MethodWork get(String replace, String klass, String method, int size) {
        MethodWork work = this.get(klass, method, size);
        if (replace == null) {
            if (work.getREPLACE() == null) return work;
            else throw VariableException.VALUE_ERROR.getThrow(null);
        } else {
            if (CheckToken.isKlass(replace)) {
                CreateWork<?> createWork = Repository.createWorks.get(replace);
                if (createWork == work.getREPLACE()) return work;
                else throw VariableException.VALUE_ERROR.getThrow(replace);
            } else throw VariableException.NO_DEFINE_TYPE.getThrow(replace);
        }
    }

    ////////////////////////////////////////////////////////////////////////
    public void addStatic(String klass, String method, NoConsumer consumer) {
        this.put(klass, method, new MethodWork(null, klass, true) {
            @Override
            protected Object methodItem(Object klassItem, Object[] params) {return null;}
            @Override
            public Object method(Object klassItem, ParamToken[] param) {
                getKlassItem(klassItem);
                casting(param);
                consumer.accept();
                return null;
            }
        });
    }

    public <R> void addStatic(String klass, String method, Supplier<R> supplier, String r) {
        this.put(klass, method, new MethodWork(r, klass, true) {
            @Override
            protected Object methodItem(Object klassItem, Object[] params) {return null;}
            @Override
            public Object method(Object klassItem, ParamToken[] param) {
                getKlassItem(klassItem);
                casting(param);
                return supplier.get();
            }
        });
    }

    public <R, A> void addStatic(String klass, String method, String a, BiSupplier<R, A> supplier, String r) {
        this.put(klass, method, new MethodWork(r, klass, true, a) {
            @Override
            protected Object methodItem(Object klassItem, Object[] params) {return null;}
            @Override
            public Object method(Object klassItem, ParamToken[] param) {
                getKlassItem(klassItem);
                return supplier.get((A) casting(param)[0]);
            }
        });
    }

    public <R, A, B> void addStatic(String klass, String method, String a, String b, BiFunction<A, B, R> function, String r) {
        this.put(klass, method, new MethodWork(r, klass, true, a, b) {
            @Override
            protected Object methodItem(Object klassItem, Object[] params) {return null;}
            @Override
            public Object method(Object klassItem, ParamToken[] param) {
                return function.apply((A) getKlassItem(klassItem), (B) casting(param)[0]);
            }
        });
    }

    // consumer
    public <R> void add(String klass, String method, Consumer<R> consumer) {
        this.put(klass, method, new MethodWork(null, klass, false) {
            @Override
            protected Object methodItem(Object klassItem, Object[] params) {return null;}
            @Override
            public Object method(Object klassItem, ParamToken[] param) {
                casting(param);
                consumer.accept((R) getKlassItem(klassItem));
                return null;
            }
        });
    }

    public <R, A> void add(String klass, String method, String a, BiConsumer<R, A> consumer) {
        this.put(klass, method, new MethodWork(null, klass, false, a) {
            @Override
            protected Object methodItem(Object klassItem, Object[] params) {return null;}
            @Override
            public Object method(Object klassItem, ParamToken[] param) {
                consumer.accept((R) getKlassItem(klassItem), (A) casting(param)[0]);
                return null;
            }
        });
    }

    public <R, A, B> void add(String klass, String method, String a, String b, CiConsumer<R, A, B> consumer) {
        this.put(klass, method, new MethodWork(null, klass, false, a, b) {
            @Override
            protected Object methodItem(Object klassItem, Object[] params) {return null;}
            @Override
            public Object method(Object klassItem, ParamToken[] param) {
                Object[] params = casting(param);
                consumer.accept((R) getKlassItem(klassItem), (A) params[0], (B) params[1]);
                return null;
            }
        });
    }

    // function
    public <K, R> void add(String klass, String method, Function<K, R> function, String r) {
        this.put(klass, method, new MethodWork(r, klass, false) {
            @Override
            protected Object methodItem(Object klassItem, Object[] params) {return null;}
            @Override
            public Object method(Object klassItem, ParamToken[] param) {
                casting(param);
                return function.apply((K) getKlassItem(klassItem));
            }
        });
    }

    public <K, A, R> void add(String klass, String method, String a, BiFunction<K, A, R> function, String r) {
        this.put(klass, method, new MethodWork(r, klass, true, a) {
            @Override
            protected Object methodItem(Object klassItem, Object[] params) {return null;}
            @Override
            public Object method(Object klassItem, ParamToken[] param) {
                return function.apply((K) getKlassItem(klassItem), (A) casting(param)[0]);
            }
        });
    }

    public <K, A, B, R> void add(String klass, String method, String a, String b, CiFunction<K, A, B, R> function, String r) {
        this.put(klass, method, new MethodWork(r, klass, false, a, b) {
            @Override
            protected Object methodItem(Object klassItem, Object[] params) {return null;}
            @Override
            public Object method(Object klassItem, ParamToken[] param) {
                Object[] params = casting(param);
                return function.apply((K) getKlassItem(klassItem), (A) params[0], (B) params[1]);
            }
        });
    }
}
