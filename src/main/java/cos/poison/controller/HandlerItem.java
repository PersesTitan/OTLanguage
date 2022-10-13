package cos.poison.controller;

import cos.http.controller.HttpMethod;

public record HandlerItem(String fileName, String startFinalTotal, String[][] params,
                          HttpMethod method, String html,
                          String contentType) { }
