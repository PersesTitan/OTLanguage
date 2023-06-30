package bin.apply.line.variable;

import bin.apply.Repository;
import bin.apply.item.ParamItem;
import bin.apply.line.LineTool;
import bin.parser.param.ParamToken;
import bin.parser.param.ParserParamItem;
import bin.token.CastingToken;
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
        Repository.repositoryArray.create(TYPE, PARAMS, CastingToken.getInt(HP.replace()));
    }
}