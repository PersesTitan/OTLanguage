package make;

import make.abs.AbsString;
import make.abs.AbsVoid;
import make.method.MakeString;
import make.method.MakeVoid;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MakeClass implements MakeClassWork {
    public final List<MakeVoid> voids = new ArrayList<>();
    public final List<MakeString> strings = new ArrayList<>();
    private final String className;
    private final Pattern pattern;

    public MakeClass(String className) {
        this.className = className;
        this.pattern = Pattern.compile(className);
        this.voids.clear();
        this.strings.clear();
    }

    @Override
    public void addVoid(AbsVoid absVoid) {
        voids.add(new MakeVoid(className, absVoid));
    }

    @Override
    public void addString(AbsString absString) {
        strings.add(new MakeString(className, absString));
    }
}
