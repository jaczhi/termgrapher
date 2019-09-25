import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Plot {
    private final double X_SCALE; // how zoomed in the graph will be. scale of 1 is 1 character per unit
    private final double Y_SCALE;
    private final int X_BEGIN;
    private final int X_END;
    private final int Y_BEGIN;
    private final int Y_END;
    private final boolean SMOOTHING;
    private String[] functions; // list of functions to be graphed
    private ArrayList<Map<Integer, Integer>> allPoints; // contains x,y pairs of all functions
    char[][] plot;

    public Plot(double x_scale, double y_scale, int x_begin, int x_end, int y_begin, int y_end, boolean smoothing, String[]functions, FunctionParser fp) {
        this.X_SCALE = x_scale;
        this.Y_SCALE = y_scale;
        this.X_BEGIN = (int) (x_begin * this.X_SCALE); // scales x and y to actual amount of characters, not units
        this.X_END = (int) (x_end * this.X_SCALE);
        this.Y_BEGIN = (int) (y_begin * this.Y_SCALE);
        this.Y_END = (int) (y_end * this.Y_SCALE);

        this.functions = functions;
        this.allPoints = new ArrayList();

        for(int i = 0; i < this.functions.length; i++) {

            Map<Integer, Integer> points = new HashMap<Integer, Integer>();
            for(int x=X_BEGIN; x<=X_END; x++) {
                double mathX = (x / X_SCALE); // actual x value
                double mathY = fp.evalParsed(i, mathX);
                int y = (int) (mathY * Y_SCALE);
                // removing the test below will cause in incorrect plot
                try {
                    round(mathY, 2);
                } catch (Exception e) {
                    continue;
                }
                points.put(x, y);
            }
            this.allPoints.add(points);
        }

        // converts points into character plot
        this.SMOOTHING = smoothing;
        this.plot = new char[Y_END-Y_BEGIN+1][X_END-X_BEGIN+1];
        for (int i=(Y_END); i>=Y_BEGIN; i--) {
            for (int j=(X_END); j>=X_BEGIN; j--) {
                boolean pointExists = false;
                for(int k=0; k<this.allPoints.size(); k++){
                    try{
                        pointExists = this.allPoints.get(k).get(j).equals(i);
                        Integer nextPoint;
                        try {
                            nextPoint = this.allPoints.get(k).get(j+1);
                        } catch(Exception e) {
                            nextPoint = i;
                        }
                        try{
                            if(SMOOTHING && pointExists && ((nextPoint-i) > 1)) {
                                this.plot[Y_END - i][j - X_BEGIN] = '@';
                                for (int l = 1; l < (nextPoint - this.allPoints.get(k).get(j)); l++) {
                                    if (l > ((nextPoint - this.allPoints.get(k).get(j)) / 2)) {
                                        this.plot[Y_END - i - l][j - X_BEGIN + 1] = '*';
                                    } else {
                                        this.plot[Y_END - i - l][j - X_BEGIN] = '*';
                                    }
                                }
                                break;
                            }
                            else if(SMOOTHING && pointExists && ((nextPoint-i) < 1)) {
                                this.plot[Y_END - i][j-X_BEGIN] = '@';
                                for(int l=1; l<(this.allPoints.get(k).get(j) - nextPoint); l++) {
                                    if(l > ((this.allPoints.get(k).get(j) - nextPoint) / 2)) {
                                        this.plot[Y_END - i + l][j- X_BEGIN + 1] = '*';
                                    }
                                    else {
                                        this.plot[Y_END - i +l][j - X_BEGIN] = '*';
                                    }
                                }
                                break;
                            }
                            else if(pointExists) {this.plot[Y_END-i][j-X_BEGIN] = '@'; break;}
                        } catch(Exception e) {
                                if(pointExists) {this.plot[Y_END-i][j-X_BEGIN] = '@'; break;}
                        }
                    } catch (Exception e) {
                        pointExists = false;
                    }
                }
                if(j==0 && i==0 && !pointExists && this.plot[Y_END-i][j-X_BEGIN] != '*') { //center (0,0)
                    this.plot[Y_END-i][j-X_BEGIN] = '+';
                }
                else if(j==0 && !pointExists && this.plot[Y_END-i][j-X_BEGIN] != '*') {
                    this.plot[Y_END-i][j-X_BEGIN] = '|'; // y-axis
                }
                else if(i==0 && !pointExists && this.plot[Y_END-i][j-X_BEGIN] != '*') {
                    this.plot[Y_END-i][j-X_BEGIN] = '-'; //x-axis
                }
                else if(!pointExists && this.plot[Y_END-i][j-X_BEGIN] != '*') {
                    this.plot[Y_END-i][j-X_BEGIN] = ' '; // empty space
                }
            }
        }
    }

    public ArrayList<Map<Integer, Integer>> getAllPoints() {
        return allPoints;
    }


    public char[][] getPlot() {
        return plot;
    }

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
        String[] textpoints = new String[functions.length + 1];
        textpoints[0] = "x";
        for(double mathX=begin; mathX<=end; mathX+=interval) {
            textpoints[0] += "\n" + mathX;
        }
        for (int i=1; i<=functions.length; i++) {
            textpoints[i] = "y" + i;
            for(double mathX=begin; mathX<=end; mathX+=interval) {
                double mathY = fp.evalParsed(i-1, mathX);
                try {
                    mathY = round(mathY, 2);
                    textpoints[i] += ("\n" + mathY);
                } catch (Exception e) {
                    textpoints[i] += ("\nNA");
                }
            }
        }
        return textpoints;
    }

    private static double round(double value, int places) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
