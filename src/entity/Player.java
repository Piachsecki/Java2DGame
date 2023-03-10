package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    //screenX and screenY are for the camera - the rectangle that our character can see(it doesnt change)
    public final int screenX;
    public final int screenY;
    GamePanel gp;
    KeyHandler keyH;
    public int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(this.getClass().getResourceAsStream("/res/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void update() {
        if (
                keyH.upPressed == true ||
                        keyH.downPressed == true ||
                        keyH.leftPressed == true ||
                        keyH.rightPressed == true) {


            if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.rightPressed) {
                direction = "right";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.upPressed) {
                direction = "up";
            }

            collisionOn = false;
            //checking tile collision
            gp.checker.checkTile(this);

            //checking object collision
            int objIndex = gp.checker.checkObject(this, true);
            pickUpObject(objIndex);

            if (collisionOn == false) {
                switch (direction) {
                    case "up":
                        this.worldY -= speed;
                        break;
                    case "down":
                        this.worldY += speed;
                        break;
                    case "left":
                        this.worldX -= speed;
                        break;
                    case "right":
                        this.worldX += speed;
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

    }


    public void pickUpObject(int index){
        if(index != 999){
            String objectName = gp.obj[index].name;
            switch (objectName){
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[index] = null;
                    gp.ui.showMessage("You got a key!");
                    break;
                case "Door":
                    if(hasKey > 0){
                        gp.playSE(3);
                        gp.obj[index] = null;
                        hasKey--;
                        gp.ui.showMessage("You opened the door!");

                    }else {
                        gp.ui.showMessage("You need a key to open the door!");
                    }
                    break;
                case "Boots":
                    gp.playSE(2 );
                    speed+=2;
                    gp.obj[index] = null;
                    gp.ui.showMessage("Speed up!");
                    break;
                case "Chest":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSE(4);
                    break;

            }
        }
    }
    public void draw(Graphics2D g2) {
//        g2.setColor(Color.WHITE);
//
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);  //draw a rectangle and fills it with the color  that was specified

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null); // observer - design pattern, draw image draws an image on {x, y} coordinates the image on height and wwidth of tiileSize-48/48pixels

    }
}




// java default library cannot load mp3 - we have to prepare sound in WAV format and also it has to be 16 bit (32 bit doesn't work)
