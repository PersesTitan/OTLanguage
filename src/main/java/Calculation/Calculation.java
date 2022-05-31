package Calculation;

import item.ActivityItem;
import item.Check;
import item.Setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Calculation extends Setting implements Check {

    private final String SPECIFIED1 = "\\n\\s*(ㅇ\\+ㅇ|ㅇ-ㅇ|ㅇ\\*ㅇ|ㅇ/ㅇ|ㅇ%ㅇ)\\s";
    private final String SPECIFIED2 = "^\\s*(ㅇ\\+ㅇ|ㅇ-ㅇ|ㅇ\\*ㅇ|ㅇ/ㅇ|ㅇ%ㅇ)\\s";
    private final Pattern pattern1 = Pattern.compile(SPECIFIED1);
    private final Pattern pattern2 = Pattern.compile(SPECIFIED2);

    private boolean checkSign(String line) {
        boolean bool = pattern1.matcher(line).find();
        return  bool || pattern2.matcher(line).find();
    }

    /**
     * @param line 한 줄을 받아옴
     * @return 개산 후의 값을 반환함
     * @throws Exception 괄호 짝이 일치하지 않을때
     */
    public String cutting(String line) throws Exception {
        //1개씩 짤라서 함
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
            if (line.contains(":")) {
                int start1 = line.lastIndexOf("(");
                int end1 = line.indexOf(")") + 1;
                //괄호를 포함한 값
                //변수에 있는 값 가겨오는 작업
                String value = line.substring(start1, end1);
                line = line.replace(value, getVar(value));
            } else {
                int start2 = line.lastIndexOf("(");
                int end2 = line.indexOf(")") + 1;
                //괄호를 포함하는 값
                //괄호를 포함하지 않는 값
                String value1 = line.substring(start2, end2);
                String value2 = line.substring(start2+1, end2-1);
                String calBefore = String.valueOf(account.account(value2));
                line = line.replace(value1, calBefore);
            } return calculation(line);
        } else return line;
    }

    /**
     * 괄호를 포함하는 값이 넘어옴
     * 이후 괄호를 제거하고 계산을 함
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
                .filter(this::checkVar)
                .forEach(v -> {
                    int start = v.indexOf(":") + 1;
                    String word = v.substring(start);
                    line[0] = line[0].replace(":"+word, checkValue(word));
                });
        } return line[0];
    }

    /**
     * 시작이 : 일때 :를 제거함 <br>
     * : 이 포함 되어 있을때 : 위치를 얻은 뒤 이후의 값을 반환 <br>
     * 위 조건이 없을 시에는 변수 이름이 존재하는지 확인함 <br>
     * (:ㅇㅁㅁ) -> ㅇㅁㅁ <br>
     * @param word 글자를 받아옴
     * @return 변수가 이미존재하는지 확인함.
     */
    private boolean checkVar(String word) {
        if (word.startsWith(":")) return set.contains(word.substring(1));
        else if (word.contains(":")) {
            int start = word.indexOf(":");
            return set.contains(word.substring(start));
        } else return set.contains(word);
    }

    @Override
    public boolean check(String line) {
        return line.contains("(") || line.contains(")");
    }
}
