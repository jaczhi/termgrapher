package me.quickTwix898.termgrapher.window;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import me.quickTwix898.termgrapher.FunctionParser;
import me.quickTwix898.termgrapher.IntersectionEngine;
import me.quickTwix898.termgrapher.button.CloseButton;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Pattern;

public class IntersectionAnalysisWindow extends BasicWindow {
    public IntersectionAnalysisWindow(FunctionParser fp) {
        super("Intersections/Zeroes");
        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        Panel configPanel = new Panel();
        configPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        Panel boundsPanel = new Panel();
        boundsPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        Panel buttonPanel = new Panel();
        buttonPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        mainPanel.addComponent(new Label("Find first intersection of"));
        mainPanel.addComponent(configPanel);
        mainPanel.addComponent(boundsPanel);

        CheckBox zeroBox = new CheckBox("Find zeroes instead of second y-function");
        mainPanel.addComponent(zeroBox);
        mainPanel.addComponent(buttonPanel);
        Label resultLabel = new Label("Waiting...");
        mainPanel.addComponent(resultLabel);

        configPanel.addComponent(new Label("y"));
        final TextBox y1Box = new TextBox().setValidationPattern(Pattern.compile("[\\d]{1,}")).
                addTo(configPanel).setText("1").setPreferredSize(new TerminalSize(2,1));
        configPanel.addComponent(new Label("and y"));
        final TextBox y2Box = new TextBox().setValidationPattern(Pattern.compile("[\\d]{1,}")).
                addTo(configPanel).setText("2").setPreferredSize(new TerminalSize(2,1));

        boundsPanel.addComponent(new Label("Between x > "));
        final TextBox xLeftBox = new TextBox().setValidationPattern(Pattern.compile("([^\\w]|[\\d])*")).
                addTo(boundsPanel).setText("0").setPreferredSize(new TerminalSize(4, 1));
        boundsPanel.addComponent(new Label("x < "));
        final TextBox xRightBox = new TextBox().setValidationPattern(Pattern.compile("([^\\w]|[\\d])*")).
                addTo(boundsPanel).setText("1").setPreferredSize(new TerminalSize(4, 1));

        buttonPanel.addComponent(new Button("Search", new Runnable() {
            @Override
            public void run() {
                IntersectionEngine ie = new IntersectionEngine(zeroBox.isChecked(),
                        Double.parseDouble(xLeftBox.getText()), Double.parseDouble(xRightBox.getText()),
                        Integer.parseInt(y1Box.getText()), Integer.parseInt(y2Box.getText()),
                        fp);
                resultLabel.setText(ie.getResult());
            }
        }));

        CloseButton closeButton = new CloseButton(this);
        buttonPanel.addComponent(new Button("Close", closeButton));

        setComponent(mainPanel);
    }

}
