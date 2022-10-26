package bin.orign.variable.both;

import bin.check.VariableType;
import bin.exception.VariableException;
import org.apache.hadoop.hdfs.protocol.datatransfer.sasl.DataEncryptionKeyFactory;

import java.util.Collection;

import static bin.check.VariableTypeCheck.getVariableType;
import static bin.token.cal.BoolToken.FALSE;
import static bin.token.cal.BoolToken.TRUE;

public interface ContainsTool {
    default String collectionCheck(Collection<?> collection, String variableType, String value) {
        return collection.contains(getVariable(getVariableType(variableType), value)) ? TRUE : FALSE;
    }

    // variableType : 변수 타입
    // value : 값
    private Object getVariable(VariableType variableType, String value) {
        // Boolean, Character, Double, Float, Integer, Long, String
        String variable = variableType.name();
        try {
            if (variable.startsWith("Map")) return value;
            else if (variable.endsWith("Character")) {
                if (value.length() != 1) throw VariableException.typeMatch();
                else return value;
            } else if (variable.endsWith("Double")) return Double.parseDouble(value);
            else if (variable.endsWith("Float")) return Float.parseFloat(value);
            else if (variable.endsWith("Integer")) return Integer.parseInt(value);
            else if (variable.endsWith("Long")) return Long.parseLong(value);
            else return value;
        } catch (Exception e) {throw VariableException.typeMatch();}
    }

    // 리스트, 셋 종합 구하기
    default String collectionSum(Collection<?> collection, String variableType) {
        return getSum(getVariableType(variableType), collection);
    }

    private String getSum(VariableType variableType, Collection<?> collection) {
        // Boolean, Character, Double, Float, Integer, Long, String
        String variable = variableType.name();
        try {
            if (variable.endsWith("Float")) return String.valueOf(collection.stream().mapToDouble(v -> (float) v).sum());
            else if (variable.endsWith("Double")) return String.valueOf(collection.stream().mapToDouble(v -> (double) v).sum());
            else if (variable.endsWith("Integer")) return String.valueOf(collection.stream().mapToInt(v -> (int) v).sum());
            else if (variable.endsWith("Long")) return String.valueOf(collection.stream().mapToLong(v -> (long) v).sum());
            throw VariableException.typeMatch();
        } catch (Exception e) {throw VariableException.typeMatch();}
    }
}
