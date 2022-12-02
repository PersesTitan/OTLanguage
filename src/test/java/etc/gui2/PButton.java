package etc.gui2;

import java.awt.*;

public class PButton {
    public static void main(String[] args) {
        Object ob = new Button();
        Button b = (Button) GUI.BUTTON.getValue(ob);
        System.out.println(b.getX());
    }
}
