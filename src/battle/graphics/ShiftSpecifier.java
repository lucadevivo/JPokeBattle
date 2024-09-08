package battle.graphics;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * ShiftSpecifier is a custom JTextPane that displays the trainer's name and the turn message.
 * The trainer's name is displayed in a specified color, and the turn message is in black.
 */
public class ShiftSpecifier extends JTextPane {

	private static final long serialVersionUID = 1L;

	/**
     * Constructs a ShiftSpecifier with the specified trainer's name and color.
     * @param trainerName The name of the trainer.
     * @param color The color in which the trainer's name will be displayed.
     */
	public ShiftSpecifier(String trainerName, Color color) {
    	
		// Get the styled document to apply text styles
		StyledDocument doc = getStyledDocument();
	    
		
		// Create attribute set for the trainer's name style
	    SimpleAttributeSet nameStyle = new SimpleAttributeSet();
	    StyleConstants.setForeground(nameStyle, color);
	    
	    
	    // Create attribute set for the turn message style
	    SimpleAttributeSet turnStyle = new SimpleAttributeSet();
	    StyleConstants.setForeground(turnStyle, Color.BLACK);
	    	
	    
	    // Insert the styled text into the document
	    try {
	        doc.insertString(doc.getLength(), trainerName, nameStyle);
	        doc.insertString(doc.getLength(), "'s turn", turnStyle);
	    } catch (Exception e) {
	        System.out.println("Errore durante l'inserimento del testo: " + e);
	    }
	    
	    
	    // Set the font for the JTextPane
	    setFont(Constants.FIRST_FONT.deriveFont(Font.BOLD, 35));
     
        // Set the JTextPane properties
        setEditable(false);
        setBackground(Constants.BACKGROUND_COLOR);
        
    }
}




