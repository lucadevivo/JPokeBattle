package battle.logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class implements the ActionListener interface to handle events when a Pokémon button is clicked.
 * It is responsible for managing the selection and changing of the Pokémon in the game.
 */
public class PokemonButtonListener implements ActionListener {
	
    private int pokemonIndex;
    private GameLogic gameLogic;
    private Trainer trainer;
       
    /**
     * Constructor for the PokemonButtonListener.
     * 
     * @param pokemonIndex The index of the Pokémon in the trainer's team.
     * @param gameLogic The game logic instance to interact with.
     * @param trainer The trainer who owns the Pokémon.
     */
    public PokemonButtonListener(int pokemonIndex, GameLogic gameLogic, Trainer trainer) {
    	
        this.pokemonIndex = pokemonIndex;
        this.gameLogic = gameLogic;
        this.trainer = trainer;
    }
    /**
     * Sets a new trainer for this listener.
     * @param newTrainer The new trainer to be set.
     */
    public void setTrainer(Trainer newTrainer) {
    	
    	trainer = newTrainer;
    	
    }
    /**
     * Invoked when an action occurs. This method is called when the Pokémon button is clicked.
     * It changes the Pokémon if it has more than 0 HP.
     * 
     * @param e The event that occurred.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (trainer.getTeam()[pokemonIndex].getHp() != 0) {
            gameLogic.changePokemon(pokemonIndex);
        } else {
            System.out.println("This Pokémon cannot be selected, it has 0 HP");
        }
    }

}
