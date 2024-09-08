package battle.logic;

/**
 * The Battle class represents a Pokémon battle between two trainers.
 */
public class Battle {

	private Trainer firstTrainer;
	private Trainer secondTrainer;
	
	/**
     * Constructs a Battle with the specified trainers.
     * @param firstTrainer The first trainer.
     * @param secondTrainer The second trainer.
     */
	public Battle(Trainer firstTrainer, Trainer secondTrainer) {
		
		this.firstTrainer = firstTrainer;
		this.secondTrainer = secondTrainer;
		
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
	public int endOfBattle() {
		
		if (!firstTrainer.pokemonAvailable()) {
	        return 2;	// Second trainer wins
	    } else if (!secondTrainer.pokemonAvailable()) {
	    	return 1;	// First trainer wins
	    } else {
	        return 0;	// Battle is still ongoing
	    }
		
	}
}
