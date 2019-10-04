package me.quickTwix898.termgrapher.button;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Window;
import me.quickTwix898.termgrapher.FunctionParser;
import me.quickTwix898.termgrapher.Plot;
import me.quickTwix898.termgrapher.window.ErrorWindow;
import me.quickTwix898.termgrapher.window.MainWindow;
import me.quickTwix898.termgrapher.window.PointTableWindow;

import java.util.ArrayList;
import java.util.Arrays;

public class TableButton implements Runnable {
    MainWindow window;
    MultiWindowTextGUI gui;

    public TableButton(MainWindow window, MultiWindowTextGUI gui) {
        this.window = window;
        this.gui = gui;
    }

    @Override
    public void run() {
        try {
            Plot.GraphContext context = window.getContext();
            FunctionParser fp = new FunctionParser(context.getFunctions());
            Plot plot = new Plot(context, fp);

            PointTableWindow pt = new PointTableWindow(plot, fp);
            gui.addWindowAndWait(pt);
        } catch(Exception e) {
            ErrorWindow ew = new ErrorWindow(e);
            ew.setHints(Arrays.asList(Window.Hint.CENTERED));
            gui.addWindowAndWait(ew);
        }
    }
}
