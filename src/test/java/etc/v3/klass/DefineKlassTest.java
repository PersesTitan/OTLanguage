package etc.v3.klass;

import bin.apply.sys.item.HpMap;
import bin.apply.sys.item.SystemSetting;
import bin.apply.sys.make.StartLine;
import bin.define.item.MethodItemReturn;
import bin.define.item.MethodItemVoid;
import bin.exception.VariableException;
import bin.token.MergeToken;
import work.v3.ReturnWorkV3;
import work.v3.StartWorkV3;

import java.util.*;

import static bin.apply.Repository.*;
import static bin.apply.sys.make.StartLine.startStartLine;
import static bin.define.item.MethodItemTool.setParams;
import static bin.token.LoopToken.*;
import static bin.token.Token.BL;
import static bin.token.Token.BR;
import static bin.token.VariableToken.TOTAL_LIST;
import static etc.v3.method.MethodItemTool.resetRepository;

public class DefineKlassTest extends StartWorkV3 implements MergeToken {
    // 1
    public DefineKlassTest(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        // params = 클래스명[ㅇㅈㅇ 변수명1][ㅇㅁㅇ 변수명2] (test,1,2)
        String tokens = params[0].strip();
        int start = tokens.indexOf('[');
        int end = tokens.lastIndexOf(']') + 1;
        String klassName = tokens.substring(0, start);       // 클래스명
        String parameter = tokens.substring(start, end);     // [ㅇㅈㅇ 변수명1][ㅇㅁㅇ 변수명2]
        String loop = tokens.substring(end).strip();         // (test,1,2)
        if (parameter.startsWith("[") && parameter.endsWith("]")
                && loop.startsWith("(") && loop.endsWith(")")) {
            getParameters(klassName, parameter, loop, repositoryArray);
        }
    }

    private void getParameters(String klassName, String parameter, String loop,
                               LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        // (test,1,15) => test, 1, 15
        final String[] loopTokens = getFinal(loop);
        final String fileName = loopTokens[0];
        final String finalTotal = loopTokens[1];

        final int paramCount;
        final String[][] parameters;
        parameter = bothEndCut(parameter);          // ㅇㅈㅇ 변수명1][ㅇㅁㅇ 변수명2
        if (parameter.isEmpty()) {                  // [] 일때
            paramCount = 0;
            parameters = new String[0][0];
        } else {
            // [[ㅇㅈㅇ, 변수명1], [ㅇㅁㅇ, 변수명2]]
            parameters = getParams(parameter.split(BR + BL));
            paramCount = parameters.length;
        }

        defineKlass(paramCount, klassName, parameters, finalTotal, fileName);
    }

    private void defineKlass(int paramCount, String klassName, String[][] parameters,
                             String finalTotal, String fileName) {
        SystemSetting.createStartWorks(klassName, "", new StartWorkV3(paramCount) {
            private final String[][] param = parameters;
            private final Map<String, Map<String, Object>> repository = new HashMap<>() {{
                repositoryItems.forEach(v -> put(v, new HpMap(v)));
            }};

            @Override
            public void start(String line, String[] params,
                              LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
                var rep1 = startWorksV3.get(klassName);
                if (rep1 != null) {
                    var v = rep1.remove("");
                    rep1.clear();
                    rep1.put("", v);
                }
                var rep2 = returnWorksV3.get(klassName);
                if (rep2 != null) {
                    var v = rep2.remove("");
                    rep2.clear();
                    rep2.put("", v);
                }
                // ㅋㅅㅋ 클래스명[] (test,1,14)
                resetRepository(repository);
                setParams(this.param, params, repository);
                repositoryArray.addFirst(repository);
                try {
                    startStartLine(finalTotal, fileName, repositoryArray);
                } finally {
                    repositoryArray.removeFirst();
                    var repository = this.repository.get(METHOD);
                    repository.forEach((methodName, v) -> {
                        if (v instanceof MethodItemVoid methodItemVoid) {
                            defineMethodVoid(klassName, methodName, methodItemVoid, this.repository);
                        } else if (v instanceof MethodItemReturn methodItemReturn) {
                            defineMethodReturn(klassName, methodName, methodItemReturn, this.repository);
                        }
                    });
                    repository.clear();
                }
            }
        });
    }

    // (test,1,2) => finalTotal
    private String[] getFinal(String line) {
        final String[] loopTokens = getLoopTotal(line);
        final String fileName = loopTokens[0], total = loopTokens[1];
        final String finalTotal = StartLine.getFinalTotal(false, total, fileName).strip();
        return new String[]{fileName, finalTotal};
    }

    /**
     * 메소드 정의하는 로직
     */
    // 타입 void
    private void defineMethodVoid(String klassName, String methodName, MethodItemVoid methodItemVoid,
                                  Map<String, Map<String, Object>> klassRepository) {
        SystemSetting.createStartWorks(klassName, methodName, new StartWorkV3(methodItemVoid.getParams()) {
            @Override
            public void start(String line, String[] params,
                              LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
                repositoryArray.addFirst(klassRepository);
                try {
                    methodItemVoid.start(params, repositoryArray);
                } finally {
                    repositoryArray.removeFirst();
                }
            }
        });
    }

    // 타입 return
    private void defineMethodReturn(String klassName, String methodName, MethodItemReturn methodItemReturn,
                                    Map<String, Map<String, Object>> klassRepository) {
        SystemSetting.createReturnWorks(klassName, methodName, new ReturnWorkV3(methodItemReturn.getParams()) {
            @Override
            public String start(String line, String[] params,
                                LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
                repositoryArray.addFirst(klassRepository);
                try {
                    return methodItemReturn.start(params, repositoryArray);
                } finally {
                    repositoryArray.removeFirst();
                }
            }
        });
    }

    // 파라미터 정체 하는 로직
    private String[][] getParams(String[] ps) {
        Set<String> set = new HashSet<>();      // 변수 중복 체크 임시 저장소
        String[][] values = new String[ps.length][2];
        for (int i = 0; i< ps.length; i++) {
            StringTokenizer tokenizer = new StringTokenizer(ps[i]);
            String variableType = tokenizer.nextToken();
            String variableName = tokenizer.nextToken();
            if (!TOTAL_LIST.contains(variableType)) throw new VariableException().noDefine();
            if (set.contains(variableName)) throw new VariableException().sameVariable();
            set.add(variableName);
            values[i][0] = variableType;
            values[i][1] = variableName;
        }
        return values;
    }
}
