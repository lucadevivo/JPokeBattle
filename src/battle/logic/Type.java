package battle.logic;

// Enum representing different types of Pokemon moves and Pokemon themselves
public enum Type {

    WATER,      // Water type
    FIRE,       // Fire type
    GRASS,      // Grass type
    NORMAL;     // Normal type
    
    // Method to determine effectiveness of this type against another type
    public double effectivenessAgainst(Type opponentType) {
        
        switch (this) {
            case WATER:
                switch (opponentType) {
                    case WATER:               
                        return 0.5; // Water is half effective against Water
                    case FIRE:               
                        return 2.0; // Water is super effective against Fire
                    case GRASS:               
                        return 0.5; // Water is half effective against Grass
                    case NORMAL:               
                        return 1.0; // Normal effectiveness against Normal
                    default:
                        return 1.0; // Default to normal effectiveness
                }
            case FIRE:
                switch (opponentType) {
	                case WATER:               
	                    return 0.5; // Fire is half effective against Water
	                case FIRE:               
	                    return 0.5; // Fire is half effective against Fire
	                case GRASS:               
	                    return 2.0; // Fire is super effective against Grass
	                case NORMAL:               
	                    return 1.0; // Normal effectiveness against Normal
	                default:
	                    return 1.0; // Default to normal effectiveness
                }
            case GRASS:
                switch (opponentType) {
	                case WATER:               
	                    return 2.0; // Grass is super effective against Water
	                case FIRE:               
	                    return 0.5; // Grass is half effective against Fire
	                case GRASS:               
	                    return 0.5; // Grass is half effective against Grass
	                case NORMAL:               
	                    return 1.0; // Normal effectiveness against Normal
	                default:
	                    return 1.0; // Default to normal effectiveness
                }
            case NORMAL:
                switch (opponentType) {
	                case WATER:               
	                    return 1.0; // Normal effectiveness against Water
	                case FIRE:               
	                    return 1.0; // Normal effectiveness against Fire
	                case GRASS:               
	                    return 1.0; // Normal effectiveness against Grass
	                case NORMAL:               
	                    return 1.0; // Normal effectiveness against Normal
	                default:
	                    return 1.0; // Default to normal effectiveness
                }
                
            default:
                return 1;
        }
   }
}
