import bin.apply.Repository;
import bin.apply.Setting;
import bin.apply.sys.make.ChangeHangle;
import bin.exception.FileException;
import bin.token.LoopToken;
import etc.loop.WhileTest;
import module.download.MakeGitTest;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

import static bin.apply.Controller.br;
import static bin.apply.Controller.bw;
import static bin.apply.Repository.startWorksV3;
import static bin.apply.sys.item.Separator.SEPARATOR_HOME;

public class MainTest implements LoopToken, ChangeHangle {

    public static void main(String[] args) throws AWTException, IOException {
//        Taskbar.getTaskbar().setIconImage(Toolkit.getDefaultToolkit().getImage(sb.toString()));
//        Taskbar.getTaskbar().setIconImage(Toolkit.getDefaultToolkit().getImage("image/icon.png"));
//        Robot robot = new Robot();
//        JFrame frame = new JFrame("test");

//        new MakeGitTest();

        new Setting();
        Repository.createStartWorks(WHITE.replace("\\", ""), "", new WhileTest(1));
        try {
            new Main(new String[]{SEPARATOR_HOME, "hello.otl"});
//            new Main(new String[]{SEPARATOR_HOME, "test/set_test.otl"});
//            new Main(new String[]{SEPARATOR_HOME, "test/list_test.otl"});
//            new Main(new String[]{SEPARATOR_HOME, "test/map_test.otl"});
        } catch (FileException e) {
            new FileException().printErrorMessage(e, Setting.mainPath);
        } finally {try {br.close(); bw.close();} catch (IOException ignored) {}}
    }
}
