package me.quickTwix898.termgrapher.button;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Window;
import me.quickTwix898.termgrapher.FunctionParser;
import me.quickTwix898.termgrapher.window.DerivativeWindow;
import me.quickTwix898.termgrapher.window.ErrorWindow;
import me.quickTwix898.termgrapher.window.MainWindow;

import java.util.Arrays;

public class DerivativeButton implements Runnable {
    MainWindow window;
    MultiWindowTextGUI gui;

    public DerivativeButton(MainWindow window, MultiWindowTextGUI gui) {
        this.window = window;
        this.gui = gui;
    }

    public void run() {
        try {
            FunctionParser fp = new FunctionParser(window.getContext().getFunctions());

            DerivativeWindow dw = new DerivativeWindow(fp);
            gui.addWindowAndWait(dw);
        } catch (Exception e) {
            ErrorWindow ew = new ErrorWindow(e, window);
            ew.setHints(Arrays.asList(Window.Hint.CENTERED));
            gui.addWindowAndWait(ew);
        }
    }
}
