package me.quickTwix898.termgrapher.button;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Window;
import me.quickTwix898.termgrapher.Plot;
import me.quickTwix898.termgrapher.window.ErrorWindow;
import me.quickTwix898.termgrapher.window.MainWindow;
import me.quickTwix898.termgrapher.window.OpenWindow;

import java.util.Arrays;

public class OpenButton implements Runnable {
    MainWindow window;
    MultiWindowTextGUI gui;

    public OpenButton(MainWindow window, MultiWindowTextGUI gui) {
        this.window = window;
        this.gui = gui;
    }

    @Override
    public void run() {
        try {
            OpenWindow ow = new OpenWindow(gui);
            ow.setHints(Arrays.asList(Window.Hint.CENTERED));
            gui.addWindowAndWait(ow);
            try {
                window.setContext(ow.getContext());
            } catch(Exception e) {}
        } catch(Exception e) {
            ErrorWindow ew = new ErrorWindow(e, window);
            ew.setHints(Arrays.asList(Window.Hint.CENTERED));
            gui.addWindowAndWait(ew);
        }

    }
}
