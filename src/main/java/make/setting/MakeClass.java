package make.setting;

import event.Setting;
import make.setting.abs.AbsString;
import make.setting.abs.AbsVoid;
import make.setting.method.MakeString;
import make.setting.method.MakeVoid;
import origin.exception.MatchException;
import origin.exception.MatchMessage;
import origin.exception.VariableException;
import origin.exception.VariableMessage;
import origin.variable.model.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MakeClass implements MakeClassWork {
    public final List<MakeVoid> voids = new ArrayList<>();
    public final List<MakeString> strings = new ArrayList<>();
    private final Set<String> methods = new HashSet<>(); //메소명+괄호 갯수
    private final Set<String> methodName = new HashSet<>(); //메소드명

    private final int count;
    private final String className;
    private final String patternText;
    private final Pattern pattern;
    public final AbsVoid absVoid;

    public MakeClass(String className, int count) {
        Repository.classList.add(className);
        this.count = count;
        this.className = className;
        this.patternText = "^\\s*"+className+"\\[[^\\[\\]]+]".repeat(Math.max(0, count));
        this.pattern = Pattern.compile(patternText);
        this.absVoid = new AbsVoid() {@Override public void start(String line) {}};
    }

    public MakeClass(String className, int count, AbsVoid absVoid) {
        Repository.classList.add(className);
        this.count = count;
        this.className = className;
        this.patternText = "^\\s*"+className+"\\[[^\\[\\]]+]".repeat(Math.max(0, count));
        this.pattern = Pattern.compile(patternText);
        this.absVoid = absVoid;
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void addVoid(String method, int count, AbsVoid absVoid) {
        addMethod(method+count);
        methodName.add(method);
        voids.add(new MakeVoid(className, method, count, absVoid));
    }

    @Override
    public void addString(String method, int count, AbsString absString) {
        addMethod(method+count);
        methodName.add(method);
        strings.add(new MakeString(className, method, count, absString));
    }

    @Override
    public void start(String line) {
        StringBuilder builder = new StringBuilder("(");
        methodName.forEach(v -> builder.append(v).append("|"));
        builder.deleteCharAt(builder.length() - 1);
        builder.append(")");

        line = line.replaceFirst(patternText, "");
        if (!line.isBlank()) {
            if (!Repository.uuidMap.containsKey(line)) throw new MatchException(MatchMessage.grammarError);
            //괄호 제거
            var value = Repository.uuidMap
                    .get(line).strip();

            String text = "(?<!"+className+")~" + builder;
            Pattern pattern = Pattern.compile(text);
            Matcher matcher = pattern.matcher(value);
            while (matcher.find()) {
                String group = className + matcher.group(); // ㄷㅇㄷ~메소드명
                value = value.replaceFirst(text, group);
            }

            for (String lines : value.split("\\n")) {
                Setting.start(lines);
            }
        }
    }

    private void addMethod(String method) {
        if (methods.contains(method)) throw new VariableException(VariableMessage.sameMethod);
        methods.add(method);
    }

    public String[] getValues(String line) {
        Matcher matcher = this.pattern.matcher(line);
        if (matcher.find()) {
            String group = matcher.group().strip();
            String[] values = group
                    .substring(className.length()+1, group.length()-1)
                    .split("]\\[");
            if (values.length != count) throw new MatchException(MatchMessage.grammarError);
            else return values;
        }
        throw new MatchException(MatchMessage.grammarError);
    }
}
