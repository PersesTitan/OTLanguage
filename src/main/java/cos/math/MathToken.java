package cos.math;

public interface MathToken {
    String MATH = "ㅅㅎㅅ";

    String SIN = "ㅅㅆㅅ";
    String COS = "ㅋㅆㅋ";
    String TAN = "ㅌㅆㅌ";

    String CEIL  = "ㅇㄹㅇ";   // 올림
    String FLOOR = "ㅂㄹㅂ";   // 버림
    String ROUND_F = "ㄴㅅㄴ";  // 반올림 (float)
    String ROUND_D = "ㄴㅆㄴ";  // 반올림 (double)

    String POW = "ㅈㄱㅈ"; // 제곱 연산 (5,2 => 25)
    String SQRT = "ㅈㄴㅈ"; // 제곱근 (25 => 5)
    String EXP = "ㅈㅅㅈ"; // E 제곱근
    String LOG = "ㄹㄱㄹ";

    String E = ">ㅈㅅㅈ";
    String PI = ">ㅍㅇㅍ";

    String ABS_I = "ㅈㄷㅈ";
    String ABS_L = "ㅉㄷㅉ";
    String ABS_F = "ㅅㄷㅅ";
    String ABS_D = "ㅆㄷㅆ";

    String RANDOM_I = "@ㅈ@";
    String RANDOM_L = "@ㅉ@";
    String RANDOM_F = "@ㅅ@";
    String RANDOM_D = "@ㅆ@";
    String RANDOM_B = "@ㅂ@";
}
