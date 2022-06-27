package comparison;

import item.ActivityItem;
import item.KeyValueItem;
import item.kind.VarType;
import item.work.ComparisonWork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class Big implements ComparisonWork, ActivityItem {
    private static final String SPECIFIED = "ㅇ<ㅇ";
    private final String patternText = "[\\S+.-]\\s*ㅇ<ㅇ\\s*[\\S+.-]";
    private final Pattern pattern = Pattern.compile(patternText);

    @Override
    public boolean check(String line) throws Exception {
        return pattern.matcher(line).find() && checkLine(checkLine(line));
    }

    @Override
    public boolean start(String line) throws Exception {
        int startSave;
        int endSave;

        // key = start
        // value = end
        var lines = line.split(" ");
        List<String> lists = new ArrayList<>(Arrays.asList(lines));
        lists.removeAll(Collections.singletonList(" "));
        lists.removeAll(Collections.singletonList(null));
        int position = lists.indexOf(SPECIFIED);
        if (position-1 < 0) throw new Exception(SPECIFIED + " 위치가 잘못 되었습니다.");
        String start = lists.get(position-1);
        String end = lists.get(position+1);

        boolean s_bool1 = varCheck.check(start, VarType.Integer);
        boolean s_bool2 = varCheck.check(start, VarType.Long);
        boolean s_bool4 = varCheck.check(start, VarType.Float);
        boolean s_bool3 = varCheck.check(start, VarType.Double);

        boolean e_bool1 = varCheck.check(end, VarType.Integer);
        boolean e_bool2 = varCheck.check(end, VarType.Long);
        boolean e_bool3 = varCheck.check(end, VarType.Double);
        boolean e_bool4 = varCheck.check(end, VarType.Float);

        var value = checkLine(line);
        return false;
    }

    private KeyValueItem checkLine(String line) throws Exception {
        // key = start
        // value = end
        var lines = line.split(" ");
        List<String> lists = new ArrayList<>(Arrays.asList(lines));
        lists.removeAll(Collections.singletonList(" "));
        lists.removeAll(Collections.singletonList(null));
        int position = lists.indexOf(SPECIFIED);
        if (position-1 < 0) throw new Exception(SPECIFIED + " 위치가 잘못 되었습니다.");
        String start = lists.get(position-1);
        String end = lists.get(position+1);
        return new KeyValueItem(start, end);
    }

    private boolean checkLine(KeyValueItem line) {
        String start = line.getKey();
        String end = line.getValue();
        boolean s_bool1 = varCheck.check(start, VarType.Integer);
        boolean s_bool2 = varCheck.check(start, VarType.Long);
        boolean s_bool4 = varCheck.check(start, VarType.Float);
        boolean s_bool3 = varCheck.check(start, VarType.Double);

        boolean e_bool1 = varCheck.check(end, VarType.Integer);
        boolean e_bool2 = varCheck.check(end, VarType.Long);
        boolean e_bool3 = varCheck.check(end, VarType.Double);
        boolean e_bool4 = varCheck.check(end, VarType.Float);

        boolean bool1 = s_bool1 || s_bool2 || s_bool3 || s_bool4;
        boolean bool2 = e_bool1 || e_bool2 || e_bool3 || e_bool4;
        return bool1 && bool2;
    }
}
