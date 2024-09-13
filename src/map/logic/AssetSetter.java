package map.logic;

import map.entity.NPC_Opponent;

/**
 * The AssetSetter class is responsible for placing NPCs and other 
 * assets in the game world at specific locations.
 */
public class AssetSetter {
	
	GamePanel gp;
	/**
	 * Constructor to initialize the npc with the game panel
	 * @param gp
	 */
	public AssetSetter(GamePanel gp) {
		
		this.gp = gp;
	}
	public void setNPC() {
		
		gp.npc[0] = new NPC_Opponent(gp);
		gp.npc[0].worldX = gp.tileSize*5;
		gp.npc[0].worldY = gp.tileSize*3;
	}
}