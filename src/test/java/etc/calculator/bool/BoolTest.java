package etc.calculator.bool;

public class BoolTest extends BoolCalTest {
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
        System.out.println(orAnd("(ㅇㅇ ㄸ ㄴㄴ) ㄸ (ㅇㅇ ) ㄲ (ㅇㅇ ㄸ ㄴㄴ)ㄲㅇㅇ"));
    }
}
