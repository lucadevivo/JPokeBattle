package battle.logic;

import java.io.Serializable;

/**
 * Represents the evolution details of a Pokemon, including its name and the level at which it evolves.
 */
public class Evolution implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
    private int evolutionLevel;

    /**
     * Constructs an evolution with the specified name and evolution level.
     * @param name The name of the evolution.
     * @param evolutionLevel The level at which the evolution occurs.
     */
    public Evolution(String name, int evolutionLevel) {
        this.name = name;
        this.evolutionLevel = evolutionLevel;
    }

    /**
     * Returns the name of the evolution.
     * 
     * @return The name of the evolved form of the Pokémon.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the level at which the evolution occurs.
     * 
     * @return The evolution level.
     */
    public int getEvolutionLevel() {
        return evolutionLevel;
    }
}

