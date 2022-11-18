package cos.input.setting;

import bin.apply.Setting;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public interface SetIcon {
    default void setIcon(String filePath) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            Taskbar.getTaskbar().setIconImage(((ImageIcon) inputStream.readObject()).getImage());
        } catch (IOException | ClassNotFoundException e) {
            Setting.warringMessage("아이콘을 불러올 수 없습니다.");
        }
    }
}
