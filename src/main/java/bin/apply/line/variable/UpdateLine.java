package bin.apply.line.variable;

import bin.apply.Repository;
import bin.apply.item.ParamItem;
import bin.apply.line.LineTool;
import bin.apply.repository.variable.HpMap;
import bin.parser.param.ParamToken;
import bin.parser.param.ParserParamItem;
import bin.token.EditToken;
import bin.token.check.CheckToken;

public class UpdateLine implements LineTool {
    private final int ACCESS;
    private final String NAME;
    private final String VALUE;
    private HpMap REPOSITORY = null;
    private String TYPE = null;
    private ParamToken[] PARAMS = null;

    public UpdateLine(String NAME, String VALUE) {
        this.ACCESS = EditToken.getAccess(NAME);
        this.NAME = NAME.substring(ACCESS);
        this.VALUE = VALUE;
    }

    @Override
    public void startItem() {
        setType();
        Repository.repositoryArray.update(ACCESS, TYPE, NAME, PARAMS);
    }

    private void setType() {
        if (REPOSITORY == null) {
            this.REPOSITORY = Repository.repositoryArray.getMap(ACCESS, NAME);
            this.TYPE = REPOSITORY.getTYPE();
            this.PARAMS = ParserParamItem.startParam(TYPE, VALUE, CheckToken.isListType(TYPE));
        }
    }
}
