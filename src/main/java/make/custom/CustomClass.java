package make.custom;

import event.Controller;
import event.Setting;
import make.custom.method.CustomString;
import make.custom.method.CustomVoid;
import make.custom.start.CustomStringStart;
import make.custom.start.CustomVoidStart;
import origin.exception.MatchException;
import origin.exception.MatchMessage;
import origin.exception.VariableException;
import origin.exception.VariableMessage;
import origin.variable.model.Repository;
import origin.variable.model.VariableWork;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomClass {
    private final int count;
    private final String str;
    private final String className;
    private final Pattern pattern;
    //변수 저장하는 저장소
    private final Map<String, Map<String, Object>> repository = new HashMap<>();
    private final Set<String> methodRepository = new HashSet<>(); //메소드 저장소
    private final Set<String> set = new HashSet<>();

    public final String patternText; //[(ㅇㅅㅇ|ㅇㅁㅇ|..) 변수명]
    public final CustomVoidStart customVoidStart;
    public final Set<CustomVoid> customVoids = new HashSet<>();
    public final Set<CustomString> customStrings = new HashSet<>();

    //str : [ㅇㅅㅇ 변수명][ㅇㅁㅇ 변수명]
    public CustomClass(String className, int count, String str, CustomVoidStart customVoidStart) {
        //[(ㅇㅅㅇ|ㅇㅁㅇ|..) 변수명]
        StringBuilder builder = new StringBuilder("\\[[(");
        Repository.repository.keySet().forEach(key -> {
            builder.append(key).append("|");
            repository.put(key, new HashMap<>());
        });
        builder.deleteCharAt(builder.length()-1);
        builder.append(")\\s+[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9_-]+]+]");
        this.patternText = builder.toString();
        this.pattern = Pattern.compile("^\\s*" + className + builder.toString().repeat(Math.max(0, count)));
        this.customVoidStart = customVoidStart;
        this.className = className;
        this.count = count;
        this.str = str;
    }

    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    public void start(String line) {
        if ((line = settingStart(line)) == null) return;
        add(line);

        // 매개 변수를 넣는 작업하는 구간
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String group = matcher.group().strip();
            group = group.replaceFirst(className, "");
            //클래스 단위 변수 저장
            CustomClassWork.putValues(group, str, count, repository, set);
        }
        customVoidStart.start(line); //생성자 로직
        getUUID(line.replaceFirst(patternText, "")); //uuid 검증 및 로직
    }

    //동작을 하는 메소드
    private String settingStart(String line) {
        //현재 저장소, 전체 저장소
        line = CustomClassWork.getVariable(line, repository, Repository.repository); //변수 값 가져오기
        for (VariableWork works : Repository.variableWorks) { //변수를 정의하는 로직
            if (works.check(line)) {works.start(line, repository, set); return null;}}
        //변수 값 업데이트 로직
        if (CustomClassWork.checkVariableUpdate(line)) {
            var repositoryPos = CustomClassWork.getRepository(line.strip());
            var repository = repositoryPos == 0 ? this.repository : Repository.repository;
            var set = repositoryPos == 0 ? this.set : Repository.set;
            line = line.replaceFirst("^\\s*~*", ""); // ~ 제거 로직
            Controller.setVariable.start(line, repository, set);
            return null;
        }
        return line;
    }

    //클래스명[][][] uuid일때 uuid 실행
    private void getUUID(String id) {
        if (!id.isBlank()) {
            //String 메소드, Void 메소드
            StringBuilder builder = new StringBuilder("(?<!");
            builder.append(className).append(")~(");
            customStrings.forEach(v -> builder.append(v.methodName).append("|"));
            customVoids.forEach(v -> builder.append(v.methodName).append("|"));
            builder.deleteCharAt(builder.length() - 1);
            builder.append(")");
            //토탈 값 가져옴
            String total = Repository.uuidMap.getOrDefault(id.strip(), "");
            Matcher m = Pattern.compile(builder.toString()).matcher(total);
            while (m.find()) total = total.replaceFirst(builder.toString(), className + m.group());
            Arrays.stream(total.split("\\n")).forEach(Setting::start);
        }
    }

    //Void 메소드 추가하는 로직
    private void add(String methodName, int count, String str, CustomVoidStart customVoidStart) {
        if (methodRepository.contains(methodName + count))
            throw new VariableException(methodName + str + VariableMessage.sameMethod);
        methodRepository.add(methodName + count);
        customVoids.add(new CustomVoid(className, methodName, count, repository, set, str, customVoidStart));
    }

    //String 메소드 추가하는 로직
    //change: 바뀔 변수명
    private void add(String methodName, int count, String str, String change, CustomStringStart customStringStart) {
        if (methodRepository.contains(methodName + count))
            throw new VariableException(methodName + str + VariableMessage.sameMethod);
        methodRepository.add(methodName + count);
        customStrings.add(new CustomString(className, methodName, count, repository, set, str, change, customStringStart));
    }

    //메소드 로직
    private boolean methodCheck(String line) {
        Pattern pattern = Pattern.compile(getPattern());
        return pattern.matcher(line).find();
    }

    private void add(String line) {
        Matcher matcher = Pattern.compile(getPattern()).matcher(line);
        if (matcher.find()) {
            String returnText = "=>[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9_-]+\\s*$";
            Pattern returnPattern = Pattern.compile(returnText);
            StringTokenizer tokenizer = new StringTokenizer(matcher.group().strip());
            tokenizer.nextToken(); //ㅁㅅㅁ
            String methodName = tokenizer.nextToken(); //메소드명 + [...][..]
            String total = line.replaceFirst(getPattern(), "").strip(); //uuid + => 변수명
            if (total.isBlank()) throw new MatchException(MatchMessage.grammarError);
            String countText = className.replaceFirst("^[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9_-]+", ""); //괄호 추출
            String uuid = total.replaceFirst(returnText, "").strip(); //uuid

            Matcher returnMatcher = returnPattern.matcher(total); // => 변수명 체크
            if (returnMatcher.find()) {
                //변수명
                String group = returnMatcher.group().strip().substring(2);
                add(methodName, getCount(countText), countText, group, new CustomStringStart() {
                    @Override
                    public String start(String line) {
                        String t = Repository.uuidMap.getOrDefault(uuid, "");
                        Arrays.stream(t.split("\\n")).forEach(Setting::start);
                        return line;
                    }
                });
            } else {
                add(methodName, getCount(countText), countText, new CustomVoidStart() {
                    @Override
                    public void start(String line) {
                        String t = Repository.uuidMap.getOrDefault(uuid, "");
                        Arrays.stream(t.split("\\n")).forEach(Setting::start);
                    }
                });
            }
        }
    }

    private String getPattern() {
        //[(ㅇㅅㅇ|ㅇㅁㅇ|..) 변수명]
        StringBuilder builder = new StringBuilder("(\\[[(");
        Repository.repository.keySet().forEach(key -> builder.append(key).append("|"));
        builder.deleteCharAt(builder.length()-1);
        builder.append(")\\s+[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9_-]+]+])*");
        return "^\\s*ㅁㅅㅁ" + builder + "\\s+[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9_-]+";
    }

    private int getCount(String countText) {
        if (countText.contains("[") && countText.contains("]")) {
            return countText.substring(1, countText.length()-1).split("]\\[").length;
        } return 0;
    }
}
