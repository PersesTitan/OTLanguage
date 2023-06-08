package bin.apply.work.loop;

import bin.apply.repository.function.OSConsumer;
import bin.token.EditToken;
import bin.token.KlassToken;
import work.LoopWork;

public class ForEachString extends LoopWork {
    public ForEachString() {
        super(KlassToken.STRING_VARIABLE, false,
                new String[]{KlassToken.CHARACTER_VARIABLE},
                new String[0]
        );
    }

    @Override
    protected void loopItem(Object klass, Object[] params, OSConsumer consumer) {
        for (char c : EditToken.toString(getKlassItem(klass)).toCharArray()) consumer.accept(c);
    }
}
