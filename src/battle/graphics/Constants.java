package battle.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

/**
 * The Costanti class contains constants used throughout the battle graphics.
 * It defines panel dimensions, colors, and fonts.
 */
public class Constants {

    // Panel dimensions
    public static final int PANEL_WIDTH = 1024;
    public static final int PANEL_HEIGHT = 768;

    // Health bar settings
    public static final int HEALTH_BAR_BORDER = 4;
    public static final int BAR_LENGTH = 150;

    // Colors used in the battle interface
    public static final Color BACKGROUND_COLOR = new Color(255, 239, 255);
    public static final Color GREEN_BAR_COLOR = new Color(107, 146, 109);
    public static final Color YELLOW_BAR_COLOR = new Color(205, 167, 78);
    public static final Color RED_BAR_COLOR = new Color(183, 110, 83);

    // Fonts used in the battle interface
    public static final Font FIRST_FONT;
    public static final Font SECOND_FONT;

    // Static block to load the first font
    static {
        Font tempFont = null;
        try (InputStream is = Constants.class.getResourceAsStream("/font/pokemon_fire_red.ttf")) {
            if (is != null) {
                tempFont = Font.createFont(Font.TRUETYPE_FONT, is);
            } else {
                System.err.println("Font resource not found.");
            }
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FIRST_FONT = tempFont;
    }

    // Static block to load the second font
    static {
        Font tempFont = null;
        try (InputStream is = Constants.class.getResourceAsStream("/font/Pixellari.ttf")) {
            if (is != null) {
                tempFont = Font.createFont(Font.TRUETYPE_FONT, is);
            } else {
                System.err.println("Font resource not found.");
            }
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SECOND_FONT = tempFont;
    }
}




