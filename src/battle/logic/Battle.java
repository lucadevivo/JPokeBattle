package battle.logic;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * The Battle class represents a Pokémon battle between two trainers.
 */
public class Battle {

	private Trainer firstTrainer;
	private Trainer secondTrainer;
	private int chosenProfile;
	
	/**
     * Constructs a Battle with the specified trainers.
     * @param firstTrainer The first trainer.
     * @param secondTrainer The second trainer.
     */
	public Battle(Trainer firstTrainer, Trainer secondTrainer, int chosenPofile) {
		
		this.firstTrainer = firstTrainer;
		this.secondTrainer = secondTrainer;
		this.chosenProfile = chosenPofile;
		
	}
	
	/**
     * Executes a round of the battle where a trainer switches their Pokémon and makes a move.
     * @param trainer The trainer making the move.
     * @param pokemonSwitch The index of the Pokémon to switch in.
     * @param move The move to be executed.
     */
	
	public void round(Trainer trainer, int pokemonSwitch, int move) {
		
		// Determine the opponent trainer
		Trainer activeRound = (trainer == firstTrainer) ? secondTrainer : firstTrainer;
		
		// Switch the active Pokémon of the trainer
		trainer.switchPokemon(pokemonSwitch);
		
		// Trainer's active Pokémon attacks the opponent's active Pokémon
		trainer.getTeam()[0].attack(move, activeRound.getTeam()[0]);		
	}
	
	/**
     * Determines the outcome of the battle.
     * @return 0 if the battle is still ongoing, 1 if the first trainer wins, or 2 if the second trainer wins.
     */
	public int checkEndOfBattle() {
		
		// Check if the first trainer has no available Pokémon
		if (!firstTrainer.pokemonAvailable()) {		
	        return 2;	// Return 2, indicating the second trainer wins	        
	    } else if (!secondTrainer.pokemonAvailable()) {	    	  	
	    	return 1;	// Return 1, indicating the first trainer wins	    	
	    } else {	    	
	        return 0; // Return 0, indicating the battle is still ongoing
	    }		
	}
	
	public int endOfBattle() {
		
		// Check if the first trainer has no available Pokémon
		if (!firstTrainer.pokemonAvailable()) {
			
			secondTrainer.incrementWins(); // Increment the win count for the second trainer
			saveStats();	 // Save the current stats
	        return 2;	// Return 2, indicating the second trainer wins
	        
	    } else if (!secondTrainer.pokemonAvailable()) {
	    	
	    	firstTrainer.incrementWins(); // Increment the win count for the first trainer
	    	saveStats();	 // Save the current stats	    	
	    	return 1;	// Return 1, indicating the first trainer wins
	    	
	    } else {
	    	
	        return 0; // Return 0, indicating the battle is still ongoing
	    }
		
	}
	
	// Method to save stats to a file
    public void saveStats() {
    	
    	try (FileOutputStream fileOut = new FileOutputStream("res/profiles/profile_" + chosenProfile + ".ser");
    	         ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
    	        
    			// Serialize the first trainer
    	        out.writeObject(firstTrainer);
    	        
    	        // Serialize the second trainer
    	        out.writeObject(secondTrainer);
    	        
    	    } catch (IOException e) {
    	        e.printStackTrace();
    	    }
    	
    }

}

