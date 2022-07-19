package http.items;

import http.define.HttpMethodType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public interface HttpRepository {
    String dateFormat = "yyyy-MM-dd H:mm:ss";
    Map<String, String> partMap = new HashMap<>(); //http 에 출력될 변수
    Map<String, String> pathMap = new HashMap<>(); //url, html 경로
    Map<String, HashMap<String, Object>> POST = new HashMap<>(); //post 로 받은 값 저장
    Map<String, HashMap<String, Object>> GET = new HashMap<>(); //get 으로 받은 값 저장

    static void startSetting() {
        System.out.printf(
                """
                %s╭───────────╮╭──╮╭────────╮╭──────────╮╭─────────╮%s
                %s│  ╭─────╮  │╰──╯│  ╭─────╯│  ╭────╮  ││  ╭───╮  │%s
                %s│  ╰─────╯  │╭──╮│  ╰─────╮│  │    │  ││  │   │  │%s
                %s│  ┌────────╯│  │╰─────╮  ││  │    │  ││  │   │  │%s
                %s│  │ ╭──────╮│  │╭─────╯  ││  ╰────╯  ││  │   │  │%s
                %s│  │ │ ╭──╮ ││  │╰────────╯╰──────────╯╰──╯   ╰──╯%s
                %s│  │ │ ╰──╯ ││  │%s        %s== OTLanguage ==%s
                %s╰──╯ ╰──────╯╰──╯%s        %s==   Poison   ==%s

                """,
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
                new SimpleDateFormat(dateFormat).format(new Date()),
                Color.RESET, Color.GREEN, Color.RESET);

        Runtime.getRuntime().addShutdownHook(new Thread(() ->
                System.out.printf("%s[%s]%s%s[Poison Server 종료]%s \n",
                        Color.YELLOW,
                        new SimpleDateFormat(dateFormat).format(new Date()),
                        Color.RESET, Color.GREEN, Color.RESET)));
    }

    default void printLog(HttpMethodType method, String path, String query) {
        //[2022-07-12 18:58:53] GET   [경로]  /  | [값]  name=hi
        System.out.printf("%s[%s]%s", Color.YELLOW, new SimpleDateFormat(dateFormat).format(new Date()), Color.RESET);
        if (method.equals(HttpMethodType.POST)) System.out.printf("%s %s %s", Color.POST_PRINT, method, Color.RESET);
        else System.out.printf("%s %s  %s", Color.GET_PRINT, method, Color.RESET);
        System.out.print(" [경로] ");
        System.out.printf("%s %s %s",Color.QUERY_PRINT, path == null ? "" : path, Color.RESET);
        System.out.print(" | [값] ");
        System.out.printf("%s %s %s\n", Color.QUERY_PRINT, query == null ? "" : query, Color.RESET);
    }
}
