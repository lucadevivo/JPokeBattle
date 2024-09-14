package map.logic;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The KeyHandler class is created to handle the different game states. It maps specific
 *  key presses to game actions and updates the state of the  game accordingly.
 */
public class KeyHandler implements KeyListener {

    GamePanel gp; // Reference to the main game panel
    public boolean upPressed, downPressed, leftPressed, rightPressed; // Directional key states
    // DEBUG
    boolean checkDrawTime = false; // Toggle for checking draw time

    /**
     * Constructor initializes the KeyHandler with a reference to the GamePanel
     * @param gp
     */
    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used in this implementation
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // Handle key presses based on the current game state
        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            if (code == KeyEvent.VK_W) { // Move up in the menu
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) gp.ui.commandNum = 0; // Clamp to the first option
            }
            if (code == KeyEvent.VK_S) { // Move down in the menu
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 1) gp.ui.commandNum = 1; // Clamp to the last option
            }
            if (code == KeyEvent.VK_ENTER) { // Confirm selection
                if (gp.ui.commandNum == 0) {
                    // Start the game
                    gp.gameState = gp.profileState;
                }
                if (gp.ui.commandNum == 1) {
                    // Exit the program
                    System.exit(0);
                }
            }
        }
        // PLAY STATE
        else if (gp.gameState == gp.playState) {
            // Movement keys
            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            // Pause game or open options
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.optionState;
            }
            // DEBUG: Toggle draw time display
            if (code == KeyEvent.VK_T) {
                checkDrawTime = !checkDrawTime;
            }
        }
        // DIALOGUE STATE
        else if (gp.gameState == gp.dialogueState) {
            if (code == KeyEvent.VK_W) {
                gp.ui.dialogueChoice--;
                if (gp.ui.dialogueChoice < 0) gp.ui.dialogueChoice = 0; // Clamp to the first choice
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.dialogueChoice++;
                if (gp.ui.dialogueChoice > 1) gp.ui.dialogueChoice = 1; // Clamp to the last choice
            }
            if (code == KeyEvent.VK_ENTER) { // Confirm dialogue choice
                if (gp.ui.dialogueChoice == 0) {
                    // Start battle
                    gp.gameState = gp.battleState;
                } else {
                    // Return to play state
                    gp.gameState = gp.playState;
                }
            }
        }
        // END STATE
        else if (gp.gameState == gp.endState) {
            if (code == KeyEvent.VK_W) { // Move up in the end menu
                gp.ui.endChoice--;
                if (gp.ui.endChoice < 0) gp.ui.endChoice = 0; // Clamp to the first option
            }
            if (code == KeyEvent.VK_S) { // Move down in the end menu
                gp.ui.endChoice++;
                if (gp.ui.endChoice > 2) gp.ui.endChoice = 2; // Clamp to the last option
            }
            if (code == KeyEvent.VK_ENTER) { // Confirm end choice
                if (gp.ui.endChoice == 0) {
                    // Start battle again
                    gp.gameState = gp.battleState;
                } else if (gp.ui.endChoice == 1) {
                    // Return to play state
                    gp.gameState = gp.playState;
                } else {
                    // Exit the program
                    System.exit(0);
                }
            }
        }
        // PROFILE STATE
        else if (gp.gameState == gp.profileState) {
            if (code == KeyEvent.VK_W) { // Move up in profile selection
                gp.ui.profileChoice--;
                if (gp.ui.profileChoice < 0) {
                    gp.ui.profileChoice = 0; // Clamp to the first profile
                }
            }
            if (code == KeyEvent.VK_S) { // Move down in profile selection
                gp.ui.profileChoice++;
                if (gp.ui.profileChoice > 3) {
                    gp.ui.profileChoice = 3; // Clamp to the last profile
                }
            }
            if (code == KeyEvent.VK_ENTER) { // Confirm profile selection
                int selectedOption = gp.ui.profileChoice;

                if (selectedOption == 3) {
                    // Return to title state
                    gp.gameState = gp.titleState;
                } else {
                    // Select profile and start the game
                    gp.chosenProfile = selectedOption;
                    gp.gameState = gp.playState;
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        // Reset movement key states when keys are released
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}