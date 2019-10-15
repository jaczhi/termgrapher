package me.quickTwix898.termgrapher;

public class DerivativeEngine {
    private static final double STEP = 0.001;
    private FunctionParser fp;
    private int yNum;
    private double x;
    private String result;

    //uses limit definition of derivative as x->STEP
    public DerivativeEngine(FunctionParser fp, int yNum, double x) {
        this.fp = fp;
        this.yNum = yNum - 1;
        this.x = x;

        double calc = fp.evalParsed(this.yNum, this.x+STEP) - fp.evalParsed(this.yNum, this.x-STEP);
        calc = calc/(STEP*2);
        result = String.valueOf(Rounder.round(calc, 2));
    }

    public String getResult() {
        return result;
    }
}
