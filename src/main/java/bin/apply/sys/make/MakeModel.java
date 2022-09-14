package bin.apply.sys.make;

import work.ReturnWork;
import work.StartWork;

import java.io.*;

public class MakeModel {
    public static void makeStartWork(String dir, StartWork startWork) {
        String path = "model/bin" + dir;
        if (new File(path).mkdirs()) System.out.println("디렉토리가 생성되었습니다.");
        try (ObjectOutput output = new ObjectOutputStream(
                new FileOutputStream(path + "/" + startWork.getClass().getSimpleName() + ".otlm"))) {
            output.writeObject(startWork);
        } catch (IOException ignored) {}
    }

    public static void makeReturn(String dir, ReturnWork returnWork) {
        String path = "model/recombin/" + dir;
        if (new File(path).mkdirs()) System.out.println("디렉토리가 생성되었습니다.");
        try (ObjectOutput output = new ObjectOutputStream(
                new FileOutputStream(path + "/" + returnWork.getClass().getSimpleName() + ".otlm"))) {
            output.writeObject(returnWork);
        } catch (IOException ignored) {}
    }
}
