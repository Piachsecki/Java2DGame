package main;


import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;

//this handles all the on-screen UI
public class UI {
    Font arial_40, arial_80B;

    // isn't it better if we just put the BufferedImage image and differentiate
    // it every time depending on the image itself ? - enhanced switch for example
    BufferedImage keyImage;

    GamePanel gp;

    public boolean messageOn = false;
    public boolean gameFinished = false;
    public String message = "";
    int messageCounter = 0;

    public void showMessage(String text){
        message = text;
        messageOn= true;
    }

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        OBJ_Key key = new OBJ_Key();
        keyImage= key.image;
    }


    //this draw method refreshes 60 times per second, that's why the message counter hits to the number of 120, and then it
    //gets over the range, so we can show the message
    public void draw(Graphics2D g2){
        if(gameFinished){
            g2.setFont(arial_40);
            g2.setColor(Color.WHITE);

            String text;
            int textLength;

            text="You found the treasure!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();

            //by this action the text will be aligned to the center
            int x = gp.screenWidth/2 - textLength/2;
            int y = gp.screenHeight/2 - (gp.tileSize*3);
            g2.drawString(text, x, y);


            g2.setFont(arial_80B);
            g2.setColor(Color.YELLOW);
            text="Congratulations!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + (gp.tileSize*3);
            g2.drawString(text, x, y);

            gp.gameThread = null;
        }else {
            g2.setFont(arial_40);
            g2.setColor(Color.WHITE);
            g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            g2.drawString("x " + gp.player.hasKey, 74, 65);

            if (messageOn) {
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);
                messageCounter++;
            }

            if (messageCounter > 120) {
                messageCounter = 0;
                messageOn = false;

            }
        }
    }
}
