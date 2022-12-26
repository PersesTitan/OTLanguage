package v4;

import v4.bin.apply.Setting;
import v4.bin.apply.sys.item.Type;
import v4.bin.apply.sys.item.TypeMap;
import v4.bin.origin.console.Print;
import v4.bin.origin.console.Println;
import v4.bin.origin.console.Scanner;
import v4.item.ReturnItem;
import v4.item.StartItem;
import v4.bin.origin.string.Repeat;
import v4.work.ReturnWork;
import v4.work.StartWork;

import java.util.HashMap;
import java.util.LinkedList;

import static v4.item.Repository.*;

public class Main {
    static {
        StartItem s1 = new StartItem(false, Type.STRING_VARIABLE.getType());
        startWork("ㅅㅁㅅ", "", new Print(s1));
        startWork("ㅆㅁㅆ", "", new Println(s1));

        ReturnItem r1 = new ReturnItem(Type.STRING_VARIABLE.getType());
        ReturnItem r2 = new ReturnItem(Type.STRING_VARIABLE.getType(), Type.INT_VARIABLE.getType());
        returnWork("ㅅㅇㅅ", "", new Scanner(r1));
        returnWork("ㅇㅁㅇ", "ㅇㄹㅇ", new Repeat(r1, r2));
    }

    public static void main(String[] args) {
        String total = """
                ㅆㅁㅆ :ㅇㅁㅇ~ㅇㄹㅇ[ㅇ][5]_
                """;
        LinkedList<TypeMap> repositoryArray = new LinkedList<>() {{
            add(new TypeMap());
        }};
        total.lines().forEach(v -> Setting.start(v, "", repositoryArray));
    }

    private static void startWork(String klass, String method, StartWork work) {
        if (startWorks.containsKey(klass)) startWorks.get(klass).put(method, work);
        else startWorks.put(klass, new HashMap<>() {{put(method, work);}});
    }

    private static void returnWork(String klass, String method, ReturnWork work) {
        if (returnWorks.containsKey(klass)) returnWorks.get(klass).put(method, work);
        else returnWorks.put(klass, new HashMap<>() {{put(method, work);}});
    }
}
