package bin.apply.sys.compile;

import bin.apply.Repository;
import bin.apply.Setting;
import bin.apply.sys.compile.items.CompileItem;
import bin.apply.sys.item.SystemSetting;
import bin.apply.sys.make.StartLine;
import bin.exception.FileException;

import java.io.*;

import static bin.token.LoopToken.LOOP_SET;

public class Decompile {
    public static boolean isDecompile = false;

    public Decompile(File file) {
        isDecompile = true;
        boolean extension = SystemSetting.extensionCheck(file.getName(), "c");
        if (!extension) throw new FileException().rightExtension();
        Setting.mainPath = file.getAbsolutePath();
        Setting.path = file.getAbsoluteFile().getParent();

        CompileItem fileSave = input(file);
        LOOP_SET.addAll(fileSave.getUseModel());
        StartLine.startStartLine(fileSave.getFinalTotal(), fileSave.getTotal(), Repository.repository);
    }

    private CompileItem input(File file) {
        try (ObjectInput input = new ObjectInputStream(new FileInputStream(file))) {
            if (input.readObject() instanceof CompileItem fileSave) return fileSave;
            else throw new FileException().noReadError();
        } catch (IOException | ClassNotFoundException e) {
            throw new FileException().noFindError();
        }
    }
}
