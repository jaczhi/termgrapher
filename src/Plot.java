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
    private String[] functions; // list of functions to be graphed
    private ArrayList<Map<Integer, Integer>> allPoints; // contains x,y pairs of all functions
    private String textPoints; // list of points in text form
    char[][] plot;

    public Plot(double x_scale, double y_scale, int x_begin, int x_end, int y_begin, int y_end, String[]functions, FunctionParser fp) {
        this.X_SCALE = x_scale;
        this.Y_SCALE = y_scale;
        this.X_BEGIN = (int) (x_begin * this.X_SCALE); // scales x and y to actual amount of characters, not units
        this.X_END = (int) (x_end * this.X_SCALE);
        this.Y_BEGIN = (int) (y_begin * this.Y_SCALE);
        this.Y_END = (int) (y_end * this.Y_SCALE);

        this.functions = functions;
        textPoints = "";
        this.allPoints = new ArrayList();

        for(int i = 0; i < this.functions.length; i++) {
            textPoints += "y" + (i+1) + " = " + this.functions[i] + ": \n"; // labels each function in textpoints

            Map<Integer, Integer> points = new HashMap<Integer, Integer>();
            for(int x=X_BEGIN; x<=X_END; x++) {
                double mathX = (x / X_SCALE); // actual x value
                double mathY = fp.evalParsed(i, mathX);
                int y = (int) (mathY * Y_SCALE);

                try {
                    textPoints += "x=" + round(mathX, 2) + "\ty=" + round(mathY, 2) + "\n";
                } catch (Exception e) {
                    continue;
                }
                points.put(x, y);
            }
            this.allPoints.add(points);
        }

        // converts points into character plot
        this.plot = new char[Y_END-Y_BEGIN+1][X_END-X_BEGIN+1];
        for (int i=(Y_END); i>=Y_BEGIN; i--) {
            for (int j=(X_END); j>=X_BEGIN; j--) {
                boolean pointExists = false;
                for(int k=0; k<this.allPoints.size(); k++){
                    try{
                        pointExists = this.allPoints.get(k).get(j).equals(i);
                        if(pointExists) {break;}
                    } catch (Exception e) {
                        pointExists = false;
                    }
                }
                if(pointExists) {
                    this.plot[Y_END-i][j-X_BEGIN] = '@';
                }
                else if(j==0 && i==0) { //center (0,0)
                    this.plot[Y_END-i][j-X_BEGIN] = '+';
                }
                else if(j==0) {
                    this.plot[Y_END-i][j-X_BEGIN] = '|'; // y-axis
                }
                else if(i==0) {
                    this.plot[Y_END-i][j-X_BEGIN] = '-'; //x-axis
                }
                else {
                    this.plot[Y_END-i][j-X_BEGIN] = ' '; // empty space
                }
            }
        }
    }

    public ArrayList<Map<Integer, Integer>> getAllPoints() {
        return allPoints;
    }

    public String getTextPoints() {
        return textPoints;
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

    private static double round(double value, int places) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
