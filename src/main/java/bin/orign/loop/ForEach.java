package bin.orign.loop;

import bin.apply.sys.make.StartLine;
import bin.check.VariableCheck;
import bin.exception.VariableException;
import bin.orign.GetSetVariable;
import bin.orign.variable.list.get.GetList;
import bin.token.LoopToken;
import work.StartWork;

import java.util.*;
import java.util.regex.Pattern;

import static bin.check.VariableCheck.listCheck;

public class ForEach extends GetSetVariable implements StartWork, LoopToken, GetList {
    private final String patternText;
    private final Pattern pattern;

    public ForEach() {
        String black = blackMerge(FOR, "(" + ARGUMENT + "|", VARIABLE_ACCESS + ")", FOR);
        this.patternText = startEndMerge(black, BLANKS, BRACE_STYLE(), BLANKS, PUTIN);
        this.pattern = Pattern.compile(this.patternText);
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line, String origen,
                      Map<String, Map<String, Object>>[] repositoryArray) {
        // ^[1, 2, 3]^ (test,1,10), ㅇㅁㅇ 변수명
        String[] groups = matchSplitError(line.strip(), PUTIN_TOKEN, 2);
        String[] forEachTokens = matchSplitError(groups[0], FOR, 3); // [, [1, 2, 3], (test,1,10)]
        String[] variableToken = matchSplitError(groups[1], BLANKS, 2); // ㅇㅁㅇ, 변수명

        // Total
        String[] variables = matchSplitError(bothEndCut(forEachTokens[2].strip()), ",", 3);
        String total = LOOP_TOKEN.get(variables[0]);
        int start = total.indexOf("\n" + variables[1] + " ");
        int end = total.indexOf("\n" + variables[2] + " ");
        total = total.substring(start, end);  // (test,1,10) => total

        String listValue = forEachTokens[1].strip(); // [1, 2, 3], 변수명
        String variableType = variableToken[0].strip(); // 들어가는 변수 타입
        String variableName = variableToken[1].strip(); // 들어가는 변수명

        int count = accessCount(listValue);
        listValue = listValue.substring(count);
        if (count > repositoryArray.length) throw VariableException.localNoVariable();
        var rep = repositoryArray[count];
        variableDefineError(variableName, rep);

        // ^^문 들어가는 변수
        if (!ORIGIN_LIST.contains(variableType)) throw VariableException.forTypeMatchError();
        if (listCheck(listValue)) {
            for (Map.Entry<String, Map<String, Object>> entry : rep.entrySet()) {
                if (entry.getValue().containsKey(listValue)) {
                    if (SET_LIST.contains(entry.getKey())) {
                        Set<Object> set = (Set<Object>) rep.get(entry.getKey()).get(listValue);
                        if (getCheck(set, variableType)) throw VariableException.typeMatch();
                        loop(rep.get(variableType), variableName, set, total, variables[0], repositoryArray);
                        return;
                    } else if (LIST_LIST.contains(entry.getKey())) {
                        List<Object> list = (List<Object>) rep.get(entry.getKey()).get(listValue);
                        if (getCheck(list, variableType)) throw VariableException.typeMatch();
                        loop(rep.get(variableType), variableName, list, total, variables[0], repositoryArray);
                        return;
                    } else throw VariableException.typeMatch();
                }
            }
        } else {
            List<Object> list = (List<Object>) changeList(listValue, variableType);
            loop(rep.get(variableType), variableName, list, total, variables[0], repositoryArray);
        }
    }

    @SafeVarargs
    private void loop(Map<String, Object> rep, String variableName,
                      Set<Object> set, String total, String fileName,
                      Map<String, Map<String, Object>>... repository) {
        for (Object var : set) {
            rep.put(variableName, var);
            if (Objects.equals(StartLine.startLoop(total, fileName, repository), LoopToken.BREAK)) break;
        }
        rep.remove(variableName);
    }

    @SafeVarargs
    private void loop(Map<String, Object> rep, String variableName,
                      List<Object> list, String total, String fileName,
                      Map<String, Map<String, Object>>... repository) {
        for (Object var : list) {
            rep.put(variableName, var);
            if (Objects.equals(StartLine.startLoop(total, fileName, repository), LoopToken.BREAK)) break;
        }
        rep.remove(variableName);
    }

    private LinkedList<?> changeList(String listValue, String variableType) {
        return switch (variableType) {
            case INT_VARIABLE -> getIntegerList(listValue);
            case LONG_VARIABLE -> getLongList(listValue);
            case BOOL_VARIABLE -> getBoolList(listValue);
            case CHARACTER_VARIABLE -> getCharacterList(listValue);
            case FLOAT_VARIABLE -> getFlotList(listValue);
            case DOUBLE_VARIABLE -> getDoubleList(listValue);
            default -> getStringList(listValue);
        };
    }

    private boolean getCheck(Collection<Object> list, String variableType) {
        return !switch (variableType) {
            case INT_VARIABLE -> list.stream().map(Object::toString).allMatch(VariableCheck::isInteger);
            case LONG_VARIABLE -> list.stream().map(Object::toString).allMatch(VariableCheck::isLong);
            case BOOL_VARIABLE -> list.stream().map(Object::toString).allMatch(VariableCheck::isBoolean);
            case CHARACTER_VARIABLE -> list.stream().map(Object::toString).allMatch(VariableCheck::isCharacter);
            case FLOAT_VARIABLE -> list.stream().map(Object::toString).allMatch(VariableCheck::isFloat);
            case DOUBLE_VARIABLE -> list.stream().map(Object::toString).allMatch(VariableCheck::isDouble);
            default -> true;
        };
    }
}
