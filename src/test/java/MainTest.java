import bin.apply.Setting;
import bin.apply.sys.make.ChangeHangle;
import bin.apply.sys.make.StartLine;
import bin.exception.FileException;
import bin.token.LoopToken;
import cos.poison.controller.HttpMethod;
import module.download.MakeGitTest;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

import static bin.apply.Controller.br;
import static bin.apply.Controller.bw;
import static bin.apply.sys.item.Separator.*;
import static etc.groovy.items.GroovyRepositoryTest.JAVA;
import static etc.groovy.items.GroovyRepositoryTest.JAVA_START;
import static etc.gui.setting.RepositoryTest.*;

public class MainTest implements LoopToken, ChangeHangle {
    public static void main(String[] args) throws AWTException, IOException {
        StartLine.developmentMode = true;
        LOOP_SET.addAll(Arrays.asList(IF_, ELSE_IF_, ELSE_, WHITE_, TRY_CATCH, METHOD, KLASS));
        LOOP_SET.add(JAVA + ACCESS + JAVA_START);
        LOOP_SET.addAll(Arrays.asList(
                GUI + ACCESS + ACTIONS,
                POISON + ACCESS + POISON_POST,
                POISON + ACCESS + POISON_GET));

//        Taskbar.getTaskbar().setIconImage(Toolkit.getDefaultToolkit().getImage(sb.toString()));
//        Taskbar.getTaskbar().setIconImage(Toolkit.getDefaultToolkit().getImage("image/icon.png"));
//        Robot robot = new Robot();
//        JFrame frame = new JFrame("test");


//        AnsiConsole.systemInstall();
//        System.out.println(ansi().fg(Ansi.Color.RED).a("Hello").reset());
//        System.out.println(ansi().bg(Ansi.Color.RED).a("Hello").reset());

        new MakeGitTest();
        new Setting();

        System.exit(0);
        try {
//            new Main(new String[]{SEPARATOR_HOME, "hello.otl"});
//            new Main(new String[]{SEPARATOR_HOME, "web.otl"});
            new Main(new String[]{SEPARATOR_HOME, "web.otlc"});

//            new Main(new String[]{SEPARATOR_HOME, "icon.otlm"});

//            new Main(new String[]{SEPARATOR_HOME, "test/set_test.otl"});
//            new Main(new String[]{SEPARATOR_HOME, "test/list_test.otl"});
//            new Main(new String[]{SEPARATOR_HOME, "test/map_test.otl"});
        } catch (FileException e) {
            new FileException().printErrorMessage(e, Setting.mainPath);
        } finally {try {br.close(); bw.close();} catch (IOException ignored) {}}
    }
}
