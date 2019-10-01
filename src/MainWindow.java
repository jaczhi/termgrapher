import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;

import java.util.ArrayList;
import java.util.Arrays;
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

        super("TermGrapher v0.0.3 (c)Jacob Zhi - Beware the Jabberwock, my son!");
        this.gui = gui;
        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        Panel taskbarPanel = new Panel();
        taskbarPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        Panel horizontalPanel = new Panel();
        horizontalPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        Panel leftPanel = new Panel();
        leftPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        Panel rightPanel = new Panel();
        Panel lTopPanel = new Panel();
        lTopPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        Panel lTopConfig = new Panel();
        lTopConfig.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        Panel lTopFinish = new Panel();
        Panel lBotPanel = new Panel();
        Panel configLeft = new Panel();
        Panel configRight = new Panel();

        mainPanel.addComponent(taskbarPanel);
        mainPanel.addComponent(horizontalPanel);
        horizontalPanel.addComponent(leftPanel);
        horizontalPanel.addComponent(rightPanel);
        leftPanel.addComponent(lTopPanel.withBorder(Borders.singleLine("Options")));
        leftPanel.addComponent(lBotPanel.withBorder(Borders.singleLine()));
        lTopPanel.addComponent(lTopConfig);
        lTopPanel.addComponent(lTopFinish);
        lTopConfig.addComponent(configLeft);
        lTopConfig.addComponent(configRight);

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
                Pattern.compile("([^\\w]|[\\d])*")).addTo(configRight).setText("4").setPreferredSize(smallForm);
        configLeft.addComponent(new Label("Y Scale"));
        final TextBox yScaleBox = new TextBox().setValidationPattern(
                Pattern.compile("([^\\w]|[\\d])*")).addTo(configRight).setText("2").setPreferredSize(smallForm);

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
                    lTopFinish).setPreferredSize(new TerminalSize(25, 1)));
            lTopFinish.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        }

        CheckBox smoothBox = new CheckBox("Enable Fancy Line Smoothing");
        lTopFinish.addComponent(smoothBox);
        lTopFinish.addComponent(new EmptySpace(new TerminalSize(0, 1)));

        lTopFinish.addComponent(new Button("Graph", new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<String> functionList = new ArrayList<String>();
                    for (int i = 0; i < 9; i++) { functionList.add(functionBoxes.get(i).getText()); }
                    for (int i = 0; i < 9; i++) { functionList.remove(""); }
                    String[] functions = new String[functionList.size()];
                    for (int i = 0; i < functionList.size(); i++) { functions[i] = functionList.get(i); }

                    FunctionParser fp = new FunctionParser(functions);

                    double x_scale = Double.parseDouble(xScaleBox.getText());
                    double y_scale = Double.parseDouble(yScaleBox.getText());
                    int x_begin = Integer.parseInt(xLeftBox.getText());
                    int x_end = Integer.parseInt(xRightBox.getText());
                    int y_begin = Integer.parseInt(yBottomBox.getText());
                    int y_end = Integer.parseInt(yTopBox.getText());
                    boolean smoothing = smoothBox.isChecked();

                    plot = new Plot(x_scale, y_scale, x_begin, x_end, y_begin, y_end, smoothing, functions, fp);
                    rightPanel.removeAllComponents();
                    rightPanel.addComponent(new Label(
                            plot.getStringPlot()).setBackgroundColor(
                                    new TextColor.RGB(255, 255, 255)).setForegroundColor(TextColor.ANSI.BLACK));
                } catch (Exception e) {
                    ErrorWindow ew = new ErrorWindow(e);
                    ew.setHints(Arrays.asList(Window.Hint.CENTERED));
                    gui.addWindowAndWait(ew);
                }
            }
        } ));

        taskbarPanel.addComponent(new Button("Save...", new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<String> functionList = new ArrayList<String>();
                    for (int i = 0; i < 9; i++) { functionList.add(functionBoxes.get(i).getText()); }
                    for (int i = 0; i < 9; i++) { functionList.remove(""); }
                    String[] functions = new String[functionList.size()];
                    for (int i = 0; i < functionList.size(); i++) { functions[i] = functionList.get(i); }


                    double x_scale = Double.parseDouble(xScaleBox.getText());
                    double y_scale = Double.parseDouble(yScaleBox.getText());
                    int x_begin = Integer.parseInt(xLeftBox.getText());
                    int x_end = Integer.parseInt(xRightBox.getText());
                    int y_begin = Integer.parseInt(yBottomBox.getText());
                    int y_end = Integer.parseInt(yTopBox.getText());
                    boolean smoothing = smoothBox.isChecked();

                    SaveWindow sw = new SaveWindow(gui, x_scale, y_scale, x_begin, x_end, y_begin, y_end, smoothing, functions);
                    sw.setHints(Arrays.asList(Window.Hint.CENTERED));
                    gui.addWindowAndWait(sw);
                } catch(Exception e) {
                    ErrorWindow ew = new ErrorWindow(e);
                    ew.setHints(Arrays.asList(Window.Hint.CENTERED));
                    gui.addWindowAndWait(ew);
                }
            }
        }));

        taskbarPanel.addComponent(new Button("Open...", new Runnable() {
            @Override
            public void run() {
                try {
                    OpenWindow ow = new OpenWindow(gui);
                    ow.setHints(Arrays.asList(Window.Hint.CENTERED));
                    gui.addWindowAndWait(ow);
                    if(ow.opened) {
                        xScaleBox.setText(String.valueOf(ow.inputContext.x_scale));
                        yScaleBox.setText(String.valueOf(ow.inputContext.y_scale));
                        xLeftBox.setText(String.valueOf(ow.inputContext.x_begin));
                        xRightBox.setText(String.valueOf(ow.inputContext.x_end));
                        yBottomBox.setText(String.valueOf(ow.inputContext.y_begin));
                        yTopBox.setText(String.valueOf(ow.inputContext.y_end));
                        smoothBox.setChecked(ow.inputContext.smoothing);

                        for(int i=0; i<functionBoxes.size(); i++) {
                            try {
                                functionBoxes.get(i).setText(ow.inputContext.functions[i]);
                            } catch(Exception e) {
                                functionBoxes.get(i).setText("");
                            }
                        }
                    }
                } catch(Exception e) {
                    ErrorWindow ew = new ErrorWindow(e);
                    ew.setHints(Arrays.asList(Window.Hint.CENTERED));
                    gui.addWindowAndWait(ew);
                }
            }
        }));

        taskbarPanel.addComponent(new Button("Table...", new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<String> functionList = new ArrayList<String>();
                    for (int i = 0; i < 9; i++) { functionList.add(functionBoxes.get(i).getText()); }
                    for (int i = 0; i < 9; i++) { functionList.remove(""); }
                    String[] functions = new String[functionList.size()];
                    for (int i = 0; i < functionList.size(); i++) { functions[i] = functionList.get(i); }

                    FunctionParser fp = new FunctionParser(functions);
                    PointTable pt = new PointTable(plot, fp);
                    gui.addWindowAndWait(pt);
                } catch(Exception e) {
                    ErrorWindow ew = new ErrorWindow(e);
                    ew.setHints(Arrays.asList(Window.Hint.CENTERED));
                    gui.addWindowAndWait(ew);
                }
            }
        }));

        taskbarPanel.addComponent(new Button("Intersection...", new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<String> functionList = new ArrayList<String>();
                    for (int i = 0; i < 9; i++) { functionList.add(functionBoxes.get(i).getText()); }
                    for (int i = 0; i < 9; i++) { functionList.remove(""); }
                    String[] functions = new String[functionList.size()];
                    for (int i = 0; i < functionList.size(); i++) { functions[i] = functionList.get(i); }

                    FunctionParser fp = new FunctionParser(functions);

                    IntersectionAnalysis ia = new IntersectionAnalysis(fp);
                    gui.addWindowAndWait(ia);
                } catch(Exception e) {
                    ErrorWindow ew = new ErrorWindow(e);
                    ew.setHints(Arrays.asList(Hint.CENTERED));
                    gui.addWindowAndWait(ew);
                }
            }
        }));

        taskbarPanel.addComponent(new Button("Integral...", new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<String> functionList = new ArrayList<String>();
                    for (int i = 0; i < 9; i++) { functionList.add(functionBoxes.get(i).getText()); }
                    for (int i = 0; i < 9; i++) { functionList.remove(""); }
                    String[] functions = new String[functionList.size()];
                    for (int i = 0; i < functionList.size(); i++) { functions[i] = functionList.get(i); }

                    FunctionParser fp = new FunctionParser(functions);

                    IntegralWindow iw = new IntegralWindow(fp);
                    gui.addWindowAndWait(iw);
                } catch(Exception e) {
                    ErrorWindow ew = new ErrorWindow(e);
                    ew.setHints(Arrays.asList(Hint.CENTERED));
                    gui.addWindowAndWait(ew);
                }
            }
        }));

        taskbarPanel.addComponent(new Button("Quit", new Runnable() {
            @Override
            public void run() {
                close();
            }
        }));

        lBotPanel.addComponent(new Label("Status: OK"));
        lBotPanel.addComponent(new EmptySpace(new TerminalSize(15, 5)));

        // This ultimately links in the panels as the window content
        setComponent(mainPanel);
    }
}