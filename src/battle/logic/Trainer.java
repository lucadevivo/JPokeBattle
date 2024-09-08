package battle.logic;

/**
 * The Trainer class represents a Pokémon trainer with a team of Pokémon, a name, and a trainer level.
 */
public class Trainer {

    private Pokemon[] team;
    private int trainerLevel;
    private String name;

    // Nuovi attributi per il conteggio delle vittorie e delle sconfitte
    private int wins;
    private int losses;

    /**
     * Constructs a Trainer with the specified team and name.
     * @param team The array of Pokémon in the trainer's team.
     * @param name The name of the trainer.
     */
    public Trainer(Pokemon[] team, String name) {
        this.team = team;
        this.name = name;
        this.trainerLevel = 0;    // Initialize the trainer's level to 0
        this.wins = 0;           // Initialize the wins to 0
        this.losses = 0;         // Initialize the losses to 0
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
     * Increments the win count for this trainer.
     */
    public void incrementWins() {
        this.wins++;
    }

    /**
     * Increments the loss count for this trainer.
     */
    public void incrementLosses() {
        this.losses++;
    }

    /**
     * Returns a string representation of the Trainer.
     * @return A string containing the trainer's name, level, and the list of Pokémon.
     */
    @Override
    public String toString() {
        String teamList = "";
        for (Pokemon pokemon : team) {
            teamList += pokemon.toString() + " ";
        }
        return name + " Level: " + trainerLevel + " Wins: " + wins + " Losses: " + losses + " Team: " + teamList;
    }
    
    public String getAllAttributes() {
    	
        StringBuilder attributes = new StringBuilder();
        
        attributes.append(name).append("\n")
        .append(trainerLevel).append("\n")
        .append(wins).append("\n")
        .append(losses).append("\n");


        return attributes.toString();
    }

}

