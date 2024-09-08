package map.logic;

import map.entity.NPC_Opponent;

public class AssetSetter {
	
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		
		this.gp = gp;
	}
	public void setNPC() {
		
		gp.npc[0] = new NPC_Opponent(gp);
		gp.npc[0].worldX = gp.tileSize*5;
		gp.npc[0].worldY = gp.tileSize*3;
	}
}