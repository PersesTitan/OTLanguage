package klass;

import bin.apply.sys.item.HpMap;
import bin.token.LoopToken;
import klass.str.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bin.apply.Controller.scanner;
import static bin.check.VariableCheck.*;
import static bin.token.ConsoleToken.SCANNER;
import static bin.token.StringToken.*;
import static bin.token.cal.NumberToken.*;

public class ReturnKlassMapTest implements LoopToken {
    public static void main(String[] args) {
        String s = "              a                 ";
        System.out.printf("|%s|\n", s.stripIndent());
        System.out.printf("|%s|\n", s.stripLeading());
        System.out.printf("|%s|\n", s.stripTrailing());
        ReturnKlassMapTest kt = new ReturnKlassMapTest();
        kt.reset();
        KlassMapStartTest ks = new KlassMapStartTest(COPY_REPOSITORY);

        ks.start("ㅇㅁㅇ 안녕:123");

        kt.start(":ㅇㅁㅇ_기본값;");
        kt.start(":변수명              1>>1_");
        kt.start(":ㅇㅁㅇ~ㅅㅍㅅ[:안녕_][2]_ :ass_a;");
    }

    static HashMap<String, Map<String, Object>> COPY_REPOSITORY = new HashMap<>();
    public static Map<String, Map<String, ReturnWorkTest>> returnWorksTest = new HashMap<>();

    public void print() {
        System.out.println(COPY_REPOSITORY);
    }

    public void reset() {
        TOTAL_LIST.forEach(v -> COPY_REPOSITORY.put(v, new HpMap(v)));

        createMap(RANDOM_BOOL, "", new ReturnWorkTest(1) {
            @Override public String start(String line, String[] params,
                                          LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
                return new Random().nextBoolean() ? "ㅇㅇ" : "ㄴㄴ";
            }
        });
        createMap(RANDOM_DOUBLE, "", new ReturnWorkTest(1, 2) {
            @Override public String start(String line, String[] params,
                                          LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
                Random random = new Random();
                if (params.length == 1) {
                    if (params[0].isBlank()) return Double.toString(random.nextDouble());
                    else {
                        if (!isDouble(params[0])) return null;
                        double num = Double.parseDouble(params[0]);
                        return Double.toString(random.nextDouble(num));
                    }
                } else {
                    if (!isDouble(params[0])) return null;
                    else if (!isDouble(params[1])) return null;
                    double num1 = Double.parseDouble(params[0]);
                    double num2 = Double.parseDouble(params[1]);
                    return Double.toString(random.nextDouble(num1, num2));
                }
            }
        });
        createMap(RANDOM_FLOAT, "", new ReturnWorkTest(1, 2) {
            @Override public String start(String line, String[] params,
                                          LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
                Random random = new Random();
                if (params.length == 1) {
                    if (params[0].isBlank()) return Float.toString(random.nextFloat());
                    else {
                        if (!isFloat(params[0])) return null;
                        float num = Float.parseFloat(params[0]);
                        return Float.toString(random.nextFloat(num));
                    }
                } else {
                    if (!isFloat(params[0])) return null;
                    else if (!isFloat(params[1])) return null;
                    float num1 = Float.parseFloat(params[0]);
                    float num2 = Float.parseFloat(params[1]);
                    return Float.toString(random.nextFloat(num1, num2));
                }
            }
        });
        createMap(RANDOM_INTEGER, "", new ReturnWorkTest(1, 2) {
            @Override public String start(String line, String[] params,
                                          LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
                Random random = new Random();
                if (params.length == 1) {
                    if (params[0].isBlank()) return Integer.toString(random.nextInt());
                    else {
                        if (!isInteger(params[0])) return null;
                        int num = Integer.parseInt(params[0]);
                        return Integer.toString(random.nextInt(num));
                    }
                } else {
                    if (!isInteger(params[0])) return null;
                    else if (!isInteger(params[1])) return null;
                    int num1 = Integer.parseInt(params[0]);
                    int num2 = Integer.parseInt(params[1]);
                    return Integer.toString(random.nextInt(num1, num2));
                }
            }
        });
        createMap(RANDOM_LONG, "", new ReturnWorkTest(1, 2) {
            @Override
            public String start(String line, String[] params,
                                LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
                Random random = new Random();
                if (params.length == 1) {
                    if (params[0].isBlank()) return Long.toString(random.nextLong());
                    else {
                        if (!isLong(params[0])) return null;
                        long num = Long.parseLong(params[0]);
                        return Long.toString(random.nextLong(num));
                    }
                } else {
                    if (!isLong(params[0])) return null;
                    else if (!isLong(params[1])) return null;
                    long num1 = Long.parseLong(params[0]);
                    long num2 = Long.parseLong(params[1]);
                    return Long.toString(random.nextLong(num1, num2));
                }
            }
        });
        createMap(SCANNER, "", new ReturnWorkTest(0) {
            @Override public String start(String line, String[] params,
                                          LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
                return scanner();
            }
        });
        createMap(STRING_VARIABLE, JOIN, new JoinTest(2));
        createMap(STRING_VARIABLE, SPLIT, new SplitTest(2));
        createMap(STRING_VARIABLE, SPLIT_REGULAR, new SplitRegularTest(2));
        createMap(STRING_VARIABLE, CONTAINS.replace("\\", ""), new ContainsTest(2));
        createMap(STRING_VARIABLE, EQUALS.replace("\\", ""), new EqualsTest(2));

        // 변수
        ReturnWorkTest getVariable = new ReturnWorkTest() {
            @Override public String start(String line, String[] params,
                                          LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
                int count = accessCount(line, repositoryArray.size());
                if (count == -1) return null;
                else {
                    var repository = repositoryArray.get(count).entrySet();
                    for (Map.Entry<String, Map<String, Object>> map : repository) {
                        if (map.getValue().containsKey(line)) return map.getValue().get(line).toString();
                    }
                } return null;
            }
        };

        createMap(VAR_TOKEN, "안녕", getVariable);

    }

    private void createMap(String klass, String method, ReturnWorkTest returnWorkTest) {
        if (returnWorksTest.containsKey(klass)) returnWorksTest.get(klass).put(method, returnWorkTest);
        else returnWorksTest.put(klass, new HashMap<>() {{ put(method, returnWorkTest); }});
    }

    String VAR_TOKEN = "@otl;";
    String patternText = merge(VARIABLE_GET_S, "[^:_]+", VARIABLE_GET_E, "([^;_]+;)?");
    Matcher matcher = Pattern.compile(patternText).matcher("");
    String merge = merge("(?=", BLANKS, "|", BL, ")");
    private final LinkedList<Map<String, Map<String, Object>>> totalList = new LinkedList<>() {{add(COPY_REPOSITORY);}};
    // 변수 패턴
    String variablePatternText = splitNoCutBack("[^" + VARIABLE_ACCESS + "]");
    public void start(String line) {
        matcher.reset(line);
        while (matcher.find()) {
            String group = matcher.group();
            // 값_기본값 or 값
            String variable = bothEndCut(group);
            String value = null;
            if (!group.endsWith(VARIABLE_GET_E)) {
                // 값, 기본값
                String[] tokens = matchSplitError(variable, VARIABLE_GET_E, 2);
                variable = tokens[0];
                value = tokens[1];
            }
            String sub;
            if ((sub = sub(variable, value)) != null) {
                line = line.replaceFirst(Pattern.quote(group), sub);
                matcher.reset(line);
            } else if ((sub = varSub(variable, value)) != null) {
                line = line.replaceFirst(Pattern.quote(group), sub);
                matcher.reset(line);
            }
        }
        System.out.println(line);
    }

    private String varSub(String line, String def) {
        String[] tokens = line.split(variablePatternText, 2);
        String local = tokens[0];
        String value = tokens.length == 2 ? tokens[1].stripLeading() : "";
        var startWork = getStartWork(VAR_TOKEN, local);
        if (startWork != null) return startWork.start(local, new String[]{value}, totalList);
        else return def;
    }

    private String sub(String line, String def) {
        String[] tokens = line.split(merge, 2);
        String local = tokens[0];
        String value = tokens.length == 2 ? tokens[1] : "";

        String[] params = value.startsWith("[")
                ? getCheck(value)
                : new String[]{value.stripLeading()};
        if (params == null) return def;

        StringTokenizer tokenizer = new StringTokenizer(local, ACCESS);
        String className = tokenizer.nextToken();
        String methodName = tokenizer.hasMoreTokens() ? tokenizer.nextToken("").substring(1) : "";
        ReturnWorkTest startWork = getStartWork(className, methodName);
        if (startWork != null) return startWork.paramsCheck(params.length, params[0]).start(line, params, totalList);
        else return def;
    }

    private String[] getCheck(String value) {
        return value.endsWith("]")
                ? bothEndCut(value).split(BR + BL, count(value))
                : null;
    }

    private int count(String value) {
        int count = 1, i = -1;
        while ((i = value.indexOf("][", i+1)) != -1) count++;
        return count;
    }

    private ReturnWorkTest getStartWork(String klassName, String methodName) {
        Map<String, ReturnWorkTest> returnWorkTestMap;
        if (returnWorksTest.containsKey(klassName)
                && (returnWorkTestMap = returnWorksTest.get(klassName)).containsKey(methodName))
            return returnWorkTestMap.get(methodName);
        return null;
    }
}
