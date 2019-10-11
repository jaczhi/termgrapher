package me.quickTwix898.termgrapher.window;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import me.quickTwix898.termgrapher.DerivativeEngine;
import me.quickTwix898.termgrapher.FunctionParser;
import me.quickTwix898.termgrapher.button.CloseButton;

import java.util.regex.Pattern;

public class DerivativeWindow extends BasicWindow {
    public DerivativeWindow(FunctionParser fp) {
        super("Derivative");
        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        Panel panel1 = new Panel();
        panel1.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        mainPanel.addComponent(panel1);

        Panel buttonPanel = new Panel();
        buttonPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        mainPanel.addComponent(buttonPanel);

        Label resultLabel = new Label("");
        mainPanel.addComponent(resultLabel);

        panel1.addComponent(new Label("y"));
        final TextBox yBox = new TextBox().setValidationPattern(Pattern.compile("[\\d]{1,}")).
                addTo(panel1).setText("1").setPreferredSize(new TerminalSize(2,1));

        panel1.addComponent(new Label(" at"));
        final TextBox xBox = new TextBox().setValidationPattern(Pattern.compile("([^\\w]|[\\d])*")).
                addTo(panel1).setText("0").setPreferredSize(new TerminalSize(4, 1));

        buttonPanel.addComponent(new Button("Calculate", new Runnable() {
            @Override
            public void run() {
                DerivativeEngine derivativeEngine = new DerivativeEngine(fp, Integer.parseInt(yBox.getText()),
                        Double.parseDouble(xBox.getText()));

                resultLabel.setText(derivativeEngine.getResult());
            }
        }));

        CloseButton closeButton = new CloseButton(this);
        buttonPanel.addComponent(new Button("Close", closeButton));

        setComponent(mainPanel);
    }
}
