package Calculation;

import item.Check;
import item.Setting;

import java.util.Arrays;

public class Calculation extends Setting implements Check {

    /**
     * @param line 한 줄을 받아옴
     * @return 개산 후의 값을 반환함
     * @throws Exception 괄호 짝이 일치하지 않을때
     */
    public String start(String line) throws Exception {
        String[] lines = line.split("(?<!^)");
        long count1 = Arrays.stream(lines).filter(view->view.equals("(")).count();
        long count2 = Arrays.stream(lines).filter(view->view.equals(")")).count();
        if (count1 != count2) throw new Exception("괄호의 짝이 일치 하지 않습니다.");
        int start = line.lastIndexOf("(");
        int end = line.indexOf(")");
        line = line.substring(start, end);
        line = getVar(line);

        line = line.replace(" ", "");
        return calculation(line);
    }

    private String calculation(String line) {
        if (check(line)) {
            int start = line.lastIndexOf("(");
            int end = line.indexOf(")") + 1;
            String value = line.substring(start, end);
            line = line.replace(value, getVar(value));
            return calculation(line);
        } else return line;
    }

    /**
     * @param line 라인 값을 받아 오기
     * @return : 없어질때 까지 line 값 반환
     */
    private String getVar(String line) {
        if (line.contains(":")) {
            String[] words = line.split("[\\-|\\+|\\/|\\*]");
            for (String word : words) {
                if (!word.isBlank() && word.startsWith(":") && checkVar(word)) {
                    line = line.replace(word, checkValue(word.substring(1)));
                    return getVar(line);
                }
            }
        } return line;
    }

    private boolean checkVar(String word) {
        return set.contains(word.substring(1));
    }

    @Override
    public boolean check(String line) {
        return line.contains("(") || line.contains(")");
    }
}
