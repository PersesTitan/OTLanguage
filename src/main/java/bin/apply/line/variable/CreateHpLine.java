package bin.apply.line.variable;

import bin.apply.Repository;
import bin.apply.item.ParamItem;
import bin.apply.line.LineTool;
import bin.exception.VariableException;
import bin.parser.param.ParamToken;
import bin.parser.param.ParserParamItem;
import bin.token.check.CheckToken;

public class CreateHpLine implements LineTool {
    private final ParamItem TYPE;
    private final ParamToken[] PARAMS;
    private final ParamToken HP;

    public CreateHpLine(String TYPE, String NAME, String VALUE, String HP) {
        this.TYPE = new ParamItem(TYPE, NAME);
        this.PARAMS = ParserParamItem.startParam(TYPE, VALUE, CheckToken.isListType(TYPE));
        this.HP = new ParserParamItem(HP).start();
    }

    @Override
    public void startItem() {
        if (HP.replace() instanceof Integer hp) {
            Repository.repositoryArray.create(TYPE, PARAMS, hp);
        } else throw VariableException.TYPE_ERROR.getThrow(HP.getReplace());
    }
}
