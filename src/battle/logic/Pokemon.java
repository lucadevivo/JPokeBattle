package battle.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * This class represents a Pokémon with its attributes and methods for battle mechanics.
 */
public class Pokemon implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Constants for maximum numbers
	private static final int MAX_NUM_MOVES = 4;
	private static final int MAX_EV = 510;
    private static final int MAX_EV_FOR_STAT = 255;
	
    // Static variables for tracking move and status
	public static String usedMove = "";
	public static String pokemonExhausted = "";
	
	// Boolean to track if a new move is available
	private Boolean newMove = false;
	
	// Basic attributes
	private String name;
	private Type type;	
	private int level;
	private int exp;	
	private int hp;	
	private int maxHp;
	private int maxHpCurrent;	
	private int defCurrent;
	private int atkCurrent;	
	private int velCurrent;
	private int SpecialCurrent;    
    private int def;
	private int atk;	
	private int vel;
    private int Special;	
	private int baseXp;	
	private List<String> moves;
	protected HashMap<Integer, String> unlockableMoves;
	private List<Evolution> evolutions;	
	
	// Individual Values (IVs) and Effort Values (EVs)
	private int ivHp;
	private int ivAtk;
	private int ivDef;
	private int ivVel;
	private int ivSpecial;
	
	private int evHp;
	private int evAtk;
	private int evDef;
	private int evVel;
	private int evSpecial;
	
	
	// EV type for this Pokémon
	private TypeEV typeEv;
	
	/**
     * Constructor for a Pokémon.
     * 
     * @param name The name of the Pokémon.
     * @param type The type of the Pokémon.
     * @param hp The base HP of the Pokémon.
     * @param atk The base attack of the Pokémon.
     * @param def The base defense of the Pokémon.
     * @param vel The base speed of the Pokémon.
     * @param Special The base special of the Pokémon.
     * @param baseXp The base experience of the Pokémon.
     * @param initialMoves The initial moves of the Pokémon.
     * @param evolutions The possible evolutions of the Pokémon.
     * @param typeEv The EV type the Pokémon grants.
     */
	public Pokemon(String name, Type type, int hp, int atk, int def, int vel, int Special, int baseXp, String[] initialMoves, List<Evolution> evolutions, TypeEV typeEv) {
		
		this.name = name;
		
		this.type = type;
		
		level = 1;
		exp = 0;
		
		// Initialize IVs randomly
		Random rand = new Random();
		this.ivHp = rand.nextInt(16);
		this.ivAtk = rand.nextInt(16);
		this.ivDef = rand.nextInt(16);
		this.ivVel = rand.nextInt(16);
		this.ivSpecial = rand.nextInt(16);
		
		// Initialize EVs to zero
		this.evHp = 0;
	    this.evAtk = 0;
	    this.evDef = 0;
	    this.evVel = 0;
	    this.evSpecial = 0;
	    
	    this.typeEv = typeEv;
		
	    // Initialize stats
		this.maxHp = hp;
		this.hp = this.maxHpCurrent = generateHp(hp, ivHp, evHp);
		
		this.def = def;		
		defCurrent = generateStat(def, ivDef, evDef);
		
		this.atk = atk;
		atkCurrent = generateStat(atk, ivAtk, evAtk);
				
		this.vel = vel;
		velCurrent = generateStat(vel, ivVel, evVel);
		
		this.Special = Special;
		SpecialCurrent = generateStat(Special, ivSpecial, evSpecial);
        
		this.baseXp = baseXp;
		
		// Initialize moves	
		this.moves = new ArrayList<>();
        for (String move : initialMoves) {this.moves.add(move);}
         
        // Initialize unlockable moves and evolution
		this.unlockableMoves = new HashMap<>();
		this.evolutions = evolutions;
		
		
	}	
	
	/**
     * Generates a stat for the Pokémon.
     * 
     * @param stat The base stat.
     * @param iv The individual value for the stat.
     * @param ev The effort value for the stat.
     * @return The calculated stat.
     */
	public int generateStat(int stat, int iv, int ev) { 
	    int finalStat = (int)((((2 * stat + iv + (ev / 4.0)) * level) / 100.0) + 5);
	    return finalStat;
	}

	/**
     * Generates the HP for the Pokémon.
     * 
     * @param stat The base HP.
     * @param iv The individual value for HP.
     * @param ev The effort value for HP.
     * @return The calculated HP.
     */
	public int generateHp(int stat, int iv, int ev) { 
	    int finalStat = (int)((((2 * stat + iv + (ev / 4.0)) * level) / 100.0) + level + 10);
	    return finalStat;
	}
	
	/**
     * Handles the attack move of the Pokémon.
     * 
     * @param moveNumber The index of the move.
     * @param opponent The opposing Pokémon.
     */
	public void attack(int moveNumber, Pokemon opponent) {
	    
	    if (moveNumber >= 0 && moveNumber < moves.size()) {
	    		    	
	    	usedMove = this.getName() + " uses " + moves.get(moveNumber);
	    	
	    	MoveEffects.applyEffect(moves.get(moveNumber), this, opponent);
	    	
	    	// If opponent's HP is 0, gain experience and potentially level up
	        if (opponent.getHp() == 0) {
	        	
	        	int experienceEarned = (int) (opponent.getBaseXp() * opponent.getLevel()/ 7);
	        	
	        	exp += experienceEarned;
	            
	        	increaseLevel();
	        	increaseEv(opponent.getTypeEv());
	        	updateStats();
	              		               
            }
	        	        
	    } else {
	        System.out.println("Invalid move number");
	    }
	}
	
	/**
     * Increases the EV of a specified type.
     * 
     * @param typeEV The type of EV to increase.
     */
	public void increaseEv(TypeEV typeEV) {
	    
		int evAmount = 10;  
		
	    int totalEv = evHp + evAtk + evDef + evVel + evSpecial;

	    
	    int availableEv = MAX_EV - totalEv;
	    if (availableEv < evAmount) {
	        evAmount = availableEv;
	    }

	    switch (typeEV) {
	    
	        case HP:
	            evHp = Math.min(evHp + evAmount, MAX_EV_FOR_STAT);
	            break;
	        case ATTACK:
	            evAtk = Math.min(evAtk + evAmount, MAX_EV_FOR_STAT);
	            break;
	        case DEFENSE:
	            evDef = Math.min(evDef + evAmount, MAX_EV_FOR_STAT);
	            break;
	        case SPEED:
	            evVel = Math.min(evVel + evAmount, MAX_EV_FOR_STAT);
	            break;
	        case SPECIAL:
	            evSpecial = Math.min(evSpecial + evAmount, MAX_EV_FOR_STAT);
	            break;
	        default:
	        	break;
	    }
	}

	/**
     * Receives damage and updates the Pokémon's HP.
     * 
     * @param damage The amount of damage received.
     */
	public void receiveDamage(int damage) {
	    
	    hp -= damage;
	    if (hp <= 0) {
	    	pokemonExhausted = "The pokemon " + name + " is exhausted";
	        hp = 0;
	    }
	}
	
	/**
     * Increases the level of the Pokémon if it has enough experience.
     */
	public void increaseLevel() {
		
		while (exp >= (level / 100.0)) {
			
			exp -= (level + 1);
            level++;
            
            //System.out.println(name + " has risen to level " + level + "!");
            
            // Check for evolutions
            for (Evolution evolution : evolutions) {
                if (level == evolution.getEvolutionLevel()) {                   
                    setName(evolution.getName());
                }
            }
            
            // Check for new moves
            checkNewMoves();
        }
	}
	
	/**
     * Updates the Pokémon's stats based on its current level, IVs, and EVs.
     */
	private void updateStats() {
		
		maxHpCurrent = generateHp(maxHp, ivHp, evHp);
		defCurrent = generateStat(def, ivDef, evDef);
		atkCurrent = generateStat(atk, ivAtk, evAtk);
		velCurrent = generateStat(vel, ivVel, evVel);
		SpecialCurrent = generateStat(Special, ivSpecial, evSpecial);
        
    }
	
	/**
     * Checks if the Pokémon learns any new moves at its current level.
     */
	private void checkNewMoves() {
		
        if (unlockableMoves.containsKey(level)) {
        	
        	if (moves.size() >= MAX_NUM_MOVES) {
        		newMove = true;
        	}else {
        		
        		moves.add(unlockableMoves.get(level));
        		unlockableMoves.remove(level);
        	}
            
        }
    }
	/**
     * Allows the Pokémon to learn a new move by replacing an existing move. 
     * @param indexToDelete The index of the move to be replaced.
     * @param newMove The new move to be learned.
     */
	public void learnMove(int indexToDelete, String newMove) {
		

	    if (indexToDelete >= 0 && indexToDelete < moves.size()) {
	    	
	    	moves.set(indexToDelete, newMove);
	      
	    }
    
	    Integer AssociatedKey = null;
	    
        for (Map.Entry<Integer, String> entry : unlockableMoves.entrySet()) {
            if (entry.getValue().equals(newMove)) {
            	AssociatedKey = entry.getKey();
                break;
            }
        }
        
        
        HashMap<Integer, String> copyMap = new HashMap<>(unlockableMoves);
        
        for (Integer key : copyMap.keySet()) {
            if (key <= AssociatedKey) {
            	unlockableMoves.remove(key);
            }
        }
	}	
	
	public void resetStats() {
		
		updateStats();
		
		if (hp <= 0) {
			hp = generateHp(maxHp, ivHp, evHp);
		}
	}
	///////////////////////Current defense Getter and Setter///////////////////////////
	
	public int getDefCurrent() {
		return defCurrent;
	}
	public void setDefCurrent(int newDefAttuale) {
		defCurrent = newDefAttuale;
	}
	
	///////////////////////Current attack getter and setter///////////////////////////
		
	public int getAtkCurrent() {
		return atkCurrent;
	}
	
	public void setAtkCurrent(int newAtkAttuale) {
		
		if (newAtkAttuale == 1) {
			atkCurrent = 2;
	    } else {
	    	atkCurrent = newAtkAttuale;
	    }
	}
	
	///////////////////////Current special getter and setter///////////////////////////
		
	public int getSpecialCurrent() {
        return SpecialCurrent;
    }
	
	public void setSpecialCurrent(int newSpecialAttuale) {
		
		if (newSpecialAttuale <= 1) {
			SpecialCurrent = 2;
	    } else {
	    	SpecialCurrent = newSpecialAttuale;
	    }
	}
	
	///////////////////////Current speed getter and setter///////////////////////////
		
	public int getVelCurrent() {
		return velCurrent;
	}
	
	public void setVelCurrent(int newVelocitaAttuale) {
		velCurrent = newVelocitaAttuale;
	}
	
	///////////////////////Current maxHp getter and setter///////////////////////////
		
	public int getHpMaxCurrent() {
	    return maxHpCurrent;
	}
	
	public void setHpMaxCurrent(int newMaxHpAttuali) {
		maxHpCurrent = newMaxHpAttuali;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////
		
	public int getHp() {
		return hp;
	}
	
	public void setHp(int newHp) {
		
	    if (newHp >= maxHpCurrent) {
	        hp = maxHpCurrent;
	    } else if (newHp >= 0) {
	        hp = newHp;
	    } else {
	        hp = 0;
	    }
	}
	
	public TypeEV getTypeEv() {
        return typeEv;
    }
	
	public Type getType() {
		return type;
	}

    public void setTypeEv(TypeEV tipoEv) {
        this.typeEv = tipoEv;
    }
	
	public String getName() {
		return name;
	}
	
	public void setName(String newNome) {
		name = newNome;
	}
	
	public int getLevel() {
		return level;
	}
	
	
	public int getBaseXp() {
        return baseXp;
    }
	
	public boolean getNewMove() {
		return newMove;
	}
	
	public void resetNewMove() {
		newMove = false;
	}
	
	public List<String> getMoves() {
        return moves;
    }
	
	public HashMap<Integer, String> getUnlockableMoves() {
	    return unlockableMoves;
	}
	/**
     * Returns a string representation of the Pokémon.
     * 
     * @return A string describing the Pokémon.
     */
	public String toString() {
		
	    return "Pokemon{" +
	            "nome='" + name + '\'' +
	            ", tipo=" + type +
	            ", livello=" + level +
	            ", esperienza=" + exp +
	            ", hp=" + hp +
	            ", maxHp=" + maxHpCurrent +
	            ", def=" + defCurrent +
	            ", atk=" + atkCurrent +
	            ", vel=" + velCurrent +
	            ", Special=" + SpecialCurrent +
	            ", baseXp=" + baseXp +
	            ", mosse=" + moves +
	            ", evHp=" + evHp +
	            ", evAtk=" + evAtk +
	            ", evDef=" + evDef +
	            ", evVel=" + evVel +
	            ", evSpecial=" + evSpecial +
	            '}';
	}
}