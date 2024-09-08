package battle.graphics;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import battle.logic.Pokemon;

import java.awt.*;


/**
 * PokemonStatsPanel is a custom JPanel that displays a Pokémon's stats.
 * It includes the Pokémon's name, level, and a health bar.
 */
public class PokemonStatsPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	JTextField nameField;
    JTextField levelField;
    JProgressBar healthBar;
    
    JLabel psLabel;
    
    //------------- health bar borders --------------------
    
    JLabel upperBorder;
    JLabel bottomBorder;
    JLabel rightBorder;
    JLabel leftBorder;
    
    //------------------------------------------------------

    
    /**
     * Constructs a PokemonStatsPanel with the specified Pokémon.
     * @param pokemon The Pokémon whose stats should be displayed.
     */
    public PokemonStatsPanel(Pokemon pokemon) {
    	
        this.setBackground(Constants.BACKGROUND_COLOR);
        this.setLayout(null);

        nameField = new JTextField("Name");
        nameField.setText(pokemon.getName().toUpperCase());
        nameField.setFont(new Font(Constants.FIRST_FONT.getName(), Font.PLAIN, 70));
        nameField.setBackground(Constants.BACKGROUND_COLOR);
        nameField.setBorder(new EmptyBorder(0, 0, 0, 0));
        nameField.setEditable(false);

        levelField = new JTextField("Level");
        levelField.setText(":L" + pokemon.getLevel());
        levelField.setFont(new Font(Constants.SECOND_FONT.getName(), Font.BOLD, 30));
        levelField.setBackground(Constants.BACKGROUND_COLOR);
        levelField.setBorder(new EmptyBorder(0, 0, 0, 0));
        levelField.setEditable(false);

        
        psLabel = new JLabel("PS:");
        psLabel.setFont(new Font("Pixellari", Font.BOLD, 30));
        
     
        healthBar = new JProgressBar();
        
        healthBar.setPreferredSize(new Dimension(Constants.BAR_LENGTH, Constants.HEALTH_BAR_BORDER * 2));
        healthBar.setValue(pokemon.getHp() * 100 / pokemon.getHpMaxCurrent());
        healthBar.setForeground(Constants.GREEN_BAR_COLOR);
        healthBar.setBackground(Constants.BACKGROUND_COLOR);
        healthBar.setStringPainted(false);
        
        //------------- health bar borders --------------------
        
        upperBorder = new JLabel();
        bottomBorder = new JLabel();
        rightBorder = new JLabel();
        leftBorder = new JLabel();
        
        upperBorder.setOpaque(true);
        bottomBorder.setOpaque(true);
        rightBorder.setOpaque(true);
        leftBorder.setOpaque(true);
        
        upperBorder.setBackground(Color.black);
        bottomBorder.setBackground(Color.black);
        rightBorder.setBackground(Color.black);
        leftBorder.setBackground(Color.black);
        
        //------------------------------------------------------
       
        
        this.add(nameField);
        this.add(levelField);
        
        this.add(psLabel);    
        this.add(healthBar);
        
        //------------- health bar borders --------------------
        
        this.add(upperBorder);
        this.add(bottomBorder);
        this.add(rightBorder);
        this.add(leftBorder);
        
        //------------------------------------------------------

    }

    @Override
    protected void paintComponent(Graphics g) {
    	
        super.paintComponent(g);

        int nameLabelWidth = nameField.getPreferredSize().width;
        int nameLabelHeight = nameField.getPreferredSize().height;

        int nameLabelX = (getWidth() - nameLabelWidth) / 2;
        int nameLabelY = getHeight()/5;
        
        nameField.setBounds(nameLabelX, nameLabelY, nameLabelWidth, nameLabelHeight);
        

        int levelFieldWidth = levelField.getPreferredSize().width;
        int levelFieldHeight = levelField.getPreferredSize().height;

        int levelFieldX = (getWidth() - levelFieldWidth) / 2;
        int levelFieldY = nameLabelY + nameLabelHeight;
        
        levelField.setBounds(levelFieldX, levelFieldY, levelFieldWidth, levelFieldHeight);
        

        int healthBarWidth = healthBar.getPreferredSize().width;
        int healthBarHeight = healthBar.getPreferredSize().height;

        int healthBarX = (getWidth() - healthBarWidth) / 2;
        int healthBarY = levelFieldY + levelFieldHeight + Constants.HEALTH_BAR_BORDER;
        
        healthBar.setBounds(healthBarX, healthBarY, healthBarWidth, healthBarHeight);
        
        
        int psLabelWidth = psLabel.getPreferredSize().width;
        int psLabelHeight = psLabel.getPreferredSize().height;
        
        int psLabelY = healthBarY + (int)(healthBarHeight/2) - (int)(psLabelHeight/2);
        int psLabelX = healthBarX - psLabelWidth - Constants.HEALTH_BAR_BORDER * 2;
        
        psLabel.setBounds(psLabelX, psLabelY, psLabelWidth, psLabelHeight);
        
        
        //------------- health bar borders --------------------     
        
        upperBorder.setBounds(healthBarX, healthBarY - Constants.HEALTH_BAR_BORDER, healthBarWidth, Constants.HEALTH_BAR_BORDER);
        leftBorder.setBounds(healthBarX - Constants.HEALTH_BAR_BORDER, healthBarY, Constants.HEALTH_BAR_BORDER, healthBarHeight);
        bottomBorder.setBounds(healthBarX, healthBarY + healthBarHeight, healthBarWidth, Constants.HEALTH_BAR_BORDER);
        rightBorder.setBounds(healthBarX + healthBarWidth, healthBarY, Constants.HEALTH_BAR_BORDER, healthBarHeight);
        
        //------------------------------------------------------
        
    }
    
    /**
     * Updates the stats displayed in the panel with the updated Pokémon information.
     * @param updatedPokemon The updated Pokémon whose stats should be displayed.
     */
    
    public void updateStats(Pokemon updatedPokemon) {
    	
    	nameField.setText(updatedPokemon.getName().toUpperCase());   	
    	levelField.setText(":L" + updatedPokemon.getLevel());   	
    	
    	int lifePercentage = (int) ((double) updatedPokemon.getHp() / updatedPokemon.getHpMaxCurrent() * 100);
    	healthBar.setValue(lifePercentage);
    	
    	if (lifePercentage >= 50) {
    		
    	    healthBar.setForeground(Constants.GREEN_BAR_COLOR);
    	    
    	} else if (lifePercentage <= 50 && lifePercentage >= 25) {
    		
    	    healthBar.setForeground(Constants.YELLOW_BAR_COLOR);
    	    
    	} else {
    		
    	    healthBar.setForeground(Constants.RED_BAR_COLOR);
    	    
    	}
    	
    	revalidate();
    	repaint();
  	  	
    }

}
