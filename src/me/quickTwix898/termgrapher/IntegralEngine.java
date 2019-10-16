package me.quickTwix898.termgrapher;

public class IntegralEngine {
    // riemann sums with interval of STEP
    private final double STEP = 0.001;
    private FunctionParser fp;
    private int yNum;
    private double xBegin;
    private double xEnd;
    private String result;

    public IntegralEngine(FunctionParser fp, int yNum, double xBegin, double xEnd) {
        this.fp = fp;
        this.yNum = yNum - 1;
        this.xBegin = xBegin;
        this.xEnd = xEnd;

        double sum = 0;

        for (double i = (this.xBegin); i <= (this.xEnd); i += STEP) {
            sum += (fp.evalParsed(this.yNum, i));
        }

        sum *= (STEP);

        result = String.valueOf(Rounder.round(sum, 2));
    }

    public String getResult() {
        return result;
    }
}