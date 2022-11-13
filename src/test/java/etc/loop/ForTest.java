package etc.loop;

import bin.apply.sys.make.StartLine;
import bin.calculator.tool.Calculator;
import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.orign.variable.GetList;
import bin.token.LoopToken;
import bin.token.MergeToken;

import java.util.*;

import static bin.apply.Repository.repository;
import static bin.apply.sys.make.StartLine.getFinalTotal;
import static bin.token.LoopToken.LOOP_TOKEN;
import static bin.token.LoopToken.PUTIN_TOKEN;
import static bin.token.VariableToken.*;

public class ForTest implements Calculator, MergeToken, GetList {
    public static void main(String[] args) {
        String put = """
                1 ^[1]^ {
                2   ㅆㅁㅆ :ㅁ_
                3 }
                """;
        LOOP_TOKEN.put("test", put);
        LinkedList<Integer> list = new LinkedList<>(Arrays.asList(5, 4, 3, 2, 1));
        repository.get(0).get(LIST_INTEGER).put("a", list);
        new ForTest().start("0^10^1 (test,2,3) <= ㅇㅈㅇ ㅁ", repository);
//        new ForTest().start("^a^ (test,2,3) <= ㅇㅈㅇ ㅁ", repository);
    }

    public boolean check(String line) {
        return line.chars().filter(v -> v == '^').count() == 2;
    }

    public void start(String line, LinkedList<Map<String, Map<String, Object>>> ra) {
        try {
            startTokens(new StringTokenizer(line, "^"), ra);
        } catch (NoSuchElementException e) {
            throw new MatchException().grammarError();
        } catch (ClassCastException | NumberFormatException e) {
            throw new VariableException().typeMatch();
        }
    }

    private void startTokens(StringTokenizer tokenizer, LinkedList<Map<String, Map<String, Object>>> ra) {
        if (tokenizer.countTokens() == 2) {
            // ^[1, 2, 3, 4]^ (test,1,3)
            String token1 = tokenizer.nextToken().strip();  // [1, 2, 3, 4]
            String token2 = tokenizer.nextToken().strip();  // (test,1,3)

            // (test,1,3) -> test, 1, 3
            String variables = null;
            int position = token2.lastIndexOf('(');
            token2 = token2.substring(position);
            if (token2.contains(PUTIN_TOKEN)) {
                String[] tokens = token2.split(PUTIN_TOKEN, 2);
                token2 = tokens[0];                 // (test,1,15)
                variables = tokens[1].strip();      // ㅇㅈㅇ 변수명
            }

            StringTokenizer st = new StringTokenizer(bothEndCut(token2.strip()), ",");
            String fileName = st.nextToken();
            String total = LOOP_TOKEN.get(fileName);
            int s = total.indexOf("\n" + st.nextToken() + " ");
            int e = total.indexOf("\n" + st.nextToken() + " ");
            String finalTotal = getFinalTotal(false, total.substring(s, e), fileName);

            if (variables == null) start(finalTotal, fileName, ra, 0, getList(token1, null, ra).size(),1);
            else {
                StringTokenizer variableToken = new StringTokenizer(variables);
                String variableType = variableToken.nextToken();
                String variableName = variableToken.nextToken();
                variableDefineError(variableName, ra.get(0));
                List<?> list = getList(token1, variableType, ra);
                if (list == null) throw new VariableException().typeMatch();
                var rep = ra.get(0).get(variableType);
                try {
                    switch (variableType) {
                        case INT_VARIABLE -> {
                            for (Object ob : list) {
                                int i = (int) ob;
                                rep.put(variableName, i);
                                if (Objects.equals(StartLine.startLoop(finalTotal, fileName, ra), LoopToken.BREAK)) break;
                            }
                        }
                        case FLOAT_VARIABLE -> {
                            for (Object ob : list) {
                                float i = (float) ob;
                                rep.put(variableName, i);
                                if (Objects.equals(StartLine.startLoop(finalTotal, fileName, ra), LoopToken.BREAK)) break;
                            }
                        }
                        case LONG_VARIABLE -> {
                            for (Object ob : list) {
                                long i = (long) ob;
                                rep.put(variableName, i);
                                if (Objects.equals(StartLine.startLoop(finalTotal, fileName, ra), LoopToken.BREAK)) break;
                            }
                        }
                        case DOUBLE_VARIABLE -> {
                            for (Object ob : list) {
                                double i = (double) ob;
                                rep.put(variableName, i);
                                if (Objects.equals(StartLine.startLoop(finalTotal, fileName, ra), LoopToken.BREAK)) break;
                            }
                        }
                        default -> throw new VariableException().forTypeMatchError();
                    }
                } finally {rep.remove(variableName);}
            }

        } else if (tokenizer.countTokens() == 3) {
            // 1^10^1 (test,1,3)
            double a = getNumber(tokenizer.nextToken(), ra);
            double b = getNumber(tokenizer.nextToken(), ra);
            String token = tokenizer.nextToken();
            int position = token.lastIndexOf('(');
            double c = getNumber(token.substring(0, position), ra);

            // (test,1,3) -> test, 1, 3
            String variableName = null;
            token = token.substring(position).strip();
            if (token.contains(PUTIN_TOKEN)) {
                String[] tokens = token.split(PUTIN_TOKEN, 2);
                token = tokens[0];
                variableName = tokens[1].strip();
            }

            StringTokenizer st = new StringTokenizer(bothEndCut(token.strip()), ",");
            String fileName = st.nextToken();
            String total = LOOP_TOKEN.get(fileName);
            int s = total.indexOf("\n" + st.nextToken() + " ");
            int e = total.indexOf("\n" + st.nextToken() + " ");
            String finalTotal = getFinalTotal(false, total.substring(s, e), fileName);

            if (variableName == null) start(finalTotal, fileName, ra, a, b, c);
            else {
                StringTokenizer variableNameTokens = new StringTokenizer(variableName);
                createVariable(variableNameTokens.nextToken(), variableNameTokens.nextToken(),
                        fileName, finalTotal, ra, a, b, c);
            }
        }
    }

    private void createVariable(String type, String variableName,
                                String fileName, String finalTotal,
                                LinkedList<Map<String, Map<String, Object>>> repositoryArray,
                                double a, double b, double c) {
        variableDefineError(variableName, repositoryArray.get(0));
        switch (type) {
            case INT_VARIABLE -> start(variableName, finalTotal, fileName, repositoryArray, (int) a, (int) b, (int) c);
            case LONG_VARIABLE -> start(variableName, finalTotal, fileName, repositoryArray, (long) a, (long) b, (long) c);
            case FLOAT_VARIABLE -> start(variableName, finalTotal, fileName, repositoryArray, (float) a, (float) b, (float) c);
            case DOUBLE_VARIABLE -> start(variableName, finalTotal, fileName, repositoryArray, a, b, c);
            default -> throw new VariableException().forTypeMatchError();
        }
    }

    private List<?> getList(String line, String type,
                            LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (type == null) return setStringList(new LinkedList<>(), bothEndCut(line));
        else if (line.startsWith("[") && line.endsWith("]")) {
            // [1, 2, 3, 4]
            return switch (type) {
                case INT_VARIABLE -> setIntegerList(new LinkedList<>(), line);
                case FLOAT_VARIABLE -> setFlotList(new LinkedList<>(), line);
                case LONG_VARIABLE -> setLongList(new LinkedList<>(), line);
                case DOUBLE_VARIABLE -> setDoubleList(new LinkedList<>(), line);
                default -> throw new VariableException().forTypeMatchError();
            };
        } else {
            int accessCount = accessCount(line, repositoryArray.size());
            line = line.substring(accessCount);
            return switch (type) {
                case INT_VARIABLE -> (List<Integer>) repositoryArray.get(accessCount).get(LIST_INTEGER).get(line);
                case FLOAT_VARIABLE -> (List<Float>) repositoryArray.get(accessCount).get(LIST_FLOAT).get(line);
                case LONG_VARIABLE -> (List<Long>) repositoryArray.get(accessCount).get(LIST_LONG).get(line);
                case DOUBLE_VARIABLE -> (List<Double>) repositoryArray.get(accessCount).get(LIST_DOUBLE).get(line);
                default -> throw new VariableException().forTypeMatchError();
            };
        }
    }

    private void start(String variableName,
                       String finalTotal, String fileName,
                       LinkedList<Map<String, Map<String, Object>>> repositoryArray,
                       int a, int b, int c) {
        var rep = repositoryArray.get(0).get(INT_VARIABLE);
        try {
            rep.put(variableName, a);
            for (int repValue; (repValue = (int) rep.get(variableName)) < b; rep.put(variableName, repValue + c)) {
                if (Objects.equals(StartLine.startLoop(finalTotal, fileName, repositoryArray), LoopToken.BREAK)) break;
            }
        } finally {rep.remove(variableName);}
    }

    private void start(String variableName,
                       String finalTotal, String fileName,
                       LinkedList<Map<String, Map<String, Object>>> repositoryArray,
                       long a, long b, long c) {
        var rep = repositoryArray.get(0).get(LONG_VARIABLE);
        try {
            rep.put(variableName, a);
            for (long repValue; (repValue = (long) rep.get(variableName)) < b; rep.put(variableName, repValue + c)) {
                if (Objects.equals(StartLine.startLoop(finalTotal, fileName, repositoryArray), LoopToken.BREAK)) break;
            }
        } finally {rep.remove(variableName);}

    }

    private void start(String variableName,
                       String finalTotal, String fileName,
                       LinkedList<Map<String, Map<String, Object>>> repositoryArray,
                       float a, float b, float c) {
        var rep = repositoryArray.get(0).get(LONG_VARIABLE);
        try {
            rep.put(variableName, a);
            for (float repValue; (repValue = (float) rep.get(variableName)) < b; rep.put(variableName, repValue + c)) {
                if (Objects.equals(StartLine.startLoop(finalTotal, fileName, repositoryArray), LoopToken.BREAK)) break;
            }
        } finally {rep.remove(variableName);}
    }

    private void start(String variableName,
                       String finalTotal, String fileName,
                       LinkedList<Map<String, Map<String, Object>>> repositoryArray,
                       double a, double b, double c) {
        var rep = repositoryArray.get(0).get(LONG_VARIABLE);
        try {
            rep.put(variableName, a);
            for (double repValue; (repValue = (double) rep.get(variableName)) < b; rep.put(variableName, repValue + c)) {
                if (Objects.equals(StartLine.startLoop(finalTotal, fileName, repositoryArray), LoopToken.BREAK)) break;
            }
        } finally {rep.remove(variableName);}
    }

    private void start(String finalTotal, String fileName,
                       LinkedList<Map<String, Map<String, Object>>> repositoryArray,
                       double a, double b, double c) {
        for (double i = a; i < b; i+=c) {
            if (Objects.equals(StartLine.startLoop(finalTotal, fileName, repositoryArray), LoopToken.BREAK)) break;
        }
    }
}
