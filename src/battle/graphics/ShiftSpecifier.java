
package battle.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * ShiftSpecifier is a custom JPanel that displays the trainer's name and the turn message.
 * The trainer's name is displayed in a specified color, and the turn message is in black.
 */
public class ShiftSpecifier extends JPanel {

	private static final long serialVersionUID = 1L;

	JLabel text; // Label to display the turn message
	
	/**
	 * Constructs a ShiftSpecifier panel that shows which trainer's turn it is.
	 * 
	 * @param trainerName The name of the trainer whose turn it is.
	 * @param color The color in which the trainer's name should be displayed.
	 */
	public ShiftSpecifier(String trainerName, Color color) {
    	
		// Set background color and layout of the panel
		this.setBackground(Constants.BACKGROUND_COLOR);
        this.setLayout(null);
        
        // Create the text label
        text = new JLabel();
        
        // Convert the Color object to a hexadecimal string format for HTML
        String hexColor = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
        
        // Set the text to display the trainer's name and the turn message in HTML format
        text.setText("<html><font color='" + hexColor + "'>" + trainerName + "</font> 's turn</html>");
     
        // Set the font of the text
        text.setFont(Constants.FIRST_FONT.deriveFont(Font.BOLD, 30));
        
        this.add(text);
    }
	
	/**
	 * Overridden paintComponent method to handle custom component placement.
	 * 
	 * @param g The Graphics object used to draw the components.
	 */
	@Override
    protected void paintComponent(Graphics g) {
    	
        super.paintComponent(g);
        
        // Calculate the preferred size of the text label
        int textWidth = text.getPreferredSize().width;
        int textHeight = text.getPreferredSize().height;
        
        // Set the position and size of the label
        text.setBounds(0, 0, textWidth, textHeight);
	}
	
	/**
	 * Updates the displayed trainer's name and color.
	 * 
	 * @param newTrainerName The new trainer's name to display.
	 * @param color The color to display the new trainer's name in.
	 */
	public void updateName(String newTrainerName, Color color) {
		
		String hexColor = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
        text.setText("<html><font color='" + hexColor + "'>" + newTrainerName + "</font> 's turn</html>");
		
		revalidate();
    	repaint();
	}
}
