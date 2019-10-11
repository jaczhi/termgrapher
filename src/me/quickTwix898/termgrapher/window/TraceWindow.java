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
        panel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        Plot temp = new Plot(window.getContext(), fp);

        panel.addComponent(new Label("y"));
        final TextBox yBox = new TextBox().setValidationPattern(Pattern.compile("[\\d]{1,}")).
                addTo(panel).setText("1").setPreferredSize(new TerminalSize(2,1));

        panel.addComponent(new Button("Initialize", new Runnable() {
            @Override
            public void run() {
                tracer = new Tracer(window, fp, temp.getPlot(), Integer.parseInt(yBox.getText()));
            }
        }));

        panel.addComponent(new Button("Left", new Runnable() {
            @Override
            public void run() {
                tracer.previous();
            }
        }));


        panel.addComponent(new Button("Right", new Runnable() {
            @Override
            public void run() {
                tracer.next();
            }
        }));

        panel.addComponent(new Button("Close", new Runnable() {
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
