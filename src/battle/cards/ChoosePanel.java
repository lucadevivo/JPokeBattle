package battle.cards;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import battle.graphics.Constants;
import battle.logic.*;

/**
 * The ChoosePanel class is a custom JPanel that displays buttons for selecting moves and Pokémon to change.
 * Use the trainer object to dynamically create buttons and notify gameLogic via buttonListeners
 */

public class ChoosePanel extends JPanel {
    
	private static final long serialVersionUID = 1L;
	
	int numButtonsMoves;
    int numButtonsPokemons;
    
    List<JButton> buttonsMoves;	// List to hold move buttons
    List<JButton> buttonsPokemons;	// List to hold Pokémon buttons
    
    List<MoveButtonListener> moveButtonListeners;	// List to hold listeners for move buttons
    List<PokemonButtonListener> pokemonButtonListeners;	// List to hold listeners for Pokémon buttons
    
    Trainer trainer;
    GameLogic gameLogic;
    
    /**
     * Constructs a ChoosePanel with the given trainer and game logic.
     * @param allenatore the trainer
     * @param gameLogic the game logic
     */
    
    public ChoosePanel(Trainer trainer, GameLogic gameLogic) {
    	
    	this.trainer = trainer;
        
        numButtonsMoves = trainer.getTeam()[0].getMoves().size();
        numButtonsPokemons = trainer.getTeam().length - 1;
        
        buttonsMoves = new ArrayList<>();
        buttonsPokemons = new ArrayList<>();
        
        moveButtonListeners = new ArrayList<>();
        pokemonButtonListeners = new ArrayList<>();
        
        this.gameLogic = gameLogic;
        
        
        // Initialize Pokémon buttons
        for (int i = 1; i < trainer.getTeam().length; i++) {
        	
            JButton buttonPokemon = new JButton(trainer.getTeam()[i].getName());
            buttonPokemon.setBackground(Constants.BACKGROUND_COLOR);
            buttonPokemon.setFont(new Font(Constants.FIRST_FONT.getName(), Font.PLAIN, 30));
            buttonPokemon.setFocusable(false);
            
            PokemonButtonListener pokemonButtonListener = new PokemonButtonListener(i, gameLogic, trainer);
            
            buttonPokemon.addActionListener(pokemonButtonListener);
            
            if (trainer.getTeam()[i].getHp() == 0) { 
                buttonPokemon.setEnabled(false);           
            }
            
            pokemonButtonListeners.add(pokemonButtonListener);           
            buttonsPokemons.add(buttonPokemon);
            this.add(buttonPokemon);
        }
        
        
        // Initialize move buttons
        for (int i = 0; i < numButtonsMoves; i++) {
        	
            JButton buttonMove = new JButton(trainer.getTeam()[0].getMoves().get(i));
            buttonMove.setBackground(Constants.BACKGROUND_COLOR);
            buttonMove.setFont(new Font(Constants.FIRST_FONT.getName(), Font.PLAIN, 30));
            buttonMove.setFocusable(false);
            
            MoveButtonListener moveButtonListener = new MoveButtonListener(i, gameLogic, trainer);
            
            buttonMove.addActionListener(moveButtonListener);
            moveButtonListeners.add(moveButtonListener);
            
            buttonsMoves.add(buttonMove);
            this.add(buttonMove);
        }
        
        this.setBackground(Constants.BACKGROUND_COLOR);
    }
    
    
    /**
     * Override the paintComponent method to arrange buttons dynamically based on panel size and on the number of buttons.
     * @param g2 the Graphics object
     */
    
    @Override
    protected void paintComponent(Graphics g2) {
        super.paintComponent(g2);
        
        
        // Arrange move buttons
        for (int i = 0; i < numButtonsMoves; i++) {
            JButton buttonMove = buttonsMoves.get(i);
            int buttonWidth = (int)(getWidth() / numButtonsMoves);
            buttonMove.setBounds(buttonWidth * i, 0, buttonWidth, (int)(getHeight() / 2));
        }
        
        // Arrange Pokémon buttons
        for (int i = 0; i < numButtonsPokemons; i++) {
            JButton buttonPokemon = buttonsPokemons.get(i);
            int buttonWidth = (int)(getWidth() / numButtonsPokemons);
            buttonPokemon.setBounds(buttonWidth * i, (int)(getHeight() / 2), buttonWidth, (int)(getHeight() / 2));
        }
    }
    
    
    /**
     * Updates the buttons based on the new trainer's Pokémon and moves.
     * @param nuovoAllenatore the new trainer
     */
    
    public void updateButtons(Trainer newTrainer) {
    	
        
    	numButtonsMoves = newTrainer.getTeam()[0].getMoves().size();
        numButtonsPokemons = newTrainer.getTeam().length - 1;
        
        
        // Update Pokémon buttons
        for (int i = 0; i < numButtonsPokemons; i++) {
        	
            JButton buttonPokemon = buttonsPokemons.get(i);
            buttonPokemon.setText(newTrainer.getTeam()[i+1].getName());
            
            if (newTrainer.getTeam()[i+1].getHp() == 0) {
                buttonPokemon.setEnabled(false);  
            } else {
                buttonPokemon.setEnabled(true);
            }
        }
        
        int newMovesCount = newTrainer.getTeam()[0].getMoves().size();

        // Remove existing move buttons
        for (JButton button : buttonsMoves) {
            this.remove(button);
            
            }
        
        buttonsMoves.clear();
        moveButtonListeners.clear();

        // Add new move buttons
        for (int i = 0; i < newMovesCount; i++) {
        	
            JButton buttonMove = new JButton(newTrainer.getTeam()[0].getMoves().get(i));
            buttonMove.setBackground(Constants.BACKGROUND_COLOR);
            buttonMove.setFont(new Font(Constants.FIRST_FONT.getName(), Font.PLAIN, 30));
            buttonMove.setFocusable(false);

            MoveButtonListener moveButtonListener = new MoveButtonListener(i, gameLogic, newTrainer);
            buttonMove.addActionListener(moveButtonListener);
            moveButtonListeners.add(moveButtonListener);

            buttonsMoves.add(buttonMove);
            this.add(buttonMove);
        }

    
        // Update listeners with the new trainer
        for (MoveButtonListener listener : moveButtonListeners) {
            listener.setTrainer(newTrainer);
        }

        for (PokemonButtonListener listener : pokemonButtonListeners) {
            listener.setTrainer(newTrainer);
        }
        
        this.revalidate();
        this.repaint();
    }
}