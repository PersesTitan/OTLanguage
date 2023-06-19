package cos.file;

import bin.apply.item.Item;
import bin.apply.mode.TypeMode;
import bin.exception.FileException;
import bin.token.SepToken;
import bin.variable.OtherList;
import bin.variable.custom.CustomList;
import lombok.Getter;

import java.io.*;
import java.util.Arrays;
import java.util.Iterator;

public class FileItem implements Item {
    @Getter
    private final File file;
    private final String filePath;

    public FileItem(String path) {
        this.file = new File(path);
        this.filePath = path;
    }

    public FileItem(File file) {
        this.file = file;
        this.filePath = file.getPath();
    }

    public boolean isFile() {
        return file.isFile();
    }

    public boolean isDir() {
        return file.isDirectory();
    }

    public boolean exists() {
        return this.file.exists();
    }

    public String getName() {
        return this.file.getName();
    }

    public String getPath() {
        return this.file.getPath();
    }

    public String getAbsolutePath() {
        return this.file.getAbsolutePath();
    }

    public OtherList<FileItem> list() {
        OtherList<FileItem> list = new OtherList<>(FileToken.FILE);
        File[] files = this.file.listFiles();
        if (files != null) Arrays.stream(files)
                .map(FileItem::new)
                .forEach(list::add);
        return list;
    }

    public CustomList<String> read() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return new CustomList<>(TypeMode.STRING, reader.lines().toList());
        } catch (IOException e) {
            throw FileException.DO_NOT_READ.getThrow(this);
        }
    }

    public String readAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) total.append(line).append(SepToken.LINE);
            return total.toString();
        } catch (IOException e) {
            throw FileException.DO_NOT_READ.getThrow(this);
        }
    }

    public void write(CustomList<String> collection) {
        Iterator<String> iterator = collection.iterator();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            while (iterator.hasNext()) {
                writer.write(iterator.next());
                if (iterator.hasNext()) writer.newLine();
            }
        } catch (IOException e) {
            throw FileException.CREATE_FILE_ERROR.getThrow(this.file);
        }
    }

    @Override
    public String toString() {
        return this.itemToString(FileToken.FILE, filePath);
    }
}
