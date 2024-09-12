package map.logic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

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

public class GamePanel extends JPanel implements Runnable, BattleObserver {

    private static final long serialVersionUID = 1L;

    // SCREEN SETTINGS
    final int originalTileSize = 32; // Base tile size 32x32
    final int scale = 2; // Scale factor for tile size
    public final int tileSize = originalTileSize * scale; // Final tile size 64x64
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // Screen width: 1024 pixels
    public final int screenHeight = tileSize * maxScreenRow; // Screen height: 768 pixels

    // WORLD SETTINGS
    public final int maxWorldRow = 11;
    public final int maxWorldCol = 11;

    // Frame per second setting
    int FPS = 60;

    // Game components and managers
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Sound sfxDoor = new Sound();
    Sound sfx = new Sound();
    Sound music = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public Entity interactingNpc = null;
    Thread gameThread;

    // ENTITY
    public Player player = new Player(this, keyH);
    public Entity npc[] = new Entity[10];

    // GAME STATE CONSTANTS
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
    public int chosenProfile = 0;

    // Constructor to set up the game panel
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setLayout(new BorderLayout());
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        // Start music in a separate thread
        MusicThread musicThread = new MusicThread();
        musicThread.start();
    }

    // Sets up the game by initializing NPCs and setting the initial game state
    public void setupGame() {
        aSetter.setNPC();
        gameState = titleState;
    }

    // Starts the game thread, which handles the game loop
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS; // Time per frame in nanoseconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        //int drawCount = 0;

        // Game loop
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update(); // Update game logic
                repaint(); // Redraw the screen
                delta--;
                //drawCount++;
            }
            if (timer >= 1000000000) {
                
            	//System.out.println("FPS: " + drawCount);
                //drawCount = 0;
                timer = 0;
            }
        }
    }

    // Update the game state, mainly updating the player position
    public void update() {
        player.update();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Optional debug feature to check draw time
        long drawStart = 0;
        if (keyH.checkDrawTime) {
            drawStart = System.nanoTime();
        }

        // Render different screens based on the current game state
        switch (gameState) {
            case titleState -> ui.draw(g2); // TITLE SCREEN
            case playState -> { // PLAY SCREEN
                tileM.draw(g2); // Draw tiles
                for (Entity npcEntity : npc) { // Draw NPCs
                    if (npcEntity != null) {
                        npcEntity.draw(g2);
                    }
                }
                player.draw(g2); // Draw player
            }
            case dialogueState -> { // DIALOGUE SCREEN
                tileM.draw(g2);
                for (Entity npcEntity : npc) {
                    if (npcEntity != null) {
                        npcEntity.draw(g2);
                    }
                }
                player.draw(g2);
                ui.draw(g2); // Draw UI
            }
            case battleState -> { // BATTLE SCREEN
                gameThread = null; // Stop the game thread during battle
                startBattle();
            }
            case endState, profileState -> ui.draw(g2); // END and PROFILE SCREENS
            default -> System.out.println(gameState);
        }

        // Display draw time if debugging
        if (keyH.checkDrawTime) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw time: " + passed, 10, 400);
            System.out.println("Draw Time: " + passed);
        }

        g2.dispose();
    }

    // Initiates a battle sequence between two trainers
public void startBattle() {
		
		Trainer firstTrainer = null;
	    Trainer secondTrainer = null;
	    
	    File file = new File("res/profiles/profile_" + chosenProfile + ".ser");
	    
	    if (file.exists()) {
	    	
	    	// If the file exists, deserialize the trainer
	        try (FileInputStream fileIn = new FileInputStream(file);
	             ObjectInputStream in = new ObjectInputStream(fileIn)) {
	            
	        	firstTrainer = (Trainer) in.readObject();
	        	secondTrainer = (Trainer) in.readObject();
	            
	        } catch (IOException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        
	        //Cycle on every Pokémon of both trainers and reset the current stats
	        for (Pokemon pokemon : firstTrainer.getTeam()) {
	            pokemon.resetStats();
	        }
	        for (Pokemon pokemon : secondTrainer.getTeam()) {
	            pokemon.resetStats();
	        }
	        
	    } else {
	    	
	    	// If the file does not exist, create new trainers
	    	firstTrainer = new Trainer(new Pokemon[] { new Squirtle(),  new Bulbasaur(), new Charmander()}, "Red");
	    	secondTrainer = new Trainer(new Pokemon[] { new Bulbasaur(), new Charmander(), new Squirtle()}, "Blue");
	    }
	    
        Battle battle = new Battle(firstTrainer, secondTrainer, chosenProfile);
        GameLogic gameLogic = new GameLogic(firstTrainer, secondTrainer, battle, null, null);
        
        gameLogic.setBattleObserver(this);
		
        BattlePanel battlePanel = new BattlePanel(firstTrainer, secondTrainer, 0, gameLogic, chosenProfile);
        battlePanel.setPreferredSize(new Dimension(screenWidth, screenHeight));
        battlePanel.setBackground(Color.LIGHT_GRAY);
        
        gameLogic.setGameObserver(battlePanel);
        
        removeAll();
        add(battlePanel, BorderLayout.CENTER); // Add battlePanel to the center
        revalidate(); // Trigger layout update
	}
    // Handles the end of a battle, determining the winner and setting the end game state
    @Override
    public void onBattleEnd(int i) {
        if (i == 1 || i == 2) {
            winner = (i == 1);
            gameState = endState;

            removeAll();
            revalidate();
            repaint();

            startGameThread(); // Restart the game thread after the battle
        }
    }

    // Plays background music based on the current game state
    public void playMusic(int i) {
        if (i != currentMusic) {
            music.stop();
            music.setFile(i);
            music.play();
            music.loop();
            currentMusic = i;
        }
    }

    // Plays non-looped background music
    public void playNonLoppedMusic(int i) {
        if (i != currentMusic) {
            music.stop();
            music.setFile(i);
            music.play();
            currentMusic = i;
        }
    }

    // A thread dedicated to managing background music based on the game's state
    class MusicThread extends Thread {
        @Override
        public void run() {
            while (true) {
                int newMusic = -1;

                // Determine which music track to play based on the current game state
                switch (gameState) {
                    case titleState, profileState -> newMusic = 0;
                    case playState, dialogueState -> newMusic = 1;
                    case battleState -> newMusic = 2;
                    case endState -> newMusic = winner ? 3 : 4;
                    default -> {}
                }

                // Play the selected music track
                if ((gameState == endState || gameState == titleState) && newMusic != currentMusic) {
                    playNonLoppedMusic(newMusic);
                } else if (newMusic != currentMusic) {
                    playMusic(newMusic);
                }

                // Sleep to periodically check for state changes
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
}