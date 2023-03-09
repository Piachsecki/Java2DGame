package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //to close the window properly
        window.setResizable(false); //to be able to resize the window
        window.setTitle("2D ADVENTURE"); // title

        GamePanel gamePanel = new GamePanel();

        window.add(gamePanel);

        window.pack(); // causes this window to be sized to fit the preferred size and layouts of tis subcomponetnts(= GamePanel)

        window.setLocationRelativeTo(null);
        window.setVisible(true); //to see our window properly


        gamePanel.setupGame();

        gamePanel.startGameThread();
    }
}