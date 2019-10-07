package me.quickTwix898.termgrapher;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class IntegralEngine {
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

        for(double i=(this.xBegin); i<=(this.xEnd); i+=STEP) {
            sum += (fp.evalParsed(this.yNum, i));
        }

        sum *= (STEP);

        result = String.valueOf(round(sum, 2));
    }

    public String getResult() {
        return result;
    }

    private double round(double value, int places) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
