package make.setting;

import make.setting.abs.AbsString;
import make.setting.abs.AbsVoid;

public interface MakeClassWork {
    void addVoid(String method, int count, AbsVoid absVoid);
    void addString(String method, int count, AbsString absString);
    void start(String line);
    boolean check(String line);
}
