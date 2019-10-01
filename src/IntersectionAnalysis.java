import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Pattern;

public class IntersectionAnalysis extends BasicWindow {
    public IntersectionAnalysis(FunctionParser fp) {
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
                if(zeroBox.isChecked()) {
                    outerZero:
                    for(double i=Double.parseDouble(xLeftBox.getText());
                        i<=Double.parseDouble(xRightBox.getText()); i=i+0.1) {
                        if(Math.abs(fp.evalParsed(Integer.parseInt(y1Box.getText()) - 1, i)) < 1) {
                            for(double j=i; j<=Double.parseDouble(xRightBox.getText()); j=j+0.001) {
                                if(Math.abs(fp.evalParsed(
                                        Integer.parseInt(y1Box.getText()) - 1, j)) < 0.001) {
                                    resultLabel.setText("x = " + round(j, 2)); break outerZero;
                                }
                            }
                            for(double j=i; j<=Double.parseDouble(xRightBox.getText()); j=j+0.001) {
                                if(Math.abs(fp.evalParsed(
                                        Integer.parseInt(y1Box.getText()) - 1, j)) < 0.01) {
                                    resultLabel.setText("x = " + round(j, 2)); break outerZero;
                                }
                            }
                            resultLabel.setText("Not Found. ");
                        }
                    }

                }
                else {
                    outerInter:
                    for(double i=Double.parseDouble(xLeftBox.getText());
                        i<=Double.parseDouble(xRightBox.getText()); i=i+0.1) {
                        if(Math.abs(fp.evalParsed(Integer.parseInt(y1Box.getText()) - 1, i)-
                                fp.evalParsed(Integer.parseInt(y2Box.getText()) - 1, i)) < 1) {
                            for(double j=i; j<=Double.parseDouble(xRightBox.getText()); j=j+0.001) {
                                if(Math.abs(fp.evalParsed(Integer.parseInt(y1Box.getText()) - 1, j)
                                        - fp.evalParsed(Integer.parseInt(y2Box.getText()) - 1, j)) < 0.001) {
                                    resultLabel.setText("x = " + round(j, 2)); break outerInter;
                                }
                            }
                            for(double j=i; j<=Double.parseDouble(xRightBox.getText()); j=j+0.001) {
                                if(Math.abs(fp.evalParsed(Integer.parseInt(y1Box.getText()) - 1, j)
                                        - fp.evalParsed(Integer.parseInt(y2Box.getText()) - 1, j)) < 0.005) {
                                    resultLabel.setText("x = " + round(j, 2)); break outerInter;
                                }
                            }
                            for(double j=i;
                                j<=Double.parseDouble(xRightBox.getText()); j=j+0.001) {
                                if(Math.abs(fp.evalParsed(Integer.parseInt(y2Box.getText()) - 1, j)
                                        - fp.evalParsed(Integer.parseInt(y2Box.getText()) - 1, j)) < 0.01) {
                                    resultLabel.setText("x = " + round(j, 2)); break outerInter;
                                }
                            }
                            resultLabel.setText("Not Found. ");
                        }
                    }
                }
            }
        }));

        buttonPanel.addComponent(new Button("Cancel", new Runnable() {
            @Override
            public void run() { close(); }}));

        setComponent(mainPanel);
    }

    private static double round(double value, int places) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
