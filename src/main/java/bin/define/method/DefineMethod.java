package bin.define.method;

import bin.define.item.MethodItemReturn;
import bin.define.item.MethodItemVoid;
import bin.exception.VariableException;
import bin.token.MergeToken;
import work.v3.StartWorkV3;

import java.util.*;

import static bin.apply.sys.make.StartLine.getFinalTotal;
import static bin.define.item.MethodItemTool.getParams;
import static bin.token.LoopToken.*;
import static bin.token.Token.BL;
import static bin.token.Token.BR;

public class DefineMethod extends StartWorkV3 implements MergeToken {
    // 1
    public DefineMethod(int... counts) {
        super(counts);
    }

    // ㅁㅅㅁ 메소드명[메게변수][메게변수] (test,1,2) => 변수명
    // ㅁㅅㅁ 메소드명[메게변수][메게변수] (test,1,2)
    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        // 메소드명[메게변수][메게변수] (test,1,2) => 변수명
        // 메소드명[메게변수][메게변수] (test,1,2)
        int positionStart = params[0].indexOf('[');
        String methodName = params[0].substring(0, positionStart).strip();  // 메소드 명
        String parameter = params[0].substring(positionStart);              // [ㅇㅈㅇ ㅁ][ㅇㅁㅇ ㅁㅁ] (test,1,2)

        int positionEnd = parameter.lastIndexOf(']') + 1;
        String parameters = parameter.substring(0, positionEnd);            // [ㅇㅈㅇ ㅁ][ㅇㅁㅇ ㅁㅁ]
        String loop = parameter.substring(positionEnd).strip();             // (test,1,2), (test,1,2) => 변수명

        String returnValue = null;
        if (loop.contains(RETURN_TOKEN)) {
            String[] tokens = loop.split(RETURN_TOKEN, 2);
            loop = tokens[0].strip();                   // (test,1,2)
            returnValue = tokens[1].strip();            // 변수명
        }

        // [ㅇㅈㅇ ㅁ][ㅇㅁㅇ ㅁㅁ] => ㅇㅈㅇ ㅁ][ㅇㅁㅇ ㅁㅁ
        parameters = bothEndCut(parameters);
        // [] => [] or [ㅇㅈㅇ ㅁ][ㅇㅁㅇ ㅁㅁ] => [[ㅇㅈㅇ, ㅁ], [ㅇㅁㅇ, ㅁㅁ]]
        final String[][] methodParameters = parameters.isEmpty()    // [[ㅇㅈㅇ, ㅁ], [ㅇㅁㅇ, ㅁㅁ]]
                ? new String[0][0]
                : getParams(parameters.split(BR + BL));

        // (test,1,2) => test, 1, 2
        final StringTokenizer tokenizer = new StringTokenizer(bothEndCut(loop), ",");
        String fileName = tokenizer.nextToken();
        String total = LOOP_TOKEN.get(fileName);
        int s = total.indexOf("\n" + tokenizer.nextToken() + " ");
        int e = total.indexOf("\n" + tokenizer.nextToken() + " ");
        String finalTotal = getFinalTotal(false, total.substring(s, e), fileName).strip();

        var rep = repositoryArray.get(0).get(METHOD);
        if (rep.containsKey(methodName)) throw new VariableException().definedMethodName();

        if (returnValue == null) rep.put(methodName, new MethodItemVoid(methodParameters, finalTotal, fileName));
        else rep.put(methodName, new MethodItemReturn(methodParameters, finalTotal, fileName, returnValue));
    }
}
