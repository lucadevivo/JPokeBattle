package battle.cards;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import battle.graphics.Constants;
import battle.graphics.JLayeredTextPanel;
import battle.logic.*;


/**
 * The LearnMovePanel class is a custom JPanel that allows a pokemon to learn a new move.
 * It displays buttons for each move and handles interactions with the game logic.
 */
public class LearnMovePanel extends JPanel {
    
    private static final long serialVersionUID = 1L;
    
    int numButtonsMoves;
    
    List<JButton> buttonsMoves;  	// List to hold move buttons 
    List<LearnMoveButtonListener> moveButtonListeners;	// List to hold listeners for move buttons
    
    Trainer trainer;	// The current trainer
    Trainer firstTrainer;	// The initial trainer
    JLayeredTextPanel textJLayeredPanel;	// Panel for layered text display
    
    private JTextField messageField;	// Field to display messages
    private String lastUnlockedMove;		// The latest unlocked move
    
    
    /**
     * Constructs a LearnMovePanel with the given trainer and text panel.
     * @param trainer the trainer
     * @param textJLayeredPanel the text panel for displaying messages
     */
    public LearnMovePanel(Trainer trainer, JLayeredTextPanel textJLayeredPanel) {
        
        this.textJLayeredPanel = textJLayeredPanel;
    	this.trainer = trainer;
    	this.firstTrainer = trainer;
        numButtonsMoves = trainer.getTeam()[0].getMoves().size();
        
        buttonsMoves = new ArrayList<>();
        moveButtonListeners = new ArrayList<>();
        
        //gets the last move unlocked
        lastUnlockedMove = getLastUnlockedMove(trainer.getTeam()[0].getUnlockableMoves(), trainer.getTeam()[0].getLevel());
        
        //set the text to display
        messageField = new JTextField("New move available:  " + lastUnlockedMove + ", select the move to change it to");
        messageField.setFont(Constants.FIRST_FONT.deriveFont(Font.PLAIN, 40));
        messageField.setBackground(Constants.BACKGROUND_COLOR);
        messageField.setHorizontalAlignment(JTextField.CENTER);
        messageField.setBorder(new EmptyBorder(0, 0, 0, 0));
        messageField.setEditable(false);
        this.add(messageField);
        
        // Initialize move buttons
        for (int i = 0; i < numButtonsMoves; i++) {
            
            JButton buttonMove = new JButton(trainer.getTeam()[0].getMoves().get(i));
            buttonMove.setBackground(Constants.BACKGROUND_COLOR);
            buttonMove.setFont(new Font(Constants.FIRST_FONT.getName(), Font.PLAIN, 30));
            buttonMove.setFocusable(false);
            
            LearnMoveButtonListener learnMoveButtonListener = new LearnMoveButtonListener(i, trainer, firstTrainer, textJLayeredPanel, lastUnlockedMove);
            
            buttonMove.addActionListener(learnMoveButtonListener);
            moveButtonListeners.add(learnMoveButtonListener);
            
            buttonsMoves.add(buttonMove);
            this.add(buttonMove);
        }
        
        
        // Initialize "Don't learn" button
        JButton buttonMove = new JButton("Don't learn");
        buttonMove.setBackground(Constants.BACKGROUND_COLOR);
        buttonMove.setFont(Constants.FIRST_FONT.deriveFont(Font.PLAIN, 30));
        buttonMove.setFocusable(false);
        
        LearnMoveButtonListener learnMoveButtonListener = new LearnMoveButtonListener(numButtonsMoves, trainer, firstTrainer, textJLayeredPanel, lastUnlockedMove);
        
        buttonMove.addActionListener(learnMoveButtonListener);
        moveButtonListeners.add(learnMoveButtonListener);
        
        buttonsMoves.add(buttonMove);
        this.add(buttonMove);
        
        this.setBackground(Constants.BACKGROUND_COLOR);
    }
    
    /**
     * Finds the latest unlocked move based on the pokemon's level.
     * @param unlockableMoves a map of unlockable moves and their levels
     * @param level the pokemon's current level
     * @return the latest unlocked move
     */
    
    private String getLastUnlockedMove(HashMap<Integer, String> unlockableMoves, int level) {
    	
        // Find the highest key less than or equal to the level
        Integer lastMoveKey = null;

        for (Integer key : unlockableMoves.keySet()) {
            if (key <= level && (lastMoveKey == null || key > lastMoveKey)) {
            	lastMoveKey = key;
            }
        }
        
        if (lastMoveKey != null) {              
            return unlockableMoves.get(lastMoveKey);
        }else {
        	return null;
        }
        
    }
 
    /**
     * Override the paintComponent method to arrange buttons dynamically based on panel size and on the number of buttons.
     * @param g2 the Graphics object
     */
    
    @Override
    protected void paintComponent(Graphics g2) {
    	
        super.paintComponent(g2);

        int totalButtons = numButtonsMoves + 1; // Include the "Don't learn" button
        
        int buttonWidth = getWidth() / totalButtons;
        int buttonHeight = getHeight() / 3;
        int buttonY = (getHeight() - buttonHeight) / 2;

        // Calculate the size of the messageField
        int messageFieldHeight = messageField.getPreferredSize().height;
        int messageFieldY = (buttonY - messageFieldHeight) / 2;
        
        // Set bounds for the messageField
        messageField.setBounds(0, messageFieldY, getWidth(), messageFieldHeight);

        // Set bounds for the buttons
        for (int i = 0; i < totalButtons; i++) {
            JButton buttonMove = buttonsMoves.get(i);
            buttonMove.setBounds(buttonWidth * i, buttonY, buttonWidth, buttonHeight);
        }
    }
    
    /**
     * Updates the buttons based on the new pokemon's moves and state.
     * @param newTrainer the new trainer
     */
    
    public void updateButtons(Trainer newTrainer) {
    	
    	// Update the trainer instance with the new one
        this.trainer = newTrainer;
        lastUnlockedMove = getLastUnlockedMove(trainer.getTeam()[0].getUnlockableMoves(), trainer.getTeam()[0].getLevel());

        
        // Remove existing buttons from the panel
        for (JButton button : buttonsMoves) {
            this.remove(button);
        }
        buttonsMoves.clear();
        moveButtonListeners.clear();
        
        // Update the number of available moves
        numButtonsMoves = trainer.getTeam()[0].getMoves().size();
        
        // Update the text message
        messageField.setText("New move available: " + lastUnlockedMove + ", select the move to change it to");
        
        // Initialize the new move buttons
        for (int i = 0; i < numButtonsMoves; i++) {
        	
            JButton buttonMove = new JButton(trainer.getTeam()[0].getMoves().get(i));
            buttonMove.setBackground(Constants.BACKGROUND_COLOR);
            buttonMove.setFont(Constants.FIRST_FONT.deriveFont(Font.PLAIN, 30));
            buttonMove.setFocusable(false);
            
            LearnMoveButtonListener learnMoveButtonListener = new LearnMoveButtonListener(i, trainer, firstTrainer, textJLayeredPanel, lastUnlockedMove);
            
            buttonMove.addActionListener(learnMoveButtonListener);
            moveButtonListeners.add(learnMoveButtonListener);
            
            buttonsMoves.add(buttonMove);
            this.add(buttonMove);
        }
        
        // Add the "Don't learn" button again
        JButton buttonMove = new JButton("Don't learn");
        buttonMove.setBackground(Constants.BACKGROUND_COLOR);
        buttonMove.setFont(new Font(Constants.FIRST_FONT.getName(), Font.PLAIN, 30));
        buttonMove.setFocusable(false);
        
        LearnMoveButtonListener learnMoveButtonListener = new LearnMoveButtonListener(numButtonsMoves, trainer, firstTrainer, textJLayeredPanel, lastUnlockedMove);
        
        buttonMove.addActionListener(learnMoveButtonListener);
        moveButtonListeners.add(learnMoveButtonListener);
        
        buttonsMoves.add(buttonMove);
        this.add(buttonMove);
        
        // Redraw the panel to reflect the changes
        revalidate();
        repaint();
    }

}
