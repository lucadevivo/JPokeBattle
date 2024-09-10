package battle.pokemons;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import battle.logic.Evolution;
import battle.logic.Pokemon;
import battle.logic.Type;
import battle.logic.TypeEV;

public class Squirtle extends Pokemon implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Squirtle() {
		
		super("Squirtle", Type.WATER, /*hp*/44, /*atk*/48, /*def*/65, /*vel*/43, /*Special*/57, /*base XP*/63, new String[]{
				
	            //It deals damage with no additional effects.
				"Tackle",
				
				//Tail Whip lowers the target's Defense by one stage.
				"Tail Whip"
	            
		},
				
		// Lista di evoluzioni
		Arrays.asList(
		new Evolution("Wartortle", 16),
		new Evolution("Blastoise", 36)
				            
		),
		TypeEV.DEFENSE
		);
		
		
		unlockableMoves = new HashMap<>();
		
		//Bubble deals damage and has a 10% chance of lowering the target's Speed by one stage.
		unlockableMoves.put(8, "Bubble");
		
		//It deals damage with no additional effects.
		unlockableMoves.put(15, "Water Gun");
		
		//Withdraw raises the user's Defense by one stage.
		unlockableMoves.put(28, "Withdraw");
		
		//It deals damage with no additional effects.
		unlockableMoves.put(42, "Hydro Pump");
		
	}	
}
