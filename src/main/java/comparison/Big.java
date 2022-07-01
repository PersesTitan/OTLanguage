package comparison;

import item.ActivityItem;
import item.work.ComparisonWork;

import java.util.regex.Pattern;

public class Big implements ComparisonWork, ActivityItem {
    private static final String SPECIFIED = "ㅇ<ㅇ";
    private final String patternText = "-?\\d+\\.?\\d*\\s*ㅇ<ㅇ\\s*-?\\d+\\.?\\d*";
    private final String singText = "ㅇ<ㅇ";
    private final String numberText = "-?\\d+\\.?\\d*";
    private final Pattern pattern = Pattern.compile(patternText);
    private final static char left = '(';
    private final static char right = ')';

    @Override
    public boolean check(String line) throws Exception {
        return pattern.matcher(line).find();
    }

    @Override
    public String start(String line) throws Exception {
        return null;
    }
//
//    private void stack(String line) throws Exception {
//        Stack<Integer> stack = new Stack<>();
//        for (int i = 0; i<line.length(); i++) {
//            if (line.charAt(i) == left) stack.add(i);
//            else if (line.charAt(i) == right) {
//                if (stack.isEmpty()) throw new Exception("괄호 짝이 일치하지 않습니다.");
//                int start = stack.pop();
//                //(10>25)
//                String numbers = line.substring(start, i+1);
//                line = line.replaceFirst(numbers, );
//            }
//        }
//    }
}
