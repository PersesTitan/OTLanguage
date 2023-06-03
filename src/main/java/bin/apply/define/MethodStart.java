package bin.apply.define;

import bin.apply.Repository;
import bin.apply.item.CodesItem;
import bin.apply.item.ParamItem;
import bin.apply.repository.variable.TypeMap;
import work.StartWork;

public class MethodStart extends StartWork {
    private final ParamItem[] PARAMS;
    private final CodesItem CODE;

    public MethodStart(String TYPE, boolean IS_STATIC, ParamItem[] PARAMS, CodesItem CODE) {
        super(TYPE, IS_STATIC, ParamItem.getType(PARAMS));
        this.PARAMS = PARAMS;
        this.CODE = CODE;
    }

    @Override
    protected void startItem(Object klassItem, Object[] params) {
        int size = super.getSIZE();
        TypeMap repository = new TypeMap() {{
            for (int i = 0; i < size; i++) create(PARAMS[i], params[i]);
        }};

        if (super.isSTATIC()) {
            try {
                Repository.repositoryArray.addFirst(repository);
                CODE.start();
            } finally {
                Repository.repositoryArray.removeFirst();
            }
        } else {
            try {
                ((KlassItem) klassItem).addRepository();
                Repository.repositoryArray.addFirst(repository);
                CODE.start();
            } finally {
                Repository.repositoryArray.removeFirst();
                Repository.repositoryArray.removeFirst();
            }
        }
    }
}
