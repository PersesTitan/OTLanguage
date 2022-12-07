package v4;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class CreateItem {
    public static JButton createButton() {
        return new JButton();
    }

    public static Canvas createCanvas() {
        return new Canvas();
    }

    public static Checkbox createCheckbox() {
        return new Checkbox();
    }

    public static Choice createChoice() {
        return new Choice();
    }

    public static Container createContainer() {
        return new Container();
    }

    public static JPanel createPanel() {
        return new JPanel();
    }

    public static JWindow createWindow() {
        return new JWindow();
    }

    public static JDialog createDialog() {
        return new JDialog();
    }

    public static FileDialog createFileDialog(Frame frame) {
        return new FileDialog(frame);
    }

    public static JFrame createFrame() {
        return new JFrame();
    }

    public static JScrollPane createScrollPane() {
        return new JScrollPane();
    }

    public static JLabel createLabel() {
        return new JLabel();
    }

    public static Scrollbar createScrollbar() {
        return new Scrollbar();
    }

//    public static JTextComponent createTextComponent() {
//        return new JTextComponent();
//    }
}
