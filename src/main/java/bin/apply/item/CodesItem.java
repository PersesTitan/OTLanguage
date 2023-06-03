package bin.apply.item;

import bin.exception.MatchException;

public record CodesItem(int START, int END, CodeItem CODE) {
    public CodesItem {
        if (++START > END) throw MatchException.GRAMMAR_ERROR.getThrow(null);
    }

    public void start() {
        CODE.start(START, END);
    }
}
