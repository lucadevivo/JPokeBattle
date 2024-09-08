package battle.logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import battle.graphics.JLayeredTextPanel;

/**
 * This class handles the action of a trainer learning a new move.
 */
public class LearnMoveButtonListener implements ActionListener {

    private int moveIndex;
    private Trainer trainer;
    private Trainer firstTrainer;
    private String newMove;
    JLayeredTextPanel textJLayeredPanel;

    /**
     * Constructor for the LearnMoveButtonListener.
     * 
     * @param moveIndex The index of the move to be replaced.
     * @param trainer The trainer whose Pokémon is learning a new move.
     * @param firstTrainer The first trainer in the battle.
     * @param textJLayeredPanel The panel displaying text and buttons.
     * @param newMove The new move to be learned.
     */
    public LearnMoveButtonListener(int moveIndex, Trainer trainer, Trainer firstTrainer, JLayeredTextPanel textJLayeredPanel, String newMove) {
        this.textJLayeredPanel = textJLayeredPanel;
        this.moveIndex = moveIndex;
        this.trainer = trainer;
        this.firstTrainer = firstTrainer;
        this.newMove = newMove;
    }

    /**
     * Handles the action event when the button is pressed to learn a new move.
     *
     * @param e The action event triggered by the button press.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Reset the new move flag for the current Pokémon
        trainer.getTeam()[0].resetNewMove();

        // Teach the new move to the Pokémon at the specified index
        trainer.getTeam()[0].learnMove(moveIndex, newMove);

        // Update the display panel with the updated trainer information
        textJLayeredPanel.updateBox(firstTrainer);

        // Show the buttons on the display panel
        textJLayeredPanel.showButtons();
    }
}

