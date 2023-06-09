package bin.token.work;

import bin.apply.repository.method.KlassMap;
import bin.apply.work.error.ThrowItem;

import static work.ResetWork.s;

public interface ThrowToken {
    String THROW = "ㄸㄹㄸ";

    String START = "ㅅㅌㅅ";
    String ADD_SUB = "<<ㅁㅆㅁ";

    String SET_BIG = "<ㅁㅈㅁ";
    String SET_MES = "<ㅁㅅㅁ";
    String SET_SUB = "<ㅁㅆㅁ";

    String GET_BIG = ">ㅁㅈㅁ";
    String GET_MES = ">ㅁㅅㅁ";
    String GET_SUB = ">ㅁㅆㅁ";

    static void reset(KlassMap work) {
        work.add(THROW, START, s, ThrowItem::createError);
        work.add(THROW, ADD_SUB, s, ThrowItem::addSub);
        work.add(THROW, GET_BIG, ThrowItem::getBig, s);
        work.add(THROW, GET_MES, ThrowItem::getMes, s);
        work.add(THROW, GET_SUB, ThrowItem::getSub, s);
        work.add(THROW, SET_BIG, s, ThrowItem::setBig);
        work.add(THROW, SET_MES, s, ThrowItem::setMes);
        work.add(THROW, SET_SUB, s, ThrowItem::setSub);
    }
}
