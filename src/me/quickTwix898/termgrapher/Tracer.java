package me.quickTwix898.termgrapher;

import me.quickTwix898.termgrapher.window.MainWindow;

//i'm already tracer
public class Tracer {
    private MainWindow window;
    private FunctionParser fp;
    private int position;
    private int yFunc;

    private int Y_END;
    private int Y_BEGIN;
    private int X_BEGIN;
    private int X_END;

    private double X_SCALE;
    private double Y_SCALE;

    final private char[][] original;
    private Plot.GraphContext context;

    public Tracer(MainWindow window, FunctionParser fp, char[][] original, int yFunc) {
        this.window = window;
        this.fp = fp;
        this.original = original;
        this.position = 0;
        this.context = window.getContext();

        this.Y_SCALE = context.getY_scale();
        this.X_SCALE = context.getX_scale();

        this.Y_BEGIN = (int) (context.getY_scale() * context.getY_begin());
        this.Y_END = (int) (context.getY_scale() * context.getY_end());
        this.X_BEGIN = (int) (context.getX_scale() * context.getX_begin());
        this.X_END = (int) (context.getX_scale() * context.getX_end());

        this.yFunc = yFunc - 1;
    }

    public void next() {
        if((position += 1) < X_END) {
            char[][] temp = copy();
            position += 1;
            double mathY = fp.evalParsed(yFunc, position/X_SCALE);
            int y = (int) (mathY * Y_SCALE);

            temp[Y_END - y][position - X_BEGIN] = 'x';

            String coordinates = "(" + Rounder.round(position/X_SCALE, 2) + "," +
                    Rounder.round(mathY, 2) + ")";
            for(int i=0; i<coordinates.length(); i++) {
                try {
                    temp[(Y_END - y) - 1][(position - X_BEGIN) + i] = coordinates.charAt(i);
                } catch(Exception e) {
                    continue;
                }
            }
            window.setGraphLabel(getString(temp));
        }
    }

    public void previous() {
        if((position -= 1) > X_BEGIN) {
            char[][] temp = copy();
            position -= 1;
            double mathY = fp.evalParsed(yFunc, position/X_SCALE);
            int y = (int) (mathY * Y_SCALE);

            temp[Y_END - y][position - X_BEGIN] = 'x';

            String coordinates = "(" + Rounder.round(position/X_SCALE, 2) + "," +
                    Rounder.round(mathY, 2) + ")";
            for(int i=0; i<coordinates.length(); i++) {
                try {
                    temp[(Y_END - y) - 1][(position - X_BEGIN) + i] = coordinates.charAt(i);
                } catch(Exception e) {
                    continue;
                }
            }
            window.setGraphLabel(getString(temp));
        }
    }

    public void reset() {
        window.setGraphLabel(getString(original));
    }

    private String getString(char[][] charArray) {
        String result = "";
        for(int i = 0; i<=(int)((context.getY_end()-context.getY_begin())*context.getY_scale()); i++) {
            for (int j = 0; j<=(int)((context.getX_end()-context.getX_begin())*context.getX_scale()); j++) {
                result += charArray[i][j];
            }
            //System.out.println();
            result += "\n";
        }
        return result;
    }

    private char[][] copy() {
        char[][] result = new char[original.length][original[0].length];
        for(int i=0; i<original.length; i++) {
            for(int j=0; j<original[0].length; j++) {
                result[i][j] = original[i][j];
            }
        }
        return result;
    }
}
