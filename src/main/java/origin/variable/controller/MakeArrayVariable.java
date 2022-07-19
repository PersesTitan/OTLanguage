package origin.variable.controller;

import origin.variable.model.VariableWork;

import java.util.regex.Pattern;

public class MakeArrayVariable implements VariableWork {
    Pattern pattern;

    public MakeArrayVariable(String pattern) {
        //ㅇㅅㅇ[10][13] 변수명:초기값
        this.pattern = Pattern.compile(pattern + "(\\[\\d])+ [ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9_-]+:");
    }

    @Override
    public boolean check(String line) {
        return false;
    }

    @Override
    public void start(String line) {

    }
}
