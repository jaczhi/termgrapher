package me.quickTwix898.termgrapher;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Plot {
    private final double X_SCALE; // how zoomed in the graph will be. scale of 1 is 1 character per unit
    private final double Y_SCALE;
    private final int X_BEGIN;
    private final int X_END;
    private final int Y_BEGIN;
    private final int Y_END;
    private final boolean SMOOTHING;
    private List<String> functions; // list of functions to be graphed
    private List<Map<Integer, Integer>> allPoints; // contains x,y pairs of all functions
    char[][] plot;

    public Plot(GraphContext graphContext, FunctionParser fp) {
        this.X_SCALE = graphContext.getX_scale();
        this.Y_SCALE = graphContext.getY_scale();
        this.X_BEGIN = (int) (graphContext.getX_begin() * this.X_SCALE); // scales x and y to actual amount of characters, not units
        this.X_END = (int) (graphContext.getX_end() * this.X_SCALE);
        this.Y_BEGIN = (int) (graphContext.getY_begin() * this.Y_SCALE);
        this.Y_END = (int) (graphContext.getY_end() * this.Y_SCALE);

        this.functions = graphContext.functions;
        this.allPoints = makePoints(fp);

        // converts points into character plot
        this.SMOOTHING = graphContext.isSmoothing();
        this.plot = drawPlot();
    }

    //will create hashmaps with scaled x and y pairs
    private List<Map<Integer, Integer>> makePoints(FunctionParser fp) {
        List<Map<Integer, Integer>> result = new ArrayList<>();
        for(int i = 0; i < this.functions.size(); i++) {
            Map<Integer, Integer> points = new HashMap<>();
            for (int x = X_BEGIN; x <= X_END; x++) {
                double mathX = (x / X_SCALE); // actual x value
                double mathY = fp.evalParsed(i, mathX);
                int y = (int) (mathY * Y_SCALE);
                // removing the test below will cause in incorrect plot
                try {
                    Rounder.round(mathY, 2);
                } catch (Exception e) {
                    continue;
                }
                points.put(x, y);
            }
            result.add(points);
        }
        return result;
    }

    // draws the plot with various features such as axes
    private char[][] drawPlot() {
        char[][] result = new char[Y_END-Y_BEGIN+1][X_END-X_BEGIN+1];
        for (int i=(Y_END); i>=Y_BEGIN; i--) { // iterate through scaled y
            for (int j=(X_END); j>=X_BEGIN; j--) { // iterate through scaled x
                boolean pointExists = false;
                for(int k=0; k<this.allPoints.size(); k++){ //iterate through list of functions
                    try{
                        pointExists = this.allPoints.get(k).get(j).equals(i);
                        Integer nextPoint;
                        try {
                            nextPoint = this.allPoints.get(k).get(j+1);
                        } catch(Exception e) {
                            nextPoint = i; // will not do line smoothing
                        }

                        if(SMOOTHING && pointExists && ((nextPoint-i) > 1)) { // positive-slope smoothing
                            result[Y_END - i][j - X_BEGIN] = '@';
                            for (int l = 1; l < (nextPoint - this.allPoints.get(k).get(j)); l++) {
                                if (l > ((nextPoint - this.allPoints.get(k).get(j)) / 2)) {
                                    result[Y_END - i - l][j - X_BEGIN + 1] = '*';
                                } else {
                                    result[Y_END - i - l][j - X_BEGIN] = '*';
                                }
                            }
                            break;
                        }
                        else if(SMOOTHING && pointExists && ((nextPoint-i) < 1)) { // negative-slope smoothing
                            result[Y_END - i][j-X_BEGIN] = '@';
                            for(int l=1; l<(this.allPoints.get(k).get(j) - nextPoint); l++) {
                                if(l > ((this.allPoints.get(k).get(j) - nextPoint) / 2)) {
                                    result[Y_END - i + l][j- X_BEGIN + 1] = '*';
                                }
                                else {
                                    result[Y_END - i +l][j - X_BEGIN] = '*';
                                }
                            }
                            break;
                        }
                        else if(pointExists) {result[Y_END-i][j-X_BEGIN] = '@'; break;} // no smoothing
                    } catch(Exception e) {
                        if(pointExists) {result[Y_END-i][j-X_BEGIN] = '@'; break;}
                    }
                }
                if(!pointExists && result[Y_END-i][j-X_BEGIN] != '*') {
                    if (j == 0 && i == 0) { // center (0,0)
                        result[Y_END - i][j - X_BEGIN] = '+';
                    } else if(j==0 && i == Y_BEGIN) { // bottom axis
                        result[Y_END - i][j - X_BEGIN] = 'v';
                    } else if(j==0 && i == Y_END) { // top axis
                        result[Y_END - i][j - X_BEGIN] = '^';
                    } else if(i == 0 && j == X_BEGIN) { // left axis
                        result[Y_END - i][j - X_BEGIN] = '<';
                    } else if(i == 0 && j == X_END) { // right axis
                        result[Y_END - i][j - X_BEGIN] = '>';
                    } else if(j == 1 && i == Y_END) { // label for top
                        String label = String.valueOf(Rounder.round(Y_END / Y_SCALE, 2));
                        for (int h = 0; h < label.length(); h++) {
                            result[Y_END - i][j - X_BEGIN + h] = label.charAt(h);
                        }
                    } else if(i == 1 && j == X_END) { //label for right
                        String label = String.valueOf(Rounder.round(X_END/X_SCALE, 2));
                        for (int h = 0; h < label.length(); h++) {
                            result[Y_END - i][(j - X_BEGIN) - h] = label.charAt(label.length() - h - 1);
                        }
                    } else if (j == 0) { // vertical axis
                        result[Y_END - i][j - X_BEGIN] = '|';
                    } else if (i == 0) { // horizontal axis
                        result[Y_END - i][j - X_BEGIN] = '-';
                    } else if (!Character.isDigit(result[Y_END-i][j-X_BEGIN]) && result[Y_END-i][j-X_BEGIN] != '.'){
                        result[Y_END - i][j - X_BEGIN] = ' '; // empty space
                    }
                }
            }
        }
        return result;
    }

    public List<Map<Integer, Integer>> getAllPoints() { return allPoints; }


    public char[][] getPlot() { return plot; }

    // converts two-dimensional character array into string with newlines
    public String getStringPlot() {
        String result = "";
        for(int i = 0; i<=(Y_END-Y_BEGIN); i++) {
            for (int j = 0; j<=(X_END-X_BEGIN); j++) {
                //System.out.print(plot[i][j]);
                result += plot[i][j];
            }
            //System.out.println();
            result += "\n";
        }
        return result;
    }

    public String[] getTextPoints(double begin, double end, double interval, FunctionParser fp) {
        String[] textpoints = new String[functions.size() + 1];
        textpoints[0] = "x";
        for(double mathX=begin; mathX<=end; mathX+=interval) {
            textpoints[0] += "\n" + mathX;
        }
        for (int i=1; i<=functions.size(); i++) {
            textpoints[i] = "y" + i;
            for(double mathX=begin; mathX<=end; mathX+=interval) {
                double mathY = fp.evalParsed(i-1, mathX);
                try {
                    mathY = Rounder.round(mathY, 2);
                    textpoints[i] += ("\n" + mathY);
                } catch (Exception e) {
                    textpoints[i] += ("\nNA");
                }
            }
        }
        return textpoints;
    }


    public static class GraphContext {
        private double x_scale;
        private double y_scale;
        private int x_begin;
        private int x_end;
        private int y_begin;
        private int y_end;
        private boolean smoothing;
        private List<String> functions;

        public double getX_scale() { return x_scale; }

        public double getY_scale() { return y_scale; }

        public int getX_begin() { return x_begin; }

        public int getX_end() { return x_end; }

        public int getY_begin() { return y_begin; }

        public int getY_end() { return y_end; }

        public boolean isSmoothing() { return smoothing; }

        public List<String> getFunctions() { return functions; }

        public GraphContext(double x_scale, double y_scale, int x_begin, int x_end, int y_begin, int y_end, boolean smoothing, List<String> functions) {
            this.x_scale = x_scale;
            this.y_scale = y_scale;
            this.x_begin = x_begin;
            this.x_end = x_end;
            this.y_begin = y_begin;
            this.y_end = y_end;
            this.smoothing = smoothing;
            this.functions = functions;
        }


        public GraphContext(String x_scale, String y_scale, String x_begin, String x_end, String y_begin, String y_end, boolean smoothing, List<String> functions) {
            this.x_scale = Double.parseDouble(x_scale);
            this.y_scale = Double.parseDouble(y_scale);
            this.x_begin = Integer.parseInt(x_begin);
            this.x_end = Integer.parseInt(x_end);
            this.y_begin = Integer.parseInt(y_begin);
            this.y_end = Integer.parseInt(y_end);
            this.smoothing = smoothing;
            this.functions = functions;
        }


        public String getJSON() {
            Gson g = new Gson();
            return g.toJson(this);
        }

    }
}
