package battle.pokemons;

import java.util.Arrays;
import java.util.HashMap;
import battle.logic.Evolution;
import battle.logic.Pokemon;
import battle.logic.Type;
import battle.logic.TypeEV;

public class Bulbasaur extends Pokemon{

	public Bulbasaur() {
		
		super("Bulbasaur", Type.GRASS, /*hp*/45, /*atk*/49, /*def*/49, /*vel*/45, /*Special*/65,/*base XP*/64, new String[]{
				
				//Growl lowers the target's Attack by one stage.
				"Growl", 
				
				//Tackle deals damage with no additional effects.
	            "Tackle",
	            
	        },
		
			// Lista di evoluzioni
            Arrays.asList(
            new Evolution("Ivysaur", 16),
            new Evolution("Venusaur", 32)
  
	        ),            
            TypeEV.SPECIAL
            );
		
		
		unlockableMoves = new HashMap<>();
		
   
		//Leech Seed plants a seed on the target that drains 1⁄8 of its maximum HP and restores it to the user
		unlockableMoves.put(7, "Leech Seed");
		
		//Vine Whip deals damage with no additional effect, special
		unlockableMoves.put(13, "Vine Whip");
				
        //Razor Leaf deals damage and has an increased critical hit ratio (1⁄8 instead of 1⁄24), special
		unlockableMoves.put(27, "Razor Leaf"); 
        
        //Growth raises the user's Attack and Special Attack by one stage each.
		unlockableMoves.put(34, "Growth");
        
        //Solar Beam deals damage with no additional effect, special
		unlockableMoves.put(48, "Solar Beam");
        
        
		
	}
}
