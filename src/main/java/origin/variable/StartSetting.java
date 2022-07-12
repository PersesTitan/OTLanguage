package origin.variable;

import origin.item.kind.VarType;

public class StartSetting implements VariableRepository {

    public void start() {
        varList.add(new MakeVariable("ㅇㅈㅇ", VarType.Integer));
        varList.add(new MakeVariable("ㅇㅉㅇ", VarType.Long));
        varList.add(new MakeVariable("ㅇㅂㅇ", VarType.Boolean));
        varList.add(new MakeVariable("ㅇㅁㅇ", VarType.String));
        varList.add(new MakeVariable("ㅇㄱㅇ", VarType.Character));
        varList.add(new MakeVariable("ㅇㅅㅇ", VarType.Float));
        varList.add(new MakeVariable("ㅇㅆㅇ", VarType.Double));

    }
}
