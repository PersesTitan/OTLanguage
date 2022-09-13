package bin.token.cal;

public interface BoolToken {
    // BOOLEAN
    String TRUE = "ㅇㅇ";
    String FALSE = "ㄴㄴ";
    String OR = "ㄸ";
    String AND = "ㄲ";
    String NOT = "ㅇㄴ";

    String BOOL = "(" + TRUE + "|" + FALSE + ")";

    String BIG = "ㅇ>ㅇ";
    String SMALL = "ㅇ<ㅇ";
    String SAME = "ㅇ=ㅇ";
    String BIG_SAME = "ㅇ>=ㅇ";
    String SMALL_SAME = "ㅇ<=ㅇ";
}