package me.quickTwix898.termgrapher;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import me.quickTwix898.termgrapher.window.MainWindow;

import java.io.IOException;
import java.util.Arrays;

public class Grapher {
    //static
    public static void main(String[] args) throws IOException {
        INSTANCE = init();
    }

    private static Grapher INSTANCE;

    public static Grapher init() throws IOException{
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.DEFAULT));
        MainWindow window = new MainWindow(gui);
        window.setHints(Arrays.asList(Window.Hint.FULL_SCREEN));

        gui.addWindowAndWait(window);

        screen.stopScreen();
        terminal.clearScreen();

        return new Grapher(gui);
    }

    public static Grapher get(){
        return INSTANCE;
    }

    //instance
    MultiWindowTextGUI gui;

    private Grapher(MultiWindowTextGUI gui){
        this.gui = gui;
    }

    public MultiWindowTextGUI getGui() {
        return gui;
    }
}