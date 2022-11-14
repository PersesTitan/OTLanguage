package etc.v3.sub;

public class SubStringTest {
    public static void main(String[] args) {
        String text = """
                1 as
                3 asdf
                32 232
                """;
        int start = text.indexOf("\n3 ");
        int end = text.indexOf("\n", start + 1);
        System.out.println(start);
        System.out.println(end);
        System.out.println(text.substring(start, end));
    }
}
