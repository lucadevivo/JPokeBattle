package map.entity;

import map.logic.GamePanel;

public class NPC_Opponent extends Entity{

	public NPC_Opponent(GamePanel gp) {
		
		super(gp);
		direction = "down";
		speed = 0;
		
		getImage();
		setDialogue();
	}
	public void setDialogue() {
		
		dialogues[0] = "Guarda un po' chi si vede, haha!\nSei pronto a sfidarmi?";
	}
	public void getImage() {
		
		standingNpc = setup("/npc/npcOpponent");
	}
	public void speak() {
		
		gp.ui.currentDialogue = dialogues[dialogueIndex];
		// dialogueIndex++; questo serve se vogliamo aggiungere altri dialoghi
	}
}