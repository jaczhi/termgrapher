package me.quickTwix898.termgrapher.button;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Window;
import me.quickTwix898.termgrapher.FunctionParser;
import me.quickTwix898.termgrapher.window.ErrorWindow;
import me.quickTwix898.termgrapher.window.IntersectionAnalysisWindow;
import me.quickTwix898.termgrapher.window.MainWindow;

import java.util.ArrayList;
import java.util.Arrays;

public class IntersectionButton implements Runnable {
    MainWindow window;
    MultiWindowTextGUI gui;

    public IntersectionButton(MainWindow window, MultiWindowTextGUI gui) {
        this.window = window;
        this.gui = gui;
    }
    @Override
    public void run() {
        try {
            FunctionParser fp = new FunctionParser(window.getContext().getFunctions());

            IntersectionAnalysisWindow ia = new IntersectionAnalysisWindow(fp);
            gui.addWindowAndWait(ia);
        } catch(Exception e) {
            ErrorWindow ew = new ErrorWindow(e);
            ew.setHints(Arrays.asList(Window.Hint.CENTERED));
            gui.addWindowAndWait(ew);
        }
    }
}
