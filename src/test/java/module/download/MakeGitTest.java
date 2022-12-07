package module.download;

import bin.apply.sys.item.SystemSetting;
import bin.apply.sys.run.*;
import bin.define.klass.DefineKlass;
import bin.define.method.DefineMethod;
import bin.orign.console.normal.*;
import bin.orign.console.priority.PriorityPrint;
import bin.orign.console.priority.PriorityPrintSpace;
import bin.orign.console.priority.PriorityPrintTap;
import bin.orign.console.priority.PriorityPrintln;
import bin.orign.loop.If;
import bin.orign.loop.While;
import bin.orign.math.Max;
import bin.orign.math.Min;
import bin.orign.math.random.*;
import bin.orign.math.round.Abs;
import bin.orign.math.round.Ceil;
import bin.orign.math.round.Floor;
import bin.orign.math.round.Round;
import bin.orign.variable.CreateList;
import bin.orign.variable.CreateMap;
import bin.orign.variable.CreateOrigin;
import bin.orign.variable.CreateSet;
import bin.string.*;
import bin.string.list.ListAdd;
import bin.string.list.ListContains;
import bin.string.list.ListRemove;
import bin.string.list.ListRetain;
import bin.string.pattern.MatcherFind;
import bin.string.position.Index;
import bin.string.position.LastIndex;
import bin.string.position.SubString;
import bin.string.tocase.ToLower;
import bin.string.tocase.ToUpper;
import bin.token.LoopToken;
import bin.token.VariableToken;
import cos.poison.setting.PoisonPassURL;
import cos.shell.CreateShell;
import cos.shell.StartShell;
import cos.music.replace.GetLoop;
import cos.music.replace.GetPitch;
import cos.music.replace.GetSpeed;
import cos.music.replace.GetVolume;
import cos.music.start.*;
import cos.poison.controller.HttpMethod;
import cos.poison.setting.PoisonCreate;
import cos.poison.setting.PoisonMethod;
import cos.poison.setting.PoisonStart;
import etc.gui.OGUITest;
import etc.gui.item.*;
import etc.gui.replace.*;
import etc.gui.start.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static bin.apply.Repository.*;
import static bin.apply.sys.item.Separator.*;
import static bin.apply.sys.item.SystemSetting.*;
import static bin.token.ConsoleToken.*;
import static bin.token.LoopToken.*;
import static bin.token.StringToken.*;
import static bin.token.VariableToken.STRING_VARIABLE;
import static bin.token.cal.NumberToken.*;
import static cos.poison.PoisonRepository.POISON_PASS_URL;
import static etc.gui.setting.RepositoryTest.*;
import static etc.music.paly.MusicRepository.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class MakeGitTest {
    public MakeGitTest() {
        clear(); start1();
        new MakeGitTest("origin", "src/main/java/bin");
        clear(); start2();
        new MakeGitTest("poison", "src/main/java/cos/poison");
        clear(); start3();
        new MakeGitTest("gui", "src/main/java/cos/gui");
        clear(); start4();
        new MakeGitTest("music", "src/main/java/cos/music");
        clear(); start5();
        new MakeGitTest("shell", "src/main/java/cos/shell");
    }

    private static void clear() {
        system.clear();
        list.clear();
        priorityStartWorksV3.clear();
        startWorksV3.clear();
        returnWorksV3.clear();
        files.clear();
    }

    private static final List<String> system = new ArrayList<>();
    private static final List<File> list = new ArrayList<>();
    private static final List<String> files = new ArrayList<>();
    private void listFile(File file) {
        for (File cpFile : Objects.requireNonNull(file.listFiles())) {
            if (cpFile.isFile()) list.add(cpFile);
            else listFile(cpFile);
        }
    }

    public static String SYSTEM = "system:";
    public static String MODULE = "module:";
    public static String CLASS  = "class:";
    public static String FILE = "file:";

    String COMPULSION = "compulsion";   // 강제
    String ALTERATION = "alteration";   // 변경
    String OPERATE = "operate";         // 동작

    private void priority(String path) {
        makeDir(path);
        try (var output = new ObjectOutputStream(new FileOutputStream(path))) {
            output.writeObject(priorityStartWorksV3);
        } catch (IOException ignored) {}
    }

    private void priority(File file) {
        priority(file.getParent());
    }

    private void returnWork(String path) {
        makeDir(path);
        try (var output = new ObjectOutputStream(new FileOutputStream(path))) {
            output.writeObject(returnWorksV3);
        } catch (IOException ignored) {}
    }

    private void returnWork(File path) {
        returnWork(path.getAbsolutePath());
    }

    private void start(String path) {
        makeDir(path);
        try (var output = new ObjectOutputStream(new FileOutputStream(path))) {
            output.writeObject(startWorksV3);
        } catch (IOException ignored) {}
    }

    private void start(File path) {
        start(path.getAbsolutePath());
    }

    private void makeDir(String path) {
        File file = new File(path).getParentFile();
        if (file.isDirectory()) return;
        file.mkdirs();
    }

    private void makeDir(File file) {
        makeDir(file.getAbsolutePath());
    }

    // name : poison
    private MakeGitTest(String name, String fileName) {
        // 기본 세팅 확인
        boolean bool = name.equals("origin");
        listFile(new File(fileName));
        // User/name/Documents/GitHub/module/poison
        String namePath = SEPARATOR_HOME + "/Documents/GitHub/module/" + name;
        String namePath2 = null;
        if (bool) namePath2 = SEPARATOR_HOME + "/Documents/GitHub/.otl/module/";
        File file = new File(namePath);
        makeDir(file);

        // 기본 값 추가
        if (bool) {
            File file1 = new File(namePath2);
            makeDir(file1);
            try (BufferedWriter bw = new BufferedWriter(
                    new FileWriter(namePath2 + "/system.otls", false))) {
                system.forEach(v -> {
                    try {
                        bw.write(v.replace("\\", ""));
                        bw.newLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedWriter br = new BufferedWriter(new FileWriter(namePath + "/system.otls", false))) {
            br.write(SYSTEM);
            br.newLine();
            for (String systemItem : system) {
                br.write("   " + systemItem.replace("\\", ""));
                br.newLine();
            }
            br.newLine();

            br.write(MODULE);
            br.newLine();
            if (!priorityStartWorksV3.isEmpty()) {
                priority(namePath + "/" + COMPULSION + MODULE_EXTENSION);
                priority(COMPULSION_PATH + "/" + name + ".otlm");
                if (bool) priority(namePath2 + "compulsion/" + name + MODULE_EXTENSION);
                br.write("   ");
                br.write(COMPULSION);
                br.newLine();
            }

            if (!returnWorksV3.isEmpty()) {
                returnWork(namePath + "/" + ALTERATION + MODULE_EXTENSION);
                returnWork(ALTERATION_PATH + "/" + name + ".otlm");
                if (bool) returnWork(namePath2 + "alteration/" + name + MODULE_EXTENSION);
                br.write("   ");
                br.write(ALTERATION);
                br.newLine();
            }

            if (!startWorksV3.isEmpty()) {
                start(namePath + "/" + OPERATE + MODULE_EXTENSION);
                start(OPERATE_PATH + "/" + name + MODULE_EXTENSION);
                if (bool) start(namePath2 + "operate/" + name + ".otlm");
                br.write("   ");
                br.write(OPERATE);
                br.newLine();
            }

            br.newLine();
            br.write(CLASS);
            for (File f : list) {
                int start = (System.getProperty("user.home") + "/Documents/Java/OTLanguage/src/main/java/").length();
                int end = f.getAbsolutePath().length() - ".java".length();
                String value = f.getAbsolutePath().substring(start, end).replace("/", "~");
                br.newLine();
                br.write("   ");
                br.write(value.replace(".", "~"));

                String klassName = file.getAbsolutePath() + "/" + f.getName().substring(0, f.getName().length()-".java".length()) + ".class";
                copy(value.replace("~", SEPARATOR_FILE), klassName);
                String pa = getPath(INSTALL_PATH, "analyzer", value.replace("~", SEPARATOR_FILE)) + ".class";
                makeDir(pa);
                copy(value.replace("~", SEPARATOR_FILE), pa);
            }

            if (!files.isEmpty()) {
                br.newLine();
                br.newLine();
                br.write(FILE);
                files.forEach(line -> {
                    try {
                        br.newLine();
                        br.write("   ");
                        br.write(line.replace("/", ACCESS));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copy(String klassPath, String klassName) throws IOException {
        String path = "out/production/classes/" + klassPath + ".class";
        Files.copy(new File(path).toPath(), new File(klassName).toPath(), REPLACE_EXISTING);
    }

    private static void start1() {
        system.add(IF_);
        system.add(ELSE_IF_);
        system.add(ELSE_);
        system.add(WHITE_);
        system.add(TRY_CATCH);
        system.add(METHOD);
        system.add(KLASS);

        String WHITE = LoopToken.WHITE.replace("\\", "");
        String IF = LoopToken.IF.replace("\\", "");

        priorityCreateStartWorks(FORCE_QUIT, "", new ForceQuit(0));
        priorityCreateStartWorks(PRIORITY_PRINT, "", new PriorityPrint(1));
        priorityCreateStartWorks(PRIORITY_PRINTLN, "", new PriorityPrintln(1));
        priorityCreateStartWorks(PRIORITY_PRINT_TAP, "", new PriorityPrintTap(1));
        priorityCreateStartWorks(PRIORITY_PRINT_SPACE, "", new PriorityPrintSpace(1));
        priorityCreateStartWorks(TRY_CATCH, "", new TryCatch(1));

        CreateOrigin createOrigin = new CreateOrigin();
        CreateSet createSet = new CreateSet();
        CreateList createList = new CreateList();
        CreateMap createMap = new CreateMap();
        ORIGIN_LIST.forEach(v -> createStartWorks(v, "", createOrigin));
        SET_LIST.forEach(v -> createStartWorks(v, "", createSet));
        LIST_LIST.forEach(v -> createStartWorks(v, "", createList));
        MAP_LIST.forEach(v -> createStartWorks(v, "", createMap));
        createStartWorks(PRINT, "", new Print(1));
        createStartWorks(PRINTLN, "", new Println(1));
        createStartWorks(PRINT_SPACE, "", new PrintSpace(1));
        createStartWorks(PRINT_TAP, "", new PrintTap(1));
        createStartWorks(SLEEP, "", new Sleep(1));
        createStartWorks(FILE, "", new FilePath(1));
        createStartWorks(WHITE, "", new While(1));
        createStartWorks(IF, "", new If(1));
        createStartWorks(METHOD, "", new DefineMethod(1));
        createStartWorks(KLASS, "", new DefineKlass(1));

        createReturnWorks(RANDOM_BOOL, "", new RandomBoolean(1));
        createReturnWorks(RANDOM_DOUBLE, "", new RandomDouble(1, 2));
        createReturnWorks(RANDOM_FLOAT, "", new RandomFloat(1, 2));
        createReturnWorks(RANDOM_INTEGER, "", new RandomInteger(1, 2));
        createReturnWorks(RANDOM_LONG, "", new RandomLong(1, 2));
        createReturnWorks(SCANNER, "", new Scanner(0));
        createReturnWorks(VariableToken.SYSTEM, SYSTEM_IS_CHECK, new IsCheck(1));
        createReturnWorks(STRING_VARIABLE, JOIN, new Join(2));
        createReturnWorks(STRING_VARIABLE, SPLIT, new Split(2));
        createReturnWorks(STRING_VARIABLE, SPLIT_REGULAR, new SplitRegular(2));
        createReturnWorks(STRING_VARIABLE, CONTAINS_S, new Contains(2));
        createReturnWorks(STRING_VARIABLE, EQUALS_S, new Equals(2));
        createReturnWorks(STRING_VARIABLE, PATTERN, new MatcherFind(2));
        createReturnWorks(STRING_VARIABLE, TO_LOWER, new ToLower(1));
        createReturnWorks(STRING_VARIABLE, TO_UPPER, new ToUpper(1));
        createReturnWorks(STRING_VARIABLE, INDEX, new Index(2, 3));
        createReturnWorks(STRING_VARIABLE, LAST_INDEX, new LastIndex(2, 3));
        createReturnWorks(STRING_VARIABLE, SUBSTRING, new SubString(2, 3));
        LIST_LIST.forEach(v -> {
            createStartWorks(v, ADD_ALL, new ListAdd(v, 2));
            createStartWorks(v, RETAIN_ALL, new ListRetain(v, 2));
            createStartWorks(v, REMOVE_ALL, new ListRemove(v, 2));
            createReturnWorks(v, CONTAINS_ALL, new ListContains(v, 2));
        });

        Round round = new Round(1);
        Ceil ceil = new Ceil(1);
        Floor floor = new Floor(1);
        Abs abs = new Abs(1);
        SystemSetting.createReturnWorks(INT_VARIABLE, ABS, abs);
        SystemSetting.createReturnWorks(LONG_VARIABLE, ABS, abs);
        Arrays.asList(DOUBLE_VARIABLE, FLOAT_VARIABLE).forEach(v -> {
            SystemSetting.createReturnWorks(v, ROUND, round);
            SystemSetting.createReturnWorks(v, CEIL, ceil);
            SystemSetting.createReturnWorks(v, FLOOR, floor);
            SystemSetting.createReturnWorks(v, ABS, abs);
        });

        Max max = new Max(2);
        Min min = new Min(2);
        Arrays.asList(INT_VARIABLE, LONG_VARIABLE, FLOAT_VARIABLE, DOUBLE_VARIABLE).forEach(v -> {
            createReturnWorks(v, MAX, max);
            createReturnWorks(v, MIN, min);
        });
    }

    private static void start2() {
        system.add(POISON + ACCESS + POISON_POST);
        system.add(POISON + ACCESS + POISON_GET);
        createStartWorks(POISON, "", new PoisonCreate(1, 2));
        createStartWorks(POISON, POISON_START, new PoisonStart(0));
        createStartWorks(POISON, POISON_PASS_URL, new PoisonPassURL(1));
        createStartWorks(POISON, POISON_POST, new PoisonMethod(HttpMethod.POST));
        createStartWorks(POISON, POISON_GET, new PoisonMethod(HttpMethod.GET));
    }

    private static void start3() {
        system.add(GUI + ACCESS + ACTIONS);

        createStartWorks(GUI, "", new OGUITest(0));
        createStartWorks(GUI, ADD, new OAddTest(2));
        createStartWorks(GUI, REMOVE, new ORemoveTest(2));
        createStartWorks(GUI, SET_MOVE, new OSetMoveTest(3));
        createStartWorks(GUI, SET_SIZE, new OSetSizeTest(3));
        createStartWorks(GUI, SET_TEXT, new OSetTextTest(2));
        createStartWorks(GUI, SET_VISIBLE, new OSetVisibleTest(2));
        createStartWorks(GUI, ACTIONS, new OAddActionsTest(1));

        createStartWorks(GUI, BUTTON, new OButtonTest(1, 2));
        createStartWorks(GUI, LABEL, new OLabelTest(1, 2));
        createStartWorks(GUI, PASSWORD_FIELD, new OPasswordFieldTest(1, 2));
        createStartWorks(GUI, TEXT_FIELD, new OTextFieldTest(1, 2));
        createStartWorks(GUI, PANEL, new OPanelTest(1));
        createStartWorks(GUI, FRAME, new OFrameTest(0, 1));

        createReturnWorks(GUI, GET_CONTAINS, new OGetContainsTest(3));
        createReturnWorks(GUI, GET_HEIGHT, new OGetHeightTest(1));
        createReturnWorks(GUI, GET_POSITION, new OGetPositionTest(1));
        createReturnWorks(GUI, GET_TEXT, new OGetTextTest(1));
        createReturnWorks(GUI, GET_WIDTH, new OGetWidthTest(1));
        createReturnWorks(GUI, GET_X, new OGetXTest(1));
        createReturnWorks(GUI, GET_Y, new OGetYTest(1));
    }

    private static void start4() {
        new File(MODULE_PATH + "/music").mkdirs();
        for (File file : Objects.requireNonNull(new File("module/music").listFiles())) {
            String name = file.getName();
            if (name.endsWith(".wav")) {
                files.add(name);
                cp("music", file);
                cp1("music", file);
            }
        }

        CreateMusic createMusic = new CreateMusic(1);
        Arrays.asList(도, 레, 미, 파, 솔, 라, 시).forEach(v -> createStartWorks(MUSIC, v, createMusic));
        createStartWorks(MUSIC, PITCH, new SetPitch(2));
        createStartWorks(MUSIC, VOLUME, new SetVolume(2));
        createStartWorks(MUSIC, SPEED, new SetSpeed(2));
        createStartWorks(MUSIC, LOOP, new SetLoop(2));
        createStartWorks(MUSIC, PLAY, new PlaySound());

        createReturnWorks(MUSIC, VOLUME, new GetVolume(1));
        createReturnWorks(MUSIC, PITCH, new GetPitch(1));
        createReturnWorks(MUSIC, SPEED, new GetSpeed(1));
        createReturnWorks(MUSIC, LOOP, new GetLoop(1));
    }

    private static void start5() {
        new File(MODULE_PATH + "/shell").mkdirs();
        for (File file : Objects.requireNonNull(new File("module/groovy").listFiles())) {
            String name = file.getName();
            if (name.endsWith(".jar")) {
                files.add(name);
                cp("shell", file);
                cp1("shell", file);
            }
        }

        system.add(SHELL + ACCESS + SHELL_START);

        createStartWorks(SHELL, "", new CreateShell());
        createStartWorks(SHELL, SHELL_START, new StartShell());
    }

    private static void cp(String name, File file) {
        String path = MODULE_PATH + "/" + name + "/" + file.getName();
        if (!new File(path).isFile()) {
            try {Files.copy(file.toPath(), Path.of(path), REPLACE_EXISTING);}
            catch (IOException ignored) {}
        }
    }

    private static void cp1(String name, File file) {
        String path = getPath(SEPARATOR_HOME, "Documents/GitHub/module", name, file.getName());
        if (!new File(path).isFile()) {
            try {Files.copy(file.toPath(), Path.of(path), REPLACE_EXISTING);}
            catch (IOException ignored) {}
        }
    }
}
