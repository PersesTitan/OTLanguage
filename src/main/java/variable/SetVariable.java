package variable;

import item.Setting;
import item.kind.VarType;
import item.work.Check;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetVariable extends Setting implements Check {
    private final String MATCHER = "\\S+: \\S+";
    private final Pattern pattern = Pattern.compile(MATCHER);

    public void start(String line) throws Exception {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            StringTokenizer stringTokenizer = new StringTokenizer(matcher.group(), ":");
            String key = stringTokenizer.nextToken().strip();
            String value = stringTokenizer.nextToken().strip();

            if (BM.containsKey(key)) {
                if (!varCheck.check(value, VarType.Boolean)) throw new Exception("타입 오류 발생");
                else BM.put(key, value.equals("ㅇㅇ"));
            } else if (CM.containsKey(key)) {
                if (!varCheck.check(value, VarType.Character)) throw new Exception("타입 오류 발생");
                else CM.put(key, value.charAt(0));
            } else if (DM.containsKey(key)) {
                if (!varCheck.check(value, VarType.Double)) throw new Exception("타입 오류 발생");
                else DM.put(key, Double.parseDouble(value));
            } else if (FM.containsKey(key)) {
                if (!varCheck.check(value, VarType.Float)) throw new Exception("타입 오류 발생");
                else FM.put(key, Float.parseFloat(value));
            } else if (IM.containsKey(key)) {
                if (!varCheck.check(value, VarType.Integer)) throw new Exception("타입 오류 발생");
                else IM.put(key, Integer.parseInt(value));
            } else if (LM.containsKey(key)) {
                if (!varCheck.check(value, VarType.Long)) throw new Exception("타입 오류 발생");
                else LM.put(key, Long.parseLong(value));
            }  else if (SM.containsKey(key)) {
                if (!varCheck.check(value, VarType.String)) throw new Exception("타입 오류 발생");
                else SM.put(key, value);
            }
        }
    }

    @Override
    public boolean check(String line) throws Exception {
        return pattern.matcher(line).find();
    }
}
