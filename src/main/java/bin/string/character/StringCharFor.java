package bin.string.character;

import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.token.MergeToken;
import work.v3.StartWorkV3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

import static bin.token.VariableToken.*;

public class StringCharFor extends StartWorkV3 implements MergeToken {
    // 1: String value
    // 3: String value, cut, loop
    public StringCharFor() {
        super(1);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        startChar(params, repositoryArray);
    }

    private void startChar(String[] params, LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        String[] loops = cutLoop(params[0]);
        String loop = loops[1], strValue = loops[0];
        // ㅇㅁㅇ~ㅁㄱㅁ String (test,1,10) <= ㅇㄱㅇ 변수명
        // 파일 이름, 시작, 마지막, 변수 타입, 변수 이름
        String[] loopToken = getLoop(loop);
        if (loopToken.length != 5) throw new MatchException().grammarError();
        // 변수 타입 및 변수 이름
        final String variableType = loopToken[3], variableName = loopToken[4];
        if (!checkVariableType(variableType)) throw new VariableException().typeMatch();
        variableDefineError(variableName, repositoryArray.get(0));

        try {
            for (int i : strValue.chars().toArray()) {
                repositoryArray.get(0).get(variableType).put(variableName, (char) i);
                if (loopStart(loopToken[0], loopToken[1], loopToken[2], repositoryArray)) break;
            }
        } finally {
            repositoryArray.get(0).get(variableType).remove(variableName);
        }
    }

    private boolean checkVariableType(String type) {
        return type.equals(INT_VARIABLE) || type.equals(CHARACTER_VARIABLE);
    }
}
