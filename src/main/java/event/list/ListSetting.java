package event.list;

import origin.exception.IndexException;
import origin.exception.IndexMessage;
import origin.variable.controller.list.ListVariable;
import origin.variable.define.VariableCheck;
import origin.variable.model.Repository;
import origin.variable.model.VariableListWork;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface ListSetting extends Repository {
    String setPattern = "^\\s*[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9_-]+:<<";  // [변수명]:<<[값]
    String getPattern = ":[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9_-]+>>\\d+[ _]"; // :[변수명]>>index[공백]
    String textPattern = "^\\s*[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9_-]+--\\d+"; // [변수명]--index

    //값 넣기
    VariableListWork setListVariable = new ListVariable(Pattern.compile(setPattern)) {
        @Override
        public String start(String line) {
            Matcher matcher = Pattern.compile(setPattern).matcher(line);
            if (matcher.find()) {
                //ㄹㅁㄹ, ㄹㅅㄹ, ...
                String k = matcher.group().replace(":<<", "");
                line = line.replaceFirst(setPattern, ""); //값
                for (String key : repository.keySet()) {
                    if (repository.get(key).containsKey(k)) {
                        List<Object> list = (List<Object>) repository.get(key).get(k);
                        checkList(list, line, key);
                        return null;
                    }
                }
            }
            return null;
        }
    };

    //[값1, 값2, 값3, ...] 인지 값 인지 확인하는 메소드
    //var = ㄹㅁㄹ, ㄹㄱㄹ, ...
    private static void checkList(List<Object> list, String value, String var) {
        if (var.charAt(0) != 'ㄹ' && var.charAt(0) != 'l') return;
        value = value.trim();
        if (value.startsWith("[") && value.endsWith("]")) {
            for (String v : value.substring(1, value.length()-1).split(",")) {
                if (VariableCheck.check(v.trim(), var))
                    list.add(v.trim());
            }
        } else {
            if (VariableCheck.check(value, var))
                list.add(value);
        }
    }

    //값 불러오기
    VariableListWork getListVariable = new ListVariable(Pattern.compile(getPattern)) {
        @Override
        public String start(String line) {
            // :[변수명]>>index[공백]
            Matcher matcher = Pattern.compile(getPattern).matcher(line);
            while (matcher.find()) {
                // :[변수명]>>index[공백]
                String group = matcher.group();
                int pos = group.length();
                String value = group.substring(1, pos-1); //[변수명]>>index
                var values = value.split(">>");
                var type = values[0];
                var position = Integer.parseInt(values[1].replaceAll("[^0-9]", ""));
                for (String key : repository.keySet()) {
                    if (repository.get(key).containsKey(type)) {
                        List<Object> list = (List<Object>) repository.get(key).get(type);
                        line = line.replaceFirst(getPattern, list.get(position).toString());
                    }
                }
            }
            return line;
        }
    };

    //값 삭제
    VariableListWork deleteListVariable = new ListVariable(Pattern.compile(textPattern)) {
        @Override
        public String start(String line) {
            // [변수명]--index[공백]
            Matcher matcher = Pattern.compile(textPattern).matcher(line);
            if (matcher.find()) {
                var groups = matcher.group().strip().split("--");
                var variableName = groups[0];
                var pos = Integer.parseInt(groups[1].replaceAll("[^0-9]", ""));

                for (String key : repository.keySet()) {
                    if (repository.get(key).containsKey(variableName)) {
                        List<Object> list = (List<Object>) repository.get(key).get(variableName);
                        if (list.size() > pos) list.remove(pos);
                        else throw new IndexException(IndexMessage.removeError);
                        return null;
                    }
                }
            }
            return " ";
        }
    };
}
