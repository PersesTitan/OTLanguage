package module;

import bin.apply.Setting;
import work.v3.ReturnWorkV3;
import work.v3.StartWorkV3;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static bin.apply.Repository.*;
import static bin.apply.sys.item.Separator.*;

public class MakeTest {

    public static void main(String[] args) {
        HashMap<String, Map<String, StartWorkV3>> priorityStartWorksV3 = new HashMap<>();

        new Setting();
        System.out.println(COMPULSION_PATH);
        System.out.println(ALTERATION_PATH);
        System.out.println(OPERATE_PATH);

        new File(COMPULSION_PATH).mkdirs();
        new File(ALTERATION_PATH).mkdirs();
        new File(OPERATE_PATH).mkdirs();

        new MakeModule<LinkedList<Map<String, Map<String, Object>>>>().makeWork("list.otlm", repository);
        new MakeModule<HashMap<String, Map<String, StartWorkV3>>>().makeWork(OPERATE_PATH + SEPARATOR_FILE + COMPULSION + MODULE_EXTENSION, startWorksV3);
        new MakeModule<HashMap<String, Map<String, StartWorkV3>>>().makeWork(COMPULSION_PATH + SEPARATOR_FILE + ALTERATION + MODULE_EXTENSION, priorityStartWorksV3);
        new MakeModule<HashMap<String, Map<String, ReturnWorkV3>>>().makeWork(ALTERATION_PATH + SEPARATOR_FILE + OPERATE + MODULE_EXTENSION, returnWorksV3);
    }
}
