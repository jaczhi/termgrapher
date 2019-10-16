package me.quickTwix898.termgrapher.window;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import me.quickTwix898.termgrapher.*;
import me.quickTwix898.termgrapher.button.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class MainWindow extends BasicWindow {
    private MultiWindowTextGUI gui;

    /*Lanterna Terminal Code
     * ________________________
     * |_________4)___________|
     * |      |               |
     * |  1)  |               |
     * |      |       3)      |
     * |______|               |
     * |  2)  |               |
     * |______|_______________|
     *
     * 1) Options and list of functions
     * 2) Status box with error log
     * 3) Graph itself
     * 4) Menu bar with different applets
     */

    // instance variables for grabbing current graph configuration
    private final TextBox xScaleBox;
    private final TextBox yScaleBox;
    private final TextBox xLeftBox;
    private final TextBox xRightBox;
    private final TextBox yBottomBox;
    private final TextBox yTopBox;
    private List<TextBox> functionBoxes;
    private CheckBox smoothBox;

    private Panel rightPanel; // variable set new text into graph
    private Label statusLabel; // log

    public MainWindow(MultiWindowTextGUI gui) {
        super("TermGrapher v0.1.2 (c)Jacob Zhi - Beware the Jabberwock, my son!");
        this.gui = gui; // gui will be used by child windows
        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        Panel taskbarPanel = new Panel();
        taskbarPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        Panel horizontalPanel = new Panel();
        horizontalPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        Panel leftPanel = new Panel();
        leftPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        rightPanel = new Panel();
        Panel lTopPanel = new Panel();
        lTopPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        Panel lTopConfig = new Panel();
        lTopConfig.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        Panel lTopFinish = new Panel();
        Panel lBotPanel = new Panel();
        lBotPanel.setPreferredSize(new TerminalSize(25, 10));
        Panel configLeft = new Panel();
        Panel configRight = new Panel();

        mainPanel.addComponent(taskbarPanel);
        mainPanel.addComponent(horizontalPanel);
        horizontalPanel.addComponent(leftPanel);
        horizontalPanel.addComponent(rightPanel);
        leftPanel.addComponent(lTopPanel.withBorder(Borders.singleLine("Options")));
        leftPanel.addComponent(lBotPanel.withBorder(Borders.singleLine("Error Log")));
        lTopPanel.addComponent(lTopConfig);
        lTopPanel.addComponent(lTopFinish);
        lTopConfig.addComponent(configLeft);
        lTopConfig.addComponent(configRight);

        rightPanel.addComponent(new Label("Welcome. Please enter a function to view graph below..."));
        rightPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));

        // will be cleared after graph is done
        rightPanel.addComponent(new Label("Message of the Day:"));
        String tips[] = new String[6];
        tips[0] = "You can throw an intentional error message by typing the word error into the function box.";
        tips[1] = "Not all content and buttons will show if the window size is too small.";
        tips[2] = "TermGrapher is still beta and has many bugs.";
        tips[3] = "In Inside Out, the pizza toppings were changed from broccolis to bell peppers in Japan, since kids in Japan don’t like bell peppers.";
        tips[4] = "Only y= functions can be graphed in TermGrapher at the moment.";
        tips[5] = "Have a .tgs file you want to edit without termgrapher? Open it in your favorite text editor!";
        rightPanel.addComponent(new Label(tips[new Random().nextInt(tips.length)]));
        rightPanel.addComponent(new EmptySpace(new TerminalSize(30, 30)));

        TerminalSize smallForm = new TerminalSize(4, 1);
        configLeft.addComponent(new Label("X Scale"));
        xScaleBox = new TextBox().setValidationPattern(
                Pattern.compile("([^\\w]|[\\d])*")).addTo(configRight).setText("4").setPreferredSize(smallForm);
        configLeft.addComponent(new Label("Y Scale"));
        yScaleBox = new TextBox().setValidationPattern(
                Pattern.compile("([^\\w]|[\\d])*")).addTo(configRight).setText("2").setPreferredSize(smallForm);

        configLeft.addComponent(new Label("x > "));
        xLeftBox = new TextBox().setValidationPattern(Pattern.compile("([^\\w]|[\\d])[0-9]*")).
                addTo(configRight).setText("-7").setPreferredSize(smallForm);
        configLeft.addComponent(new Label("x < "));
        xRightBox = new TextBox().setValidationPattern(Pattern.compile("([^\\w]|[\\d])[0-9]*")).
                addTo(configRight).setText("7").setPreferredSize(smallForm);
        configLeft.addComponent(new Label("y > "));
        yBottomBox = new TextBox().setValidationPattern(Pattern.compile("([^\\w]|[\\d])[0-9]*")).
                addTo(configRight).setText("-7").setPreferredSize(smallForm);
        configLeft.addComponent(new Label("y < "));
        yTopBox = new TextBox().setValidationPattern(Pattern.compile("([^\\w]|[\\d])[0-9]*")).
                addTo(configRight).setText("7").setPreferredSize(smallForm);

        lTopFinish.addComponent(new Label("Functions:"));
        functionBoxes = new ArrayList<TextBox>();
        for (int i=0; i<9; i++) {
            functionBoxes.add(new TextBox().setValidationPattern(Pattern.compile(
                    "[^\\;\\\"\\\\\\!\\@\\#\\$\\%\\&\\_\\'\\:\\<\\>\\~\\`\\,\\?]{1,200}")).addTo(
                    lTopFinish).setPreferredSize(new TerminalSize(25, 1)));
            lTopFinish.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        }

        smoothBox = new CheckBox("Enable Fancy Line Smoothing");
        lTopFinish.addComponent(smoothBox);
        lTopFinish.addComponent(new EmptySpace(new TerminalSize(0, 1)));

        GraphButton graphButton = new GraphButton(this, gui);
        lTopFinish.addComponent(new Button("Graph", graphButton));

        SaveButton saveButton = new SaveButton(this, gui);
        taskbarPanel.addComponent(new Button("Save...", saveButton));

        OpenButton openButton = new OpenButton(this, gui);
        taskbarPanel.addComponent(new Button("Open...", openButton));

        TableButton tableButton = new TableButton(this, gui);
        taskbarPanel.addComponent(new Button("Table...", tableButton));

        TraceButton traceButton = new TraceButton(this, gui);
        taskbarPanel.addComponent(new Button("Trace...", traceButton));

        IntersectionButton intersectionButton = new IntersectionButton(this, gui);
        taskbarPanel.addComponent(new Button("Intersection...", intersectionButton));

        IntegralButton integralButton = new IntegralButton(this, gui);
        taskbarPanel.addComponent(new Button("Integral...", integralButton));

        DerivativeButton derivativeButton = new DerivativeButton(this, gui);
        taskbarPanel.addComponent(new Button("Derivative...", derivativeButton));
        CloseButton closeButton = new CloseButton(this);
        taskbarPanel.addComponent(new Button("Quit", closeButton));

        statusLabel = new Label("Status: OK\n");
        lBotPanel.addComponent(statusLabel);

        // This ultimately links in the panels as the window content
        setComponent(mainPanel);
    }

    public Plot.GraphContext getContext() {
        List<String> functionList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            functionList.add(functionBoxes.get(i).getText());
        }
        for (int i = 0; i < 9; i++) {
            functionList.remove("");
        }
        return new Plot.GraphContext(xScaleBox.getText(),
                yScaleBox.getText(), xLeftBox.getText(), xRightBox.getText(), yBottomBox.getText(), yTopBox.getText(),
                smoothBox.isChecked(), functionList);
    }

    public void setContext(Plot.GraphContext context) {
        xScaleBox.setText(String.valueOf(context.getX_scale()));
        yScaleBox.setText(String.valueOf(context.getY_scale()));
        xLeftBox.setText(String.valueOf(context.getX_begin()));
        xRightBox.setText(String.valueOf(context.getX_end()));
        yBottomBox.setText(String.valueOf(context.getY_begin()));
        yTopBox.setText(String.valueOf(context.getY_end()));
        smoothBox.setChecked(context.isSmoothing());
        for(int i = 0; i<context.getFunctions().size(); i++) {
            functionBoxes.set(i, functionBoxes.get(i).setText(context.getFunctions().get(i)));
        }
    }

    public void setGraphLabel(String s) {
        rightPanel.removeAllComponents();
        rightPanel.addComponent(
                new Label(s).
                        setBackgroundColor(new TextColor.RGB(255, 255, 255)).
                        setForegroundColor(TextColor.ANSI.BLACK));
    }

    public void addStatus(String message) { statusLabel.setText(statusLabel.getText() + "\n" + message); }
}