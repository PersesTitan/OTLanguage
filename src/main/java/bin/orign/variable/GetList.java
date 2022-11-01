package bin.orign.variable;

import bin.exception.VariableException;
import bin.token.Token;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static bin.check.VariableCheck.*;
import static bin.check.VariableCheck.isListString;
import static bin.token.Token.COMMA;

public interface GetList {
    String SING = COMMA + " \t\n\r\f";

    default LinkedList<String> setBoolList(LinkedList<String> list, String line) {
        if (listCheck(line) && isBoolean(line)) list.add(line);
        else if (isListBoolean(line)) throw VariableException.typeMatch();
        else {
            StringTokenizer t = new StringTokenizer(line.substring(1, line.length()-1), SING);
            while (t.hasMoreTokens()) list.add(t.nextToken());
        }
        return list;
    }

    default LinkedList<Character> setCharacterList(LinkedList<Character> set, String line) {
        if (listCheck(line) && isCharacter(line)) set.add(line.charAt(0));
        else if (isListCharacter(line)) throw VariableException.typeMatch();
        else {
            StringTokenizer t = new StringTokenizer(line.substring(1, line.length()-1), SING);
            while (t.hasMoreTokens()) set.add(t.nextToken().charAt(0));
        }
        return set;
    }

    default LinkedList<Double> setDoubleList(LinkedList<Double> set, String line) {
        if (listCheck(line) && isDouble(line)) set.add(Double.parseDouble(line));
        else if (isListDouble(line)) throw VariableException.typeMatch();
        else {
            StringTokenizer t = new StringTokenizer(line.substring(1, line.length()-1), SING);
            while (t.hasMoreTokens()) set.add(Double.parseDouble(t.nextToken()));
        }
        return set;
    }

    default LinkedList<Float> setFlotList(LinkedList<Float> set, String line) {
        if (listCheck(line) && isFloat(line)) set.add(Float.parseFloat(line));
        else if (isListFloat(line)) throw VariableException.typeMatch();
        else {
            StringTokenizer t = new StringTokenizer(line.substring(1, line.length()-1), SING);
            while (t.hasMoreTokens()) set.add(Float.parseFloat(t.nextToken()));
        }
        return set;
    }

    default LinkedList<Integer> setIntegerList(LinkedList<Integer> set, String line) {
        if (listCheck(line) && isInteger(line)) set.add(Integer.parseInt(line));
        else if (isListInteger(line)) throw VariableException.typeMatch();
        else {
            StringTokenizer t = new StringTokenizer(line.substring(1, line.length()-1), SING);
            while (t.hasMoreTokens()) set.add(Integer.parseInt(t.nextToken()));
        }
        return set;
    }

    default LinkedList<Long> setLongList(LinkedList<Long> set, String line) {
        if (listCheck(line) && isLong(line)) set.add(Long.parseLong(line));
        else if (isListLong(line)) throw VariableException.typeMatch();
        else {
            StringTokenizer t = new StringTokenizer(line.substring(1, line.length()-1), SING);
            while (t.hasMoreTokens()) set.add(Long.parseLong(t.nextToken()));
        }
        return set;
    }

    default LinkedList<String> setStringList(LinkedList<String> set, String line) {
        if (listCheck(line)) set.add(line);
        else if (!isListString(line)) throw VariableException.typeMatch();
        else {
            StringTokenizer t = new StringTokenizer(line.substring(1, line.length()-1), COMMA);
            while (t.hasMoreTokens()) set.add(t.nextToken().strip());
        }
        return set;
    }
}
