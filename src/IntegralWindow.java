import com.googlecode.lanterna.gui2.*;

public class IntegralWindow extends BasicWindow {
    public IntegralWindow(FunctionParser fp) {
        super("Integral");
        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        mainPanel.addComponent(new Label("Still WIP"));
        mainPanel.addComponent(new Button("Close", new Runnable() {
            @Override
            public void run() {
                close();
            }
        }));
        setComponent(mainPanel);
    }
}
