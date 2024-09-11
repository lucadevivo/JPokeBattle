package battle.graphics;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameInfo extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JLabel profileLabel;
	JLabel winsLabel;
	
	public GameInfo(int chosenProfile, String firstTrainerName, String secondTrainerName, int firstTrainerWins, int secondTrainerWins) {
		
		
		this.setBackground(Constants.BACKGROUND_COLOR);
        this.setLayout(null);
        
		profileLabel = new JLabel("Profile " + (chosenProfile + 1));
		profileLabel.setFont(Constants.FIRST_FONT.deriveFont(Font.BOLD, 30));
		
		winsLabel = new JLabel("<html><font color='red'>" + firstTrainerName + "</font> " + firstTrainerWins + " / " + "<font color='blue'>" + secondTrainerName + "</font> " + secondTrainerWins + "</html>");
		winsLabel.setFont(Constants.FIRST_FONT.deriveFont(Font.BOLD, 30));
		
		this.add(profileLabel);
		this.add(winsLabel);
	}
	
	@Override
    protected void paintComponent(Graphics g) {
    	
        super.paintComponent(g);
        
        int profileLabelWidth = profileLabel.getPreferredSize().width;
        int profileLabelHeight = profileLabel.getPreferredSize().height;

        int profileLabelX = (getWidth() - profileLabelWidth) / 2;
        int profileLabelY = getHeight()/5;
        
        profileLabel.setBounds(profileLabelX, profileLabelY, profileLabelWidth, profileLabelHeight);
        
        int winsLabelWidth = winsLabel.getPreferredSize().width;
        int winsLabelHeight = winsLabel.getPreferredSize().height;

        int winsLabelX = (getWidth() - winsLabelWidth) / 2;
        int winsLabelY = profileLabelY + profileLabelHeight;
        
        winsLabel.setBounds(winsLabelX, winsLabelY, winsLabelWidth, winsLabelHeight);
	}
	
	public void update() {
	
    	revalidate();
    	repaint();
  	  	
    }
}
