package module.download;

import java.io.*;
import java.util.*;

import static bin.apply.Repository.*;
import static bin.apply.sys.item.Separator.*;
import static bin.token.LoopToken.*;

public class MakeGitTest implements SetMakeGit, CreateGitDir {
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

    public static final List<String> system = new ArrayList<>();
    public static final List<File> list = new ArrayList<>();
    public static final List<String> files = new ArrayList<>();
    private void listFile(File file) {
        for (File cpFile : Objects.requireNonNull(file.listFiles())) {
            if (cpFile.isFile()) list.add(cpFile);
            else listFile(cpFile);
        }
    }

    public final static String SYSTEM = "system:";
    public final static String MODULE = "module:";
    public final static String CLASS  = "class:";
    public final static String FILE = "file:";

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
            try (BufferedWriter bw = getSystem(namePath2);
                 BufferedWriter bw1 = getSystem(MODULE_PATH)) {
                for (String v : system) {
                    bw.write(v.replace("\\", ""));
                    bw.newLine();
                    // 기본 설치 위치
                    bw1.write(v.replace("\\", ""));
                    bw1.newLine();
                }
            } catch (IOException e) {e.printStackTrace();}
        }

        try (BufferedWriter br = getSystem(namePath)) {
            for (String str : files) {
                String path1 = getPath("module", name, str);
                String path2 = getPath(MODULE_PATH, name, str);
                copy(new File(path1), new File(path2));
            }

            br.write(SYSTEM);
            br.newLine();
            for (String systemItem : system) {
                br.write("   " + systemItem.replace("\\", ""));
                br.newLine();
            }
            br.newLine();

            br.write(MODULE);
            br.newLine();
            createModule(priorityStartWorksV3, br, Type.COMPULSION, name, bool, namePath, namePath2);
            createModule(returnWorksV3, br, Type.ALTERATION, name, bool, namePath, namePath2);
            createModule(startWorksV3, br, Type.OPERATE, name, bool, namePath, namePath2);

            br.newLine();
            br.write(CLASS);
            // class 파일 복사 및 가져오기 & 값 복사
            List<File> fileList = copyFiles(br, fileName, bool);
            for (File f : fileList) {
                // 모듈
                String path = getPath(file.getAbsolutePath(), f.getName());
                File copyPath = new File(path);
                copy(f, copyPath);
                // 기본
                String path1 = getPath(INSTALL_PATH, "analyzer", f.toString().substring("out/production/classes/".length()));
                File file1 = new File(path1);
                makeDir(file1);
                copy(f, file1);
            }

            // origin 추가시 work 값 추가
            if (bool) {
                for (File v : copyFiles(br, "src/main/java/work", true)) {
                    String path = getPath(file.getAbsolutePath(), v.getName());
                    File copyPath = new File(path);
                    copy(v, copyPath);
                }
            }

            if (!files.isEmpty()) {
                br.newLine();
                br.newLine();
                br.write(FILE);
                for (String line : files) {
                    br.newLine();
                    br.write("   ");
                    br.write(line.replace("/", ACCESS));
                }
            }
        } catch (IOException e) {e.printStackTrace();}
    }
}
