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
    public void getVar(String line) {
        List<String > list = new ArrayList<>();
        String[] words = line.split(" ");
        for (String word : words) {
            if (!word.isBlank() && word.startsWith(":"))
                list.add(word.substring(1));
        }

    }

    @Override
    public boolean check(String line) {
        String[] keys = line.split(" ");
        for (String key : keys) {
            if (!key.isBlank() && key.startsWith(":")) {
                return set.contains(key.substring(1));
            }
        }
        return false;
    }
}