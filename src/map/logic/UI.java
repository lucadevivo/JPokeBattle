package map.logic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import battle.graphics.Constants;

public class UI {

	GamePanel gp;
	Graphics2D g2;
	
	private BufferedImage image1;
	private BufferedImage image2;
	
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	public int commandNum = 0;
	public int dialogueChoice = 0;
	public int endChoice = 0;
	public int profileChoice = 0;
	public String currentDialogue = "";
	public static final Color BACKGROUND_COLOR = Color.WHITE;
	
	public UI(GamePanel gp) {	
		
		this.gp = gp;
		loadImage();
	}
	private void loadImage() {
		
	    Random random = new Random();
	    
	    try {
	    	
	        int randomIndex1 = random.nextInt(151) + 1; // Genera un numero casuale tra 1 e 151
	        int randomIndex2 = random.nextInt(151) + 1; // Genera un numero casuale tra 1 e 151
	        
	        UtilityTool uTool = new UtilityTool();
	        
	        image1 = ImageIO.read(getClass().getResourceAsStream("/pokemonbacks/" + randomIndex1 + ".png"));
	        image1 = uTool.scaleImage(image1, image1.getWidth() * 4, image1.getWidth() * 4);
	        
	        image2 = ImageIO.read(getClass().getResourceAsStream("/pokemonfronts/" + randomIndex2 + ".png"));
	        image2 = uTool.scaleImage(image2, gp.tileSize * 4, gp.tileSize * 4);
	        
	    } 
	    catch (IOException e) {
	    	
	        e.printStackTrace();
	    }
	}
	public void showMessage(String text) {
		
		message = text;
		messageOn = true;
	}
	public void draw(Graphics2D g2) {
		
        this.g2 = g2;

        g2.setFont(Constants.SECOND_FONT);
        g2.setColor(Color.BLACK);

        // Disegna lo schermo appropriato in base allo stato del gioco
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        } else if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        } else if (gp.gameState == gp.endState) {
            drawEndScreen();
        } else if (gp.gameState == gp.profileState) {
            drawProfileScreen();
        }
    }
	public int getXForCenteredText(String text) {
		
		int x;
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		x = gp.screenWidth/2 - length/2;
		
		return x;
	}
	public void drawTitleScreen() {
		
	    g2.setColor(BACKGROUND_COLOR);
	    g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
	    
	    g2.setFont(Constants.SECOND_FONT.deriveFont(Font.BOLD, 120F));
	    String text = "JPokeBattle";
	    int x = getXForCenteredText(text);
	    int y = gp.tileSize * 4;

	    // Disegna il bordo blu attorno alla scritta "JPokeBattle"
	    g2.setColor(new Color(57, 95, 107));
	    int borderSize = 9; // Dimensione del bordo
	    for (int i = -borderSize; i <= borderSize; i++) {
	        for (int j = -borderSize; j <= borderSize; j++) {
	            g2.drawString(text, x + i, y + j);
	        }
	    }
	    
	    // Disegna il testo principale al centro
	    g2.setColor(new Color(255, 203, 5));
	    g2.drawString(text, x, y);


	    g2.setColor(Color.black);

	    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 36F));
	    text = "play game";
	    x = getXForCenteredText(text);
	    y += gp.tileSize * 3;
	    g2.drawString(text, x, y);
	    if (commandNum == 0) {
	        g2.drawString(">", x - gp.tileSize, y);
	    }
	    text = "quit game";
	    x = getXForCenteredText(text);
	    y += gp.tileSize;
	    g2.drawString(text, x, y);
	    if (commandNum == 1) {
	        g2.drawString(">", x - gp.tileSize, y);
	    }

	    int img1X = 50;
	    int img1Y = gp.screenHeight - image1.getHeight() - 50;
	    
	    int img2X = gp.screenWidth - image2.getWidth() - 50;
	    int img2Y = gp.screenHeight - image2.getHeight() - 50;
	    
	    g2.drawImage(image1, img1X, img1Y, null);
	    g2.drawImage(image2, img2X, img2Y, null);
	}

	public void drawDialogueScreen() {
        // WINDOW
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 7;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;

        drawSubWindow(x, y, width, height);

        g2.setFont(Constants.FIRST_FONT.deriveFont(Font.PLAIN, 48F));
        int textX = x + gp.tileSize;
        int textY = y + gp.tileSize;

        // DRAW DIALOGUE TEXT
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // DRAW CHOICES
        int choiceY = y + height - gp.tileSize; // Adjust this value to move choices higher or lower in the box
        int choiceX = x + gp.tileSize * 2; // Fixed position from the left side of the box

        String text = "accetta";
        g2.drawString(text, choiceX, choiceY);
        if (dialogueChoice == 0) {
            g2.drawString(">", choiceX - gp.tileSize, choiceY);
        }

        text = "rifiuta";
        choiceY += 40; // Adjust this value to change spacing between choices
        g2.drawString(text, choiceX, choiceY);
        if (dialogueChoice == 1) {
            g2.drawString(">", choiceX - gp.tileSize, choiceY);
        }
    }
	public void drawEndScreen() {
		
		g2.setColor(BACKGROUND_COLOR);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
        g2.setFont(Constants.SECOND_FONT.deriveFont(Font.BOLD, 120F));

        String text;
        if (gp.winner) {
            text = "YOU WON!";
        } else {
            text = "YOU LOST!";
        }

        int x = getXForCenteredText(text);
        int y = gp.tileSize * 4;

        g2.setColor(Color.BLACK);
        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 36F));
        text = "play again";
        x = getXForCenteredText(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);

        if (endChoice == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }
        text = "quit battle";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);

        if (endChoice == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }
        text = "quit game";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);

        if (endChoice == 2) {
            g2.drawString(">", x - gp.tileSize, y);
        }
    }
	public void drawProfileScreen() {
	    // Imposta lo sfondo bianco
	    g2.setColor(Color.WHITE);
	    g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

	    // Imposta il colore del testo a nero
	    g2.setColor(Color.BLACK);
	    g2.setFont(Constants.SECOND_FONT.deriveFont(Font.BOLD, 36F));
	    String text = "Seleziona Profilo";
	    int x = getXForCenteredText(text);
	    int y = gp.tileSize * 4;

	    g2.drawString(text, x, y);

	    // Disegna "Profilo 1" e la freccia se selezionato
	    text = "Profilo 1";
	    x = getXForCenteredText(text);
	    y += gp.tileSize;
	    g2.drawString(text, x, y);
	    if (profileChoice == 0) {
	        g2.drawString(">", x - gp.tileSize, y);
	    }

	    // Disegna "Profilo 2" e la freccia se selezionato
	    text = "Profilo 2";
	    x = getXForCenteredText(text);
	    y += gp.tileSize;
	    g2.drawString(text, x, y);
	    if (profileChoice == 1) {
	        g2.drawString(">", x - gp.tileSize, y);
	    }

	    // Disegna "Profilo 3" e la freccia se selezionato
	    text = "Profilo 3";
	    x = getXForCenteredText(text);
	    y += gp.tileSize;
	    g2.drawString(text, x, y);
	    if (profileChoice == 2) {
	        g2.drawString(">", x - gp.tileSize, y);
	    }

	    // Disegna "indietro" e la freccia se selezionato
	    text = "indietro";
	    x = getXForCenteredText(text);
	    y += gp.tileSize;
	    g2.drawString(text, x, y);
	    if (profileChoice == 3) {
	        g2.drawString(">", x - gp.tileSize, y);
	    }

	    // Disegna le immagini
	    int img1X = 50;
	    int img1Y = gp.screenHeight - image1.getHeight() - 50;

	    int img2X = gp.screenWidth - image2.getWidth() - 50;
	    int img2Y = gp.screenHeight - image2.getHeight() - 50;

	    g2.drawImage(image1, img1X, img1Y, null);
	    g2.drawImage(image2, img2X, img2Y, null);
	}


	public void drawSubWindow(int x,int y,int width,int height) {
		
		Color c = new Color(0,0,0,230);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		// WHITE BORDER
		c = new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
	}
}