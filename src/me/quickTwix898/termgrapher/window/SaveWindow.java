package me.quickTwix898.termgrapher.window;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import me.quickTwix898.termgrapher.GraphIO;
import me.quickTwix898.termgrapher.Plot;
import me.quickTwix898.termgrapher.button.CloseButton;
import me.quickTwix898.termgrapher.window.ErrorWindow;

import java.util.Arrays;
import java.util.regex.Pattern;

public class SaveWindow extends BasicWindow {
    public SaveWindow(MultiWindowTextGUI gui, Plot.GraphContext graphContext) {
        super("Save graph");
        Panel verticalPanel = new Panel();
        verticalPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        verticalPanel.addComponent(new Label("Specify filename or path to save all settings and functions to: "));
        final TextBox filenameBox = new TextBox().setValidationPattern(
                Pattern.compile("[^\\*\\[\\]\\:\\;\\,]{1,200}")).addTo(
                verticalPanel).setText("Untitled.tgs").setPreferredSize(new TerminalSize(35, 1));
        verticalPanel.addComponent(new Label("The file must end in the .tgs extension"));
        Panel buttonPanel = new Panel();
        buttonPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        verticalPanel.addComponent(buttonPanel);

        CloseButton closeButton = new CloseButton(this);
        buttonPanel.addComponent(new Button("Cancel", closeButton));

        buttonPanel.addComponent(new Button("Save", new Runnable() {
            @Override
            public void run(){
                try {
                    GraphIO.writeToFile(filenameBox.getText(), graphContext);
                    close();
                } catch(Exception e) {
                    ErrorWindow ew = new ErrorWindow(e);
                    ew.setHints(Arrays.asList(Window.Hint.CENTERED));
                    gui.addWindowAndWait(ew);   
                }
            }
        }));

        setComponent(verticalPanel);
    }
}
