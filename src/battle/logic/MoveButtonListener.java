package battle.logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class handles the action of a trainer's Pokémon using a move during a battle.
 */
public class MoveButtonListener implements ActionListener {

    private int moveIndex;
    private GameLogic gameLogic;
    private Trainer trainer;

    /**
     * Constructor for the MoveButtonListener.
     * 
     * @param moveIndex The index of the move to be used.
     * @param gameLogic The game logic that handles the battle mechanics.
     * @param trainer The trainer whose Pokémon is using the move.
     */
    public MoveButtonListener(int moveIndex, GameLogic gameLogic, Trainer trainer) {
        this.moveIndex = moveIndex;
        this.gameLogic = gameLogic;
        this.trainer = trainer;
    }

    /**
     * Sets a new trainer for this listener.
     * 
     * @param newTrainer The new trainer to be set.
     */
    public void setTrainer(Trainer newTrainer) {
        this.trainer = newTrainer;
    }

    /**
     * Handles the action event when the button is pressed to use a move.
     * 
     * @param e The action event triggered by the button press.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
    	
        // Check if the Pokémon has more than 0 HP
        if (trainer.getTeam()[0].getHp() > 0) {
            // Execute the attack using the specified move index
            gameLogic.attack(moveIndex);
        } else {
            // Print a message if the Pokémon cannot attack because it has 0 HP
            System.out.println("Cannot attack with this Pokémon, it has 0 HP");
        }
    }
}

