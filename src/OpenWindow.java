import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

import java.util.Arrays;
import java.util.regex.Pattern;

public class OpenWindow extends BasicWindow {
    public GraphIO inputContext;
    public boolean opened = false;
    public OpenWindow(MultiWindowTextGUI gui) {
        super("Open graph");
        Panel verticalPanel = new Panel();
        verticalPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        verticalPanel.addComponent(new Label("Please specify directory where .tgs file is located: "));
        final TextBox filenameBox = new TextBox().setValidationPattern(
                Pattern.compile("[^\\*\\[\\]\\:\\;\\,]{1,200}")).addTo(
                verticalPanel).setPreferredSize(new TerminalSize(35, 1));
        Panel buttonPanel = new Panel();
        buttonPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        verticalPanel.addComponent(buttonPanel);

        buttonPanel.addComponent(new Button("Cancel", new Runnable() {
            @Override
            public void run(){ close(); }
        }));
        buttonPanel.addComponent(new Button("Open", new Runnable() {
            @Override
            public void run(){
                try {
                    GraphIO gIO = new GraphIO(filenameBox.getText());
                    gIO.readFile();
                    inputContext = gIO;
                    opened = true;
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
