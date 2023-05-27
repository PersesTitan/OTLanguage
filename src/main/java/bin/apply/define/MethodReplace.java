package bin.apply.define;

import bin.apply.Repository;
import bin.apply.item.CodesItem;
import bin.apply.item.ParamItem;
import bin.apply.repository.variable.TypeMap;
import bin.parser.param.ParamToken;
import bin.parser.param.ParserParamTool;
import work.MethodWork;

public class MethodReplace extends MethodWork {
    private final ParamItem[] PARAMS;
    private final ParamToken TOKEN;
    private final CodesItem CODE;

    public MethodReplace(String TYPE, boolean IS_STATIC, CodesItem CODE, ParamItem[] PARAMS,
                         String REPLACE_TYPE, String REPLACE_NAME) {
        super(REPLACE_TYPE, TYPE, IS_STATIC, ParamItem.getType(PARAMS));
        this.TOKEN = ParserParamTool.start(REPLACE_TYPE, REPLACE_NAME);
        this.PARAMS = PARAMS;
        this.CODE = CODE;
    }

    @Override
    protected Object methodItem(Object klassItem, Object[] params) {
        int size = super.getSIZE();
        TypeMap repository = new TypeMap() {{
            for (int i = 0; i < size; i++) create(PARAMS[i], params[i]);
        }};

        if (super.isSTATIC()) {
            try {
                Repository.repositoryArray.addFirst(repository);
                CODE.start();
                return TOKEN.replace();
            } finally {
                Repository.repositoryArray.removeFirst();
            }
        } else {
            try {
                ((KlassItem) klassItem).addRepository();
                Repository.repositoryArray.addFirst(repository);
                CODE.start();
                return TOKEN.replace();
            } finally {
                Repository.repositoryArray.removeFirst();
                Repository.repositoryArray.removeFirst();
            }
        }
    }
}
