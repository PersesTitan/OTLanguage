package cos.poison;

import bin.apply.Repository;
import cos.poison.tool.PoisonCountMap;
import cos.poison.type.MethodType;
import work.ResetWork;

public class Reset implements ResetWork, PoisonToken, Repository {
    @Override
    public String version() {
        return "1.0.0";
    }

    @Override
    public void reset() {
        checkModuleError("file");

        create(POISON, PoisonItem.class, v -> new PoisonItem());

        loopWorks.put(POISON, POST, new PoisonCountMap(MethodType.POST));
        loopWorks.put(POISON, GET, new PoisonCountMap(MethodType.GET));
        loopWorks.put(POISON, PUT, new PoisonCountMap(MethodType.PUT));
        loopWorks.put(POISON, PATCH, new PoisonCountMap(MethodType.PATCH));
        loopWorks.put(POISON, DELETE, new PoisonCountMap(MethodType.DELETE));

        methodWorks.add(POISON, CREATE, PoisonItem::create);
        methodWorks.add(POISON, START, PoisonItem::start);
        methodWorks.add(POISON, STOP, PoisonItem::stop);
        methodWorks.add(POISON, SET_HOST, s, PoisonItem::setHost);
        methodWorks.add(POISON, SET_PORT, i, PoisonItem::setPort);
        methodWorks.add(POISON, REDIRECT, s, PoisonItem::redirect);

        methodWorks.add(POISON, IS_COOKIE, s, PoisonItem::isCookie, b);
        methodWorks.add(POISON, GET_COOKIE, s, PoisonItem::getCookie, s);
        methodWorks.add(POISON, DELETE_COOKIE, s, s, PoisonItem::deleteCookie);

        methodWorks.<PoisonItem, String, String>add(POISON, SET_COOKIE, s, s, PoisonItem::setCookie);
        this.<PoisonItem>add(POISON, SET_COOKIE,
                (p, v) -> p.setCookie((String)v[0],(String)v[1],(String)v[2]), s, s, s);
        this.<PoisonItem>add(POISON, SET_COOKIE,
                (p, v) -> p.setCookie((String)v[0],(String)v[1],(String)v[2],(int)v[3]), s, s, s, i);
    }
}
