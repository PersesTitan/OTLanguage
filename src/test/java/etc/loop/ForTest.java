package etc.loop;

import bin.apply.sys.make.StartLine;
import bin.calculator.tool.Calculator;
import bin.exception.VariableException;
import bin.token.LoopToken;
import bin.token.MergeToken;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static bin.apply.Repository.repository;
import static bin.apply.sys.make.StartLine.getFinalTotal;
import static bin.token.LoopToken.LOOP_TOKEN;
import static bin.token.LoopToken.PUTIN_TOKEN;
import static bin.token.VariableToken.*;

public class ForTest implements Calculator, MergeToken {
    public static void main(String[] args) {
        new ForTest().start("^[1]^ (test,12,1)", repository);
//        new ForTest().start("1^16^2 (test,12,1)", repository);
    }

    public boolean check(String line) {
        return line.chars().filter(v -> v == '^').count() == 2;
    }

    public void start(String line, LinkedList<Map<String, Map<String, Object>>> ra) {
        startTokens(new StringTokenizer(line, "^"), ra);
    }

    private void startTokens(StringTokenizer tokenizer, LinkedList<Map<String, Map<String, Object>>> ra) {
        if (tokenizer.countTokens() == 2) {
            // ^[1, 2, 3, 4]^ (test,1,3)
            String token1 = tokenizer.nextToken().strip();  // [1, 2, 3, 4]
            String token2 = tokenizer.nextToken().strip();  // (test,1,3)

            // (test,1,3) -> test, 1, 3
            String variableName = null;
            int position = token2.lastIndexOf('(');
            token2 = token2.substring(position);
            if (token2.contains(PUTIN_TOKEN)) {
                String[] tokens = token2.split(PUTIN_TOKEN, 2);
                token2 = tokens[0];
                variableName = tokens[1].strip();
            }

            StringTokenizer st = new StringTokenizer(bothEndCut(token2.strip()), ",");
            String fileName = st.nextToken();
            String total = LOOP_TOKEN.get(fileName);
            int s = total.indexOf("\n" + st.nextToken() + " ");
            int e = total.indexOf("\n" + st.nextToken() + " ");
            String finalTotal = getFinalTotal(false, total.substring(s, e), fileName);

            if (variableName == null) start(finalTotal, fileName, ra, 0, ,1);
            else {
                var rep = ra.get(0).get(INT_VARIABLE);
                try {
                    for (int i : list) {
                        rep.put(variableName, i);
                        if (Objects.equals(StartLine.startLoop(finalTotal, fileName, ra), LoopToken.BREAK)) break;
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
            default -> throw new VariableException().typeMatch();
        }
    }

    private List<?> getList(String line, String type) {
        if (line.startsWith("[") && line.endsWith("]")) {
            line = bothEndCut(line);
            Stream<String> stream = Pattern.compile(",").splitAsStream(line).map(String::strip);
            return switch (type) {
                case INT_VARIABLE -> stream.map(Integer::parseInt).toList();
                case FLOAT_VARIABLE -> stream.map(Float::parseFloat).toList();
                case LONG_VARIABLE -> stream.map(Long::parseLong).toList();
                case DOUBLE_VARIABLE -> stream.map(Double::parseDouble).toList();
                default -> throw new VariableException().typeMatch();
            };
        } else {

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
