package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    //SCREEN PANEL - SCREEN SETTINGS
    final int originalTileSize = 16; // 16 x 16 tile - the size of every character / creature that is being created e

    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48

    public final int maxScreenCol = 16; //maximum width

    public final int maxScreenRow = 12; //maximum height

    public final int screenWidth = tileSize * maxScreenCol; //768 pixels
    public final int screenHeight = tileSize * maxScreenRow; //567 pixels
    //World settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;


    KeyHandler keyH = new KeyHandler();
    Thread gameThread; //it keeps our programm running until we stop it

    //if fps is set up to 60 - then our program is going to update and draw do this 60 times per sec
    int FPS = 60;
    TileManager tileM = new TileManager(this);
    public Player player = new Player(this, keyH);

    // it means that we can display 10 objects at the same time
    // it is for not slowing down the game -> if we pick the objectA we can still add some object and display it- it is not a problem

    public SuperObject obj[] = new SuperObject[10];


    public CollisionChecker checker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));  //set the size of the JPanel class
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // if it is set to true, all the drawing from this component will be done in an offscreen paiting buffer
        this.addKeyListener(keyH);
        this.setFocusable(true); // this GamePanel can be "focused" to receive key input



    }

    //wwe have to call this method before the game starts
    public void setupGame(){
        aSetter.setObject();
    }

    public void startGameThread(){

        gameThread = new Thread(this); //passing the GamePanel class to this constructor
        gameThread.start(); // it is calling the run methood
    }

    /*
        Below in run method we are providing the game loop - the most important stuff for the beginning
     */

    @Override
    public void run() {

        long currentTime;

        long lastTime = System.nanoTime();
        double delta = 0;
        double drawInterval = 1000000000/FPS; // 0.01666...7 - seconds it draws the screen
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
        player.update();
    }

    public void paintComponent(Graphics g){ //graphics class has many functions to draw objects on the screen
        super.paintComponent(g); // always u just have to write this
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2); //this have to be first(before drawing a player) to be a layer and to do not hide players
        for (int i = 0; i < obj.length; i++) {
            if(obj[i]!=null){
                obj[i].draw(g2, this);
            }
        }
        player.draw(g2);
        g2.dispose(); // dispose of this graphics context and release any system resources that is using.


    }

}
