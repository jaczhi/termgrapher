package me.quickTwix898.termgrapher;

public class IntersectionEngine {
    private final double INC_L = 0.1;
    private final double TOL_L = 1.0;
    private final double INC_S = 0.001;
    private final double TOL_S = 0.001;
    private final double TOL_S_FALL = 0.005;
    private final double TOL_S_LAST = 0.01;
    //uses series of increasingly precise loops to find x value close to 0. tolerance constants above
    
    private int y1;
    private int y2;
    String result;

    public IntersectionEngine(boolean zeroOnly, double boundLeft, double boundRight, int y1, int y2, FunctionParser fp) {
        this.y1 = y1 - 1;
        this.y2 = y2 - 1;

        if(zeroOnly) {
            outerZero:
            for(double i=boundLeft; i<=boundRight; i=i+INC_L) {
                if(Math.abs(fp.evalParsed(this.y1, i)) < TOL_L) {
                    for(double j=i; j<=boundRight; j=j+INC_S) {
                        if(Math.abs(fp.evalParsed(this.y1, j)) < TOL_S) {
                            result = "x = " + Rounder.round(j, 2); break outerZero;
                        }
                    }
                    for(double j=i; j<=boundRight; j=j+INC_S) {
                        if(Math.abs(fp.evalParsed(this.y1, j)) < TOL_S_FALL) {
                            result = "x = " + Rounder.round(j, 2); break outerZero;
                        }
                    }
                    for(double j=i; j<=boundRight; j=j+INC_S) {
                        if(Math.abs(fp.evalParsed(this.y1, j)) < TOL_S_LAST) {
                            result = "x = " + Rounder.round(j, 2); break outerZero;
                        }
                    }
                    result = "Not Found. ";
                }
            }

        }
        else {
            outerInter:
            for(double i=boundLeft; i<=boundRight; i=i+INC_L) {
                if(Math.abs(fp.evalParsed(this.y1, i) - fp.evalParsed(this.y2, i)) < TOL_L) {
                    for(double j=i; j<=boundRight; j=j+INC_S) {
                        if(Math.abs(fp.evalParsed(this.y1, j) - fp.evalParsed(this.y2, j)) < TOL_S) {
                            result = "x = " + Rounder.round(j, 2); break outerInter;
                        }
                    }
                    for(double j=i; j<=boundRight; j=j+INC_S) {
                        if(Math.abs(fp.evalParsed(this.y1, j) - fp.evalParsed(this.y2, j)) < TOL_S_FALL) {
                            result = "x = " + Rounder.round(j, 2); break outerInter;
                        }
                    }
                    for(double j=i; j<=boundRight; j=j+INC_S) {
                        if(Math.abs(fp.evalParsed(this.y1, j) - fp.evalParsed(this.y2, j)) < TOL_S_LAST) {
                            result = "x = " + Rounder.round(j, 2); break outerInter;
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
}
