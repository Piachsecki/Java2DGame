package entity;


import java.awt.*;
import java.awt.image.BufferedImage;

//class that stores variables that will be used in player/monster/npc classes
public class Entity {
    public int worldX, worldY;
    public int speed;
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;

    BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea;
    public boolean collisionOn = false;
}
