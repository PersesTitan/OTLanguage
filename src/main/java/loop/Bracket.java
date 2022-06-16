package loop;

import item.Check;
import item.Setting;

import java.util.UUID;

public class Bracket extends Setting implements Check {

    private final static char left = '{';
    private final static char right = '}';

    //괄호가 존재하면 아이디로 데체
    public String bracket(String total) throws Exception {
        stack.clear();
        for (int i = 0; i < total.length(); i++) {

            if (total.charAt(i) == left) {
                stack.add(i);
            }

            if (total.charAt(i) == right) {
                if (stack.empty()) throw new Exception("괄호 짝이 맞지 않습니다.");
                String uuid = UUID.randomUUID().toString();
                int start = stack.pop();
                String bl = total.substring(start, i+1);

                total = total.replace(bl, uuid.concat("\n"));
                i = start;
                uuidMap.put(uuid, bl);
            }
        }
        return total;
    }

    @Override
    public boolean check(String line) {
        if (uuidMap.isEmpty()) return false;
        return uuidMap.entrySet().stream()
                .anyMatch(v -> line.contains(v.getKey()));
    }
}
