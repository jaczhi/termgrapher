package me.quickTwix898.termgrapher.window;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import me.quickTwix898.termgrapher.FunctionParser;
import me.quickTwix898.termgrapher.Plot;
import me.quickTwix898.termgrapher.Tracer;

import java.util.regex.Pattern;

public class TraceWindow extends BasicWindow {
    MainWindow window;
    FunctionParser fp;
    private Tracer tracer;
    public TraceWindow(MainWindow window, FunctionParser fp) {
        super("Trace");
        this.window = window;
        this.fp = fp;

        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        Panel topPanel = new Panel();
        topPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        panel.addComponent(topPanel);

        Panel bottomPanel = new Panel();
        bottomPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        panel.addComponent(bottomPanel);

        Plot temp = new Plot(window.getContext(), fp);

        topPanel.addComponent(new Label("y"));
        final TextBox yBox = new TextBox().setValidationPattern(Pattern.compile("[\\d]{1,}")).
                addTo(topPanel).setText("1").setPreferredSize(new TerminalSize(2,1));

        topPanel.addComponent(new Button("Initialise", new Runnable() {
            @Override
            public void run() {
                tracer = new Tracer(window, fp, temp.getPlot(), Integer.parseInt(yBox.getText()));
            }
        }));

        bottomPanel.addComponent(new Button("Left", new Runnable() {
            @Override
            public void run() {
                tracer.previous();
            }
        }));


        bottomPanel.addComponent(new Button("Right", new Runnable() {
            @Override
            public void run() {
                tracer.next();
            }
        }));

        topPanel.addComponent(new Button("Close", new Runnable() {
            @Override
            public void run() {
                try {
                    tracer.reset();
                } catch(Exception e) {}
                close();
            }
        }));

        setComponent(panel);
    }
}
