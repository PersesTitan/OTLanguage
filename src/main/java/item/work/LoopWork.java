package item.work;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface LoopWork {
    String endWord = "\\n\\s*=\\(\\^ㅇㅅㅇ\\^\\)=\\s*";
    Pattern endPattern = Pattern.compile(endWord);
    String loop = "\\n\\s*(\\?ㅅ\\?|\\^\\^)\\s|^\\s*(\\?ㅅ\\?|\\^\\^)\\s";
    Pattern pattern = Pattern.compile(loop);

    default int countBlank(String line) {
        int count = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) != ' ') break;
            count++;
        } return count;
    }

    default boolean check(String total) {
        return pattern.matcher(total).find();
    }

    default LoopPosition countPattern(String total) {
        int count = 0;
        int position = 0;
        Matcher matcher = pattern.matcher(total);
        while (matcher.find()) {
            String text = matcher.group();
            int i = countBlank(text);
            if (count < i) {
                count = i;
                position = matcher.start();
            }
        }
        return new LoopPosition(count, position);
    }
}
