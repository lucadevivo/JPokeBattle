package battle.logic;

import java.util.Random;
/**
 * This class manages the effects of moves used in battles between Pokemon.
 */
public class MoveEffects {
	
	private static final Random random = new Random();
	public static String criticalHit = "";
	public static String effectiveness = "";

	/**
     * Applies the effect of the move based on its name.
     * @param move The name of the move.
     * @param user The Pokemon using the move.
     * @param target The Pokemon targeted by the move.
     */
	public static void applyEffect(String move, Pokemon user, Pokemon target) {
		
			switch (move) {

			
			case "Tackle":
				
				applyPhysicalDamage(35, 95, Type.NORMAL, 24, user, target, false);				
				break;
				
			case "Tail Whip":				
			case "Leer":
				
				target.setDefCurrent((int) Math.round(target.getDefCurrent() * (2.0 / (2 - /*num of stage*/ - 1))));
				break;
				
			case "Bubble":
				
				applyPhysicalDamage(20, 100, Type.WATER, 24, user, target, true);	
				
				int specialBubbleOdds = random.nextInt(100);
				if (specialBubbleOdds <= 10) {target.setVelCurrent((int) Math.round(target.getVelCurrent() * (2.0 / (2 - /*num of stage*/ - 1))));}	
				break;
				
			case "Water Gun":
				
				applyPhysicalDamage(40, 100, Type.WATER, 24, user, target, true);	
				break;
				
			case "Withdraw":
				
				user.setDefCurrent((int) Math.round(user.getDefCurrent() * ((2 + /*num of stages*/1)/2.0)));
				break;

			case "Hydro Pump":
				
				applyPhysicalDamage(120, 80, Type.WATER, 24, user, target, true);	
				break;
			
			case "Growl":
			
				target.setAtkCurrent((int) Math.round(target.getAtkCurrent() * (2.0 / (2 - /*num of stage*/ - 1))));			
				break;
				
			case "Scratch":
				
				applyPhysicalDamage(40, 100, Type.NORMAL, 24, user, target, false);				
				break;
				
			case "Ember":
				
				int probAdditionalDamageEmber = random.nextInt(100);
				if (probAdditionalDamageEmber > 10) {applyPhysicalDamage(40, 100, Type.FIRE, 24, user, target, true);}
				else {applyPhysicalDamage(60, 100, Type.FIRE, 24, user, target, true);}
				
				break;

			case "Slash":
				
				applyPhysicalDamage(70, 100, Type.NORMAL, 8, user, target, true);				
				break;
				
			case "Flamethrower":
				
				int probAdditionalDamageFlamethrower = random.nextInt(100);
				if (probAdditionalDamageFlamethrower > 10) {applyPhysicalDamage(95, 100, Type.FIRE, 24, user, target, true);}
				else {applyPhysicalDamage(140, 100, Type.FIRE, 24, user, target, true);}
				
				break;

			case "Fire Spin":
				
				target.receiveDamage((int)(Math.round(target.getHpMaxCurrent()/8.0)));
				break;
				
			case "Leech Seed":
				
				int randomPrecision = random.nextInt(100);
				
				if (randomPrecision <= 90) {
					
				int damageInflictedLeechSeed = (int)(Math.round(target.getHpMaxCurrent()/8.0));
				target.receiveDamage(damageInflictedLeechSeed);
				user.setHp(user.getHp() + damageInflictedLeechSeed);
				
				}
				break;
				
				
			case "Vine Whip":
				
				applyPhysicalDamage(35, 100, Type.GRASS, 24, user, target, true);
				break;
				
			case "Razor Leaf":
				
				applyPhysicalDamage(55, 95, Type.GRASS, 8, user, target, true);
                break;
				
				
			case "Growth":

				user.setAtkCurrent((int) Math.round(user.getAtkCurrent() * ((2 + /*num of stages*/1)/2.0)));
				user.setSpecialCurrent((int) Math.round(user.getSpecialCurrent() * ((2 + /*num of stages*/1)/2.0)));				
				break;
				
				
			case "Solar Beam":
				
				applyPhysicalDamage(120, 100, Type.GRASS, 24, user, target, true);
				break;
				
			default:
				
                System.out.println("Mossa non gestita");
                break;
			
					
		}
		
}
	/**
     * Applies physical damage to the target Pokemon based on the move's parameters.
     * @param power The power of the move.
     * @param accuracy The accuracy of the move.
     * @param type The type of the move.
     * @param criticalProbability The critical hit chance of the move.
     * @param user The Pokemon using the move.
     * @param target The Pokemon targeted by the move.
     * @param special Indicates if the move is special (uses special attack) or physical (uses attack).
     * @return The amount of damage dealt to the target.
     */
	private static int applyPhysicalDamage(int power, int accuracy, Type type, int criticalProbability, Pokemon user, Pokemon target, boolean special) {
	
		int attack;
	    int defence;
	    
	    // Determine attack and defense stats based on move type
	    if (!special) {
	    	attack = user.getAtkCurrent();
	        defence = target.getDefCurrent();
	    } else {
	    	attack = user.getSpecialCurrent();
	        defence = target.getSpecialCurrent();
	    }
	    
	    int damage = 0;	
		
		// Randomly determine if the move hits based on its accuracy
		int randomPrecision = random.nextInt(100);
		
		if (randomPrecision < accuracy) {
	    	
			// Calculate damage based on the formula for Pokemon battles
	    	double effectivenessType = type.effectivenessAgainst(target.getType());
	    	
	    	int randomNumber = 85 + random.nextInt(16);
	    	
	    	damage = (int)(((((2 * user.getLevel())/7.0)*attack*power)/(defence*50.0)+2)*effectivenessType*randomNumber/100);
			
	    	
			
	    	// Check for critical hit
			int randomCritic = random.nextInt(criticalProbability);
	
	        // Verifica se si tratta di un colpo critico
	        boolean critic = randomCritic == 0;
	        
	        if (critic) {
	        	
	        	// Apply critical hit damage bonus
	        	damage *= 1.5;
	            criticalHit = "Critical hit!";
	        }
	        
	        // Deal damage to the target Pokemon
	        target.receiveDamage(damage);
			
	        // Determine effectiveness message based on effectiveness against target's type
			String effectivenessString;
			
			switch (String.valueOf(effectivenessType)) {
			
			case "0.5":
				effectivenessString = "The move is not very effective...";
	            break;
	        case "2.0":
	        	effectivenessString = "The move is super effective!";
	            break;
	        default:
	        	effectivenessString = "The move has a normal effect";
	            break;	
			}
			
			effectiveness = effectivenessString;
			
	    }
		// If the move misses due to low accuracy
	    else { effectiveness = "The move missed the target!";}
			
		return damage;	
	}

}	