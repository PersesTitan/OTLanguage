package cos.poison.controller;

import bin.apply.sys.item.Color;

public enum HttpMethod {
    POST(Color.POST_PRINT),
    GET(Color.GET_PRINT);

    private static final int maxLen = 4;
    private final String name;
    HttpMethod(String color) {
        this.name = color.concat(" ")
                .concat(this.name())
                .concat(" ".repeat(maxLen - this.name().length() + 1))
                .concat(Color.RESET);
    }

    public String getName() {
        return this.name;
    }
}
