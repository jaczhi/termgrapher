package me.quickTwix898.termgrapher;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class GraphIO {
    // only used to return multiple variables from readFile
    public Plot.GraphContext graphContext;
    private String filename;

    public GraphIO (String filename) { this.filename = filename; }

    public Plot.GraphContext readFile() throws IOException {
        File inputFile = new File(filename);
        BufferedReader brF = new BufferedReader(
                new FileReader(inputFile));

        graphContext = new Plot.GraphContext(brF.readLine(), brF.readLine(),
                brF.readLine(), brF.readLine(), brF.readLine(), brF.readLine(),
                Boolean.parseBoolean(brF.readLine()), getFunctionList(brF));
        return graphContext;
    }

    //TODO use json instead

    // IMPORTANT: static method
    public static void writeToFile(
            String filename, Plot.GraphContext context) throws IOException {
        File outputFile = new File(filename);
        PrintWriter outFS = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(outputFile, false)));

        outFS.println(context.getX_scale()); outFS.println(context.getY_scale()); outFS.println(context.getX_begin());
        outFS.println(context.getX_end()); outFS.println(context.getY_begin()); outFS.println(context.getY_end());
        outFS.println(context.isSmoothing());
        for (int i=0; i<context.getFunctions().size(); i++) { outFS.println(context.getFunctions().get(i)); }
        outFS.flush();
    }

    private List<String> getFunctionList(BufferedReader brF) throws IOException {
        ArrayList<String> functionList = new ArrayList<String>();
        String line;
        while ((line = brF.readLine()) != null) {
            functionList.add(line);
        }
        return functionList;
    }
}
