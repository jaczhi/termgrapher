package me.quickTwix898.termgrapher.button;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Window;
import me.quickTwix898.termgrapher.Plot;
import me.quickTwix898.termgrapher.window.ErrorWindow;
import me.quickTwix898.termgrapher.window.MainWindow;
import me.quickTwix898.termgrapher.window.SaveWindow;

import java.util.ArrayList;
import java.util.Arrays;

public class SaveButton implements Runnable {
    MainWindow window;
    MultiWindowTextGUI gui;

    public SaveButton(MainWindow window, MultiWindowTextGUI gui) {
        this.window = window;
        this.gui = gui;
    }

    @Override
    public void run() {
        try {
            Plot.GraphContext context = window.getContext();

            SaveWindow sw = new SaveWindow(gui, context);
            sw.setHints(Arrays.asList(Window.Hint.CENTERED));
            gui.addWindowAndWait(sw);

        } catch(Exception e) {
            ErrorWindow ew = new ErrorWindow(e);
            ew.setHints(Arrays.asList(Window.Hint.CENTERED));
            gui.addWindowAndWait(ew);
        }
    }
}
