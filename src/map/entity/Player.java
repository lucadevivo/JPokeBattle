package map.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import map.logic.GamePanel;
import map.logic.KeyHandler;

/**
 * The Player class represents the main player character in the game, 
 * handling movement, interactions, animations, and input controls.
 */
public class Player extends Entity {

    // Reference to the KeyHandler for handling player input
    KeyHandler keyH;

    // String to store the direction the player is standing in
    private String standingDirection;

    // Screen coordinates where the player is drawn (center of the screen)
    public final int screenX, screenY;

    // Private method to set the standing direction of the player
    private void setStandingDirection(String direction) {
        standingDirection = direction;
    }

    /**
     * Constructor to initialize the player with the game panel and key handler
     * @param gp
     * @param keyH
     */
    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        // Calculate the player's screen position (centered)
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        // Set up the player's collision area
        solidArea = new Rectangle();
        solidArea.x = 6;
        solidArea.y = 28;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 50;
        solidArea.height = 34;

        // Set default values and load the player images
        setDefaultValues();
        getPlayerImage();
    }

    // Method to set default values for the player's attributes
    public void setDefaultValues() {
        // Set initial position in the world
        worldX = gp.tileSize * 5;
        worldY = gp.tileSize * 6;

        // Set the player's speed
        speed = 4;

        // Initial direction can be any; here, it's set to "up"
        direction = "up";
    }

    // Method to load images representing the player's movement and standing animations
    public void getPlayerImage() {
        up1 = setup("/player/trainerLeftFootUp");
        up2 = setup("/player/trainerRightFootUp");
        standUp = setup("/player/trainerStandingUp");
        down1 = setup("/player/trainerLeftFootDown");
        down2 = setup("/player/trainerRightFootDown");
        standDown = setup("/player/trainerStandingDown");
        left = setup("/player/trainerWalkingLeft");
        standLeft = setup("/player/trainerStandingLeft");
        right = setup("/player/trainerWalkingRight");
        standRight = setup("/player/trainerStandingRight");
    }

    // Method to get the image representing the player's standing direction
    public BufferedImage getStandingImage() {
        switch (standingDirection) {
            case "up": return standUp;
            case "down": return standDown;
            case "left": return standLeft;
            case "right": return standRight;
            default: return standDown;
        }
    }

    // Update method called each frame to handle player movement and interaction
    public void update() {
        // Check if any directional key is pressed
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            // Set the player's direction based on the input
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }

            // Check for collisions with tiles and NPCs
            collisionOn = false;
            gp.cChecker.checkTile(this);
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // Move the player if no collision is detected
            if (!collisionOn) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            // Update the sprite animation counter
            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 3;
                } else if (spriteNum == 3) {
                    spriteNum = 4;
                } else if (spriteNum == 4) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        // Set the standing direction if no directional key is pressed
        if (!keyH.upPressed && !keyH.downPressed && !keyH.rightPressed && !keyH.leftPressed) {
            setStandingDirection(direction);
        }
    }

    // Method to handle interaction with NPCs
    public void interactNPC(int i) {
        if (i != 999) {
            gp.gameState = gp.dialogueState;
            gp.npc[i].speak();
        }
    }

    // Method to draw the player on the screen
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        // Determine which image to use based on the player's movement direction and animation state
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            switch (direction) {
                case "up":
                    image = (spriteNum == 1 || spriteNum == 3) ? up1 : standUp;
                    break;
                case "down":
                    image = (spriteNum == 1 || spriteNum == 3) ? down1 : standDown;
                    break;
                case "left":
                    image = (spriteNum == 1 || spriteNum == 3) ? left : standLeft;
                    break;
                case "right":
                    image = (spriteNum == 1 || spriteNum == 3) ? right : standRight;
                    break;
            }
        } else {
            // Use the standing image if no directional keys are pressed
            image = getStandingImage();
        }

        // Draw the player at the calculated screen position
        g2.drawImage(image, screenX, screenY, null);
    }
}