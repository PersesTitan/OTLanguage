package etc.enu;

public class EnumStartTest {

    public static void main(String[] args) {
        TestEnum testEnum1 = TestEnum.INFO;
        TestEnum testEnum2 = TestEnum.WARRING;
        System.out.println(testEnum1.ordinal());
        System.out.println(testEnum2.ordinal());
    }

    public enum TestEnum {
        INFO, WARRING, ERROR
    }
}
