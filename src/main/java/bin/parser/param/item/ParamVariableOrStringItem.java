package bin.parser.param.item;

import bin.apply.Repository;
import bin.apply.repository.variable.HpMap;
import bin.parser.param.ParamToken;
import bin.token.EditToken;
import bin.token.KlassToken;
import bin.token.Token;

public class ParamVariableOrStringItem extends ParamToken {
    private final int ACCESS;
    private final String NAME;

    private boolean IS_STRING = false;
    private boolean IS_HP = false;
    private HpMap MAP = null;

    public ParamVariableOrStringItem(String NAME) {
        this.ACCESS = EditToken.getAccess(NAME);
        this.NAME = NAME.substring(ACCESS);
    }

    @Override
    public Object replace() {
        setMap();
        return IS_STRING
                ? Character.toString(Token.ACCESS).repeat(ACCESS) + NAME
                : MAP.get(NAME);
    }

    @Override
    public String getReplace() {
        setMap();
        return IS_STRING
                ? KlassToken.STRING_VARIABLE
                : MAP.getTYPE();
    }

    private void setMap() {
        if (IS_STRING && IS_HP) {
            if (Repository.repositoryArray.find(ACCESS, NAME)) {
                MAP = Repository.repositoryArray.getMap(ACCESS, NAME);
                IS_HP = !MAP.isHp(NAME);
                IS_STRING = false;
            }
        } else if (MAP == null || IS_HP || !MAP.containsKey(NAME)) {
            if (Repository.repositoryArray.find(ACCESS, NAME)) {
                MAP = Repository.repositoryArray.getMap(ACCESS, NAME);
                IS_HP = !MAP.isHp(NAME);
            } else IS_STRING = true;
        }
    }
}
