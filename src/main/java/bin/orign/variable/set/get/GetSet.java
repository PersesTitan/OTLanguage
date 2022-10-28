package bin.orign.variable.set.get;

import bin.exception.VariableException;

import java.util.LinkedHashSet;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static bin.check.VariableCheck.*;
import static bin.token.Token.*;

public interface GetSet {
    String SING = COMMA + " \t\n\r\f";

    default LinkedHashSet<String> setBoolSet(LinkedHashSet<String> set, String line) {
        if (listCheck(line) && isBoolean(line)) set.add(line);
        else if (isListBoolean(line)) throw VariableException.typeMatch();
        else {
            StringTokenizer t = new StringTokenizer(line.substring(1, line.length()-1), SING);
            while (t.hasMoreTokens()) set.add(t.nextToken());
        }
        return set;
    }
    
    default LinkedHashSet<Character> setCharacterSet(LinkedHashSet<Character> set, String line) {
        if (listCheck(line) && isCharacter(line)) set.add(line.charAt(0));
        else if (isListCharacter(line)) throw VariableException.typeMatch();
        else {
            StringTokenizer t = new StringTokenizer(line.substring(1, line.length()-1), SING);
            while (t.hasMoreTokens()) set.add(t.nextToken().charAt(0));
        }
        return set;
    }

    default LinkedHashSet<Double> setDoubleSet(LinkedHashSet<Double> set, String line) {
        if (listCheck(line) && isDouble(line)) set.add(Double.parseDouble(line));
        else if (isListDouble(line)) throw VariableException.typeMatch();
        else {
            StringTokenizer t = new StringTokenizer(line.substring(1, line.length()-1), SING);
            while (t.hasMoreTokens()) set.add(Double.parseDouble(t.nextToken()));
        }
        return set;
    }

    default LinkedHashSet<Float> setFlotSet(LinkedHashSet<Float> set, String line) {
        if (listCheck(line) && isFloat(line)) set.add(Float.parseFloat(line));
        else if (isListFloat(line)) throw VariableException.typeMatch();
        else {
            StringTokenizer t = new StringTokenizer(line.substring(1, line.length()-1), SING);
            while (t.hasMoreTokens()) set.add(Float.parseFloat(t.nextToken()));
        }
        return set;
    }

    default LinkedHashSet<Integer> setIntegerSet(LinkedHashSet<Integer> set, String line) {
        if (listCheck(line) && isInteger(line)) set.add(Integer.parseInt(line));
        else if (isListInteger(line)) throw VariableException.typeMatch();
        else {
            StringTokenizer t = new StringTokenizer(line.substring(1, line.length()-1), SING);
            while (t.hasMoreTokens()) set.add(Integer.parseInt(t.nextToken()));
        }
        return set;
    }

    default LinkedHashSet<Long> setLongSet(LinkedHashSet<Long> set, String line) {
        if (listCheck(line) && isLong(line)) set.add(Long.parseLong(line));
        else if (isListLong(line)) throw VariableException.typeMatch();
        else {
            StringTokenizer t = new StringTokenizer(line.substring(1, line.length()-1), SING);
            while (t.hasMoreTokens()) set.add(Long.parseLong(t.nextToken()));
        }
        return set;
    }

    default LinkedHashSet<String> setStringSet(LinkedHashSet<String> set, String line) {
        if (listCheck(line)) set.add(line);
        else if (!isListString(line)) throw VariableException.typeMatch();
        else {
            StringTokenizer t = new StringTokenizer(line.substring(1, line.length()-1), COMMA);
            while (t.hasMoreTokens()) set.add(t.nextToken().strip());
        }
        return set;
    }
}
