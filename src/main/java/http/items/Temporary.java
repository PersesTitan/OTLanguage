package http.items;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public interface Temporary {
    String dateFormat = "yyyy-MM-dd H:mm:ss";

    Map<String, String> portMap = new HashMap<>(); //
    Map<String, String> pathMap = new HashMap<>(); //url, html 경로
    Map<String, Object> POST = new HashMap<>();
    Map<String, Object> GET = new HashMap<>();

    static void startSetting() {
        System.out.printf(
                "%s╭───────────╮╭──╮╭────────╮╭──────────╮╭─────────╮%s\n"+
                "%s│  ╭─────╮  │╰──╯│  ╭─────╯│  ╭────╮  ││  ╭───╮  │%s\n"+
                "%s│  ╰─────╯  │╭──╮│  ╰─────╮│  │    │  ││  │   │  │%s\n"+
                "%s│  ┌────────╯│  │╰─────╮  ││  │    │  ││  │   │  │%s\n"+
                "%s│  │ ╭──────╮│  │╭─────╯  ││  ╰────╯  ││  │   │  │%s\n"+
                "%s│  │ │ ╭──╮ ││  │╰────────╯╰──────────╯╰──╯   ╰──╯%s\n"+
                "%s│  │ │ ╰──╯ ││  │%s        %s== OTLanguage ==%s\n"+
                "%s╰──╯ ╰──────╯╰──╯%s        %s==   Poison   ==%s\n\n",
                Color.PURPLE_BRIGHT, Color.RESET,
                Color.PURPLE_BRIGHT, Color.RESET,
                Color.PURPLE_BRIGHT, Color.RESET,
                Color.PURPLE_BRIGHT, Color.RESET,
                Color.PURPLE_BRIGHT, Color.RESET,
                Color.PURPLE_BRIGHT, Color.RESET,
                Color.PURPLE_BRIGHT, Color.RESET,
                Color.PURPLE, Color.RESET,
                Color.PURPLE_BRIGHT, Color.RESET,
                Color.PURPLE, Color.RESET);

        System.out.printf("%s[%s]%s%s[Poison Server 시작]%s ",
                Color.YELLOW,
                new SimpleDateFormat("yyyy-MM-dd H:mm:ss").format(new Date()),
                Color.RESET, Color.GREEN, Color.RESET);

        Runtime.getRuntime().addShutdownHook(new Thread(() ->
            System.out.printf("%s[%s]%s%s[Poison Server 종료]%s ",
                    Color.YELLOW,
                    new SimpleDateFormat("yyyy-MM-dd H:mm:ss").format(new Date()),
                    Color.RESET, Color.GREEN, Color.RESET)));
    }

    default void printLog(String method, String path, String query) {
        //[2022-07-09 16:42:11] GET  [PATH]  /  | QueryString username=Test
        System.out.printf("%s[%s]%s", Color.YELLOW, new SimpleDateFormat(dateFormat).format(new Date()), Color.RESET);
        if (method.equals("POST")) System.out.printf("%s %s %s", Color.POST_PRINT, method, Color.RESET);
        else System.out.printf("%s %s  %s", Color.GET_PRINT, method, Color.RESET);
        System.out.print(" [경로] ");
        System.out.printf("%s %s %s",Color.QUERY_PRINT, path == null ? "" : path, Color.RESET);
        System.out.print(" | [값] ");
        System.out.printf("%s %s %s\n", Color.QUERY_PRINT, query == null ? "" : query, Color.RESET);
    }
}
