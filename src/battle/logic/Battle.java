package battle.logic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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
			saveStats();
	        return 2;	// Second trainer wins
	    } else if (!secondTrainer.pokemonAvailable()) {
	    	saveStats();
	    	return 1;	// First trainer wins
	    } else {
	        return 0;	// Battle is still ongoing
	    }
		
	}
	
	// Method to save stats to a file
    public void saveStats() {
    	
    	try (BufferedWriter writer = new BufferedWriter(new FileWriter("res/profiles/battle_one.txt"))) {

            writer.write(firstTrainer.getAllAttributes());
            writePokemonStats(writer, firstTrainer.getTeam());

            writer.write(secondTrainer.getAllAttributes());
            writePokemonStats(writer, secondTrainer.getTeam());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to write Pokémon stats
    private void writePokemonStats(BufferedWriter writer, Pokemon[] team) throws IOException {
        for (Pokemon pokemon : team) {
            writer.write(pokemon.getAllAttributes());
        }
    }
}

