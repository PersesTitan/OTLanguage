package work.setting;

import bin.apply.Repository;
import bin.apply.Setting;
import work.ReturnWork;
import work.StartWork;

import java.io.*;

import static bin.apply.sys.item.Separator.SEPARATOR_FILE;
import static work.setting.ReadOTLM.*;

public class MakeOTLM implements Repository {
    public static void main(String[] args) {
        Setting.firstStart();
        new MakeOTLM().start("system.otls");
    }

    public void start(String url) {
        File file = new File(url);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            makeWriter(writer, compulsion);
            priorityWorks.forEach(v -> {
                StringBuilder builder = new StringBuilder("module");
                builder.append(SEPARATOR_FILE).append(compulsion);
                if (!new File(builder.toString()).mkdirs()) {
                    builder.append(SEPARATOR_FILE).append(v.getClass().getSimpleName()).append(modelPath);
                    try {
                        writer.append("    ").append(v.getClass().getSimpleName());
                        writer.newLine();
                    } catch (IOException ignored) {}
                    makeWork(builder.toString(), v);
                }
            });

            writer.newLine();
            makeWriter(writer, alteration);
            returnWorks.forEach(v -> {
                StringBuilder builder = new StringBuilder("module");
                builder.append(SEPARATOR_FILE).append(alteration);
                if (!new File(builder.toString()).mkdirs()) {
                    builder.append(SEPARATOR_FILE).append(v.getClass().getSimpleName()).append(modelPath);
                    try {
                        writer.append("    ").append(v.getClass().getSimpleName());
                        writer.newLine();
                    } catch (IOException ignored) {}
                    makeWork(builder.toString(), v);
                }
            });

            writer.newLine();
            makeWriter(writer, operate);
            startWorks.forEach(v -> {
                StringBuilder builder = new StringBuilder("module");
                builder.append(SEPARATOR_FILE).append(operate);
                if (!new File(builder.toString()).mkdirs()) {
                    builder.append(SEPARATOR_FILE).append(v.getClass().getSimpleName()).append(modelPath);
                    try {
                        writer.append("    ").append(v.getClass().getSimpleName());
                        writer.newLine();
                    } catch (IOException ignored) {}
                    makeWork(builder.toString(), v);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void makeWriter(BufferedWriter writer, String type) throws IOException {
        writer.append(type).append(":");
        writer.newLine();
    }

    private void makeWork(String path, ReturnWork work) {
        try (ObjectOutput output = new ObjectOutputStream(new FileOutputStream(path))) {
            output.writeObject(work);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void makeWork(String path, StartWork work) {
        try (ObjectOutput output = new ObjectOutputStream(new FileOutputStream(path))) {
            output.writeObject(work);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
