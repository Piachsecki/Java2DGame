package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    //SCREEN PANEL - SCREEN SETTINGS
    final int originalTileSize = 16; // 16 x 16 tile - the size of every character / creature that is being created e

    final int scale = 3;

    final int tileSize = originalTileSize * scale; // 48x48

    final int maxScreenCol = 16; //maximum width

    final int maxScreenRow = 12; //maximum height

    final int screenWidth = tileSize * maxScreenCol; //768 pixels
    final int screenHeight = tileSize * maxScreenRow; //567 pixels


    KeyHandler keyH = new KeyHandler();
    Thread gameThread; //it keeps our programm running until we stop it

    //if fps is set up to 60 - then our program is going to update and draw do this 60 times per sec
    int FPS = 60;

    //setting players default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));  //set the size of the JPanel class
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // if it is set to true, all the drawing from this component will be done in an offscreen paiting buffer
        this.addKeyListener(keyH);
        this.setFocusable(true); // this GamePanel can be "focused" to receive key input



    }

    public void startGameThread(){
        gameThread = new Thread(this); //passing the GamePanel class to this constructor
        gameThread.start();
    }

    /*
        Below in run method we are providing the game loop - the most important stuff for the beginning
     */

    @Override
    public void run() {

        long currentTime;

        long lastTime = System.nanoTime();
        double delta = 0;
        double drawInterval = 1000000000/FPS;
        long timer = 0;

        int drawCount = 0;
        while(gameThread != null){

            currentTime = System.nanoTime();
            delta+= (currentTime - lastTime) / drawInterval;
            timer+=(currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                //1UPDATE update  information such as character position
                update();


                //2 DRAW: draw the screen with the updated information
                repaint();
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000){
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }


        }
    }

    public void update(){
        if(keyH.downPressed){
            this.playerY += playerSpeed;
        }
        else if(keyH.rightPressed){
            this.playerX += playerSpeed;
        }
        else if(keyH.leftPressed){
            this.playerX -= playerSpeed;
        }
        if(keyH.upPressed){
            this.playerY -= playerSpeed;
        }
    }

    public void paintComponent(Graphics g){ //graphics class has many functions to draw objects on the screen
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.WHITE);

        g2.fillRect(playerX, playerY, tileSize, tileSize);  //draw a rectangle and fills it with the color  that was specified

        g2.dispose(); // dispose of this graphics context and release any system resources that is using.


    }

}
