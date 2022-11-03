package etc.klass;

import bin.token.MergeToken;

import java.util.*;

import static bin.token.ConsoleToken.*;
import static bin.token.Token.*;
import static bin.token.VariableToken.*;
import static bin.token.VariableToken.MAP_LIST;

public class KlassMapStartTest implements MergeToken {

    public KlassMapStartTest(HashMap<String, Map<String, Object>> rep) {
         this.totalList = new LinkedList<>() {{add(rep);}};
        reset();
    }

    public static void main(String[] args) {
//        KlassMapStartTest kt = new KlassMapStartTest();
//        kt.reset();
//        kt.start("ㅇㅁㅇ 안녕:1234");
//        kt.start("ㅆㅁㅆ[1]");
//        kt.start("ㅆㅁㅆ");
//        kt.start("ㅆㅁㅆ[ㅁㄴㅇㄹ]");
//        kt.print();
    }

    Map<String, Map<String, StartWorkTest>> startWorks = new HashMap<>();
    Map<String, Map<String, ReturnWorkTest>> returnWorks = new HashMap<>();
    String merge = merge("(?=", BLANKS, "|", BL, ")");

    public void reset() {
        StartWorkTest createOrigin = new StartWorkTest() {
            @Override void start(String line, String[] params,
                       LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
                String[] values = matchSplitError(line, BLANKS, 2);
                String[] tokens = values[1].split(VARIABLE_PUT, 2);
                variableDefineError(tokens[0], repositoryArray.get(0));
                repositoryArray.get(0).get(values[0]).put(tokens[0], tokens.length == 2 ? tokens[1] : "");
            }
        };
        StartWorkTest createList = new StartWorkTest() {
            @Override void start(String line, String[] params,
                       LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
                String[] values = matchSplitError(line, BLANKS, 2);
                String[] tokens = values[1].split(splitNoCutBack(VARIABLE_PUT, LIST_ADD), 2);
                variableDefineError(tokens[0], repositoryArray.get(0));
                repositoryArray.get(0).get(values[0]).put(tokens[0], tokens.length == 2 ? tokens[1] : "");
            }
        };
        StartWorkTest createSet = new StartWorkTest() {
            @Override void start(String line, String[] params,
                                 LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
                String[] values = matchSplitError(line, BLANKS, 2);
                String[] tokens = matchSplitError(values[1], splitNoCutBack(VARIABLE_PUT, SET_ADD), 2);
                variableDefineError(tokens[0], repositoryArray.get(0));
                repositoryArray.get(0).get(values[0]).put(tokens[0], tokens[1]);
            }
        };
        StartWorkTest createMap = new StartWorkTest() {
            @Override void start(String line, String[] params,
                       LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
                String[] values = matchSplitError(line, BLANKS, 2);
                String[] tokens = values[1].split(splitNoCutBack(VARIABLE_PUT, MAP_ADD), 2);
                variableDefineError(tokens[0], repositoryArray.get(0));
                repositoryArray.get(0).get(values[0]).put(tokens[0], tokens.length == 2 ? tokens[1] : "");
            }
        };
        ORIGIN_LIST.forEach(v -> createMap(v, "", createOrigin));
        LIST_LIST.forEach(v -> createMap(v, "", createList));
        SET_LIST.forEach(v -> createMap(v, "", createSet));
        MAP_LIST.forEach(v -> createMap(v, "", createMap));

        createMap(PRINT, "", new StartWorkTest(1) {
            @Override void start(String line, String[] params,
                       LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
                System.out.print(params[0]);
            }
        });
        createMap(PRINTLN, "", new StartWorkTest(1) {
            @Override void start(String line, String[] params,
                                 LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
                System.out.println(params[0]);
            }
        });
        createMap(PRINT_SPACE, "", new StartWorkTest(1) {
            @Override void start(String line, String[] params,
                                 LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
                System.out.print(params[0] + " ");
            }
        });
        createMap(PRINT_TAP, "", new StartWorkTest(1) {
            @Override void start(String line, String[] params,
                       LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
                System.out.print(params[0] + "\t");
            }
        });
    }

    private void createMap(String klass, String method, StartWorkTest startWorkTest) {
        if (startWorks.containsKey(klass)) startWorks.get(klass).put(method, startWorkTest);
        else startWorks.put(klass, new HashMap<>() {{ put(method, startWorkTest); }});
    }

    private final LinkedList<Map<String, Map<String, Object>>> totalList;
    public void start(String line) {
        String[] tokens = line.split(merge, 2);
        String local = tokens[0];
        String value = tokens.length == 2 ? tokens[1] : "";

        String[] params = value.startsWith("[")
                ? getCheck(value)
                : new String[]{value.stripLeading()};
        if (params == null) return;

        StringTokenizer tokenizer = new StringTokenizer(local, "~");
        String className = tokenizer.nextToken();
        String methodName = tokenizer.hasMoreTokens() ? tokenizer.nextToken("").substring(1) : "";
        StartWorkTest startWork = getStartWork(className, methodName);
        if (startWork != null) startWork.paramsCheck(params.length, params[0]).start(line, params, totalList);
    }

    private String[] getCheck(String value) {
        if (value.contains("(") && value.strip().endsWith(")")) {
            int loopPoison = value.lastIndexOf('(');
            String loop = value.substring(loopPoison);          // (test,1,10)
            value = value.substring(0, loopPoison).strip();     // [][]
            int count = count(value);                           // 2
            if (!value.endsWith("]")) return null;
            // value 쪼개기
            String[] values = Arrays.copyOf(bothEndCut(value).split(BR + BL, count), count+1);
            values[count] = loop;
            return values;
        } else return value.endsWith("]")
                ? bothEndCut(value).split(BR + BL, count(value))
                : null;
    }

    private int count(String value) {
        int count = 1, i = -1;
        while ((i = value.indexOf("][", i+1)) != -1) count++;
        return count;
    }

    private StartWorkTest getStartWork(String klassName, String methodName) {
        Map<String, StartWorkTest> startWorkTestMap;
        if (startWorks.containsKey(klassName)
                && (startWorkTestMap = startWorks.get(klassName)).containsKey(methodName))
            return startWorkTestMap.get(methodName);
        return null;
    }
}
