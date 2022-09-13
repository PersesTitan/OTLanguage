package bin.token;

import java.util.Arrays;

import static bin.token.Token.*;

public interface MergeToken {
    default String merge(String...texts) {
        StringBuilder builder = new StringBuilder();
        Arrays.stream(texts).forEach(builder::append);
        return builder.toString();
    }

    default String startEndMerge(String...texts) {
        StringBuilder builder = new StringBuilder(START);
        builder.append(BLANK);
        Arrays.stream(texts).forEach(builder::append);
        builder.append(BLANK).append(END);
        return builder.toString();
    }

    default String orMerge(String...texts) {
        return "("+String.join("|", texts)+")";
    }

    default String startMerge(String...texts) {
        return START + BLANK + String.join("", texts);
    }

    default String blackMerge(String...texts) {
        return String.join(BLANK, texts);
    }

    default String blacksMerge(String...texts) {
        return String.join(BLANKS, texts);
    }

    // 양쪽 끝 삭제
    default String bothEndCut(String text, int start, int end) {
        return text.substring(start, text.length()-end);
    }

    default String bothEndCut(String text) {
        return bothEndCut(text, 1, 1);
    }

    // Iterable
    default String orMerge(Iterable<? extends CharSequence> elements) {
        return "(" + String.join("|", elements) + ")";
    }
}
