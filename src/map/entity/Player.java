package map.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import map.logic.GamePanel;
import map.logic.KeyHandler;

public class Player extends Entity{
	
	KeyHandler keyH;
	
	private String standing_direction;
	
	public final int screenX, screenY;
	
	private void setStandingDirection(String direction) {
		
		standing_direction = direction;
	}
	public Player(GamePanel gp, KeyHandler keyH) {
		
		super(gp);
		this.keyH = keyH;
		
		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
		
		solidArea = new Rectangle();
		solidArea.x = 6;
		solidArea.y = 28;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 50;
		solidArea.height = 34;
		
		setDefaultValues();
		getPlayerImage();
	}
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 5;
		worldY = gp.tileSize * 6;
		
		speed = 4;
		
		//ANY DIRECTION IS FINE
		direction = "up";
	}
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
	public BufferedImage getStandingImage() {
		
		switch(standing_direction) {
			
		case "up": return standUp;
		case "down": return standDown;
		case "left": return standLeft;
		case "right": return standRight;
		
		default: return standDown;
		}
	}
	public void update() {
		
		if(keyH.upPressed==true || keyH.downPressed==true || 
			keyH.leftPressed == true || keyH.rightPressed == true) {
			
			if(keyH.upPressed == true) {
				
				direction = "up";
			}
			else if(keyH.downPressed==true) {
				
				direction="down";
			}
			else if(keyH.leftPressed==true) {
				
				direction="left";
			}
			else if(keyH.rightPressed==true) {
				
				direction="right";
			}
			//CHECK TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			//CHECK NPC COLLISION
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			// IF COLLISION IS FALSE, PLAYER CAN MOVE
			if(collisionOn == false) {
				
				switch(direction) {
				
				case "up": worldY -= speed;
					break;
					
				case "down": worldY += speed;
					break;
					
				case "left": worldX -= speed;
					break;
					
				case "right": worldX += speed;
					break;
				}
			}
			spriteCounter++;
			if(spriteCounter>10) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}
				else if(spriteNum == 2) {
					
					spriteNum = 3;
				}
				else if(spriteNum == 3) {
					
					spriteNum = 4;
				}
				else if (spriteNum == 4) {
					
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
		if(!keyH.upPressed && !keyH.downPressed &&
			!keyH.rightPressed && !keyH.leftPressed) {
					
				setStandingDirection(direction);	
			}
	}
	public void interactNPC(int i) {
		
		if(i != 999) {
			
			gp.gameState = gp.dialogueState;
			gp.npc[i].speak();
		}
	}
	public void draw(Graphics2D g2) {
	
		BufferedImage image = null;
		
		if(keyH.upPressed==true || keyH.downPressed==true || 
			keyH.leftPressed == true || keyH.rightPressed == true) {
			
			switch(direction) {
			
			case "up":
				if(spriteNum == 1) {
					
					image = up1;
				}
				if (spriteNum == 2) {
					
					image = standUp;
				}
				if (spriteNum == 3) {
					
					image = up2;
				}
				if (spriteNum == 4) {
					
					image = standUp;
				}
				break;
			case "down":
				if(spriteNum == 1) {
					
					image = down1;
				}
				if (spriteNum == 2) {
					
					image = standDown;
				}
				if (spriteNum == 3) {
					
					image = down2;
				}
				if (spriteNum == 4) {
					
					image = standDown;
				}
				break;
			case "left":
				if (spriteNum == 1) {
					
					image = left;
				}
				if (spriteNum == 2) {
					
					image = standLeft;
				}
				if (spriteNum == 3) {
					
					image = left;
				}
				if (spriteNum == 4) {
					
					image = standLeft;
				}
				break;
			case "right":
				if (spriteNum == 1) {
					
					image = right;
				}
				if (spriteNum == 2) {
					
					image = standRight;
				}
				if (spriteNum == 3) {
					
					image = right;
				}
				if (spriteNum == 4) {
					
					image = standRight;
				}
				break;
			}
		}
		else {
			
			image = getStandingImage();
		}
		g2.drawImage(image, screenX, screenY, null);
	}
}