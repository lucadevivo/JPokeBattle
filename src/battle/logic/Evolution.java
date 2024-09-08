package battle.logic;
/**
 * Represents the evolution details of a Pokemon, including its name and the level at which it evolves.
 */
public class Evolution {
	
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

    public String getName() {
        return name;
    }

    public int getEvolutionLevel() {
        return evolutionLevel;
    }
}

