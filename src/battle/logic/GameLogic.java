	package battle.logic;
	/**
	 * Manages the game logic for a battle between two trainers and their Pokemon.
	 */
	public class GameLogic{
	    
		// Static variables to store information for the text panel
		public static String usedMove1 = "";
		public static String usedMove2 = "";
		public static String effectiveness1 = "";
		public static String effectiveness2 = "";
		public static String critic1 = "";
		public static String critic2 = "";
		public static String changePokemon1 = "";
		public static String changePokemon2 = "";
		public static String pokemonExhausted1 = "";
		public static String pokemonExhausted2 = "";
		
		private GameObserver observer;	
		private BattleObserver battleObserver;
		
		private Trainer firstTrainer;
	    private Trainer secondTrainer;
	    private Trainer currentTrainer;
	    
	    private int wichTrainer = 0;
	    private Battle battle;
	    
	    private int firstMoveIndex = -1;

	    /**
	     * Constructs the game logic for a battle between two trainers.
	     * @param firstTrainer The first trainer participating in the battle.
	     * @param secondTrainer The second trainer participating in the battle.
	     * @param battle The Battle instance managing the battle mechanics.
	     * @param observer The observer to notify game updates.
	     * @param battleObserver The observer to notify battle end.
	     */
	    public GameLogic(Trainer firstTrainer, Trainer secondTrainer, Battle battle, GameObserver observer, BattleObserver battleObserver) {
	        
	    	this.firstTrainer = firstTrainer;
	    	currentTrainer = firstTrainer;
	        this.secondTrainer = secondTrainer;
	        
	        this.battle = battle;
	        this.observer = observer;
	        this.battleObserver = battleObserver;
	
	    }
	    
	    /**
	     * Sets the observer for game updates.
	     * @param observer The observer to set.
	     */
	    public void setGameObserver(GameObserver observer) {
	        this.observer = observer;
	    }
	    
	    /**
	     * Notifies the game observer of any updates.
	     */
	    private void notifyObserver() {
	    	
	        observer.onGameUpdate(firstTrainer, secondTrainer, wichTrainer);
	    	
	    }
	    /**
	     * Sets the observer for battle end.
	     * @param battleObserver The observer to set.
	     */
	    public void setBattleObserver(BattleObserver battleObserver) {
	        this.battleObserver = battleObserver;
	    }
	    
	    /**
	     * Notifies the battle observer of the end of the battle.
	     * @param i The result of the battle.
	     */
	    private void notifyBattleEnd(int i) {	    	
	        battleObserver.onBattleEnd(i);

	    }
	    /**
	     * Allows the current trainer to change their active Pokemon.
	     * @param newPokemonIndex The index of the new Pokemon in the trainer's team.
	     */
	    public void changePokemon(int newPokemonIndex) {
	    	
	        if (currentTrainer.getTeam()[0].getHp() == 0) {currentTrainer.switchPokemon(newPokemonIndex);}
	        else {
	        	
	        	currentTrainer.switchPokemon(newPokemonIndex);

	        	if (currentTrainer == firstTrainer) {
	        		
	        		changePokemon1 = "GO!   " + currentTrainer.getTeam()[0].getName() + "!";
	        		
		            wichTrainer = 1;
		            currentTrainer = secondTrainer;
		        } else {
		        	
		        	changePokemon2 = "GO!   " + currentTrainer.getTeam()[0].getName() + "!";
		        	
		        	attack(-1);
		        	
		            wichTrainer = 0;
		            currentTrainer = firstTrainer;      
		        }
	        }
	        
	        // Notify observer about the game update
	        notifyObserver();
	        
	    }
	    
	    /**
	     * Executes an attack move from the current trainer's active Pokemon.
	     * @param moveIndex The index of the move to be used.
	     */
	    public void attack(int moveIndex) {
	    	
	    	// Determine whose turn it is and set the move index
	    	if (currentTrainer == firstTrainer) {
	    		
	    		System.out.println("si");
	    		
	    		firstMoveIndex = moveIndex;
	            wichTrainer = 1;
	            currentTrainer = secondTrainer;
	            
	        } else {
	            
	        	System.out.println("sii");
	        	// Get Pokemon instances for both trainers
	            Pokemon firstPokemon = firstTrainer.getTeam()[0];
	            Pokemon secondPokemon = secondTrainer.getTeam()[0];
	            
	            
	            if (firstMoveIndex != -1) {
	            	if (moveIndex != -1){
	            		
	            		if (firstPokemon.getVelCurrent() > secondPokemon.getVelCurrent()) {
	    	            	
	            			battle.round(firstTrainer, 0, firstMoveIndex); 
	    	                
	    	                updateMoveResults(0);
	    	                
	    	                if ((battle.endOfBattle() == 0) && secondPokemon.getHp() > 0) {battle.round(secondTrainer, 0, moveIndex);}
	    	                
	    	                updateMoveResults(1);
	    	                
	    	            }
	            		else {	  
	            			
	            			battle.round(secondTrainer, 0, moveIndex);
	            			
	            			updateMoveResults(1);
	    	                
	    	                if ((battle.endOfBattle() == 0) && firstPokemon.getHp() > 0) {battle.round(firstTrainer, 0, firstMoveIndex);}
	    	                
	    	                updateMoveResults(0);
	            		}
	            	}else {
	            		
	            		battle.round(firstTrainer, 0, firstMoveIndex);
	            		
	            		updateMoveResults(0);
	            		
	            	}
	            	
	            }else {
	            	
	            	if (moveIndex != -1){
	            		
	            		battle.round(secondTrainer, 0, moveIndex);
	            		
	            		updateMoveResults(1);
	            	}
	            }	             
	            firstMoveIndex = -1;
	            
	            notifyBattleEnd(battle.endOfBattle());

	            
	            wichTrainer = 0;
	            currentTrainer = firstTrainer;
	            
	            System.out.println(firstPokemon);
	            System.out.println(secondPokemon);
	        }
	    	
	    	
	        notifyObserver();
	        
	        
	        
	    }
	    
	    private void updateMoveResults(int trainerIndex) {
	    	
	        if (trainerIndex == 0) {
	        	usedMove1 = Pokemon.usedMove;
	        	effectiveness1 = MoveEffects.effectiveness;
	        	critic1 = MoveEffects.criticalHit;
	        	pokemonExhausted2 = Pokemon.pokemonExhausted;
	        } else {
	        	usedMove2 = Pokemon.usedMove;
	        	effectiveness2 = MoveEffects.effectiveness;
	        	critic2 = MoveEffects.criticalHit;
	        	pokemonExhausted1 = Pokemon.pokemonExhausted;
	        }
	        
	        // Reset the static variables for the next turn
	        Pokemon.usedMove = "";
	        MoveEffects.criticalHit = "";
	        MoveEffects.effectiveness = "";
	        Pokemon.pokemonExhausted = "";
	    }
	    
	}


