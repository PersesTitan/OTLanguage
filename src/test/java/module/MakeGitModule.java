package module;

import bin.apply.Repository;
import bin.apply.sys.run.FilePath;
import bin.apply.sys.run.ForceQuit;
import bin.apply.sys.run.Sleep;
import bin.apply.sys.run.TryCatch;
import bin.orign.console.normal.*;
import bin.orign.console.normal.Scanner;
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
import cos.poison.setting.PoisonPassURL;
import cos.poison.setting.PoisonStart;
import work.v3.ReturnWorkV3;
import work.v3.StartWorkV3;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

import static bin.apply.Repository.*;
import static bin.apply.Repository.returnWorksV3;
import static bin.apply.sys.item.Separator.*;
import static bin.apply.sys.item.SystemSetting.*;
import static bin.token.ConsoleToken.*;
import static bin.token.ConsoleToken.PRIORITY_PRINT_SPACE;
import static bin.token.LoopToken.*;
import static bin.token.StringToken.*;
import static bin.token.VariableToken.FILE;
import static bin.token.VariableToken.MAP_LIST;
import static bin.token.cal.NumberToken.*;
import static cos.poison.PoisonRepository.POISON_PASS_URL;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class MakeGitModule {
    public static void main(String[] args) {
        File file = new File(SEPARATOR_HOME + "/Documents/GitHub/module");
        for (File folder : Objects.requireNonNull(file.listFiles())) {
            if (folder == null
                    || folder.getName().equals(".git")
                    || folder.getName().startsWith(".")) continue;
            else if (folder.listFiles() == null) {
                folder.delete();
                continue;
            }
            for (File fileList : Objects.requireNonNull(folder.listFiles())) fileList.delete();
            folder.delete();
        }

        clear(); start1();
        reads(new File("src/main/java/bin"));
        new MakeGitModule("origin", list);

        // poison
        clear(); start2();
        reads(new File("src/main/java/cos/poison"));
        new MakeGitModule("poison", list);
    }

    private static void clear() {
        list.clear();
        priorityStartWorksV3.clear();
        startWorksV3.clear();
        returnWorksV3.clear();
    }

    private static final List<File> list = new ArrayList<>();
    private static void reads(File file) {
        for (File cpFile : Objects.requireNonNull(file.listFiles())) {
            if (cpFile.isFile()) list.add(cpFile);
            else reads(cpFile);
        }
    }

    private String getModuleName(HashMap<String, Map<String, StartWorkV3>> map) {
        if (map.equals(priorityStartWorksV3)) return COMPULSION;
        else return OPERATE;
    }

    // ex) moduleName = origin
    private MakeGitModule(String moduleName, List<File> lists) {
        String p = SEPARATOR_HOME + "/Documents/GitHub/module/" + moduleName;
        File file = new File(p);
        if (!file.exists() && file.mkdirs() || file.exists()) {
            try (BufferedWriter br = new BufferedWriter(
                    new FileWriter(p + "/system" + SYSTEM_EXTENSION, !file.exists() && file.createNewFile()))) {
                write(priorityStartWorksV3, br, moduleName, p);
                write(startWorksV3, br, moduleName, p);

                if (!returnWorksV3.isEmpty()) {
                    br.write(ALTERATION + ":");
                    br.write(moduleName);
                    br.newLine();
                    // 모듈 생성
                    try (var output = new ObjectOutputStream(new FileOutputStream(p + "/" + ALTERATION + MODULE_EXTENSION))) {
                        output.writeObject(returnWorksV3);
                    } catch (IOException ignored) {}
                }

//                if (lists != null) {
//                    for (File f : lists) {
//                        int start = (System.getProperty("user.home") + "/Documents/Java/OTLanguage/src/main/java/").length();
//                        int end = f.getAbsolutePath().length() - ".java".length();
//                        String value = f.getAbsolutePath().substring(start, end).replace("/", ".");
//                        br.newLine();
//                        br.write(value.replace(".", "~"));
//
//                        String fileName = f.getName();
//                        String klassName = file.getAbsolutePath() + "/" + fileName.substring(0, fileName.length()-".java".length()) + ".class";
//                        copy(value.replace(".", SEPARATOR_FILE), klassName);
//                    }
//                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void write(HashMap<String, Map<String, StartWorkV3>> map,
                       BufferedWriter br,
                       String moduleName, String p) throws IOException {
        if (map.isEmpty()) return;
        br.write(getModuleName(map) + ":");
        br.write(moduleName);
        br.newLine();
        // 모듈 생성
        try (var output = new ObjectOutputStream(new FileOutputStream(p + "/" + getModuleName(map) + MODULE_EXTENSION))) {
            output.writeObject(map);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        for (var a : map.values().stream().toList()) {
//            for (var v : a.values().stream().toList()) {
//                String klassPath = v.getClass().getName();
//                br.newLine();
//                br.write(klassPath.replace(".", "~"));
//                String klassName = p + "/" + v.getClass().getSimpleName() + ".class";
//                copy(klassPath.replace(".", SEPARATOR_FILE), klassName);
//            }
//        }
    }

    private void copy(String klassPath, String klassName) throws IOException {
        String path = "out/production/classes/" + klassPath + ".class";
        Files.copy(new File(path).toPath(), new File(klassName).toPath(), REPLACE_EXISTING);
    }

    //////////////////////////////////////////////////////////////////////////
    private static void start1() {
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

        createReturnWorks(RANDOM_BOOL, "", new RandomBoolean(1));
        createReturnWorks(RANDOM_DOUBLE, "", new RandomDouble(1, 2));
        createReturnWorks(RANDOM_FLOAT, "", new RandomFloat(1, 2));
        createReturnWorks(RANDOM_INTEGER, "", new RandomInteger(1, 2));
        createReturnWorks(RANDOM_LONG, "", new RandomLong(1, 2));
        createReturnWorks(SCANNER, "", new Scanner(0));
        createReturnWorks(STRING_VARIABLE, JOIN, new Join(2));
        createReturnWorks(STRING_VARIABLE, SPLIT, new Split(2));
        createReturnWorks(STRING_VARIABLE, SPLIT_REGULAR, new SplitRegular(2));
        createReturnWorks(STRING_VARIABLE, CONTAINS_S, new Contains(2));
        createReturnWorks(STRING_VARIABLE, EQUALS_S, new Equals(2));
    }

    private static void start2() {
        createStartWorks(POISON, "", new PoisonCreate(1, 2));
        createStartWorks(POISON, POISON_START, new PoisonStart(0));
        createStartWorks(POISON, POISON_POST, new PoisonMethod(HttpMethod.POST));
        createStartWorks(POISON, POISON_GET, new PoisonMethod(HttpMethod.GET));
    }
}
