package etc;

import item.Check;
import item.Setting;

import java.util.regex.Pattern;

public class TryCatchPrint extends Setting implements Check {
    private static final String SPECIFIED = "ㅜㅆㅜ";
    private final Pattern pattern = Pattern.compile("\\n\\s*ㅜㅆㅜ\\s|^\\s*ㅜㅆㅜ\\s");

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
