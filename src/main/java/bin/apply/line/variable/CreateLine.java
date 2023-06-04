package bin.apply.line.variable;

import bin.apply.Repository;
import bin.apply.item.ParamItem;
import bin.apply.line.LineTool;
import bin.parser.param.ParamToken;
import bin.parser.param.ParserParamItem;
import bin.token.check.CheckToken;

public class CreateLine implements LineTool {
    private final ParamItem TYPE;
    private final ParamToken[] PARAMS;

    public CreateLine(String TYPE, String NAME, String PARAMS) {
        this.TYPE = new ParamItem(TYPE, NAME);
        this.PARAMS = ParserParamItem.startParam(TYPE, PARAMS, CheckToken.isListType(TYPE));
    }

    @Override
    public void startItem() {
        Repository.repositoryArray.create(TYPE, PARAMS);
    }
}
