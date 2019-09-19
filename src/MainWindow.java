import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.ThemeStyle;
import com.googlecode.lanterna.gui2.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;

public class MainWindow extends BasicWindow {
    private Plot plot;
    private MultiWindowTextGUI gui;
    public MainWindow(MultiWindowTextGUI gui) {
        /*Lanterna Terminal Code
         * _______________________________
         * |      |       |               |
         * |  1)  |       |               |
         * |      |   3)  |       4)      |
         * |______|       |               |
         * |  2)  |       |               |
         * |______|_______|_______________|
         *
         * 1) Options and list of functions
         * 2) Save/Open list of functions
         * 3) Table w/ x,y value
         * 4) Graph itself
         */

        super("TermGrapher v0.0.2 (c)Jacob Zhi - Beware the Jabberwock, my son!");
        this.gui = gui;
        Panel horizontalPanel = new Panel();
        horizontalPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        Panel leftPanel = new Panel();
        leftPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        Panel middlePanel = new Panel();
        Panel rightPanel = new Panel();
        Panel lTopPanel = new Panel();
        lTopPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        Panel lTopConfig = new Panel();
        lTopConfig.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        Panel lTopFinish = new Panel();
        Panel lBotPanel = new Panel();
        Panel configLeft = new Panel();
        Panel configRight = new Panel();

        horizontalPanel.addComponent(leftPanel);
        horizontalPanel.addComponent(middlePanel.withBorder(Borders.singleLine("Function Table")));
        horizontalPanel.addComponent(rightPanel);
        leftPanel.addComponent(lTopPanel.withBorder(Borders.singleLine("Options")));
        leftPanel.addComponent(lBotPanel.withBorder(Borders.singleLine("Saves")));
        lTopPanel.addComponent(lTopConfig);
        lTopPanel.addComponent(lTopFinish);
        lTopConfig.addComponent(configLeft);
        lTopConfig.addComponent(configRight);

        middlePanel.addComponent(new Label("Waiting..."));
        middlePanel.addComponent(new EmptySpace(new TerminalSize(15, 30)));
        rightPanel.addComponent(new Label("Welcome. Please enter a function to view graph below..."));
        rightPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));

        rightPanel.addComponent(new Label("Message of the Day:"));
        String tips[] = new String[5];
        tips[0] = "You can throw an intentional error message by typing the word error into the function box.";
        tips[1] = "Not all content and buttons will show if the window size is too small.";
        tips[2] = "TermGrapher is still alpha and has many bugs.";
        tips[3] = "In Inside Out, the pizza toppings were changed from broccolis to bell peppers in Japan, since kids in Japan don’t like bell peppers.";
        tips[4] = "Only y= functions can be graphed in TermGrapher at the moment.";
        rightPanel.addComponent(new Label(tips[new Random().nextInt(tips.length)]));
        rightPanel.addComponent(new EmptySpace(new TerminalSize(30, 30)));

        TerminalSize smallForm = new TerminalSize(4, 1);
        configLeft.addComponent(new Label("X Scale"));
        final TextBox xScaleBox = new TextBox().setValidationPattern(
                Pattern.compile("([^\\w]|[\\d])*")).addTo(configRight).setText("1").setPreferredSize(smallForm);
        configLeft.addComponent(new Label("Y Scale"));
        final TextBox yScaleBox = new TextBox().setValidationPattern(
                Pattern.compile("([^\\w]|[\\d])*")).addTo(configRight).setText("1").setPreferredSize(smallForm);

        configLeft.addComponent(new Label("x > "));
        final TextBox xLeftBox = new TextBox().setValidationPattern(Pattern.compile("([^\\w]|[\\d])[0-9]*")).
                addTo(configRight).setText("-7").setPreferredSize(smallForm);
        configLeft.addComponent(new Label("x < "));
        final TextBox xRightBox = new TextBox().setValidationPattern(Pattern.compile("([^\\w]|[\\d])[0-9]*")).
                addTo(configRight).setText("7").setPreferredSize(smallForm);
        configLeft.addComponent(new Label("y > "));
        final TextBox yBottomBox = new TextBox().setValidationPattern(Pattern.compile("([^\\w]|[\\d])[0-9]*")).
                addTo(configRight).setText("-7").setPreferredSize(smallForm);
        configLeft.addComponent(new Label("y < "));
        final TextBox yTopBox = new TextBox().setValidationPattern(Pattern.compile("([^\\w]|[\\d])[0-9]*")).
                addTo(configRight).setText("7").setPreferredSize(smallForm);

        lTopFinish.addComponent(new Label("Functions:"));
        ArrayList<TextBox> functionBoxes = new ArrayList<TextBox>();
        for (int i=0; i<9; i++) {
            functionBoxes.add(new TextBox().setValidationPattern(Pattern.compile(
                    "[^\\;\\\"\\\\\\!\\@\\#\\$\\%\\&\\_\\'\\:\\<\\>\\~\\`\\,\\?]{1,200}")).addTo(
                    lTopFinish).setPreferredSize(new TerminalSize(17, 1)));
            lTopFinish.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        }

        lTopFinish.addComponent(new Button("Graph", new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<String> functionList = new ArrayList<String>();
                    for (int i = 0; i < 9; i++) {
                        functionList.add(functionBoxes.get(i).getText());
                    }
                    for (int i = 0; i < 9; i++) {
                        functionList.remove("");
                    }
                    String[] functions = new String[functionList.size()];
                    for (int i = 0; i < functionList.size(); i++) {
                        functions[i] = functionList.get(i);
                    }


                    FunctionParser fp = new FunctionParser(functions);

                    double x_scale = Double.parseDouble(xScaleBox.getText());
                    double y_scale = Double.parseDouble(yScaleBox.getText());
                    int x_begin = Integer.parseInt(xLeftBox.getText());
                    int x_end = Integer.parseInt(xRightBox.getText());
                    int y_begin = Integer.parseInt(yBottomBox.getText());
                    int y_end = Integer.parseInt(yTopBox.getText());

                    plot = new Plot(x_scale, y_scale, x_begin, x_end, y_begin, y_end, functions, fp);
                    middlePanel.removeAllComponents();
                    rightPanel.removeAllComponents();
                    middlePanel.addComponent(new Label(plot.getTextPoints()));
                    rightPanel.addComponent(new Label(plot.getStringPlot()));
                } catch (Exception e) {
                    gui.addWindowAndWait(new ErrorWindow(e));
                }
            }
        } ));
        lBotPanel.addComponent(new Label("2)"));


        // This ultimately links in the panels as the window content
        setComponent(horizontalPanel);
    }
}