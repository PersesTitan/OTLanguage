package system.start;

import event.token.BoolCalculator;
import event.token.NumberCalculator;

import java.util.regex.Pattern;

public class OSSetting {
    public static void main(String[] args) {
//        String s = BoolCalculator.getBool("(ㅇㄴ ㅇㅇ) ㄸ 24 ㅇ>ㅇ 34 ㄲ (234 ㅇ=ㅇ 34 ㄲ (ㅇㅇ ㄸ (ㄴㄴ  ㄲ ㅇㅇ)))");
//        System.out.println(s);
//        System.out.println((!true) || 24>34 && (234==34 || (true && (false && true))));
//        System.out.println(NumberCalculator.getNumber("1ㅇ+ㅇ 1"));
//        System.out.println(NumberCalculator.getNumber("1 ㅇ+ㅇ 1 ㅇ-ㅇ 1"));

        System.out.print("입력 : ");
        System.out.println("1 ㅇ+ㅇ (1 ㅇ-ㅇ (1 ㅇ*ㅇ 10))");
        System.out.print("결과 : " + NumberCalculator.getNumber("1 ㅇ+ㅇ (1 ㅇ-ㅇ (1 ㅇ*ㅇ 10))"));
    }

    // 슬래쉬 반환
    public static String sep() {
        return System.getProperty("file.separator");
    }
}
