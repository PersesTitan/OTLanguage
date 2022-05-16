package Calculation;

import item.Check;
import item.Setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        int start = line.indexOf("(");
        int end = line.lastIndexOf(")") + 1;
        line = line.substring(start, end);
        line = getVar(line);
        line = line.replace(" ", "");
        return calculation(line);
    }

    /**
     * @param line 라인 받아오기
     * @return 계산후에 값을 반환함 괄호가 존재하면 재귀
     * @throws Exception 예러 발생시 에러 발생
     */
    private String calculation(String line) throws Exception {
        if (check(line)) {
            int start1 = line.lastIndexOf("(");
            int end1 = line.indexOf(")") + 1;
            //괄호를 포함하지 않은 값
            //변수에 있는 값 가겨오는 작업
            String value = line.substring(start1 + 1, end1 - 1);
            line = line.replace(value, getVar(value));
            int start2 = line.lastIndexOf("(");
            int end2 = line.indexOf(")") + 1;
            //괄호를 포함하는 값
            //괄호를 포함하지 않는 값
            String value1 = line.substring(start2, end2);
            String value2 = line.substring(start2+1, end2-1);
            String calBefore = String.valueOf(account.account(value2));
            line = line.replace(value1, calBefore);
            return calculation(line);
        } else return line;
    }

    /**
     * @param lines 라인 값을 받아 오기
     * @return : 없어질때 까지 line 값 반환
     */
    private String getVar(String lines) {
        //괄호 제거
        final String[] line = {lines.substring(1, lines.length() - 1)};
        if (!line[0].contains(":") && line[0].isBlank()) return line[0];
        else {
            String[] words = line[0].split(" ");
            List<String> list = new ArrayList<>(Arrays.asList(words));
            list.stream().filter(v -> !v.isBlank())
                .filter(v -> v.contains(":"))
                .forEach(v -> {
                    int start = v.indexOf(":") + 1;
                    String word = v.substring(start);
                    line[0] = line[0].replace(":"+word, checkValue(word));
                });
        } return line[0];
    }

    /**
     * (:ㅇㅁㅁ) -> ㅇㅁㅁ
     * @param word 글자를 받아옴
     * @return 변수가 이미존재하는지 확인함.
     */
    private boolean checkVar(String word) {
        return set.contains(word.substring(1));
    }

    @Override
    public boolean check(String line) {
        return line.contains("(") || line.contains(")");
    }
}
