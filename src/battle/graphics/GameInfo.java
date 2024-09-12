package battle.graphics;

import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * GameInfo is a custom JPanel that displays information about the chosen profile and the trainers' wins.
 */
public class GameInfo extends JPanel{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    JLabel profileLabel;
    JLabel winsLabel;
    
    /**
     * Constructor for the GameInfo panel.
     * 
     * @param chosenProfile   The selected profile index (0-based).
     * @param firstTrainerName Name of the first trainer.
     * @param secondTrainerName Name of the second trainer.
     * @param firstTrainerWins Number of wins for the first trainer.
     * @param secondTrainerWins Number of wins for the second trainer.
     */
    public GameInfo(int chosenProfile, String firstTrainerName, String secondTrainerName, int firstTrainerWins, int secondTrainerWins) {
        
        // Set the background color and layout
        this.setBackground(Constants.BACKGROUND_COLOR);
        this.setLayout(null);
        
        // Label for displaying the chosen profile
        profileLabel = new JLabel("Profile " + (chosenProfile + 1));
        profileLabel.setFont(Constants.FIRST_FONT.deriveFont(Font.BOLD, 30));
        
        // Label for displaying the wins of the trainers
        winsLabel = new JLabel("<html><font color='red'>" + firstTrainerName + "</font> " + firstTrainerWins + " / " + "<font color='blue'>" + secondTrainerName + "</font> " + secondTrainerWins + "</html>");
        winsLabel.setFont(Constants.FIRST_FONT.deriveFont(Font.BOLD, 30));
        
        // Add the labels to the panel
        this.add(profileLabel);
        this.add(winsLabel);
    }
    
    /**
     * Overridden paintComponent method to customize the positioning of labels.
     * @param g Graphics object used for drawing the components.
     */
    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        
        // Calculate the dimensions and position for the profileLabel
        int profileLabelWidth = profileLabel.getPreferredSize().width;
        int profileLabelHeight = profileLabel.getPreferredSize().height;

        int profileLabelX = (getWidth() - profileLabelWidth) / 2;
        int profileLabelY = getHeight() / 5;
        
        profileLabel.setBounds(profileLabelX, profileLabelY, profileLabelWidth, profileLabelHeight);
        
        // Calculate the dimensions and position for the winsLabel
        int winsLabelWidth = winsLabel.getPreferredSize().width;
        int winsLabelHeight = winsLabel.getPreferredSize().height;

        int winsLabelX = (getWidth() - winsLabelWidth) / 2;
        int winsLabelY = profileLabelY + profileLabelHeight;
        
        winsLabel.setBounds(winsLabelX, winsLabelY, winsLabelWidth, winsLabelHeight);
    }
 
    /**
     * Updates the panel by revalidating and repainting it.
     */
    public void update() {
    
        revalidate();
        repaint();
        
    }
}
