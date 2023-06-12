package cos.gui;

import bin.apply.mode.TypeMode;
import bin.variable.custom.CustomList;
import cos.color.ColorItem;

import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Types;

interface ComponentTool {
    String getText();
    String getName();
    Color getBackground();
    int getX();
    int getY();
    int getWidth();
    int getHeight();
    boolean contains(int x, int y);
    boolean isEnabled();
    boolean isVisible();

    Component add(Component component);
    void setText(String text);
    void setName(String name);
    void setBackground(Color bg);
    void setEnabled(boolean b);
    void setVisible(boolean aFlag);
    void setLocation(int x, int y);
    void setSize(int width, int height);
    void setFont(Font font);
    void addActionListener(ActionListener l);

    default void setBG(ColorItem color) {
        this.setBackground(color.getColor());
    }

    default ColorItem getBG() {
        return new ColorItem(getBackground());
    }

    default void setX(int x) {
        this.setLocation(x, getY());
    }

    default void setY(int y) {
        this.setLocation(getX(), y);
    }

    default void setWidth(int width) {
        this.setSize(width, getHeight());
    }

    default void setHeight(int height) {
        this.setSize(getWidth(), height);
    }

    default CustomList<Integer> getSizeList() {
        return new CustomList<>(TypeMode.INTEGER, getWidth(), getHeight());
    }

    default CustomList<Integer> getLocationList() {
        return new CustomList<>(Types.INTEGER, getX(), getY());
    }
}
