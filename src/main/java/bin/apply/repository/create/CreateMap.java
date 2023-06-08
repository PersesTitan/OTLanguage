package bin.apply.repository.create;

import bin.apply.mode.TypeMode;
import bin.exception.VariableException;
import bin.token.KlassToken;
import bin.variable.custom.CustomList;
import bin.variable.custom.CustomMap;
import bin.variable.custom.CustomSet;
import work.CreateWork;

import java.io.Serial;
import java.util.HashMap;

public class CreateMap extends HashMap<String, CountMap> {
    @Serial
    private static final long serialVersionUID = -8950753384234554518L;

    protected void create(String klass, CreateWork<?> work) {
        super.put(klass, new CountMap(work));
    }

    private <T> void origin(String type, Class<T> klass) {
        this.create(type, new CreateWork<>(klass, type) {
            @Override
            protected T createItem(Object[] params) { return klass.cast(params[0]); }
            @Override
            public void checkParams(String[] params) {}
        });
    }

    protected <T> void create(String type, Class<T> klass) {
        this.origin(type, klass);
    }

    protected <T> void create(TypeMode mode, Class<T> klass) {
        this.origin(mode.getType(), klass);
        this.origin(mode.getSet(), (Class<CustomSet<T>>) new CustomSet<>(mode).getClass());
        this.origin(mode.getList(), (Class<CustomList<T>>) new CustomList<>(mode).getClass());
        this.origin(mode.getMap(), (Class<CustomMap<String, T>>) new CustomMap<>(TypeMode.STRING, mode).getClass());
    }

    public void put(String klass, CreateWork<?> work) {
        if (KlassToken.RESERVE.contains(klass)) throw VariableException.NO_DEFINE_NAME.getThrow(klass);
        if (super.containsKey(klass)) super.get(klass).put(work);
        else super.put(klass, new CountMap(work));
    }

    public CreateWork<?> get(String key) {
        if (super.containsKey(key)) return super.get(key).get();
        else throw VariableException.TYPE_ERROR.getThrow(key);
    }
}
