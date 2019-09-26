import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

import java.util.regex.Pattern;

public class PointTable extends BasicWindow {
    public PointTable(Plot plot, FunctionParser fp) {
        super("Table");
        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        Panel optionsPanel = new Panel();
        optionsPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        Panel plotPanel = new Panel();
        plotPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        TerminalSize smallForm = new TerminalSize(4, 1);

        mainPanel.addComponent(optionsPanel);
        optionsPanel.addComponent(new Label("x > "));
        final TextBox xLeftBox = new TextBox().setValidationPattern(Pattern.compile("([^\\w]|[\\d])*")).
                addTo(optionsPanel).setText("-7").setPreferredSize(smallForm);
        optionsPanel.addComponent(new Label("x < "));
        final TextBox xRightBox = new TextBox().setValidationPattern(Pattern.compile("([^\\w]|[\\d])*")).
                addTo(optionsPanel).setText("7").setPreferredSize(smallForm);
        optionsPanel.addComponent(new Label("Interval"));
        final TextBox intervalBox = new TextBox().setValidationPattern(Pattern.compile("([^\\w]|[\\d])*")).
                addTo(optionsPanel).setText("1").setPreferredSize(smallForm);


        mainPanel.addComponent(new Button("View Table >> ", new Runnable() {
            @Override
            public void run() {
                plotPanel.removeAllComponents();
                double begin = Double.parseDouble(xLeftBox.getText());
                double end = Double.parseDouble(xRightBox.getText());
                double interval = Double.parseDouble(intervalBox.getText());
                String[] textPoints = plot.getTextPoints(begin, end, interval, fp);
                for(int i=0; i<textPoints.length; i++) {
                    plotPanel.addComponent(new Label(textPoints[i]));
                    plotPanel.addComponent(new EmptySpace(new TerminalSize(2, 1)));
                }
            }
        }));
        mainPanel.addComponent(plotPanel);
        mainPanel.addComponent(new Button("Done", new Runnable() {
            @Override
            public void run() { close(); }
        }));

        setComponent(mainPanel);
    }
}
