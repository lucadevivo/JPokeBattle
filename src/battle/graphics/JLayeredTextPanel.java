package battle.graphics;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import battle.cards.ChoosePanel;
import battle.cards.LearnMovePanel;
import battle.cards.TextPanel;
import battle.logic.*;

import java.awt.*;

/**
 * The JLayeredTextPanel class manages different panels for displaying text, choosing moves, and learning new moves during a battle.
 * It uses a layered pane to stack components and manage their visibility.
 */
public class JLayeredTextPanel extends JLayeredPane {
    
	private static final long serialVersionUID = 1L;
	
	JLabel background;	// Label for the background
    JLabel imageLabel;	// Label for the background image
    JPanel selectionPanel;	// Panel for switching between different views (text, choose, learn)

    TextPanel textPanel;	// Panel displaying battle-related text
    ChoosePanel choosePanel;	// Panel for choosing moves	
    LearnMovePanel learnMovePanel;	// Panel for learning new moves
    
    GameLogic gameLogic;	// Game logic instance for managing the game state	
    
    /**
     * Constructor for JLayeredTextPanel.
     * Initializes the background, image, and selection panels, and sets up the layout and initial view.
     * @param trainer The trainer involved in the battle.
     * @param gameLogic The game logic instance managing the battle state.
     */
    public JLayeredTextPanel(Trainer trainer, GameLogic gameLogic) {
    	
        this.setLayout(null);	// Use null layout for manual positioning of components
        this.gameLogic = gameLogic;

        // Initialize the background label
        background = new JLabel();
        background.setBackground(Constants.BACKGROUND_COLOR);
        background.setOpaque(true);
        
        // Set up the image label with the frame
        ImageIcon imageIcon = new ImageIcon("res/battleutils/textField.png");
        imageLabel = new JLabel(imageIcon);

        // Create a panel for switching between different views
        selectionPanel = new JPanel(new CardLayout());
        selectionPanel.setBackground(Constants.BACKGROUND_COLOR);
        
        // Initialize the panels for text, choosing moves, and learning new moves
        textPanel = new TextPanel("", "");       
        choosePanel = new ChoosePanel(trainer, gameLogic);
        learnMovePanel = new LearnMovePanel(trainer, this);
        
        // Add the panels to the selectionPanel with unique identifiers
        selectionPanel.add(textPanel, "TEXT_PANEL");
        selectionPanel.add(choosePanel, "CHOOSE_PANEL");
        selectionPanel.add(learnMovePanel, "LEARN_MOVE");
       
        // Show the "CHOOSE_PANEL" view initially
        CardLayout cl = (CardLayout)(selectionPanel.getLayout());
        cl.show(selectionPanel, "CHOOSE_PANEL");
        
        // Add the components to the layered pane
        this.add(selectionPanel, Integer.valueOf(2));
        this.add(imageLabel, Integer.valueOf(1));   
        this.add(background, Integer.valueOf(0));
        
        
    }  
    /**
    * paintComponent method overridden to customize component placement
    * @param g Graphic object used to draw components.
    */
    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        
        background.setBounds(0, 0, getWidth(), getHeight());
        
        Dimension imageSize = imageLabel.getPreferredSize();
        imageLabel.setBounds((getWidth() - imageSize.width) / 2, (getHeight() - imageSize.height) / 2, imageSize.width, imageSize.height);
        
        
        int buttonPanelWidth = (int)(getWidth() * 0.9);
        int buttonPanelHeight = (int)(getHeight() * (0.7));
        
        int buttonPanelX = (getWidth() - buttonPanelWidth)/2;
        int buttonPanelY = (getHeight() - buttonPanelHeight)/2;
            
        selectionPanel.setBounds(buttonPanelX, buttonPanelY, buttonPanelWidth, buttonPanelHeight);
        
        textPanel.setBounds(0, 0, buttonPanelWidth, buttonPanelHeight);
        choosePanel.setBounds(0, 0, buttonPanelWidth, buttonPanelHeight);
        
    }
    

    /**
     * Updates the buttons in the choosePanel with new information from the provided trainer.
     * @param newTrainer The new trainer with updated information.
     */
    public void updateBox(Trainer newTrainer) {
      	
    	choosePanel.updateButtons(newTrainer);
    	
    }
    
    /**
     * Updates the textPanel with new text based on the current state of the game.
     * Shows the "TEXT_PANEL" view and switches back to "CHOOSE_PANEL" or "LEARN_MOVE" based on conditions.
     * @param firstTrainer The first trainer in the battle.
     * @param secondTrainer The second trainer in the battle.
     */
    
    public void updateText(Trainer firstTrainer, Trainer secondTrainer) {
    		
    	textPanel.updateText();
    	
    	// Show the textField
        CardLayout cl = (CardLayout)(selectionPanel.getLayout());
        cl.show(selectionPanel, "TEXT_PANEL");
        selectionPanel.revalidate();
        selectionPanel.repaint();

        
        // Create a Timer to switch to the appropriate panel after 3 second
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	if(firstTrainer.getTeam()[0].getNewMove()) {
                	
            		learnMovePanel.updateButtons(firstTrainer);
                    cl.show(selectionPanel, "LEARN_MOVE");
                    selectionPanel.revalidate();
                    selectionPanel.repaint();
                    
                }else if (secondTrainer.getTeam()[0].getNewMove()) {
                	
                	learnMovePanel.updateButtons(secondTrainer);
                	cl.show(selectionPanel, "LEARN_MOVE");
                    selectionPanel.revalidate();
                    selectionPanel.repaint();
                }else {
                	
                	cl.show(selectionPanel, "CHOOSE_PANEL");
                    selectionPanel.revalidate();
                    selectionPanel.repaint();
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    /**
     * Shows the "CHOOSE_PANEL" view to allow the player to choose a move.
     */
    public void showButtons() {
    	   	
    	CardLayout cl = (CardLayout)(selectionPanel.getLayout());
    	cl.show(selectionPanel, "CHOOSE_PANEL");
        selectionPanel.revalidate();
        selectionPanel.repaint();
        
        
    }
    
}



