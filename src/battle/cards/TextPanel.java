package battle.cards;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import battle.graphics.Constants;
import battle.logic.GameLogic;
import java.awt.*;


/**
 * The TextPanel class is a custom JPanel that displays two lines of text with icons.
 * It is used to show messages such as moves used and Pokémon status.
 */
public class TextPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JLabel upperLabel;
    private JLabel lowerLabel;
    private ImageIcon upperIcon;
    private ImageIcon lowerIcon;
    /**
     * Constructs a TextPanel with specified upper and lower text.
     * @param upperText the text for the upper label
     * @param lowerText the text for the lower label
     */
    public TextPanel(String upperText, String lowerText) {
    	
        super(new GridLayout(2, 1));  // 2 rows, 1 column
        this.setBackground(Constants.BACKGROUND_COLOR);
        this.setBorder(new EmptyBorder(0, 0, 0, 0));

        // Load icons
        upperIcon = new ImageIcon("res/battleutils/blueBall.png");
        lowerIcon = new ImageIcon("res/battleutils/redBall.png");

        // Initialize upper label
        upperLabel = new JLabel(upperText, upperIcon, SwingConstants.LEFT);
        upperLabel.setFont(new Font(Constants.FIRST_FONT.getName(), Font.BOLD, 35));
        upperLabel.setBackground(Constants.BACKGROUND_COLOR);
        upperLabel.setIconTextGap(20);
        upperLabel.setOpaque(true);

        // Initialize lower label
        lowerLabel = new JLabel(lowerText, lowerIcon, SwingConstants.LEFT);
        lowerLabel.setFont(new Font(Constants.FIRST_FONT.getName(), Font.BOLD, 35));
        lowerLabel.setBackground(Constants.BACKGROUND_COLOR);
        lowerLabel.setIconTextGap(20);
        lowerLabel.setOpaque(true);

        // Add labels to the panel
        this.add(upperLabel);
        this.add(lowerLabel);
    }

    // Method for updating text
    public void updateText() {
    	
        setLabel(lowerLabel, GameLogic.pokemonExhausted1, GameLogic.changePokemon1, GameLogic.usedMove1, GameLogic.effectiveness1, GameLogic.critic1);
        setLabel(upperLabel, GameLogic.pokemonExhausted2, GameLogic.changePokemon2, GameLogic.usedMove2, GameLogic.effectiveness2, GameLogic.critic2);
        resetGameLogic();
    }

    /**
     * Sets the text of a label based on different parameters.
     * @param label the JLabel to update
     * @param exhaustedPokemon the exhausted Pokémon text
     * @param changePokemon the Pokémon change text
     * @param usedMove the move used text
     * @param effectiveness the effectiveness text
     * @param critic the critical hit text
     */
    private void setLabel(JLabel label, String exhaustedPokemon, String changePokemon, String usedMove, String effectiveness, String critic) {
    	
        if (!exhaustedPokemon.equals("")) {
            label.setText(exhaustedPokemon);
        } else if (!changePokemon.equals("")) {
            label.setText(changePokemon);
        } else if (!usedMove.equals("")) {
            StringBuilder labelText = new StringBuilder(usedMove);
            
            if (!effectiveness.equals("")) {
                labelText.append("   /   ").append(effectiveness);
            }
            
            if (!critic.equals("")) {
                labelText.append("   /   ").append(critic);
            }
            
            label.setText(labelText.toString());
        }
    }

    /**
     * Resets the game logic state variables to empty strings.
     */
    private void resetGameLogic() {
    	
        GameLogic.usedMove1 = "";
        GameLogic.effectiveness1 = "";
        GameLogic.critic1 = "";
        GameLogic.changePokemon1 = "";
        GameLogic.pokemonExhausted1 = "";

        GameLogic.usedMove2 = "";
        GameLogic.effectiveness2 = "";
        GameLogic.critic2 = "";
        GameLogic.changePokemon2 = "";
        GameLogic.pokemonExhausted2 = "";
    }

}




