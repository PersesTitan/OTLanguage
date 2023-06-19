package cos.color;

import bin.apply.item.Item;
import bin.variable.custom.CustomList;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;

@Getter
@AllArgsConstructor
public class ColorItem implements Item {
    private Color color;

    public ColorItem(int r, int g, int b, int a) {
        checkColorValue(r);
        checkColorValue(g);
        checkColorValue(b);
        checkColorValue(a);
        this.color = new Color(r, g, b, a);
    }

    public ColorItem(int r, int g, int b) {
        checkColorValue(r);
        checkColorValue(g);
        checkColorValue(b);
        this.color = new Color(r, g, b);
    }

    public ColorItem(int rgb) {
        this.color = new Color(rgb);
    }

    public int getR() {
        return color.getRed();
    }

    public int getB() {
        return color.getBlue();
    }

    public int getG() {
        return color.getGreen();
    }

    public int getA() {
        return color.getAlpha();
    }

    public CustomList<Integer> get() {
        return new CustomList<>(getR(), getG(), getB(), getA());
    }

    public void set(int r, int g, int b, int a) {
        this.color = new Color(r, g, b, a);
    }

    public void setR(int r) {
        this.color = new Color(r, getG(), getB(), getA());
    }

    public void setG(int g) {
        this.color = new Color(getR(), g, getB(), getA());
    }

    public void setB(int b) {
        this.color = new Color(getR(), getG(), b, getA());
    }

    public void setA(int a) {
        this.color = new Color(getR(), getG(), getB(), a);
    }

    private void checkColorValue(int value) {
        if (value < 0 || value > 255) throw ColorException.COLOR_VALUE_ERROR.getThrow(value);
    }

    @Override
    public String toString() {
        return this.itemToString(ColorToken.COLOR, getR(), getG(), getB(), getA());
    }
}
