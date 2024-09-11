package battle.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * ShiftSpecifier is a custom JPanel that displays the trainer's name and the turn message.
 * The trainer's name is displayed in a specified color, and the turn message is in black.
 */
public class ShiftSpecifier extends JPanel {

	private static final long serialVersionUID = 1L;

	JLabel text;
	
	public ShiftSpecifier(String trainerName, Color color) {
    	
		this.setBackground(Constants.BACKGROUND_COLOR);
        this.setLayout(null);
        
        
        text = new JLabel();
        String hexColor = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
        text.setText("<html><font color='" + hexColor + "'>" + trainerName + "</font> 's turn</html>");
        text.setFont(Constants.FIRST_FONT.deriveFont(Font.BOLD, 30));
        
        this.add(text);
    }
	
	@Override
    protected void paintComponent(Graphics g) {
    	
        super.paintComponent(g);
        
        int textWidth = text.getPreferredSize().width;
        int textHeight = text.getPreferredSize().height;
        
        text.setBounds(0, 0, textWidth, textHeight);
	}
	
	
	public void updateName(String newTrainerName, Color color) {
		
		String hexColor = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
        text.setText("<html><font color='" + hexColor + "'>" + newTrainerName + "</font> 's turn</html>");
		
		revalidate();
    	repaint();
	}
}




