package cos.graph;

import java.awt.*;

interface Plotter {
    String getName();
    void plot(GraphItem p, Graphics g, int chartWidth, int chartHeight);
}
