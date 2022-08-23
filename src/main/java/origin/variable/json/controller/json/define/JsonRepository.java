package origin.variable.json.controller.json.define;

import org.json.JSONArray;
import org.json.JSONObject;
import origin.exception.MatchException;
import origin.exception.MatchMessage;
import origin.variable.define.VariableCheck;
import origin.variable.define.VariableType;
import tool.Tools;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static event.token.Token.*;

public interface JsonRepository {
    Map<String, JSONArray> jsonArrayRepository = new HashMap<>();
    Map<String, JSONObject> jsonObjectRepository = new HashMap<>();

    default JSONArray getArray(String variableName) {
        return jsonArrayRepository.getOrDefault(variableName, null);
    }

    default JSONObject getObject(String variableName) {
        return jsonObjectRepository.getOrDefault(variableName, null);
    }

    default Object getRepositoryValue(String variableName) {
        String copy = variableName;
        variableName = variableName.substring(1, variableName.length() - 1);
        if (jsonArrayRepository.containsKey(variableName))
            return jsonArrayRepository.get(variableName);
        else if (jsonObjectRepository.containsKey(variableName))
            return jsonObjectRepository.get(variableName);
        else return copy;
    }

    default Object changeValue(String value) {
        if (value.equals("null")) return null;
        if (VariableCheck.check(value, VariableType.Integer))
            return Integer.parseInt(value);
        else if (VariableCheck.check(value, VariableType.Long))
            return Long.parseLong(value);
        else if (VariableCheck.check(value, VariableType.Boolean))
            return Boolean.parseBoolean(value);
        else if (Pattern.compile(CHARACTER_PATTERN).matcher(value).find() &&
                VariableCheck.check(Tools.deleteBothEnds(value), VariableType.Character)) {
            return value.charAt(1);
        } else if (VariableCheck.check(value, VariableType.Float))
            return Float.parseFloat(value);
        else if (VariableCheck.check(value, VariableType.Double))
            return Double.parseDouble(value);
        else {
            if (variableCheck(value)) return getRepositoryValue(value);
            if (Pattern.compile(STRING_PATTERN).matcher(value).find())
                return Tools.deleteBothEnds(value);
            throw new MatchException(MatchMessage.grammarError);
        }
    }

    default boolean variableCheck(String line) {
        return Pattern
                .compile(make(START, VARIABLE, END))
                .matcher(line)
                .find();
    }
}
