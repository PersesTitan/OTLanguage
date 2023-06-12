package bin.variable.custom;

import bin.apply.mode.TypeMode;
import bin.exception.VariableException;
import bin.token.CastingToken;

import java.util.stream.Stream;

interface CustomTool {
    default <T> Object sum(TypeMode types, Stream<T> stream, String klassType) {
        return switch (types) {
            case CHARACTER, INTEGER -> stream.mapToInt(CastingToken::getInt).sum();
            case LONG -> stream.mapToLong(CastingToken::getLong).sum();
            case FLOAT -> (float) stream.mapToDouble(CastingToken::getFloat).sum();
            case DOUBLE -> stream.mapToDouble(CastingToken::getDouble).sum();
            case BOOLEAN, STRING -> throw VariableException.TYPE_ERROR.getThrow(klassType);
        };
    }

    default <T> Object max(TypeMode types, Stream<T> stream, String klassType) {
        return switch (types) {
            case INTEGER -> stream.mapToInt(CastingToken::getInt).max().orElse(0);
            case LONG -> stream.mapToLong(CastingToken::getLong).max().orElse(0L);
            case DOUBLE -> stream.mapToDouble(CastingToken::getDouble).max().orElse(0d);
            case FLOAT -> (float) stream.mapToDouble(CastingToken::getDouble).max().orElse(0f);
            case CHARACTER -> (char) stream.mapToInt(CastingToken::getInt).max().orElse(0);
            case BOOLEAN, STRING -> throw VariableException.TYPE_ERROR.getThrow(klassType);
        };
    }

    default <T> Object min(TypeMode types, Stream<T> stream, String klassType) {
        return switch (types) {
            case INTEGER -> stream.mapToInt(CastingToken::getInt).min().orElse(0);
            case LONG -> stream.mapToLong(CastingToken::getLong).min().orElse(0L);
            case DOUBLE -> stream.mapToDouble(CastingToken::getDouble).min().orElse(0d);
            case FLOAT -> (float) stream.mapToDouble(CastingToken::getFloat).min().orElse(0f);
            case CHARACTER -> (char) stream.mapToInt(CastingToken::getInt).max().orElse(0);
            case BOOLEAN, STRING -> throw VariableException.TYPE_ERROR.getThrow(klassType);
        };
    }
}
