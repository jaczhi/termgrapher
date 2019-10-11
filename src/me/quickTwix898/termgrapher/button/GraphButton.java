package me.quickTwix898.termgrapher.button;

import com.googlecode.lanterna.gui2.*;
import me.quickTwix898.termgrapher.window.ErrorWindow;
import me.quickTwix898.termgrapher.FunctionParser;
import me.quickTwix898.termgrapher.window.MainWindow;
import me.quickTwix898.termgrapher.Plot;

import java.util.Arrays;

public class GraphButton implements Runnable {
    MainWindow window;
    MultiWindowTextGUI gui;

    public GraphButton(MainWindow mainWindow, MultiWindowTextGUI gui){
        this.window = mainWindow;
        this.gui = gui;
    }

    @Override
    public void run(){
        try {
            Plot.GraphContext context = window.getContext();

            FunctionParser fp = new FunctionParser(context.getFunctions());

            Plot plot = new Plot(context, fp);
            window.setGraphLabel(plot.getStringPlot());
        } catch (Exception e) {
            ErrorWindow ew = new ErrorWindow(e, window);
            ew.setHints(Arrays.asList(Window.Hint.CENTERED));
            gui.addWindowAndWait(ew);
        }
    }
}
