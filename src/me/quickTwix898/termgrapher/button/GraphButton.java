package me.quickTwix898.termgrapher.button;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import me.quickTwix898.termgrapher.window.ErrorWindow;
import me.quickTwix898.termgrapher.FunctionParser;
import me.quickTwix898.termgrapher.window.MainWindow;
import me.quickTwix898.termgrapher.Plot;

import java.util.Arrays;
import java.util.List;

public class GraphButton implements Runnable {
    MainWindow window;
    Panel graphPanel;
    MultiWindowTextGUI gui;

    public GraphButton(MainWindow mainWindow, MultiWindowTextGUI gui){
        this.window = mainWindow;
        this.graphPanel = mainWindow.getGraphPanel();
        this.gui = gui;
    }

    @Override
    public void run(){
        try {
            Plot.GraphContext context = window.getContext();

            FunctionParser fp = new FunctionParser(context.getFunctions());

            Plot plot = new Plot(context, fp);
            graphPanel.removeAllComponents();
            graphPanel.addComponent(new Label(
                    plot.getStringPlot()).setBackgroundColor(
                    new TextColor.RGB(255, 255, 255)).setForegroundColor(TextColor.ANSI.BLACK));
        } catch (Exception e) {
            ErrorWindow ew = new ErrorWindow(e);
            ew.setHints(Arrays.asList(Window.Hint.CENTERED));
            gui.addWindowAndWait(ew);
        }
    }
}
