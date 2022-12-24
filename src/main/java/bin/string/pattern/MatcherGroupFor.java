package bin.string.pattern;

import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.token.MergeToken;
import work.v3.StartWorkV3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bin.token.VariableToken.STRING_VARIABLE;

public class MatcherGroupFor extends StartWorkV3 implements MergeToken {
    // 3 = 1: 그룹할 단어, 2: 패턴, 3: loop
    public MatcherGroupFor() {
        super(3);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        // (test,1,10) <= ㅇㅁㅇ 변수
        String[] loopToken = getLoop(params[2]);
        if (loopToken.length != 5) throw new MatchException().grammarError();
        else if (!loopToken[3].equals(STRING_VARIABLE)) throw new VariableException().typeMatch();
        variableDefineError(loopToken[4], repositoryArray.get(0));
        String variableName = loopToken[4];

        try {
            Matcher matcher = Pattern.compile(params[1]).matcher(params[0]);
            while (matcher.find()) {
                String group = matcher.group();
                repositoryArray.getFirst().get(STRING_VARIABLE).put(variableName, group);
                if (loopStart(loopToken[0], loopToken[1], loopToken[2], repositoryArray)) break;
            }
        } finally {
            repositoryArray.getFirst().get(STRING_VARIABLE).remove(variableName);
        }
    }
}
