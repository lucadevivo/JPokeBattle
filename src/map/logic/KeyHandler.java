package map.logic;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import battle.logic.GestoreProfili;
import battle.logic.ProfiloUtente;

public class KeyHandler implements KeyListener{

	GamePanel gp;
	public boolean upPressed, downPressed, leftPressed, rightPressed;
	//DEBUG
	boolean checkDrawTime = false;
	
	public KeyHandler(GamePanel gp) {
		
		this.gp = gp;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	@SuppressWarnings("unused")
	@Override
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
			
		// TITLE STATE
		if(gp.gameState == gp.titleState) {
			
			if(code == KeyEvent.VK_W) {
				
				gp.ui.commandNum--;
				if(gp.ui.commandNum < 0) gp.ui.commandNum = 0;
			}
			if(code == KeyEvent.VK_S) {
				
				gp.ui.commandNum++;
				if(gp.ui.commandNum > 2) gp.ui.commandNum = 2;
			}
			if(code == KeyEvent.VK_ENTER) {
				
				if(gp.ui.commandNum == 0) {
					
					// play!
					gp.gameState = gp.playState;
				}
				if(gp.ui.commandNum == 1) {
					
					gp.gameState = gp.profileState;
				}
				if(gp.ui.commandNum == 2) {
					
					// close program
					System.exit(0);
				}
			}
		}
		// PLAY STATE
		else if(gp.gameState == gp.playState) {
			
			if(code == KeyEvent.VK_W) {
				
				upPressed=true;
			}
			if(code == KeyEvent.VK_S) {
				
				downPressed=true;
			}
			if(code == KeyEvent.VK_A) {
				
				leftPressed=true;
			}
			if(code == KeyEvent.VK_D) {
				
				rightPressed=true;
			}
			if(code == KeyEvent.VK_ESCAPE) {
				
				gp.gameState = gp.optionState;
			}
			//DEBUG
			if(code == KeyEvent.VK_T) {
				
				if(checkDrawTime == false) {
					
					checkDrawTime = true;
				}
				else if(checkDrawTime == true) {
					
					checkDrawTime = false;
				}
			}
		}
		// DIALOGUE STATE
		else if(gp.gameState == gp.dialogueState) {
			
			if(code == KeyEvent.VK_W) {
				
				gp.ui.dialogueChoice--;
				if(gp.ui.dialogueChoice < 0) gp.ui.dialogueChoice = 0;
			}
			if(code == KeyEvent.VK_S) {
				
				gp.ui.dialogueChoice++;
				if(gp.ui.dialogueChoice > 1) gp.ui.dialogueChoice = 1;
			}
			
			if(code == KeyEvent.VK_ENTER) {
				
				if (gp.ui.dialogueChoice == 0) {
					
					gp.gameState = gp.battleState;
				}
				else {
					
					gp.gameState = gp.playState;
				}
			}
		}
		// END STATE
		else if(gp.gameState == gp.endState) {
			
			if(code == KeyEvent.VK_W) {
				
				gp.ui.endChoice--;
				if(gp.ui.endChoice < 0) gp.ui.endChoice = 0;
			}
			if(code == KeyEvent.VK_S) {
				
				gp.ui.endChoice++;
				if(gp.ui.endChoice > 2) gp.ui.endChoice = 2;
			}
			
			if(code == KeyEvent.VK_ENTER) {
				
				if (gp.ui.endChoice == 0) {
					
					gp.gameState = gp.battleState;
				}
				else if (gp.ui.endChoice == 1){
					
					gp.gameState = gp.playState;
				}
				else {
					System.exit(0);
				}
			}
		}
		// PROFILE STATE
		else if (gp.gameState == gp.profileState) {

            List<ProfiloUtente> profili = GestoreProfili.caricaProfili();
            int numeroProfili = profili.size();

            if (code == KeyEvent.VK_W) {
            	
                gp.ui.profileChoice--;
                if (gp.ui.profileChoice < 0) {
                	
                    gp.ui.profileChoice = numeroProfili; // Loop alla fine
                }
            }
            if (code == KeyEvent.VK_S) {
            	
                gp.ui.profileChoice++;
                if (gp.ui.profileChoice > 3) {
                	
                    gp.ui.profileChoice = 3;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
            	
            	
                int selectedOption = gp.ui.profileChoice;
                
                if (selectedOption == 3) {
                    gp.gameState = gp.titleState;
                } else {
                    ProfiloUtente profiloSelezionato = profili.get(selectedOption);
                    gp.gameState = gp.playState;
                    // Esegui altre azioni necessarie con il profilo selezionato
                }
            }
        }
    }
	@Override
	public void keyReleased(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			
			upPressed=false;
		}
		if(code == KeyEvent.VK_S) {
			
			downPressed=false;
		}
		if(code == KeyEvent.VK_A) {
			
			leftPressed=false;
		}
		if(code == KeyEvent.VK_D) {
			
			rightPressed=false;
		}
	}
}