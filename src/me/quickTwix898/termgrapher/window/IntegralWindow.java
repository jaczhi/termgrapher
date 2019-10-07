package me.quickTwix898.termgrapher.window;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import me.quickTwix898.termgrapher.FunctionParser;
import me.quickTwix898.termgrapher.IntegralEngine;
import me.quickTwix898.termgrapher.button.CloseButton;

import java.util.regex.Pattern;

public class IntegralWindow extends BasicWindow {
    public IntegralWindow(FunctionParser fp) {
        super("Integral");
        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        Panel panel1 = new Panel();
        panel1.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        mainPanel.addComponent(panel1);

        panel1.addComponent(new Label("Find the integral of y"));
        final TextBox yBox = new TextBox().setValidationPattern(Pattern.compile("[\\d]{1,}")).
                addTo(panel1).setText("1").setPreferredSize(new TerminalSize(2,1));

        Panel panel2 = new Panel();
        panel2.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        mainPanel.addComponent(panel2);

        panel2.addComponent(new Label("Between x > "));
        final TextBox xLeftBox = new TextBox().setValidationPattern(Pattern.compile("([^\\w]|[\\d])*")).
                addTo(panel2).setText("0").setPreferredSize(new TerminalSize(4, 1));
        panel2.addComponent(new Label("x < "));
        final TextBox xRightBox = new TextBox().setValidationPattern(Pattern.compile("([^\\w]|[\\d])*")).
                addTo(panel2).setText("1").setPreferredSize(new TerminalSize(4, 1));

        Panel buttonPanel = new Panel();
        buttonPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        mainPanel.addComponent(buttonPanel);

        Label resultLabel = new Label("");
        mainPanel.addComponent(resultLabel);

        buttonPanel.addComponent(new Button("Calculate", new Runnable() {
            @Override
            public void run() {
                IntegralEngine integralEngine = new IntegralEngine(fp, Integer.parseInt(yBox.getText()),
                        Double.parseDouble(xLeftBox.getText()), Double.parseDouble(xRightBox.getText()));
                resultLabel.setText(integralEngine.getResult());
            }
        }));

        CloseButton closeButton = new CloseButton(this);
        buttonPanel.addComponent(new Button("Close", closeButton));

        setComponent(mainPanel);
    }
}
