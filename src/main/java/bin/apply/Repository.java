package bin.apply;

import bin.apply.create.CreateOtherList;
import bin.apply.create.CreateOtherMap;
import bin.apply.create.CreateOtherSet;
import bin.apply.mode.TypeMode;
import bin.apply.repository.CodesMap;
import bin.apply.repository.create.CreateMap;
import bin.apply.repository.loop.LoopMap;
import bin.apply.repository.method.KlassMap;
import bin.apply.repository.variable.AccessList;
import bin.apply.work.other.ForEachOtherList;
import bin.apply.work.other.ForEachOtherMap;
import bin.apply.work.other.ForEachOtherSet;
import bin.token.work.StringToken;
import bin.token.work.SystemToken;
import bin.apply.work.system.CreateSystem;
import bin.apply.work.error.CreateThrow;
import bin.token.work.ThrowToken;
import bin.apply.work.loop.ForEachList;
import bin.apply.work.loop.ForEachMap;
import bin.apply.work.loop.ForEachSet;
import bin.apply.work.loop.ForEachString;
import bin.apply.work.system.*;

import static bin.token.KlassToken.*;
import static work.ResetWork.*;

public interface Repository {
    AccessList repositoryArray = new AccessList();
    CreateMap createWorks = new CreateMap() {{
        create(SYSTEM, new CreateSystem());
        create(ThrowToken.THROW, new CreateThrow());
        create(TypeMode.INTEGER, Integer.class);
        create(TypeMode.LONG, Long.class);
        create(TypeMode.BOOLEAN, Boolean.class);
        create(TypeMode.STRING, String.class);
        create(TypeMode.CHARACTER, Character.class);
        create(TypeMode.FLOAT, Float.class);
        create(TypeMode.DOUBLE, Double.class);
        put(LIST, new CreateOtherList());
        put(SET, new CreateOtherSet());
        put(MAP, new CreateOtherMap());
    }};
    KlassMap methodWorks = new KlassMap() {{
        SystemToken.reset(this);
        ThrowToken.reset(this);
        StringToken.reset(this);
    }};

    LoopMap loopWorks = new LoopMap() {{
        put(SYSTEM, SystemToken.WHILE, new While());
        put(s, SystemToken.FOR_EACH, new ForEachString());
        put(SET, SystemToken.FOR_EACH, new ForEachOtherSet());
        put(LIST, SystemToken.FOR_EACH, new ForEachOtherList());
        put(MAP, SystemToken.FOR_EACH, new ForEachOtherMap());
        for (TypeMode mode : TypeMode.values()) {
            put(mode.getSet(), SystemToken.FOR_EACH, new ForEachSet(mode));
            put(mode.getList(), SystemToken.FOR_EACH, new ForEachList(mode));
            put(mode.getMap(), SystemToken.FOR_EACH, new ForEachMap(mode));
        }
    }};
    CodesMap code = new CodesMap();
}
