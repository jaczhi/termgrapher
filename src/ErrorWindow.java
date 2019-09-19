import com.googlecode.lanterna.gui2.*;

public class ErrorWindow extends BasicWindow {
    private Exception error;
    boolean messageOpen = false;

    public ErrorWindow(Exception e) {
        super("Error");
        Label errorMessage = new Label(e.toString());

        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        panel.addComponent(new Label("Program Stopped! You may need to close the application."));

        Panel buttonPanel = new Panel();
        buttonPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        panel.addComponent(buttonPanel);
        buttonPanel.addComponent(new Button("Stop", new Runnable() {
            @Override
            public void run() { System.exit(0); }
        }));

        buttonPanel.addComponent(new Button("Continue [FORCE]", new Runnable() {
            @Override
            public void run() { close(); }
        }));

        buttonPanel.addComponent(new Button("View Details", new Runnable() {
            @Override
            public void run() {
                if(!messageOpen) {
                    panel.addComponent(errorMessage);
                } else {
                    panel.removeComponent(errorMessage);
                }
                messageOpen = !messageOpen;
            }
        }));

        setComponent(panel);
    }

}
