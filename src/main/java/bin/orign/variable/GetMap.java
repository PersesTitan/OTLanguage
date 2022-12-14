package bin.orign.variable;

import bin.exception.VariableException;

import java.util.*;

import static bin.check.VariableCheck.*;
import static bin.token.Token.COMMA;

public interface GetMap {
    default LinkedHashMap<String, String> setBoolMap(LinkedHashMap<String, String> map, String line) {
        if (!isMapBoolean(line)) throw new VariableException().typeMatch();
        else {
            StringTokenizer t = new StringTokenizer(line.substring(1, line.length()-1), COMMA);
            while (t.hasMoreTokens()) {
                String[] tokens = t.nextToken()
                        .strip()
                        .split("=", 2);
                map.put(tokens[0], tokens[1]);
            }
        }
        return map;
    }

    default LinkedHashMap<String, Character> setCharacterMap(LinkedHashMap<String, Character> map, String line) {
        if (!isMapCharacter(line)) throw new VariableException().typeMatch();
        else {
            StringTokenizer t = new StringTokenizer(line.substring(1, line.length()-1), COMMA);
            while (t.hasMoreTokens()) {
                String[] tokens = t.nextToken()
                        .strip()
                        .split("=", 2);
                map.put(tokens[0], getCharacter(t.nextToken()));
            }
        }
        return map;
    }

    default LinkedHashMap<String, Double> setDoubleMap(LinkedHashMap<String, Double> map, String line) {
        if (!isMapDouble(line)) throw new VariableException().typeMatch();
        else {
            StringTokenizer t = new StringTokenizer(line.substring(1, line.length()-1), COMMA);
            while (t.hasMoreTokens()) {
                String[] tokens = t.nextToken()
                        .strip()
                        .split("=", 2);
                map.put(tokens[0], Double.parseDouble(tokens[1]));
            }
        }
        return map;
    }

    default LinkedHashMap<String, Float> setFlotMap(LinkedHashMap<String, Float> map, String line) {
        if (!isMapFloat(line)) throw new VariableException().typeMatch();
        else {
            StringTokenizer t = new StringTokenizer(line.substring(1, line.length()-1), COMMA);
            while (t.hasMoreTokens()) {
                String[] tokens = t.nextToken()
                        .strip()
                        .split("=", 2);
                map.put(tokens[0], Float.parseFloat(tokens[1]));
            }
        }
        return map;
    }

    default LinkedHashMap<String, Integer> setIntegerMap(LinkedHashMap<String, Integer> map, String line) {
        if (!isMapInteger(line)) throw new VariableException().typeMatch();
        else {
            StringTokenizer t = new StringTokenizer(line.substring(1, line.length()-1), COMMA);
            while (t.hasMoreTokens()) {
                String[] tokens = t.nextToken()
                        .strip()
                        .split("=", 2);
                map.put(tokens[0], getInteger(tokens[1]));
            }
        }
        return map;
    }

    default LinkedHashMap<String, Long> setLongMap(LinkedHashMap<String, Long> map, String line) {
        if (!isMapLong(line)) throw new VariableException().typeMatch();
        else {
            StringTokenizer t = new StringTokenizer(line.substring(1, line.length()-1), COMMA);
            while (t.hasMoreTokens()) {
                String[] tokens = t.nextToken()
                        .strip()
                        .split("=", 2);
                map.put(tokens[0], Long.parseLong(tokens[1]));
            }
        }
        return map;
    }

    default LinkedHashMap<String, String> setStringMap(LinkedHashMap<String, String> map, String line) {
        if (!isMapString(line)) throw new VariableException().typeMatch();
        else {
            StringTokenizer t = new StringTokenizer(line.substring(1, line.length()-1), COMMA);
            while (t.hasMoreTokens()) {
                String[] tokens = t.nextToken()
                        .strip()
                        .split("=", 2);
                map.put(tokens[0], tokens[1]);
            }
        }
        return map;
    }
}
