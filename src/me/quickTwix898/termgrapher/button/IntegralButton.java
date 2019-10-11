package me.quickTwix898.termgrapher.button;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Window;
import me.quickTwix898.termgrapher.FunctionParser;
import me.quickTwix898.termgrapher.window.ErrorWindow;
import me.quickTwix898.termgrapher.window.IntegralWindow;
import me.quickTwix898.termgrapher.window.MainWindow;

import java.util.ArrayList;
import java.util.Arrays;

public class IntegralButton implements Runnable {
    MainWindow window;
    MultiWindowTextGUI gui;

    public IntegralButton(MainWindow window, MultiWindowTextGUI gui) {
        this.window = window;
        this.gui = gui;
    }

    @Override
    public void run() {
        try {
            FunctionParser fp = new FunctionParser(window.getContext().getFunctions());

            IntegralWindow iw = new IntegralWindow(fp);
            gui.addWindowAndWait(iw);
        } catch (Exception e) {
            ErrorWindow ew = new ErrorWindow(e, window);
            ew.setHints(Arrays.asList(Window.Hint.CENTERED));
            gui.addWindowAndWait(ew);
        }
    }
}
