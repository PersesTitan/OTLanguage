package bin.apply.calculator.number;

import bin.exception.VariableException;

interface NumberTool {
    static Object sum(Object A, Object B) {
        if (A instanceof Integer a) {
            if (B instanceof Integer b) return a + b;
            else if (B instanceof Long b) return a + b;
            else if (B instanceof Float b) return a + b;
            else if (B instanceof Double b) return a + b;
            else throw VariableException.TYPE_ERROR.getThrow(B);
        } else if (A instanceof Long a) {
            if (B instanceof Integer b) return a + b;
            else if (B instanceof Long b) return a + b;
            else if (B instanceof Float b) return a + b;
            else if (B instanceof Double b) return a + b;
            else throw VariableException.TYPE_ERROR.getThrow(B);
        } else if (A instanceof Float a) {
            if (B instanceof Integer b) return a + b;
            else if (B instanceof Long b) return a + b;
            else if (B instanceof Float b) return a + b;
            else if (B instanceof Double b) return a + b;
            else throw VariableException.TYPE_ERROR.getThrow(B);
        } else if (A instanceof Double a) {
            if (B instanceof Integer b) return a + b;
            else if (B instanceof Long b) return a + b;
            else if (B instanceof Float b) return a + b;
            else if (B instanceof Double b) return a + b;
            else throw VariableException.TYPE_ERROR.getThrow(B);
        } else throw VariableException.TYPE_ERROR.getThrow(A);
    }

    static Object sub(Object A, Object B) {
        if (A instanceof Integer a) {
            if (B instanceof Integer b) return a - b;
            else if (B instanceof Long b) return a - b;
            else if (B instanceof Float b) return a - b;
            else if (B instanceof Double b) return a - b;
            else throw VariableException.TYPE_ERROR.getThrow(B);
        } else if (A instanceof Long a) {
            if (B instanceof Integer b) return a - b;
            else if (B instanceof Long b) return a - b;
            else if (B instanceof Float b) return a - b;
            else if (B instanceof Double b) return a - b;
            else throw VariableException.TYPE_ERROR.getThrow(B);
        } else if (A instanceof Float a) {
            if (B instanceof Integer b) return a - b;
            else if (B instanceof Long b) return a - b;
            else if (B instanceof Float b) return a - b;
            else if (B instanceof Double b) return a - b;
            else throw VariableException.TYPE_ERROR.getThrow(B);
        } else if (A instanceof Double a) {
            if (B instanceof Integer b) return a - b;
            else if (B instanceof Long b) return a - b;
            else if (B instanceof Float b) return a - b;
            else if (B instanceof Double b) return a - b;
            else throw VariableException.TYPE_ERROR.getThrow(B);
        } else throw VariableException.TYPE_ERROR.getThrow(A);
    }

    static Object mul(Object A, Object B) {
        if (A instanceof Integer a) {
            if (B instanceof Integer b) return a * b;
            else if (B instanceof Long b) return a * b;
            else if (B instanceof Float b) return a * b;
            else if (B instanceof Double b) return a * b;
            else throw VariableException.TYPE_ERROR.getThrow(B);
        } else if (A instanceof Long a) {
            if (B instanceof Integer b) return a * b;
            else if (B instanceof Long b) return a * b;
            else if (B instanceof Float b) return a * b;
            else if (B instanceof Double b) return a * b;
            else throw VariableException.TYPE_ERROR.getThrow(B);
        } else if (A instanceof Float a) {
            if (B instanceof Integer b) return a * b;
            else if (B instanceof Long b) return a * b;
            else if (B instanceof Float b) return a * b;
            else if (B instanceof Double b) return a * b;
            else throw VariableException.TYPE_ERROR.getThrow(B);
        } else if (A instanceof Double a) {
            if (B instanceof Integer b) return a * b;
            else if (B instanceof Long b) return a * b;
            else if (B instanceof Float b) return a * b;
            else if (B instanceof Double b) return a * b;
            else throw VariableException.TYPE_ERROR.getThrow(B);
        } else throw VariableException.TYPE_ERROR.getThrow(A);
    }

    static Object div(Object A, Object B) {
        if (A instanceof Integer a) {
            if (B instanceof Integer b) return a / b;
            else if (B instanceof Long b) return a / b;
            else if (B instanceof Float b) return a / b;
            else if (B instanceof Double b) return a / b;
            else throw VariableException.TYPE_ERROR.getThrow(B);
        } else if (A instanceof Long a) {
            if (B instanceof Integer b) return a / b;
            else if (B instanceof Long b) return a / b;
            else if (B instanceof Float b) return a / b;
            else if (B instanceof Double b) return a / b;
            else throw VariableException.TYPE_ERROR.getThrow(B);
        } else if (A instanceof Float a) {
            if (B instanceof Integer b) return a / b;
            else if (B instanceof Long b) return a / b;
            else if (B instanceof Float b) return a / b;
            else if (B instanceof Double b) return a / b;
            else throw VariableException.TYPE_ERROR.getThrow(B);
        } else if (A instanceof Double a) {
            if (B instanceof Integer b) return a / b;
            else if (B instanceof Long b) return a / b;
            else if (B instanceof Float b) return a / b;
            else if (B instanceof Double b) return a / b;
            else throw VariableException.TYPE_ERROR.getThrow(B);
        } else throw VariableException.TYPE_ERROR.getThrow(A);
    }

    static Object rem(Object A, Object B) {
        if (A instanceof Integer a) {
            if (B instanceof Integer b) return a % b;
            else if (B instanceof Long b) return a % b;
            else if (B instanceof Float b) return a % b;
            else if (B instanceof Double b) return a % b;
            else throw VariableException.TYPE_ERROR.getThrow(B);
        } else if (A instanceof Long a) {
            if (B instanceof Integer b) return a % b;
            else if (B instanceof Long b) return a % b;
            else if (B instanceof Float b) return a % b;
            else if (B instanceof Double b) return a % b;
            else throw VariableException.TYPE_ERROR.getThrow(B);
        } else if (A instanceof Float a) {
            if (B instanceof Integer b) return a % b;
            else if (B instanceof Long b) return a % b;
            else if (B instanceof Float b) return a % b;
            else if (B instanceof Double b) return a % b;
            else throw VariableException.TYPE_ERROR.getThrow(B);
        } else if (A instanceof Double a) {
            if (B instanceof Integer b) return a % b;
            else if (B instanceof Long b) return a % b;
            else if (B instanceof Float b) return a % b;
            else if (B instanceof Double b) return a % b;
            else throw VariableException.TYPE_ERROR.getThrow(B);
        } else throw VariableException.TYPE_ERROR.getThrow(A);
    }
}
