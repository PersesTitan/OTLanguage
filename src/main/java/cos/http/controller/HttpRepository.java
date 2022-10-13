package cos.http.controller;

import bin.apply.sys.item.Color;

import java.text.SimpleDateFormat;
import java.util.*;

import static cos.http.controller.HttpMethod.POST;
import static cos.poison.controller.HttpServerManager.httpServer;

public interface HttpRepository {
    String dateFormat = "yyyy-MM-dd H:mm:ss";
    Map<HttpMethod, Map<String, String>> httpRepository = new HashMap<>(); // url, html

    default void startServerPrint() {
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

        System.out.printf("%s[%s]%s%s[Poison Server 시작]%s \n",
                Color.YELLOW,
                new SimpleDateFormat(dateFormat).format(new Date()),
                Color.RESET, Color.GREEN, Color.RESET);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.printf("%s[%s]%s%s[Poison Server 종료]%s \n",
                    Color.YELLOW,
                    new SimpleDateFormat(dateFormat).format(new Date()),
                    Color.RESET, Color.GREEN, Color.RESET);
            httpServer.stop(0);
        }));
    }

    default void printLog(HttpMethod method, String path, String query) {
        //[2022-07-12 18:58:53] GET   [경로]  /  | [값]  name=hi
        System.out.printf("[%s]", new SimpleDateFormat(dateFormat).format(new Date()));
        if (method.equals(POST)) System.out.printf("%s %s %s", Color.POST_PRINT, method, Color.RESET);
        else System.out.printf("%s %s  %s", Color.GET_PRINT, method, Color.RESET);
        System.out.print(" [경로] ");
        System.out.printf("%s %s %s",Color.QUERY_PRINT, path == null ? "" : path, Color.RESET);
        System.out.print(" | [값] ");
        System.out.printf("%s %s %s\n", Color.QUERY_PRINT, query == null ? "" : query, Color.RESET);
    }
}
