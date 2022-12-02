package etc.gui2;

import java.awt.*;

public enum GUI {
    BUTTON(Button.class),
    CANVAS(Canvas.class),
    CHECK_BOX(Checkbox.class),
    CHOICE(Choice.class),
    CONTAINER(Container.class),
    LABEL(Label.class),
    LIST(List.class),
    SCROLL_BAR(Scrollbar.class),
    TEXT_COMPONENT(TextComponent.class);

    private final Class<? extends Component> klass;
    GUI(Class<? extends Component> klass) {
        this.klass = klass;
    }

    public Component getValue(Object ob) {
        return klass.cast(ob);
    }

    // return Void
    public void setName(Object ob, String name) {
        getValue(ob).setName(name);
    }

    public void setEnabled(Object ob, boolean bool) {
        getValue(ob).setEnabled(bool);
    }

    public void setVisible(Object ob, boolean bool) {
        getValue(ob).setVisible(bool);
    }

    public void setForeground(Object ob, Color c) {
        getValue(ob).setForeground(c);
    }

    public void setBackground(Object ob, Color c) {
        getValue(ob).setBackground(c);
    }

    public void setFont(Object ob, Font f) {
        getValue(ob).setFont(f);
    }

    public void setLocation(Object ob, int x, int y) {
        getValue(ob).setLocation(x, y);
    }

    public void setCursor(Object ob, Cursor cursor) {
        getValue(ob).setCursor(cursor);
    }

    public void setSize(Object ob, int width, int height) {
        getValue(ob).setSize(width, height);
    }
}
