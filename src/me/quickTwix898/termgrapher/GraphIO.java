package me.quickTwix898.termgrapher;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;

import com.google.gson.Gson;

public class GraphIO {
    // only used to return multiple variables from readFile
    public Plot.GraphContext graphContext;
    private String filename;

    public GraphIO (String filename) { this.filename = filename; }

    public Plot.GraphContext readFile() throws IOException {
        File inputFile = new File(filename);
        BufferedReader brF = new BufferedReader(
                new FileReader(inputFile));

        Gson g = new Gson();
        graphContext = g.fromJson(brF.readLine(), Plot.GraphContext.class);

        return graphContext;
    }

    // IMPORTANT: static method
    public static void writeToFile(
            String filename, Plot.GraphContext context) throws IOException {
        File outputFile = new File(filename);
        PrintWriter outFS = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(outputFile, false)));

        outFS.println(context.getJSON());
        outFS.flush();
    }
}
