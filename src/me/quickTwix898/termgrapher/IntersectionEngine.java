package me.quickTwix898.termgrapher;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class IntersectionEngine {
    private boolean zeroOnly;
    private double boundLeft;
    private double boundRight;
    FunctionParser fp;
    private int y1;
    private int y2;
    String result;

    public IntersectionEngine(boolean zeroOnly, double boundLeft, double boundRight, int y1, int y2, FunctionParser fp) {
        this.zeroOnly = zeroOnly;
        this.boundLeft = boundLeft;
        this.boundRight = boundRight;
        this.fp = fp;
        this.y1 = y1;
        this.y2 = y2;

        if(zeroOnly) {
            outerZero:
            for(double i=boundLeft; i<=boundRight; i=i+0.1) {
                if(Math.abs(fp.evalParsed(y1 - 1, i)) < 1) {
                    for(double j=i; j<=boundRight; j=j+0.001) {
                        if(Math.abs(fp.evalParsed(y1 - 1, j)) < 0.001) {
                            result = "x = " + round(j, 2); break outerZero;
                        }
                    }
                    for(double j=i; j<=boundRight; j=j+0.001) {
                        if(Math.abs(fp.evalParsed(y1 - 1, j)) < 0.01) {
                            result = "x = " + round(j, 2); break outerZero;
                        }
                    }
                    result = "Not Found. ";
                }
            }

        }
        else {
            outerInter:
            for(double i=boundLeft; i<=boundRight; i=i+0.1) {
                if(Math.abs(fp.evalParsed(y1 - 1, i) - fp.evalParsed(y2 - 1, i)) < 1) {
                    for(double j=i; j<=boundRight; j=j+0.001) {
                        if(Math.abs(fp.evalParsed(y1 - 1, j) - fp.evalParsed(y2 - 1, j)) < 0.001) {
                            result = "x = " + round(j, 2); break outerInter;
                        }
                    }
                    for(double j=i; j<=boundRight; j=j+0.001) {
                        if(Math.abs(fp.evalParsed(y1 - 1, j) - fp.evalParsed(y2 - 1, j)) < 0.005) {
                            result = "x = " + round(j, 2); break outerInter;
                        }
                    }
                    for(double j=i; j<=boundRight; j=j+0.001) {
                        if(Math.abs(fp.evalParsed(y1 - 1, j) - fp.evalParsed(y2 - 1, j)) < 0.01) {
                            result = "x = " + round(j, 2); break outerInter;
                        }
                    }
                    result = "Not Found. ";
                }
            }
        }
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
