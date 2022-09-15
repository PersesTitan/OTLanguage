package bin.orign.variable.map.get;

import bin.exception.VariableException;
import bin.token.Token;
import org.apache.arrow.flatbuf.Int;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static bin.check.VariableCheck.*;

public interface GetMap {
    default Map<String, String> getBoolMap(String line) {
        if (!isMapBoolean(line)) throw VariableException.typeMatch();
        Map<String, String> map = new LinkedHashMap<>();
        Pattern.compile(Token.COMMA)
                .splitAsStream(line.substring(1, line.length()-1))
                .map(String::trim)
                .filter(Predicate.not(String::isEmpty))
                .map(v -> v.split("=", 2))
                .forEach(v -> map.put(v[0], v[1]));
        return map;
    }

    default Map<String, Character> getCharacterMap(String line) {
        if (!isMapCharacter(line)) throw VariableException.typeMatch();
        Map<String, Character> map = new LinkedHashMap<>();
        Pattern.compile(Token.COMMA)
                .splitAsStream(line.substring(1, line.length()-1))
                .map(String::trim)
                .filter(Predicate.not(String::isEmpty))
                .map(v -> v.split("=", 2))
                .forEach(v -> map.put(v[0], v[1].charAt(0)));
        return map;
    }

    default Map<String, Double> getDoubleMap(String line) {
        if (!isMapDouble(line)) throw VariableException.typeMatch();
        Map<String, Double> map = new LinkedHashMap<>();
        Pattern.compile(Token.COMMA)
                .splitAsStream(line.substring(1, line.length()-1))
                .map(String::trim)
                .filter(Predicate.not(String::isEmpty))
                .map(v -> v.split("=", 2))
                .forEach(v -> map.put(v[0], Double.parseDouble(v[1])));
        return map;
    }

    default Map<String, Float> getFlotMap(String line) {
        if (!isMapFloat(line)) throw VariableException.typeMatch();
        Map<String, Float> map = new LinkedHashMap<>();
        Pattern.compile(Token.COMMA)
                .splitAsStream(line.substring(1, line.length()-1))
                .map(String::trim)
                .filter(Predicate.not(String::isEmpty))
                .map(v -> v.split("=", 2))
                .forEach(v -> map.put(v[0], Float.parseFloat(v[1])));
        return map;
    }

    default Map<String, Integer> getIntegerMap(String line) {
        if (!isMapInteger(line)) throw VariableException.typeMatch();
        Map<String, Integer> map = new LinkedHashMap<>();
        Pattern.compile(Token.COMMA)
                .splitAsStream(line.substring(1, line.length()-1))
                .map(String::trim)
                .filter(Predicate.not(String::isEmpty))
                .map(v -> v.split("=", 2))
                .forEach(v -> map.put(v[0], Integer.parseInt(v[1])));
        return map;
    }

    default Map<String, Long> getLongMap(String line) {
        if (!isMapLong(line)) throw VariableException.typeMatch();
        Map<String, Long> map = new LinkedHashMap<>();
        Pattern.compile(Token.COMMA)
                .splitAsStream(line.substring(1, line.length()-1))
                .map(String::trim)
                .filter(Predicate.not(String::isEmpty))
                .map(v -> v.split("=", 2))
                .forEach(v -> map.put(v[0], Long.parseLong(v[1])));
        return map;
    }

    default Map<String, String> getStringMap(String line) {
        if (!isMapLong(line)) throw VariableException.typeMatch();
        Map<String, String> map = new LinkedHashMap<>();
        Pattern.compile(Token.COMMA)
                .splitAsStream(line.substring(1, line.length()-1))
                .map(String::trim)
                .filter(Predicate.not(String::isEmpty))
                .map(v -> v.split("=", 2))
                .forEach(v -> map.put(v[0], v[1]));
        return map;
    }
}
