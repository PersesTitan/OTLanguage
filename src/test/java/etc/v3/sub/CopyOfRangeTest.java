package etc.v3.sub;

import java.util.Arrays;

public class CopyOfRangeTest {
    public static void main(String[] args) {
        String[] str = new String[] {"a", "b"};
        System.out.println(Arrays.toString(Arrays.copyOfRange(str, 1, str.length - 1)));
    }
}
