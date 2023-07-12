package cos.graph;

abstract class DiscreteFunctionPlotter implements Plotter {
    protected double[] highs, means, lows;
    protected String[] labels;

    public DiscreteFunctionPlotter(String[] labels, double[] highs, double[] means, double[] lows) {
        this.labels = labels;
        this.highs = highs;
        this.lows = lows;
        this.means = means;
        if (highs.length != labels.length) System.err.println("Mismatch between highs length and labels length");
        if (means.length != labels.length) System.err.println("Mismatch between means length and labels length");
        if (lows.length != labels.length) System.err.println("Mismatch between lows length and labels length");
    }

    public int getColumnCount() {
        return labels.length;
    }

    public String getLabel(int i)  {
        try {return labels[i];}
        catch (ArrayIndexOutOfBoundsException e) {return "-";}
    }

    public double getHigh(int i) {
        return highs[i];
    }

    public double getLow(int i) {
        return lows[i];
    }

    public double getMean(int i) {
        return means[i];
    }
}
