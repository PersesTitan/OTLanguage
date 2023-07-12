package cos.graph;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.Format;

@Getter
@Setter
@NoArgsConstructor
class PlotSettings implements Serializable {
    protected int marginTop = 10, marginBottom = 50, marginLeft = 50, marginRight = 20;
    protected double minX = -5, maxX = 5, minY = -5, maxY = 5;

    protected Color axisColor = Color.BLACK;
    protected Color plotColor = Color.BLACK;
    protected Color backgroundColor = Color.WHITE;
    protected Color gridColor = Color.LIGHT_GRAY;
    protected Color fontColor = Color.BLACK;

    protected int notchLength = 4;
    protected int notchGap = 4;
    protected boolean horizontalGridVisible = true;
    protected boolean verticalGridVisible = true;
    protected Format numberFormatter = new DecimalFormat("0.00");
    protected double gridSpacingX = 0.25;
    protected double gridSpacingY = 0.25;
    protected String title = null;

    public PlotSettings(double xMin, double xMax, double yMin, double yMax) {
        this.minX = xMin;
        this.maxX = xMax;
        this.minY = yMin;
        this.maxY = yMax;
    }

    public double getRangeX() {
        return maxX - minX;
    }

    public double getRangeY() {
        return maxY - minY;
    }
}
