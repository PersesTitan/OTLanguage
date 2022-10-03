package bin.orign.loop;

import bin.apply.sys.make.StartLine;
import bin.check.VariableCheck;
import bin.check.VariableType;
import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.token.LoopToken;
import bin.token.Token;
import work.StartWork;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bin.check.VariableCheck.*;
import static bin.token.cal.NumberToken.NUMBER;

public class For implements
        Token, StartWork, LoopToken, VariableCheck {
    private final String patternText;
    private final Pattern pattern;

    public For() {
        this.patternText = blackMerge(NUMBER, FOR, NUMBER, FOR, NUMBER);
        this.pattern = Pattern.compile(
                startEndMerge(patternText, BLANKS, BRACE_STYLE(), "(", BLANKS, PUTIN, ")?"));
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line, String origen,
                      Map<String, Map<String, Object>>[] repositoryArray) {
        Matcher matcher = Pattern.compile(START + patternText).matcher(line.strip());
        if (matcher.find()) {
            String group = matcher.group().strip(); // 숫자^숫자^숫자
            // (test,0,1) or (test,0,1)=>ㅇㅈㅇ 변수
            line = line.replaceFirst(START + BLANK + patternText, "");
            String[] tokens = line.split(PUTIN_TOKEN, 2);

            String[] token = bothEndCut(tokens[0].strip()).split(",", 3);
            if (token.length != 3) throw MatchException.grammarError();
            String total = LOOP_TOKEN.get(token[0]);
            int start = total.indexOf("\n" + token[1] + " ");
            int end = total.indexOf("\n" + token[2] + " ");
            total = total.substring(start, end);
            if (tokens.length == 1) {
                String[] numbers = group.split(BLANK+FOR+BLANK, 3);
                if (numbers.length != 3) throw MatchException.grammarError();
                startFor(group,
                        getType(numbers[0], numbers[1], numbers[2]),
                        null, token[0], total, repositoryArray);
            } else {
                String[] variables = tokens[1].strip().split(BLANKS, 2);
                if (variables.length != 2) throw MatchException.grammarError();
                startFor(group, variables[0], variables[1], token[0],
                        total, repositoryArray);
            }
        }
    }

    @SafeVarargs
    private void startFor(String line,
                          String variableType, String variableName,
                          String fileName, String total,
                          Map<String, Map<String, Object>>...repository) {
        // line = NUMBER ^ NUMBER ^ NUMBER
        String[] tokens = line.split(BLANK+FOR+BLANK, 3);

        if (variableName != null) {
            if (tokens.length != 3) throw MatchException.grammarError();
            if (!switch (variableType) {
                case INT_VARIABLE -> Arrays.stream(tokens).allMatch(VariableCheck::isInteger);
                case LONG_VARIABLE -> Arrays.stream(tokens).allMatch(VariableCheck::isLong);
                case FLOAT_VARIABLE -> Arrays.stream(tokens).allMatch(VariableCheck::isFloat);
                case DOUBLE_VARIABLE -> Arrays.stream(tokens).allMatch(VariableCheck::isDouble);
                default -> false;
            }) throw VariableException.forTypeMatchError();
            variableDefineError(variableName, repository[0]);
        }

        switch (variableType) {
            case INT_VARIABLE -> {    // INTEGER
                int a = Integer.parseInt(tokens[0]);
                int b = Integer.parseInt(tokens[1]);
                int c = Integer.parseInt(tokens[2]);
                if (variableName == null) start(a, b, c, total, fileName, repository);
                else start(a, b, c, total, fileName, variableName, repository);
            }
            case LONG_VARIABLE -> {    // LONG
                long a = Long.parseLong(tokens[0]);
                long b = Long.parseLong(tokens[1]);
                long c = Long.parseLong(tokens[2]);
                if (variableName == null) start(a, b, c, total, fileName, repository);
                else start(a, b, c, total, fileName, variableName, repository);
            }
            case FLOAT_VARIABLE -> {    // FLOAT
                float a = Float.parseFloat(tokens[0]);
                float b = Float.parseFloat(tokens[1]);
                float c = Float.parseFloat(tokens[2]);
                if (variableName == null) start(a, b, c, total, fileName, repository);
                else start(a, b, c, total, fileName, variableName, repository);
            }
            case DOUBLE_VARIABLE -> {   // DOUBLE
                double a = Double.parseDouble(tokens[0]);
                double b = Double.parseDouble(tokens[1]);
                double c = Double.parseDouble(tokens[2]);
                if (variableName == null) start(a, b, c, total, fileName, repository);
                else start(a, b, c, total, fileName, variableName, repository);
            }
        }
    }

    private String getType(String value1, String value2, String value3) {
        List<VariableType.Origin> vars = List.of(getType(value1), getType(value2), getType(value3));
        if (vars.contains(VariableType.Origin.Double)) return DOUBLE_VARIABLE;
        else if (vars.contains(VariableType.Origin.Float)) return FLOAT_VARIABLE;
        else if (vars.contains(VariableType.Origin.Long)) return LONG_VARIABLE;
        else return INT_VARIABLE;
    }

    private VariableType.Origin getType(String value) {
        if (isInteger(value)) return VariableType.Origin.Integer;
        else if (isLong(value)) return VariableType.Origin.Long;
        else if (isFloat(value)) return VariableType.Origin.Float;
        else if (isDouble(value)) return VariableType.Origin.Double;
        else throw VariableException.typeMatch();
    }

    // INTEGER
    private void start(int a, int b, int c,
                       String total, String fileName,
                       String variableName,
                       Map<String, Map<String, Object>>[] repository) {
        var rep = repository[0].get(INT_VARIABLE);
        rep.put(variableName, a);
        for (int repValue; (repValue = (int) rep.get(variableName)) < b; rep.put(variableName, repValue + c)) {
            if (Objects.equals(StartLine.startLoop(total, fileName, repository), LoopToken.BREAK)) break;
        }
        rep.remove(variableName);
    }

    private void start(int a, int b, int c,
                       String total, String fileName,
                       Map<String, Map<String, Object>>[] repository) {
        for (int i = a; i < b; i += c) {
            if (Objects.equals(StartLine.startLoop(total, fileName, repository), LoopToken.BREAK)) break;
        }
    }

    //LONG
    private void start(long a, long b, long c,
                       String total, String fileName,
                       String variableName,
                       Map<String, Map<String, Object>>[] repository) {
        var rep = repository[0].get(LONG_VARIABLE);
        rep.put(variableName, a);
        for (long repValue; (repValue = (long) rep.get(variableName)) < b; rep.put(variableName, repValue + c)) {
            if (Objects.equals(StartLine.startLoop(total, fileName, repository), LoopToken.BREAK)) break;
        }
        rep.remove(variableName);
    }

    private void start(long a, long b, long c,
                       String total, String fileName,
                       Map<String, Map<String, Object>>[] repository) {
        for (long i = a; i < b; i += c) {
            if (Objects.equals(StartLine.startLoop(total, fileName, repository), LoopToken.BREAK)) break;
        }
    }

    //FLOAT
    private void start(float a, float b, float c,
                       String total, String fileName,
                       String variableName,
                       Map<String, Map<String, Object>>[] repository) {
        var rep = repository[0].get(FLOAT_VARIABLE);
        rep.put(variableName, a);
        for (float repValue; (repValue = (float) rep.get(variableName)) < b; rep.put(variableName, repValue + c)) {
            if (Objects.equals(StartLine.startLoop(total, fileName, repository), LoopToken.BREAK)) break;
        }
        rep.remove(variableName);
    }

    private void start(float a, float b, float c,
                       String total, String fileName,
                       Map<String, Map<String, Object>>[] repository) {
        for (float i = a; i < b; i += c) {
            if (Objects.equals(StartLine.startLoop(total, fileName, repository), LoopToken.BREAK)) break;
        }
    }

    //DOUBLE
    private void start(double a, double b, double c,
                       String total, String fileName,
                       String variableName,
                       Map<String, Map<String, Object>>[] repository) {
        var rep = repository[0].get(DOUBLE_VARIABLE);
        rep.put(variableName, a);
        for (double repValue; (repValue = (double) rep.get(variableName)) < b; rep.put(variableName, repValue + c)) {
            if (Objects.equals(StartLine.startLoop(total, fileName, repository), LoopToken.BREAK)) break;
        }
        rep.remove(variableName);
    }

    private void start(double a, double b, double c,
                       String total, String fileName,
                       Map<String, Map<String, Object>>[] repository) {
        for (double i = a; i < b; i += c) {
            if (Objects.equals(StartLine.startLoop(total, fileName, repository), LoopToken.BREAK)) break;
        }
    }
}
