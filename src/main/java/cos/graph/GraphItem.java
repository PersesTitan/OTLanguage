package cos.graph;

import bin.apply.item.Item;
import lombok.RequiredArgsConstructor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Vector;

@RequiredArgsConstructor
class GraphItem implements Item {
    public final Vector<Plotter> functions = new Vector<>(5);
    public final PlotSettings plotSettings;

    protected double plotRangeX, plotRangeY;
    protected int chartWidth, chartHeight;
    protected double unitsPerPixelX, unitsPerPixelY;

    public void draw(Graphics g, int width, int height) {
        if (plotSettings.title != null) {
            g.setColor(plotSettings.fontColor);
            if (plotSettings.marginTop < g.getFontMetrics().getHeight() + 20)
                plotSettings.marginTop = g.getFontMetrics().getHeight() + 20;
            int titleXPosition = (width / 2) - ((g.getFontMetrics().stringWidth(plotSettings.title)) / 2);
            g.drawString(plotSettings.title, titleXPosition, 10 + g.getFontMetrics().getHeight());
        }

        unitsPerPixelX = (plotRangeX = Math.abs(plotSettings.maxX - plotSettings.minX)) /
                (chartWidth = width - (plotSettings.marginLeft + plotSettings.marginRight));
        unitsPerPixelY = (plotRangeY = Math.abs(plotSettings.maxY - plotSettings.minY)) /
                (chartHeight = height - (plotSettings.marginTop + plotSettings.marginBottom));

        g.setColor(plotSettings.backgroundColor);
        g.fillRect(plotSettings.marginLeft, plotSettings.marginTop, chartWidth - 1, chartHeight - 1);

        int columnIndex = 0;
        double firstGridXLocation = ((int) (plotSettings.getMinX() / plotSettings.getGridSpacingX())) * plotSettings.getGridSpacingX();

        for (double px = firstGridXLocation; px <= plotSettings.getMaxX(); px += plotSettings.getGridSpacingX()) {
            if (px < plotSettings.getMinX()) continue;
            int plotX = getPlotX(px);
            int plotY = plotSettings.marginTop + chartHeight;
            if (plotSettings.verticalGridVisible) {
                g.setColor(plotSettings.gridColor);
                g.drawLine(plotX, plotSettings.marginTop, plotX, plotY);
            }

            g.setColor(plotSettings.axisColor);
            g.drawLine(plotX, plotY, plotX, plotY + plotSettings.notchLength);
            String value;
            int labelXPosition;

            Plotter function = functions.elementAt(0);
            if (function instanceof DiscreteFunctionPlotter discrete) {
                value = discrete.getLabel(columnIndex);
                int columnWidth = chartWidth / discrete.getColumnCount();
                int columnCenterX = (columnIndex * columnWidth) + (columnWidth / 2);
                labelXPosition = columnCenterX - ((g.getFontMetrics().stringWidth(value)) / 2) + plotSettings.marginLeft;
            } else labelXPosition = plotX - (g.getFontMetrics().stringWidth((value = plotSettings.numberFormatter.format(px)))) / 2;
            g.setColor(plotSettings.fontColor);
            g.drawString(value, labelXPosition, plotY + plotSettings.notchLength + g.getFontMetrics().getHeight() - 1 + plotSettings.notchGap);
            columnIndex++;
        }

        double firstGridYLocation = ((int) (plotSettings.getMinY() / plotSettings.getGridSpacingY())) * plotSettings.getGridSpacingY();
        for (double py = firstGridYLocation; py <= plotSettings.getMaxY(); py += plotSettings.getGridSpacingY()) {
            if (py < plotSettings.getMinY()) continue;
            int plotX = plotSettings.marginLeft;
            int plotY = getPlotY(py);
            if (plotSettings.horizontalGridVisible) {
                g.setColor(plotSettings.gridColor);
                g.drawLine(plotSettings.marginLeft, plotY, plotSettings.marginLeft + chartWidth - 1, plotY);
            }
            g.setColor(plotSettings.axisColor);
            g.drawLine(plotX, plotY, plotX - plotSettings.notchLength, plotY);
            String value = plotSettings.numberFormatter.format(py);
            int textXOffset = (g.getFontMetrics().stringWidth(value));
            g.setColor(plotSettings.fontColor);
            g.drawString(value, plotX - plotSettings.notchLength - textXOffset - plotSettings.notchGap, plotY + (g.getFontMetrics().getHeight() / 2) - 1);
        }

        g.setColor(plotSettings.axisColor);
        g.drawRect(plotSettings.marginLeft, plotSettings.marginTop, chartWidth, chartHeight);

        int yEqualsZero = getPlotY(0);
        if (0 > plotSettings.getMinY() && 0 < plotSettings.getMaxY())
            g.drawLine(plotSettings.marginLeft, yEqualsZero, plotSettings.marginLeft + chartWidth - 1, yEqualsZero);
        int xEqualsZero = getPlotX(0);
        if (0 > plotSettings.getMinX() && 0 < plotSettings.getMaxX())
            g.drawLine(xEqualsZero, plotSettings.marginTop, xEqualsZero, plotSettings.marginTop + chartHeight);
        for (int i = 0; i < functions.size(); i++) {
            Plotter function = functions.elementAt(i);
            g.setColor(plotSettings.getPlotColor());
            function.plot(this, g, chartWidth, chartHeight);
        }
    }

    public int getPlotY(double y) {
        int pixelY = ((int) ((y - plotSettings.minY) / unitsPerPixelY));
        return ((chartHeight - pixelY) + plotSettings.marginTop);
    }

    public int getPlotX(double x) {
        return (int) (((x - plotSettings.minX) / unitsPerPixelX) + plotSettings.marginLeft);
    }

    public double getActualHeight(double height) {
        return height / unitsPerPixelY;
    }

    public double getActualWidth(double width) {
        return width / unitsPerPixelX;
    }

    public double getPlotHeight(double height) {
        return height * unitsPerPixelY;
    }

    public double getPlotWidth(double width) {
        return width * unitsPerPixelX;
    }

    public double getActualX(int pixelX) {
        return plotSettings.minX + (pixelX * unitsPerPixelX);
    }

    public void drawLine(Graphics g, double x1, double y1, double x2, double y2) {
        g.drawLine(getPlotX(x1), getPlotY(y1), getPlotX(x2), getPlotY(y2));
    }

    public void drawBar(Graphics g, double columnWidth, int columnIndex, double height, Color fill) {
        final double hgap = 0.1;
        int barHeight = (int) getActualHeight(height);
        int maxPlotY = getPlotY(height);
        int columnStartX = getPlotX(columnIndex * columnWidth);
        int gap = (int) +(getActualWidth(columnWidth) * hgap);
        g.setColor(fill);
        g.fillRect(columnStartX + gap, maxPlotY, (int) getActualWidth(columnWidth) - (gap * 2), barHeight);
        g.setColor(Color.BLACK);
        g.drawRect(columnStartX + gap, maxPlotY, (int) getActualWidth(columnWidth) - (gap * 2), barHeight);
    }

    public void drawCandleStick(Graphics g, double columnWidth, int columnIndex, double high, double mean, double low, Color lineColor, Color backgroundColor) {
        int halfColumnWidth = (int) getActualWidth(columnWidth / 2);
        final int bigNotchWidth = halfColumnWidth / 2;
        final int smallNotchWidth = halfColumnWidth / 3;
        int columnX = getPlotX(columnIndex * columnWidth) + halfColumnWidth;
        int maxPlotY = getPlotY(high);
        int meanPlotY = getPlotY(mean);
        int minPlotY = getPlotY(low);
        if (backgroundColor != null) {
            g.setColor(backgroundColor);
            g.fillRect(columnX - smallNotchWidth, maxPlotY, smallNotchWidth * 2, (int) getActualHeight(high - low) + 1);
        }

        g.setColor(lineColor);
        g.drawLine(columnX, minPlotY, columnX, maxPlotY);
        g.drawLine(columnX - bigNotchWidth, minPlotY, columnX + bigNotchWidth, minPlotY);
        g.drawLine(columnX - smallNotchWidth, meanPlotY, columnX + smallNotchWidth, meanPlotY);
        g.drawLine(columnX - bigNotchWidth, maxPlotY, columnX + bigNotchWidth, maxPlotY);
    }

    public BufferedImage getImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(plotSettings.backgroundColor);
        g.fillRect(0, 0, width, height);
        draw(g, width, height);
        return image;
    }

    @Override
    public String toString() {
        return itemToString(GraphToken.GRAPH);
    }
}