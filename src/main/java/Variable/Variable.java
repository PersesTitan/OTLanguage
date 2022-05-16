package Variable;

import item.Check;
import item.Setting;

import java.util.ArrayList;
import java.util.List;

public class Variable extends Setting implements Check {

    /**
     * 변수 :[변수명][공백] 을 변수 값으로 대체함
     * @param line 한줄을 받아옴
     */
    public List<String> getVar(String line) {
        List<String> list = new ArrayList<>();
        String[] words = line.split(" ");
        for (String word : words) {
            if (!word.isBlank() && word.contains(":")) {
                int start = word.indexOf(":") + 1;
                list.add(word.substring(start));
            }
        }
        return list;
    }

    @Override
    public boolean check(String line) {
        String[] keys = line.split(" ");
        for (String key : keys) {
            if (!key.isBlank() && key.contains(":")) {
                int start = key.indexOf(":")+1;
                return set.contains(key.substring(start));
            }
        } return false;
    }
}