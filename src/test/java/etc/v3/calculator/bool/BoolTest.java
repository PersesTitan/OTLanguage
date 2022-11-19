package etc.v3.calculator.bool;

import bin.apply.Setting;
import bin.token.VariableToken;
import etc.v3.calculator.token.BlankV3Test;

import static bin.apply.Repository.repository;

public class BoolTest extends BoolCalTest implements BlankV3Test {
    public static void main(String[] args) {
        new BoolTest();
//        long start = System.currentTimeMillis();
//        int count = 10000000;
//        Matcher matcher = Pattern.compile("a").matcher("");
//        for (int i = 0; i<count; i++) {
//            if (matcher.reset("123455").find()) "123455".replaceAll("a", "");
//        }
//        System.out.println(System.currentTimeMillis() - start);
//        start = System.currentTimeMillis();
//        for (int i = 0; i<count; i++) {
//            "123455".replaceAll("1", "");
//        }
//        System.out.println(System.currentTimeMillis() - start);

    }

    public BoolTest() {
        System.out.println("()asdfs(123,324,3)".substring("() (123,324,3)".lastIndexOf("(")));
        int a = "()asdfs(123,324,3)".lastIndexOf("(");
        System.out.println("()asdfs(123,324,3)".substring(0, a));
        new Setting();
        repository.get(0).get(VariableToken.BOOL_VARIABLE).put("a", "ㅇㅇ");
        repository.get(0).get(VariableToken.BOOL_VARIABLE).put("b", "ㄴㄴ");
        String line = "(1234 ㅇ+ㅇ 1) ㅇ-ㅇ1";
        String line1 = "1ㅇ+ㅇ13 ㅇ>ㅇ 14 ㄲ ㅇㅇ";
        System.out.println(getNumber(line, repository));
        System.out.println(getBool(line1, repository));
    }
}
