package module.compile.item;

import bin.apply.sys.make.Bracket;
import bin.apply.sys.make.StartLine;
import bin.exception.FileException;
import lombok.ToString;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static bin.apply.sys.item.Separator.EXT_REP;
import static bin.apply.sys.item.Separator.SEPARATOR_LINE;
import static bin.token.LoopToken.LOOP_TOKEN;

@ToString
public class FileSave implements Serializable {
    private String total;
    private String finalTotal;

    private String fileName;      // test
    private String separator;     // .otl

    public FileSave(File file) {
        // set EXT_REP
        String fileName = file.getName();
        int count = fileName.indexOf('.');
        this.fileName  = fileName.substring(0, count);
        this.separator = fileName.substring(count+1);

        this.finalTotal = Bracket.getInstance().bracket(getTotal(file), file);
        this.total = LOOP_TOKEN.get(this.fileName);
    }

    private String getTotal(File file) {
        StringBuilder total = new StringBuilder();
        try (FileReader fr = new FileReader(file, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(fr)) {
            for (int i = 1;;i++) {
                String line = reader.readLine();
                if (line == null) break;
                total.append(i).append(" ").append(line.stripLeading()).append(SEPARATOR_LINE);
            }
        } catch (IOException e) {
            if (StartLine.developmentMode) e.printStackTrace();
            throw new FileException().noReadError();
        }
        return total.toString();
    }

    @Serial
    private void readObject(ObjectInputStream oi) throws IOException, ClassNotFoundException {
        oi.defaultReadObject();

        EXT_REP.put(fileName, separator);
        LOOP_TOKEN.put(fileName, total);
        oi.close();
    }

    public String getTotal() {
        return total;
    }

    public String getFinalTotal() {
        return finalTotal;
    }

    public String getFileName() {
        return fileName;
    }

    public String getSeparator() {
        return separator;
    }
}
