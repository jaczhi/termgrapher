import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;

public class GraphIO {
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
