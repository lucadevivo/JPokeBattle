package map.logic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import battle.graphics.BattlePanel;
import battle.logic.Battle;
import battle.logic.BattleObserver;
import battle.logic.GameLogic;
import battle.logic.Pokemon;
import battle.logic.Trainer;
import battle.pokemons.Bulbasaur;
import battle.pokemons.Charmander;
import battle.pokemons.Squirtle;
import map.entity.Entity;
import map.entity.Player;
import map.tile.TileManager;

public class GamePanel extends JPanel implements Runnable, BattleObserver{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//SCREEN SETTINGS
	final int originalTileSize = 32; // 32*32 tile
	final int scale = 2;
	
	public final int tileSize = originalTileSize * scale; // 64*64 tile
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 1024 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 768 pixels
	
	// WORLD SETTINGS
	public final int maxWorldRow = 11;
	public final int maxWorldCol = 11;
	
	//Frame per second:
	int FPS	= 60;
	
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler(this);
	Sound sfxDoor = new Sound();
	Sound sfx = new Sound();
	Sound music = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	public Entity interactingNpc = null; // Add this line
	Thread gameThread;
	
	// ENTITY 
	public Player player = new Player(this,keyH);
	public Entity npc[] = new Entity[10];
	
	//GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int dialogueState = 2;
	public final int optionState = 3;
	public final int battleState = 4;
	public final int endState = 5;
	public final int profileState = 6;
	
	
	public boolean winner = false;
	private int currentMusic = -1;
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setLayout(new BorderLayout());
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
		MusicThread musicThread = new MusicThread();
		musicThread.start();
	}
	public void setupGame() {
		
		aSetter.setNPC();
		gameState = titleState;
	}
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
	}
	@Override
	public void run() {
		
		double drawInterval= 1000000000/FPS; //0.0166666... seconds
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta>=1) {
				
				// 1. UPDATE: update information such as character positions
				update();
				// 2. DRAW: draw the screen with the updated information
				repaint();
				delta--;
				drawCount++;
			}
			if(timer>=1000000000) {
				System.out.println("FPS: "+drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
	}
	public void update() {		
		player.update();
		
	}
	public void paintComponent(Graphics g) {
		
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		//DEBUG
		long drawStart = 0;
		if(keyH.checkDrawTime == true) {
			
			drawStart = System.nanoTime();
		}
		// TITLE SCREEN
		if(gameState == titleState) {
			
			ui.draw(g2);
		}
		// PLAY SCREEN
		else if (gameState == playState){
			
			// TILE
			tileM.draw(g2);
			
			//NPC
			for(int i = 0; i < npc.length; i++) {
				
				if(npc[i] != null) {
					
					npc[i].draw(g2);
				}
			}
			// PLAYER
			player.draw(g2);
		}
		// DIALOGUE SCREEN
		else if (gameState == dialogueState) {
			
			// TILE
			tileM.draw(g2);
			
			//NPC
			for(int i = 0; i < npc.length; i++) {
				
				if(npc[i] != null) {
					
					npc[i].draw(g2);
				}
			}
			// PLAYER
			player.draw(g2);
			
			//UI
			ui.draw(g2);			
		}
		// BATTLE SCREEN
		else if (gameState == battleState){
						
			gameThread = null;
			startBattle();
		}
		// END SCREEN
		else if (gameState == endState) {
			
	        ui.draw(g2);
		}
		// PROFILE SCREEN
		else if (gameState == profileState) {
			
			ui.draw(g2);
		}
		else {System.out.println(gameState);}
		
		if(keyH.checkDrawTime == true) {
			
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setColor(Color.white);
			g2.drawString("Draw time: "+passed, 10, 400);
			System.out.println("Draw Time: "+passed);
		}
		g2.dispose();
	}
	public void startBattle() {
		
		Trainer primoAllenatore = new Trainer(new Pokemon[]{new Squirtle() /*, new Bulbasaur(), new Charmander()*/}, "Red");
		Trainer secondoAllenatore = new Trainer(new Pokemon[]{new Bulbasaur()/*, new Charmander(), new Squirtle()*/}, "Blue");
        Battle scontro = new Battle(primoAllenatore, secondoAllenatore);
        GameLogic gameLogic = new GameLogic(primoAllenatore, secondoAllenatore, scontro, null, null);
        
        gameLogic.setBattleObserver(this);
		
        BattlePanel battlePanel = new BattlePanel(primoAllenatore, secondoAllenatore, 0, gameLogic);
        battlePanel.setPreferredSize(new Dimension(screenWidth, screenHeight));
        battlePanel.setBackground(Color.LIGHT_GRAY);
        
        gameLogic.setGameObserver(battlePanel);
        
        removeAll();
        add(battlePanel, BorderLayout.CENTER); // Add battlePanel to the center
        revalidate(); // Trigger layout update
	}

	@Override
	public void onBattleEnd(int i) {
		
	    if (i == 1 || i == 2) {
	    	
	        winner = (i == 1);
	        gameState = endState;

	        removeAll();
	        revalidate();
	        repaint();
	        
	        startGameThread();
	    }
	}
	public void playMusic(int i) {
		
	    if (i != currentMusic) {
	    	
	        music.stop();
	        music.setFile(i);
	        music.play();
	        music.loop();
	        currentMusic = i;
	    }
	}
	public void playNonLoppedMusic(int i) {
		
	    if (i != currentMusic) {
	    	
	        music.stop();
	        music.setFile(i);
	        music.play();
	        currentMusic = i;
	    }
	}
	class MusicThread extends Thread {
		
	    @Override
	    public void run() {
	    	
	        while (true) {
	        	
	            int newMusic = -1;
	            
	            switch (gameState) {
	            
	                case titleState:
	                case profileState:
	                	newMusic = 0;
	                    break;
	                case playState:
	                case dialogueState:
	                	
	                    newMusic = 1;
	                    break;
	                    
	                case battleState:
	                	
	                    newMusic = 2;
	                    break;
	                    
	                case endState:
	                	
	                    if (winner) {
	                        newMusic = 3;
	                    } else {
	                        newMusic = 4;
	                    }
	                    break;
	                    
	                default:
	                    break;
	            }
	            
	            if ((gameState == endState || gameState == titleState) && newMusic != currentMusic) {
	                playNonLoppedMusic(newMusic);
	            } else if (newMusic != currentMusic) {
	                playMusic(newMusic);
	            }
	            
	            try {
	                Thread.sleep(500); // Controlla lo stato ogni secondo
	            } catch (InterruptedException e) {
	                break; // Interrompe il ciclo se il thread viene interrotto
	            }
	        }
	    }
	}
}