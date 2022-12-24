package bin.string.character;

import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.token.MergeToken;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.token.VariableToken.STRING_VARIABLE;

public class StringSplitRegularFor extends StartWorkV3 implements MergeToken {
    public StringSplitRegularFor(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        final String str = params[0];   // string
        final String cut = params[1];   // \n
        final String loop = params[2];

        // ㅇㅁㅇ~ㅅㅍㄱ$[String][\n] (test,1,10) <= ㅇㄱㅇ 변수명
        // 파일 이름, 시작, 마지막, 변수 타입, 변수 이름
        String[] loopToken = getLoop(loop);
        final String variableType = loopToken[3], variableName = loopToken[4];

        if (loopToken.length != 5) throw new MatchException().grammarError();
        else if (!variableType.equals(STRING_VARIABLE)) throw new VariableException().typeMatch();
        variableDefineError(loopToken[4], repositoryArray.get(0));

        try {
            for (String li : str.split(cut)) {
                repositoryArray.get(0).get(variableType).put(variableName, li);
                if (loopStart(loopToken[0], loopToken[1], loopToken[2], repositoryArray)) break;
            }
        } finally {
            repositoryArray.get(0).get(variableType).remove(variableName);
        }
    }
}
