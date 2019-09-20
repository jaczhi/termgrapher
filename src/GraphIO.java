import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class GraphIO {
    // only used to return multiple variables from readFile
    public double x_scale = 0.0;
    public double y_scale = 0.0;
    public int x_begin = 0;
    public int x_end = 0;
    public int y_begin = 0;
    public int y_end = 0;
    public String[] functions = new String[0];
    private String filename;

    public GraphIO (String filename) { this.filename = filename; }
    public void readFile() throws IOException {
        File inputFile = new File(filename);
        BufferedReader brF = new BufferedReader(
                new FileReader(inputFile));
        x_scale = Double.parseDouble(brF.readLine());
        y_scale = Double.parseDouble(brF.readLine());
        x_begin = Integer.parseInt(brF.readLine());
        x_end = Integer.parseInt(brF.readLine());
        y_begin = Integer.parseInt(brF.readLine());
        y_end = Integer.parseInt(brF.readLine());

        ArrayList<String> functionList = new ArrayList<String>();
        String line;
        while ((line = brF.readLine()) != null) {
            functionList.add(line);
        }
        functions = new String[functionList.size()];
        for (int i = 0; i < functionList.size(); i++) {
            functions[i] = functionList.get(i);
        }
    }


    // IMPORTANT: static method
    public static void writeToFile(
            String filename, double x_scale, double y_scale, int x_begin, int x_end, int y_begin, int y_end, String[] functions) throws IOException {
        File outputFile = new File(filename);
        PrintWriter outFS = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(outputFile, false)));

        outFS.println(x_scale); outFS.println(y_scale); outFS.println(x_begin); outFS.println(x_end);
        outFS.println(y_begin); outFS.println(y_end); for (int i=0; i<functions.length; i++) { outFS.println(functions[i]); }

        outFS.flush();
    }
}
