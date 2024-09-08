package battle.graphics;

import javax.imageio.ImageIO;
import javax.swing.*;

import battle.logic.Pokemon;
import map.logic.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * PokemonImagePanel is a custom JPanel that displays a Pokémon image.
 * It can update the image based on the Pokémon's name.
 */
public class PokemonImagePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	String pokemonName;
	String folder;
		
	BufferedImage image;
	JLabel imageLabel;
	
	UtilityTool uTool = new UtilityTool();
	
	/**
     * Constructs a PokemonImagePanel with the specified Pokémon and folder.
     * @param pokemon The Pokémon to display.
     * @param folder The folder containing the Pokémon images.
     */
	public PokemonImagePanel(Pokemon pokemon, String folder){
		
		 this.folder = folder;
		
		 pokemonName = pokemon.getName();
		 
		 this.setBackground(Constants.BACKGROUND_COLOR);
	     this.setLayout(new BorderLayout());

	     
	     // Load and scale the Pokémon image
	     try {
	    	 image = ImageIO.read(getClass().getResourceAsStream("/" + folder + "/" + PokemonImageMapper.getImageNumber(pokemonName) + ".png"));
	         image = uTool.scaleImage(image, image.getWidth() * 4, image.getHeight() * 4);
	     } catch (IOException e) {
	         e.printStackTrace(); // Handle the exception (e.g., log it, show error message)
	     }
	     
	
	     imageLabel = new JLabel(new ImageIcon(image)); // Create JLabel with image
	     this.add(imageLabel, BorderLayout.CENTER);
	        
	}
	
	/**
     * Updates the displayed Pokémon image based on the updated Pokémon information.
     * @param updatedPokemon The updated Pokémon whose image should be displayed.
     */
	public void updateImage(Pokemon updatedPokemon) {
		
		pokemonName = updatedPokemon.getName();
	    
	    // Load and scale the new Pokémon image
	    try {
	    	 image = ImageIO.read(getClass().getResourceAsStream("/" + folder + "/" + PokemonImageMapper.getImageNumber(pokemonName) + ".png"));
	         image = uTool.scaleImage(image, image.getWidth() * 4, image.getHeight() * 4);
	     } catch (IOException e) {
	         e.printStackTrace(); // Handle the exception (e.g., log it, show error message)
	     }
	    
	    // Update the JLabel with the new image
	    imageLabel.setIcon(new ImageIcon(image));
	}

}
