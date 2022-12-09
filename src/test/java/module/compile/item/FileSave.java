package module.compile.item;

import bin.apply.Controller;
import bin.apply.Setting;
import bin.apply.sys.make.Bracket;
import bin.apply.sys.make.StartLine;
import bin.exception.FileException;
import lombok.Getter;
import lombok.ToString;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

import static bin.apply.Repository.repository;
import static bin.apply.Setting.*;
import static bin.apply.Setting.mainPath;
import static bin.apply.sys.item.Separator.EXT_REP;
import static bin.token.LoopToken.LOOP_TOKEN;

@Getter
@ToString
public class FileSave implements Serializable {
    private final String total;
    private final String finalTotal;

    private final String fileName;      // test
    private final String separator;     // .otl

    private final Set<String> useModel = new HashSet<>();

    public FileSave(File file, Set<String> useModel) {
        this.useModel.addAll(useModel);
        mainPath = file.getAbsolutePath();
        path = file.getAbsoluteFile().getParent();
        // set EXT_REP
        String fileName = file.getName();
        int count = fileName.indexOf('.');
        this.fileName  = fileName.substring(0, count);
        this.separator = fileName.substring(count+1);
        // 에러 발생하는지 확인
        Controller.readFile(mainPath, Setting.total);
        check();
        // set total
        this.total = Setting.total.toString();
        String total = LOOP_TOKEN.get(this.fileName);
        this.finalTotal = Bracket.getInstance().bracket(total, this.fileName, false);
    }

    private void check() {
        try {
            StartLine.startLine(Setting.total.toString(), mainPath, repository);
        } catch (Exception e) {
            if (StartLine.developmentMode) e.printStackTrace();
            throw new FileException().compileError();
        }
    }

    @Serial
    private void readObject(ObjectInputStream oi) {
        try (oi) {
            oi.defaultReadObject();


            EXT_REP.put(fileName, separator);
            LOOP_TOKEN.put(fileName, total);
        } catch (IOException | ClassNotFoundException e) {
            if (StartLine.developmentMode) e.printStackTrace();
            throw new FileException().compileError();
        }
    }
}
