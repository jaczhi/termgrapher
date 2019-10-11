package me.quickTwix898.termgrapher.button;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Window;
import me.quickTwix898.termgrapher.FunctionParser;
import me.quickTwix898.termgrapher.Plot;
import me.quickTwix898.termgrapher.window.ErrorWindow;
import me.quickTwix898.termgrapher.window.MainWindow;
import me.quickTwix898.termgrapher.window.TraceWindow;

import java.util.Arrays;

public class TraceButton implements Runnable {
    MainWindow window;
    MultiWindowTextGUI gui;

    public TraceButton(MainWindow window, MultiWindowTextGUI gui) {
        this.window = window;
        this.gui = gui;
    }

    @Override
    public void run() {
        try {
            Plot.GraphContext context = window.getContext();
            FunctionParser fp = new FunctionParser(context.getFunctions());

            TraceWindow tw = new TraceWindow(window, fp);
            gui.addWindowAndWait(tw);
        } catch (Exception e) {
            ErrorWindow ew = new ErrorWindow(e, window);
            ew.setHints(Arrays.asList(Window.Hint.CENTERED));
            gui.addWindowAndWait(ew);
        }
    }
}
