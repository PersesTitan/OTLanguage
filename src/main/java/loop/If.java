package loop;

import item.Check;
import item.Setting;
import item.VarCheck;
import item.kind.LoopType;
import item.work.LoopWork;

import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class If extends Setting implements Check, LoopWork {
    private static final String SPECIFIED = "?ㅅ?";
    private final String patternText = "\\n\\s*\\?ㅅ\\?\\s|^\\s*\\?ㅅ\\?\\s";
    private final Pattern pattern = Pattern.compile(patternText);

    @Override
    public boolean check(String total) {
        return pattern.matcher(total).find();
    }

    private int lnCountBlank(String total) {
        int position = 0;
        StringBuilder subTotal = new StringBuilder();
        final int blank = countBlank(total) + 1;
        for (String line : total.split("\\n")) {
            if (countBlank(line) >= blank) subTotal.append(line);
            else break;
        }
        total = subTotal.toString();

        // total 안에 loop 가 있으면 검사하기
//        LoopType loopType = loopCheck());
//        if (loopType.equals())

        return position;
    }

    private void cut(String total) {
        int start = total.indexOf(SPECIFIED);
        if (start > 0) do {start -= 1;} while (total.charAt(start) != ' ');

    }

    @Override
    public void start(String line) throws Exception {
        //?ㅅ? 제거 작업
        line = line.strip().substring(SPECIFIED.length());
        StringTokenizer tokenizer = new StringTokenizer(line);
        while(tokenizer.hasMoreTokens()) {
            
        }
        line = line.replace("ㅇㅇ", "true");
        line = line.replace("ㄴㄴ", "false");
        if (changeBool(line)) {
            //동작 적는 곳
        } else {
            //false 일때의 동작
        }
    }
}
