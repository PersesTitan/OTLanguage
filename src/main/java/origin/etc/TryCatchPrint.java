package origin.etc;

import origin.item.Setting;
import origin.item.work.Check;

import java.util.regex.Pattern;

public class TryCatchPrint extends Setting implements Check {
    private static final String SPECIFIED = "ㅜㅆㅜ";
    private final String PATTERN = "(\\n|^)\\s*ㅜㅆㅜ\\s";
    private final Pattern pattern = Pattern.compile(PATTERN);

    @Override
    public boolean check(String line) throws Exception {
        return pattern.matcher(line).find();
    }

    public void start(String line) {
        int position = line.indexOf(SPECIFIED)+SPECIFIED.length();
        if (line.strip().startsWith(SPECIFIED+" ")) position+=1;
        line = line.substring(position);
        try {
            super.start(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
