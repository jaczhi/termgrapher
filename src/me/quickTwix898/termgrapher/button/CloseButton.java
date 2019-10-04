package me.quickTwix898.termgrapher.button;

import com.googlecode.lanterna.gui2.BasicWindow;

public class CloseButton implements Runnable {
    BasicWindow window;

    public CloseButton(BasicWindow window) {
        this.window = window;
    }

    @Override
    public void run() {
        window.close();
    }
}
