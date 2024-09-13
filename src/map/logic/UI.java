package map.logic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import battle.graphics.Constants;

/**
 * The UI class handles rendering the game's user interface, including title, dialogue, 
 * end, and profile screens. It manages UI state, displays messages, and updates 
 * visuals based on the current game state.
 */
public class UI {

    // Reference to the main game panel, used for accessing game states and settings
    GamePanel gp;
    Graphics2D g2;

    // Images for displaying on the screen
    private BufferedImage image1;
    private BufferedImage image2;

    // Flags and variables for managing UI state
    public boolean messageOn = false;  // Determines if a message is currently being displayed
    public String message = "";        // The current message to display
    int messageCounter = 0;            // Counter for message display duration
    public boolean gameFinished = false; // Flag indicating if the game has finished
    public int commandNum = 0;         // Tracks the currently selected command in menus
    public int dialogueChoice = 0;     // Tracks the player's choice in dialogue
    public int endChoice = 0;          // Tracks the player's choice at the end screen
    public int profileChoice = 0;      // Tracks the selected profile in the profile menu
    public String currentDialogue = ""; // The current dialogue text being shown
    public static final Color BACKGROUND_COLOR = Color.WHITE; // Default background color

    // Array to save "empty" status in profiles
    private boolean[] profilesEmpty = new boolean[3];

    /**
     * Constructor initializes the UI and checks profiles
     * @param gp
     */
    public UI(GamePanel gp) {
        this.gp = gp;
        loadImage();
        checkProfiles();
    }

    // Method to check if profile files exist and update profilesEmpty array
    private void checkProfiles() {
        for (int i = 0; i < profilesEmpty.length; i++) {
            File file = new File("res/profiles/profile_" + i + ".ser"); // Assuming profiles are saved as .ser files
            profilesEmpty[i] = !file.exists(); // True if the file does not exist
        }
    }

    // Loads images for the UI, choosing random Pokémon images from available resources
    private void loadImage() {
        Random random = new Random();

        try {
            // Randomly selects Pokémon images for the UI
            int randomIndex1 = random.nextInt(151) + 1; // Generates a random number between 1-151
            int randomIndex2 = random.nextInt(151) + 1; // Generates a random number between 1-151

            UtilityTool uTool = new UtilityTool();

            // Loads and scales the back image of a Pokémon
            image1 = ImageIO.read(getClass().getResourceAsStream("/pokemonbacks/" + randomIndex1 + ".png"));
            image1 = uTool.scaleImage(image1, image1.getWidth() * 4, image1.getWidth() * 4);

            // Loads and scales the front image of a Pokémon
            image2 = ImageIO.read(getClass().getResourceAsStream("/pokemonfronts/" + randomIndex2 + ".png"));
            image2 = uTool.scaleImage(image2, gp.tileSize * 4, gp.tileSize * 4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Displays a message on the screen
    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    // Draws the appropriate screen based on the game's current state
    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(Constants.SECOND_FONT);
        g2.setColor(Color.BLACK);

        // Draw the appropriate screen based on gameState
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        } else if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        } else if (gp.gameState == gp.endState) {
            drawEndScreen();
        } else if (gp.gameState == gp.profileState) {
            drawProfileScreen();
        }
    }

    // Calculates the X coordinate to center text on the screen
    public int getXForCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }

    // Draws the title screen with menu options and background images
    public void drawTitleScreen() {
        // Set background color and fill the screen
        g2.setColor(BACKGROUND_COLOR);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Draw the game title
        g2.setFont(Constants.SECOND_FONT.deriveFont(Font.BOLD, 120F));
        String text = "JPokeBattle";
        int x = getXForCenteredText(text);
        int y = gp.tileSize * 4;

        // Draw a blue border around the game title
        g2.setColor(new Color(57, 95, 107));
        int borderSize = 9;
        for (int i = -borderSize; i <= borderSize; i++) {
            for (int j = -borderSize; j <= borderSize; j++) {
                g2.drawString(text, x + i, y + j);
            }
        }

        // Draw the main text in the center
        g2.setColor(new Color(255, 203, 5));
        g2.drawString(text, x, y);

        // Draw menu options
        g2.setColor(Color.black);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 36F));
        text = "play game";
        x = getXForCenteredText(text);
        y += gp.tileSize * 3;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "quit game";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        // Draw Pokémon images at the bottom corners
        int img1X = 50;
        int img1Y = gp.screenHeight - image1.getHeight() - 50;
        int img2X = gp.screenWidth - image2.getWidth() - 50;
        int img2Y = gp.screenHeight - image2.getHeight() - 50;

        g2.drawImage(image1, img1X, img1Y, null);
        g2.drawImage(image2, img2X, img2Y, null);
    }

    // Draws the dialogue screen with current dialogue and choices
    public void drawDialogueScreen() {
        // Draw the dialogue window
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 7;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;

        drawSubWindow(x, y, width, height);

        g2.setFont(Constants.FIRST_FONT.deriveFont(Font.PLAIN, 48F));
        int textX = x + gp.tileSize;
        int textY = y + gp.tileSize;

        // Draw the dialogue text
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // Draw dialogue choices
        int choiceY = y + height - gp.tileSize; // Adjust vertical positioning
        int choiceX = x + gp.tileSize * 2; // Fixed horizontal position

        String text = "Yes,  I'm  ready!  Let's  fight!";
        g2.drawString(text, choiceX, choiceY);
        if (dialogueChoice == 0) {
            g2.drawString(">", choiceX - gp.tileSize, choiceY);
        }

        text = "No,  I'm  not  ready  yet...";
        choiceY += 40; // Adjust spacing between choices
        g2.drawString(text, choiceX, choiceY);
        if (dialogueChoice == 1) {
            g2.drawString(">", choiceX - gp.tileSize, choiceY);
        }
    }

    // Draws the end screen showing the result and options to continue or quit
    public void drawEndScreen() {
        // Set background color and fill the screen
        g2.setColor(BACKGROUND_COLOR);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(Constants.SECOND_FONT.deriveFont(Font.BOLD, 120F));

        // Display "YOU WIN!" or "YOU LOSE!" based on the game's outcome
        String text = gp.winner ? "YOU WIN!" : "YOU LOSE!";
        int x = getXForCenteredText(text);
        int y = gp.tileSize * 4;

        g2.setColor(Color.BLACK);
        g2.drawString(text, x, y);

        // Draw end screen menu options
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 36F));
        text = "play again";
        x = getXForCenteredText(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);
        if (endChoice == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "quit battle";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (endChoice == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "quit game";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (endChoice == 2) {
            g2.drawString(">", x - gp.tileSize, y);
        }
    }

    // Draws the profile selection screen with profile options
public void drawProfileScreen() {
		
        // Setting white background
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Setting text color black
        g2.setColor(Color.BLACK);
        g2.setFont(Constants.SECOND_FONT.deriveFont(Font.BOLD, 36F));
        String text = "Select Profile:";
        int x = getXForCenteredText(text);
        int xBack = x;
        int y = gp.tileSize * 4;

        g2.drawString(text, x, y);

        // Draw profiles and check for "(empty)"
        for (int i = 0; i < 3; i++) {
            text = "Profile " + (i + 1);
            if (profilesEmpty[i]) {
                text += " (empty)"; // Adds "(empty)" if profile has not been played
            }
            x = getXForCenteredText(text);
            xBack = x;
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (profileChoice == i) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }

        // Draws "back" and ">" if selected
        text = "back";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (profileChoice == 3) {
            g2.drawString(">", xBack - gp.tileSize, y);
        }

        // Draws the images
        int img1X = 50;
        int img1Y = gp.screenHeight - image1.getHeight() - 50;

        int img2X = gp.screenWidth - image2.getWidth() - 50;
        int img2Y = gp.screenHeight - image2.getHeight() - 50;

        g2.drawImage(image1, img1X, img1Y, null);
        g2.drawImage(image2, img2X, img2Y, null);
    }

    // Draws a rounded sub-window with a border for dialogue and other UI elements
    public void drawSubWindow(int x, int y, int width, int height) {
        // Draw the main window with a semi-transparent black background
        Color c = new Color(0, 0, 0, 230);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        // Draw a white border around the window
        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }
}
