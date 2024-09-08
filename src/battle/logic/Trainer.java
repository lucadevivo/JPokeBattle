package battle.logic;

/**
 * The Trainer class represents a Pokémon trainer with a team of Pokémon, a name, and a trainer level.
 */
public class Trainer {

	private Pokemon[] team;
	private int trainerLevel;
	
	private String name;
	
	
	/**
     * Constructs a Trainer with the specified team and name.
     * @param team The array of Pokémon in the trainer's team.
     * @param name The name of the trainer.
     */
	public Trainer(Pokemon[] team, String name) {
		
		this.team = team;
		this.name = name;
		trainerLevel = 0;	// Initialize the trainer's level to 0
		
	}
	
	/**
     * Switches the current active Pokémon with the Pokémon at the specified position.
     * @param numPokemon The position of the Pokémon to switch with the current active Pokémon.
     */
	public void switchPokemon(int numPokemon) {
		
		Pokemon temp = team[0];
		team[0] = team[numPokemon];
		team[numPokemon] = temp;
		
	}
	
	/**
     * Checks if there are any Pokémon available (i.e., with HP greater than 0).
     * @return true if at least one Pokémon has HP greater than 0, false otherwise.
     */
	public boolean pokemonAvailable() {
		
	    for (Pokemon pokemon : team) {
	        if (pokemon.getHp() > 0) {
	            return true;
	        }
	    }
	    return false;
	}
	/**
     * Returns the team of Pokémon.
     * @return The array of Pokémon in the trainer's team.
     */
	public Pokemon[] getTeam() {
		return team;
	}
	/**
     * Returns the name of the trainer.
     * @return The trainer's name.
     */
	public String getName() {
		return name;
	}	
	/**
     * Returns a string representation of the Trainer.
     * @return A string containing the trainer's name, level, and the list of Pokémon.
     */
	@Override
	public String toString() {
		
		String teamList = "";
		for (int i = 0; i < team.length; i++) {
			teamList += team[i].toString() + " ";
		}
		return name + " " + trainerLevel + " " + teamList;
	}
}
