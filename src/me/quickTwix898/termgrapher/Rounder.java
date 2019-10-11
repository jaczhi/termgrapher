package me.quickTwix898.termgrapher;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Rounder {
    public static double round(double value, int places) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
