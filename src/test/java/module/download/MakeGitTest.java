package module.download;

import bin.apply.Repository;
import bin.apply.sys.run.FilePath;
import bin.apply.sys.run.ForceQuit;
import bin.apply.sys.run.Sleep;
import bin.apply.sys.run.TryCatch;
import bin.orign.console.normal.*;
import bin.orign.console.priority.PriorityPrint;
import bin.orign.console.priority.PriorityPrintSpace;
import bin.orign.console.priority.PriorityPrintTap;
import bin.orign.console.priority.PriorityPrintln;
import bin.orign.math.random.*;
import bin.orign.variable.CreateList;
import bin.orign.variable.CreateMap;
import bin.orign.variable.CreateOrigin;
import bin.orign.variable.CreateSet;
import bin.string.*;
import cos.poison.controller.HttpMethod;
import cos.poison.setting.PoisonCreate;
import cos.poison.setting.PoisonMethod;
import cos.poison.setting.PoisonStart;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static bin.apply.Repository.*;
import static bin.apply.sys.item.Separator.*;
import static bin.token.ConsoleToken.*;
import static bin.token.LoopToken.*;
import static bin.token.StringToken.*;
import static bin.token.VariableToken.FILE;
import static bin.token.VariableToken.STRING_VARIABLE;
import static bin.token.cal.NumberToken.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class MakeGitTest {
    public MakeGitTest() {
        clear();
        start1();
        new MakeGitTest("origin", "src/main/java/bin");
        clear();
        start2();
        new MakeGitTest("poison", "src/main/java/cos/poison");
    }

    private static void clear() {
        list.clear();
        priorityStartWorksV3.clear();
        startWorksV3.clear();
        returnWorksV3.clear();
    }

    private static final List<File> list = new ArrayList<>();
    private void listFile(File file) {
        for (File cpFile : Objects.requireNonNull(file.listFiles())) {
            if (cpFile.isFile()) list.add(cpFile);
            else listFile(cpFile);
        }
    }

    String COMPULSION = "compulsion";   // 강제
    String ALTERATION = "alteration";   // 변경
    String OPERATE = "operate";         // 동작

    // name : poison
    private MakeGitTest(String name, String fileName) {
        listFile(new File(fileName));
        // User/name/Documents/GitHub/module/poison
        String namePath = SEPARATOR_HOME + "/Documents/GitHub/module/" + name;
        File file = new File(namePath);
        file.mkdirs();
        try (BufferedWriter br = new BufferedWriter(new FileWriter(namePath + "/system.otls", false))) {
            if (!priorityStartWorksV3.isEmpty()) {
                try (var output = new ObjectOutputStream(new FileOutputStream(namePath + "/" + COMPULSION + MODULE_EXTENSION))) {
                    output.writeObject(priorityStartWorksV3);
                } catch (IOException ignored) {}
                br.write(COMPULSION);
                br.newLine();
            }

            if (!returnWorksV3.isEmpty()) {
                try (var output = new ObjectOutputStream(new FileOutputStream(namePath + "/" + ALTERATION + MODULE_EXTENSION))) {
                    output.writeObject(returnWorksV3);
                } catch (IOException ignored) {}
                br.write(ALTERATION);
                br.newLine();
            }

            if (!startWorksV3.isEmpty()) {
                try (var output = new ObjectOutputStream(new FileOutputStream(namePath + "/" + OPERATE + MODULE_EXTENSION))) {
                    output.writeObject(startWorksV3);
                } catch (IOException ignored) {}
                br.write(OPERATE);
                br.newLine();
            }

            for (File f : list) {
                int start = (System.getProperty("user.home") + "/Documents/Java/OTLanguage/src/main/java/").length();
                int end = f.getAbsolutePath().length() - ".java".length();
                String value = f.getAbsolutePath().substring(start, end).replace("/", "~");
                br.newLine();
                br.write(value.replace(".", "~"));

                String klassName = file.getAbsolutePath() + "/" + f.getName().substring(0, f.getName().length()-".java".length()) + ".class";
                System.out.println(klassName);
                copy(value.replace("~", SEPARATOR_FILE), klassName);

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
        Repository.priorityCreateStartWorks(FORCE_QUIT, "", new ForceQuit(0));
        Repository.priorityCreateStartWorks(PRIORITY_PRINT, "", new PriorityPrint(1));
        Repository.priorityCreateStartWorks(PRIORITY_PRINTLN, "", new PriorityPrintln(1));
        Repository.priorityCreateStartWorks(PRIORITY_PRINT_TAP, "", new PriorityPrintTap(1));
        Repository.priorityCreateStartWorks(PRIORITY_PRINT_SPACE, "", new PriorityPrintSpace(1));
        Repository.priorityCreateStartWorks(TRY_CATCH, "", new TryCatch(1));

        CreateOrigin createOrigin = new CreateOrigin();
        CreateSet createSet = new CreateSet();
        CreateList createList = new CreateList();
        CreateMap createMap = new CreateMap();
        ORIGIN_LIST.forEach(v -> Repository.createStartWorks(v, "", createOrigin));
        SET_LIST.forEach(v -> Repository.createStartWorks(v, "", createSet));
        LIST_LIST.forEach(v -> Repository.createStartWorks(v, "", createList));
        MAP_LIST.forEach(v -> Repository.createStartWorks(v, "", createMap));
        Repository.createStartWorks(PRINT, "", new Print(1));
        Repository.createStartWorks(PRINTLN, "", new Println(1));
        Repository.createStartWorks(PRINT_SPACE, "", new PrintSpace(1));
        Repository.createStartWorks(PRINT_TAP, "", new PrintTap(1));
        Repository.createStartWorks(SLEEP, "", new Sleep(1));
        Repository.createStartWorks(FILE, "", new FilePath(1));

        Repository.createReturnWorks(RANDOM_BOOL, "", new RandomBoolean(1));
        Repository.createReturnWorks(RANDOM_DOUBLE, "", new RandomDouble(1, 2));
        Repository.createReturnWorks(RANDOM_FLOAT, "", new RandomFloat(1, 2));
        Repository.createReturnWorks(RANDOM_INTEGER, "", new RandomInteger(1, 2));
        Repository.createReturnWorks(RANDOM_LONG, "", new RandomLong(1, 2));
        Repository.createReturnWorks(SCANNER, "", new Scanner(0));
        Repository.createReturnWorks(STRING_VARIABLE, JOIN, new Join(2));
        Repository.createReturnWorks(STRING_VARIABLE, SPLIT, new Split(2));
        Repository.createReturnWorks(STRING_VARIABLE, SPLIT_REGULAR, new SplitRegular(2));
        Repository.createReturnWorks(STRING_VARIABLE, CONTAINS_S, new Contains(2));
        Repository.createReturnWorks(STRING_VARIABLE, EQUALS_S, new Equals(2));
    }

    private static void start2() {
        Repository.createStartWorks(POISON, "", new PoisonCreate(1, 2));
        Repository.createStartWorks(POISON, POISON_START, new PoisonStart(0));
        Repository.createStartWorks(POISON, POISON_POST, new PoisonMethod(HttpMethod.POST));
        Repository.createStartWorks(POISON, POISON_GET, new PoisonMethod(HttpMethod.GET));
    }
}
