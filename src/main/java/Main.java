import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {

        String SPECIFIED = "ㅇㅅㅇ";
        String line = "ㅇㅅㅇ ㅇㄹ1:ㄴㅇㄹㄴ";
        int start = line.indexOf(SPECIFIED) + SPECIFIED.length();
        int end = line.indexOf(":");
        String key = line.substring(start, end).strip();
        String value = line.substring(end+1);
        System.out.println(key);
        System.out.println(value);
    }
}
