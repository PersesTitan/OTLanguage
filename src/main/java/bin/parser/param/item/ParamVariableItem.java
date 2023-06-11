package bin.parser.param.item;

import bin.apply.Repository;
import bin.apply.repository.variable.HpMap;
import bin.parser.param.ParamToken;
import bin.token.EditToken;

public class ParamVariableItem extends ParamToken {
    private final int ACCESS;
    private final String NAME;
    private boolean IS_HP = false;
    private HpMap MAP = null;

    public ParamVariableItem(String NAME) {
        this.ACCESS = EditToken.getAccess(NAME);
        this.NAME = NAME.substring(ACCESS);
    }

    @Override
    public Object replace() {
        setMap();
        return MAP.get(NAME);
    }

    @Override
    public String getReplace() {
        setMap();
        return MAP.getTYPE();
    }

    private void setMap() {
        if (MAP == null || IS_HP || !MAP.containsKey(NAME)) {
            MAP = Repository.repositoryArray.getMap(ACCESS, NAME);
            IS_HP = !MAP.isHp(NAME);
        }
    }
}
