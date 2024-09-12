package battle.graphics;

import java.awt.Color;
import javax.swing.JPanel;
import battle.logic.GameLogic;
import battle.logic.GameObserver;
import battle.logic.Trainer;

/**
 * The BattlePanel class represents the main panel where the battle interface is displayed.
 * It includes the images and stats of the Pokémon, as well as text messages related to the battle.
 */
public class BattlePanel extends JPanel implements GameObserver{
	
	private static final long serialVersionUID = 1L;

	// Panels for displaying text and images
	JLayeredTextPanel textJLayeredPanel;
	
	PokemonImagePanel firstPokemonImage;
	PokemonImagePanel secondPokemonImage;
	
	PokemonStatsPanel firstPokemonStats;
	PokemonStatsPanel secondPokemonStats;
	
	//	Panel indicating the current shift
	ShiftSpecifier shiftSpecifier;
	
	// Panel that shows the current profile and the games won by each player
	GameInfo gameInfo;
	
	// Keeps track of the previous trainer's turn
	int pastTrainer = 0;
	
	// Field that specifies which profile I am in
	int chosenProfile = 0;
	
	
	/**
     * Constructs a BattlePanel with the given trainers and game logic.
     * @param firstTrainer the first trainer
     * @param secondTrainer the second trainer
     * @param wichTrainer the index of the current trainer
     * @param gameLogic the game logic instance
     * @param chosenProfile number that corresponds to the chosen profile
     */
	public BattlePanel(Trainer firstTrainer, Trainer secondTrainer, int wichTrainer, GameLogic gameLogic, int chosenProfile){
		
		
		this.setBackground(Color.LIGHT_GRAY);
		this.setLayout(null);
		
		this.chosenProfile = chosenProfile;
		
		// Initialize the game info panel
		gameInfo = new GameInfo(chosenProfile, firstTrainer.getName(), secondTrainer.getName(), firstTrainer.getWins(), secondTrainer.getWins());
		gameInfo.setBounds(0, 0, (int)(Constants.PANEL_WIDTH * 0.17), (int) (Constants.PANEL_HEIGHT * 0.11));
		
		// Initialize the game info panel
		shiftSpecifier = new ShiftSpecifier(firstTrainer.getName(), Color.RED);
		shiftSpecifier.setBounds(Constants.PANEL_WIDTH - 150, (int) ((Constants.PANEL_HEIGHT / 3)*2 - 30), 150, 30);
		
		// Initialize text panel
		textJLayeredPanel = new JLayeredTextPanel(firstTrainer, gameLogic);	
		textJLayeredPanel.setBounds(0, (int) ((Constants.PANEL_HEIGHT / 3)*2), Constants.PANEL_WIDTH, (int) (Constants.PANEL_HEIGHT / 3));
		
		// Initialize Pokémon image panels
		firstPokemonImage = new PokemonImagePanel(firstTrainer.getTeam()[0], "pokemonbacks");
		firstPokemonImage.setBounds(0, (int)((Constants.PANEL_HEIGHT)*0.4), (int)((Constants.PANEL_WIDTH)*0.45), (int)((Constants.PANEL_HEIGHT)*0.27));
				
		secondPokemonImage = new PokemonImagePanel(secondTrainer.getTeam()[0], "pokemonfronts");
		secondPokemonImage.setBounds((int)((Constants.PANEL_WIDTH)*0.55), 0, (int)((Constants.PANEL_WIDTH)*0.45), (int)((Constants.PANEL_WIDTH)*0.4));
		
		// Initialize Pokémon stats panels
		firstPokemonStats = new PokemonStatsPanel(firstTrainer.getTeam()[0]);
		firstPokemonStats.setBounds((int)((Constants.PANEL_WIDTH)*0.45), (int)((Constants.PANEL_HEIGHT)*0.4), (int)((Constants.PANEL_WIDTH)*0.55), (int)((Constants.PANEL_HEIGHT)*0.27));

		secondPokemonStats = new PokemonStatsPanel(secondTrainer.getTeam()[0]);
		secondPokemonStats.setBounds(0, 0, (int)((Constants.PANEL_WIDTH)*0.55), (int)((Constants.PANEL_HEIGHT)*0.4));
         
        
		
        // Add components to the panel
		this.add(firstPokemonImage);
		this.add(secondPokemonImage);
		
		this.add(firstPokemonStats);
		this.add(secondPokemonStats);
			
		this.add(textJLayeredPanel);
		
		this.add(shiftSpecifier);
		this.add(gameInfo);
		
		
		
	}
	
	/**
     * method that overrides the gameObserver interface method, when called, in turn calls the update method
     * @param firstTrainer the first trainer
     * @param secondTrainer the second trainer
     * @param wichTrainer the index of the current trainer
     */
	@Override
	public void onGameUpdate(Trainer firstTrainer, Trainer secondTrainer, int wichTrainer) {
		
		update(firstTrainer, secondTrainer, wichTrainer);
		
	}
	/**
     * Updates the images, stats, and text in the battle panel.
     * @param firstTrainer the first trainer
     * @param secondTrainer the second trainer
     * @param wichTrainer the index of the current trainer
     */
	public void update(Trainer firstTrainer, Trainer secondTrainer, int wichTrainer) {
		
		
		firstPokemonImage.updateImage(firstTrainer.getTeam()[0]);
		firstPokemonStats.updateStats(firstTrainer.getTeam()[0]);
        
		secondPokemonImage.updateImage(secondTrainer.getTeam()[0]);
		secondPokemonStats.updateStats(secondTrainer.getTeam()[0]);
		
		gameInfo.update();
		
		// Update the text panel and the shift specifier based on the current trainer
		if (wichTrainer == 0) {
			
			shiftSpecifier.updateName(firstTrainer.getName(), Color.RED);
			textJLayeredPanel.updateBox(firstTrainer); 			
			if (wichTrainer != pastTrainer) {textJLayeredPanel.updateText(firstTrainer, secondTrainer);}
		}
		else {
			
			shiftSpecifier.updateName(secondTrainer.getName(), Color.BLUE);
			textJLayeredPanel.updateBox(secondTrainer);
		}
			
		pastTrainer = wichTrainer;
	}
}

