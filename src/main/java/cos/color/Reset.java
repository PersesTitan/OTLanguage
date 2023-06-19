package cos.color;

import bin.apply.Repository;
import work.ResetWork;

public class Reset implements ResetWork, Repository, ColorToken {
    @Override
    public String version() {
        return "1.0.0";
    }

    @Override
    public void reset() {
        create(COLOR, ColorItem.class, v -> new ColorItem((int) v[0]), i);
        create(COLOR, ColorItem.class, v -> new ColorItem((int) v[0], (int) v[1], (int) v[2]), i, i, i);
        create(COLOR, ColorItem.class, v -> new ColorItem((int) v[0], (int) v[1], (int) v[2], (int) v[3]), i, i, i, i);

        methodWorks.add(COLOR, GET_COLOR, ColorItem::get, li);
        methodWorks.add(COLOR, GET_R, ColorItem::getR, i);
        methodWorks.add(COLOR, GET_G, ColorItem::getG, i);
        methodWorks.add(COLOR, GET_B, ColorItem::getB, i);
        methodWorks.add(COLOR, GET_A, ColorItem::getA, i);

        methodWorks.add(COLOR, SET_R, i, ColorItem::setR);
        methodWorks.add(COLOR, SET_G, i, ColorItem::setG);
        methodWorks.add(COLOR, SET_B, i, ColorItem::setB);
        methodWorks.add(COLOR, SET_A, i, ColorItem::setA);
        this.<ColorItem>add(COLOR, SET_COLOR, (a, b) -> a.set((int)b[0], (int)b[1], (int)b[2], (int)b[3]), i, i, i, i);
    }
}
