package etc.input.keyboard;

import bin.apply.Setting;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public interface CreateIconTest {
    static void setIcon() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("icon.otlm"))) {
            Taskbar.getTaskbar().setIconImage(((ImageIcon) inputStream.readObject()).getImage());
        } catch (IOException | ClassNotFoundException e) {
            Setting.warringMessage("아이콘을 불러올 수 없습니다.");
        }
    }
}
