package v4.bin.origin.string.character;

import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.token.MergeToken;
import v4.bin.apply.sys.item.TypeMap;
import v4.item.StartItem;
import v4.work.StartWork;

import java.util.LinkedList;

import static bin.token.VariableToken.CHARACTER_VARIABLE;
import static bin.token.VariableToken.INT_VARIABLE;

public class CharFor extends StartWork implements MergeToken {
    // 1: str
    public CharFor(StartItem... workItems) {
        super(workItems);
    }

    @Override
    public void start(String line, String loop, Object[] params, LinkedList<TypeMap> repositoryArray) {
        // 파일 이름, 시작, 마지막, 변수 타입, 변수 이름
        final String[] loopToken = getLoop(loop);
        if (loopToken.length != 5) throw new MatchException().grammarError();
        final String fileName = loopToken[0],
                variableType = loopToken[3],
                variableName = loopToken[4];
        // 타입 확인
        if (!checkVariableType(variableType)) throw new VariableException().typeMatch();
        repositoryArray.get(0).createVariable(variableType, variableName, null);

        try {
            for (int i : params[0].toString().chars().toArray()) {
                repositoryArray.get(0).get(variableType).put(variableName, (char) i);
//                if (loopStart(loopToken[0], loopToken[1], loopToken[2], repositoryArray)) break;
            }
        } finally {
            repositoryArray.get(0).get(variableType).remove(variableName);
        }
    }

    private boolean checkVariableType(String type) {
        return type.equals(INT_VARIABLE) || type.equals(CHARACTER_VARIABLE);
    }
}
