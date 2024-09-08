package battle.pokemons;

import java.util.Arrays;
import java.util.HashMap;
import battle.logic.Evolution;
import battle.logic.Pokemon;
import battle.logic.Type;
import battle.logic.TypeEV;

public class Charmander extends Pokemon{

	public Charmander() {
		
		super("Charmander", Type.FIRE, /*hp*/39, /*atk*/52, /*def*/43, /*vel*/65, /*Special*/55, /*base XP*/62, new String[]{
				
				//Growl lowers the target's Attack by one stage.
				"Growl", 
				
				//It deals damage with no additional effects.
	            "Scratch",
	            
	        },
			
			// Lista di evoluzioni
			Arrays.asList(
			new Evolution("Charmeleon", 16),
			new Evolution("Charizard", 36) 
			            
			),
			TypeEV.SPEED
			);
		
		
		
		unlockableMoves = new HashMap<>();
		
		//10% chance of dealing additional damages, special
		unlockableMoves.put(9, "Ember");
		
		//Leer lowers the target's Defense by one stage.
		unlockableMoves.put(15, "Leer");
		      
        //Slash deals damage and has an increased critical hit ratio (1⁄8 instead of 1⁄24).
		unlockableMoves.put(30, "Slash");
        
        //10% chance of dealing additional damages, special
		unlockableMoves.put(38, "Flamethrower");
              
        //Fire Spin cause the opponent to loose 1/8 of his maximum health, special
		unlockableMoves.put(46, "Fire Spin");
        
		
	}
}
