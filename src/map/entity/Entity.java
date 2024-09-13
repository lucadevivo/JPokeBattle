package map.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import map.logic.GamePanel;
import map.logic.UtilityTool;

/**
 * The Entity class is designed to draw and manage both the characters' peculiarities;
 * for example their phisical dimensions, their hitboxes, their dialogues etc.
 */
public class Entity {

    // Reference to the game panel, which holds game-related information and context
    GamePanel gp;
    // Coordinates in the game world
    public int worldX, worldY;
    // Movement speed of the entity
    public int speed;
    // BufferedImages for different sprite states of the entity
    public BufferedImage up1, up2, standUp, down1, down2, standDown, left, standLeft, right, standRight, standingNpc;
    // Current direction the entity is facing
    public String direction;
    // Counters for sprite animation
    public int spriteCounter = 0;
    public int spriteNum = 1;
    // The collision area of the entity
    public Rectangle solidArea = new Rectangle(0, 0, 64, 64);
    // Default coordinates of the collision area
    public int solidAreaDefaultX, solidAreaDefaultY;
    // Collision detection flag
    public boolean collisionOn = false;
    // Array to hold dialogues for the entity
    String dialogues[] = new String[20];
    // Index of the current dialogue
    int dialogueIndex = 0;

    /**
     * Constructs an Entity with the given gamepanel logic.
     * @param gamePanel gp
     */
    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    // Method to handle entity dialogues (implementation needed)
    public void speak() {
        // Logic for speaking will be implemented here
    }

    // Method to draw the entity on the screen
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        // Calculate the entity's screen position relative to the player
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Check if the entity is within the player's visible screen area
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            // Set the image to draw when the entity is standing
            image = standingNpc;
            // Draw the entity on the screen
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    // Method to load and scale an image for the entity
    public BufferedImage setup(String imagePath) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            // Read the image from the given path
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            // Scale the image to match the game's tile size
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
