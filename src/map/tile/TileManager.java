package map.tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import map.logic.GamePanel;
import map.logic.UtilityTool;

/**
 * The TileManager class handles tile management, including loading tile images,
 * setting up tile properties, loading the map layout from a file, and drawing tiles 
 * on the screen based on the player's position.
 */
public class TileManager extends Tile {

    // Reference to the main game panel
    GamePanel gp;

    // Array of tiles that holds different tile types
    public Tile[] tile;

    // 2D array representing the map layout with tile indices
    public int mapTileNum[][];
    
    /**
     * Constructor initializes TileManager with a reference to the GamePanel
     * @param gp
     */
    public TileManager(GamePanel gp) {
        this.gp = gp;

        // Initialize the tile array with space for 10 different tile types
        tile = new Tile[10];

        // Initialize the map with dimensions based on the maximum world columns and rows
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        // Load tile images and map layout from resources
        getTileImage();
        loadMap("/maps/room.txt");
    }

    // Loads tile images and sets up their properties
    public void getTileImage() {
        // Each tile is set up with its image and collision properties
        setup(0, "wall", true);          // Wall tile with collision
        setup(1, "floor", false);        // Floor tile without collision
        setup(2, "blackBorder", true);   // Black border tile with collision
        setup(3, "potBottom", true);     // Pot bottom tile with collision
        setup(4, "potTop", true);        // Pot top tile with collision
        setup(5, "joystick", true);      // Joystick tile with collision
        setup(6, "monitor", true);       // Monitor tile with collision
        setup(7, "stairs", true);        // Stairs tile with collision
    }

    // Sets up each tile's image, scales it, and sets collision properties
    public void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {
            // Initialize the tile at the given index
            tile[index] = new Tile();

            // Load the image for the tile
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));

            // Scale the image to fit the tile size defined in the GamePanel
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);

            // Set the collision property of the tile
            tile[index].collision = collision;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Loads the map layout from a text file and fills the mapTileNum array
    public void loadMap(String filePath) {
        try {
            // Open the map file as a stream
            InputStream is = getClass().getResourceAsStream(filePath);

            // Read the file line by line
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            // Continue reading lines until the map dimensions are filled
            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine(); // Read a line from the file

                // Split the line into numbers separated by spaces
                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");

                    // Convert the string number to an integer and set it in the map array
                    int num = Integer.parseInt(numbers[col]);

                    // Assign the tile number to the map coordinates
                    mapTileNum[col][row] = num;
                    col++;
                }

                // Move to the next row after finishing the current one
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close(); // Close the buffered reader
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Draws the tiles on the screen based on the player's position
    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        // Traverse through the entire map
        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow]; // Get the tile number at the current position

            // Calculate the world position of the tile
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;

            // Calculate the screen position of the tile relative to the player's position
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // Check if the tile is within the visible screen boundaries before drawing
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                // Draw the tile image on the screen
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }

            worldCol++; // Move to the next column

            // Move to the next row when the end of the current row is reached
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}