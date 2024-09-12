package map.logic;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * Main class that starts the JPokeBattle game.
 */	
public class Main {
	
	/**
     * Main method that launches the game.
     *
     * @param args command-line arguments
     */
	public static void main(String[] args) {
		
		// Create a window (JFrame) for the game
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close application when window is closed
		window.setResizable(false);	// Disable window resizing
		window.setTitle("JPokeBattle");	// Set the window title
		
		// Set the window icon (logo)
        ImageIcon icon = new ImageIcon("res/logo.png"); // Load the logo image
        window.setIconImage(icon.getImage()); // Set the icon for the window
		
		GamePanel gamePanel = new GamePanel();	// Create the GamePanel (the core of the game) and add it to the window	
		window.add(gamePanel);
		
		// Adjust window size to fit the contents (the GamePanel)
		window.pack();
		
		// Center the window on the screen
		window.setLocationRelativeTo(null);
		// Make the window visible
		window.setVisible(true);
		
		gamePanel.setupGame();
		gamePanel.startGameThread();
	}
}