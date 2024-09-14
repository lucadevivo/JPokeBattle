package map.entity;

import map.logic.GamePanel;

/**
 * The NPC_Opponent class represents an opponent NPC in the game. 
 * It manages the NPC's image, movement, and dialogue interactions 
 * with the player.
 */
public class NPC_Opponent extends Entity {

	/**
     * Constructor that initializes the NPC_Opponent with the game panel reference
     * @param gp
     */
    public NPC_Opponent(GamePanel gp) {
        super(gp);
        // Set the initial direction and speed of the NPC
        direction = "down";
        speed = 0;

        // Load the NPC image and set its dialogue
        getImage();
        setDialogue();
    }

    // Method to set dialogues for the NPC
    public void setDialogue() {
        // Initial dialogue when interacting with the NPC
        dialogues[0] = "Well,  well,  look  who  we  have  here,  hehe!\nAre  you  ready  to  challenge  me?";
    }

    // Method to load the image of the NPC
    public void getImage() {
        // Load the standing image of the NPC
        standingNpc = setup("/npc/npcOpponent");
    }

    // Method to handle speaking interaction with the NPC
    public void speak() {
        // Display the current dialogue on the game's UI
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        // dialogueIndex++; // Uncomment this line to cycle through multiple dialogues if needed
    }
}
