import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

import java.util.regex.Pattern;

public class SaveWindow extends BasicWindow {
    public SaveWindow(MultiWindowTextGUI gui, double x_scale, double y_scale, int x_begin, int x_end, int y_begin, int y_end, String[] functions) {
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

        buttonPanel.addComponent(new Button("Cancel", new Runnable() {
            @Override
            public void run(){ close(); }
        }));
        buttonPanel.addComponent(new Button("Save", new Runnable() {
            @Override
            public void run(){
                try {
                    GraphIO.writeToFile(filenameBox.getText(), x_scale, y_scale, x_begin, x_end, y_begin, y_end, functions);
                    close();
                } catch(Exception e) {
                    gui.addWindowAndWait(new ErrorWindow(e));
                }
            }
        }));

        setComponent(verticalPanel);
    }
}
