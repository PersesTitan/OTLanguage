package bin.apply.sys.make;

import bin.exception.MatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public interface ChangeHangle {
    List<Character> ChoList = new ArrayList<>(List.of('ㄱ','ㄲ','ㄴ','ㄷ','ㄸ','ㄹ','ㅁ','ㅂ','ㅃ','ㅅ','ㅆ','ㅇ','ㅈ','ㅉ','ㅊ','ㅋ','ㅌ','ㅍ','ㅎ'));
    List<Character> JoongList = new ArrayList<>(List.of('ㅏ','ㅐ','ㅑ','ㅒ','ㅓ','ㅔ','ㅕ','ㅖ','ㅗ','ㅘ','ㅙ','ㅚ','ㅛ','ㅜ','ㅝ','ㅞ','ㅟ','ㅠ','ㅡ','ㅢ','ㅣ'));
    String[] CHO = {"r","R","s","e","E","f","a","q","Q","t","T","d","w","W","c","z","x","v","g"};
    String[] JOONG = {"k","o","i","O","j","p","u","P","h","hk","ho","hl","y","n","nj","np","nl","b","m","ml","l"};
    String[] JONG = {"","r","R","rt","s","sw","sg","e","f","fr","fa","gq","ft","fx","fv","fg","a","q","qt","t","T","d","w","c","z","x","v","g"};

    Matcher matcher = Pattern.compile("[ㄱ-ㅎㅏ-ㅣ가-힣]").matcher("");
    default String change(String origin) {
        if (!matcher.reset(origin).find()) return origin;
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < origin.length(); i++) {
            char uniVal = origin.charAt(i);
            int position;
            if (uniVal >= 0xAC00) {
                uniVal = (char) (uniVal - 0xAC00);
                char cho = (char) (uniVal / 28 / 21);
                char joong = (char) (uniVal / 28 % 21);
                char jong = (char) (uniVal % 28);
                builder.append(CHO[cho]).append(JOONG[joong]).append(JONG[jong]);
            } else if ((position = ChoList.indexOf(uniVal)) != -1) builder.append(CHO[position]);
            else if ((position = JoongList.indexOf(uniVal)) != -1) builder.append(JOONG[position]);
            else builder.append(uniVal);
        }
        return builder.toString();
    }

    default String checkPattern(String origin) {
        try {
            Pattern.compile(origin);
            return origin;
        } catch (PatternSyntaxException e) {
            String text = e.getDescription();
            String start = "Named capturing group <";
            int a = text.indexOf(start) + start.length();
            int b = text.lastIndexOf("> is already defined");
            if (b == -1 || a-start.length() == -1) throw new MatchException().patternMatchError(null);
            throw new MatchException().patternMatchError(text.substring(a, b));
        }
    }
}
