package cos.shell.tool;

import bin.exception.VariableException;

import java.util.Collection;

import static bin.token.VariableToken.*;

public interface JavaTool {
    default void check(String variableType, Collection<?> collection) {
        switch (variableType) {
            case SET_INTEGER, LIST_INTEGER, MAP_INTEGER -> collection.forEach(v -> {
                if (!(v instanceof Integer)) throw new VariableException().typeMatch();});
            case SET_LONG, LIST_LONG, MAP_LONG -> collection.forEach(v -> {
                if (!(v instanceof Long)) throw new VariableException().typeMatch();});
            case SET_BOOLEAN, LIST_BOOLEAN, MAP_BOOLEAN -> collection.forEach(v -> {
                if (!(v instanceof Boolean)) throw new VariableException().typeMatch();});
            case SET_STRING, LIST_STRING, MAP_STRING -> collection.forEach(v -> {
                if (!(v instanceof String)) throw new VariableException().typeMatch();});
            case SET_CHARACTER, LIST_CHARACTER, MAP_CHARACTER -> collection.forEach(v -> {
                if (!(v instanceof Character)) throw new VariableException().typeMatch();});
            case SET_FLOAT, LIST_FLOAT, MAP_FLOAT -> collection.forEach(v -> {
                if (!(v instanceof Float)) throw new VariableException().typeMatch();});
            case SET_DOUBLE, LIST_DOUBLE, MAP_DOUBLE -> collection.forEach(v -> {
                if (!(v instanceof Double)) throw new VariableException().typeMatch();});
            default -> throw new VariableException().typeMatch();
        }
    }
}
