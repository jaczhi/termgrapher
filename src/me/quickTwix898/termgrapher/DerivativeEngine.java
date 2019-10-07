package me.quickTwix898.termgrapher;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DerivativeEngine {
    private final double STEP = 0.001;
    private FunctionParser fp;
    private int yNum;
    private double x;
    private String result;

    public DerivativeEngine(FunctionParser fp, int yNum, double x) {
        this.fp = fp;
        this.yNum = yNum - 1;
        this.x = x;

        double calc = fp.evalParsed(this.yNum, this.x+STEP) - fp.evalParsed(this.yNum, this.x-STEP);
        calc = calc/(STEP*2);
        result = String.valueOf(round(calc, 2));
    }

    public String getResult() {
        return result;
    }

    //TODO make global round function
    private double round(double value, int places) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
