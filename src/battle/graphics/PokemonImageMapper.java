package battle.graphics;

import java.util.HashMap;
import java.util.Map;

/**
 * The PokemonImageMapper class provides a mapping between Pokémon names and their corresponding image numbers.
 * It uses a static map to store these mappings and provides methods to access the mappings.
 */
public class PokemonImageMapper {

	// Static map to hold the Pokémon name to image number mappings
    private static final Map<String, String> pokemonImageMap = new HashMap<>();

    // Static block to initialize the map with Pokémon name and image number pairs
    static {
        
        pokemonImageMap.put("Bulbasaur", "1");
        pokemonImageMap.put("Ivysaur", "2");
        pokemonImageMap.put("Venusaur", "3");
        pokemonImageMap.put("Charmander", "4");
        pokemonImageMap.put("Charmeleon", "5");
        pokemonImageMap.put("Charizard", "6");
        pokemonImageMap.put("Squirtle", "7");
        pokemonImageMap.put("Wartortle", "8");
        pokemonImageMap.put("Blastoise", "9");
        // Add all other Pokémon here...
    }

    /**
     * Retrieves the image number associated with a given Pokémon name.
     * @param pokemonName The name of the Pokémon.
     * @return The image number as a String, or null if the Pokémon name is not found.
     */
    public static String getImageNumber(String pokemonName) {
        return pokemonImageMap.get(pokemonName);
    }

    /**
     * Checks if the map contains a given Pokémon name.
     * @param pokemonName The name of the Pokémon.
     * @return True if the Pokémon name is present in the map, false otherwise.
     */
    public static boolean containsPokemon(String pokemonName) {
        return pokemonImageMap.containsKey(pokemonName);
    }
}

