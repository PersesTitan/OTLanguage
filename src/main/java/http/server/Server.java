package http.server;

import http.items.Color;
import http.items.Temporary;

public class Server implements Temporary {

    public Server() {
        HttpServerManager httpServerManager = new HttpServerManager();
        try {
            httpServerManager.start();
            if (pathMap.isEmpty()) System.out.printf("%s경로가 존재하지 않습니다.%s\n", Color.red, Color.exit);
            else {
                while (true) {}
            }
        } finally {
            httpServerManager.stop();
        }
    }

    public Server(int port) {
        HttpServerManager httpServerManager = new HttpServerManager(port);
        try {
            httpServerManager.start();
            if (pathMap.isEmpty()) System.out.printf("%s경로가 존재하지 않습니다.%s\n", Color.red, Color.exit);
            else {
                while (true) {}
            }
        } finally {
            httpServerManager.stop();
        }
    }
}
