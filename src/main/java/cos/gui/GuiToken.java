package cos.gui;

import cos.color.ColorToken;

interface GuiToken {
    String ICON = "icon.otlm";

    String GUI = "ㄱㅁㄱ";
    String EVENT = GUI + "-ㅇㅃㅇ";

    String COLOR = ColorToken.COLOR;

    String BUTTON           = "ㅂㅇㅂ";
    String CHECK_BOX        = "ㅊㅋㅊ";
    String RADIO_BUTTON     = "ㄹㅂㄹ";
    String TEXT_FIELD       = "ㅌㅍㅌ";
    String PASSWORD_FILED   = "ㅍㅍㅍ";
    String TEXT_AREA        = "ㅌㅇㅌ";

    // EVENT
    String GET_SOURCE = ">ㅆㅅㅆ";

    // METHOD
    String ADD = "<<";
    String ADD_EVENT = "<ㅇㅃㅇ";
    String ADD_KEY = "<ㅋㅂㅋ";
    String ADD_MOUSE = "<ㅁㅇㅁ";

    String SET_MODEL = "<ㅁㄷㅁ";

    String SET_VISIBLE = "<ㅂㅈㅂ";
    String SET_ENABLE = "<ㅎㅅㅎ";
    String SET_BG = "<ㅂㄱㅂ";
    String SET_SIZE = "<ㅆㅈㅆ";
    String SET_WIDTH = "<ㄴㅂㄴ";  // 너비
    String SET_HEIGHT = "<ㄴㅍㄴ"; // 높이
    String SET_X = "<ㄱㄹㄱ";      // 가로
    String SET_Y = "<ㅅㄹㅅ";      // 세로
    String SET_TEXT = "<ㅌㅅㅌ";
    String SET_NAME = "<ㅇㄹㅇ";

    String GET_VISIBLE = ">ㅂㅈㅂ";
    String GET_ENABLE = ">ㅎㅅㅎ";
    String GET_BG = ">ㅂㄱㅂ";
    String GET_SIZE = ">ㅆㅈㅆ";
    String GET_WIDTH = ">ㄴㅂㄴ";  // 너비
    String GET_HEIGHT = ">ㄴㅍㄴ"; // 높이
    String GET_X = ">ㄱㄹㄱ";      // 가로 (X)
    String GET_Y = ">ㅅㄹㅅ";      // 세로 (Y)
    String GET_TEXT = ">ㅌㅅㅌ";
    String GET_NAME = ">ㅇㄹㅇ";

    String GET_LOCATION = ">ㄹㅋㄹ";
}
