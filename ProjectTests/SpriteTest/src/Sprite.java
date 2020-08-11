import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {

    private static BufferedImage spriteSheet;
    private static final int Y_SIZE = 96;
    private static final int X_SIZE = 69;

    public static BufferedImage loadSprite() {

        BufferedImage sprite = null;

        try {
            sprite = ImageIO.read(new File("spritesheet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sprite;
    }

    public static BufferedImage getSprite(Card card) {
    	
    	int xGrid = 14-card.value();
    	int yGrid = card.suit()-1;
    	
    	
        if (spriteSheet == null) {
            spriteSheet = loadSprite();
        }

        return spriteSheet.getSubimage(xGrid * X_SIZE, yGrid * Y_SIZE, X_SIZE, Y_SIZE);
    }

    public static BufferedImage getBackSprite() {
    	
    	int xGrid = 1;
    	int yGrid = 4;
    	
    	
        if (spriteSheet == null) {
            spriteSheet = loadSprite();
        }

        return spriteSheet.getSubimage(xGrid * X_SIZE, yGrid * Y_SIZE, X_SIZE, Y_SIZE);
    }
    
    public static BufferedImage getDeckSprite() {
    	
    	int xGrid = 0;
    	int yGrid = 4;
    	
    	
        if (spriteSheet == null) {
            spriteSheet = loadSprite();
        }

        return spriteSheet.getSubimage(xGrid * X_SIZE, yGrid * Y_SIZE, X_SIZE, Y_SIZE);
    }
    
    public static BufferedImage getIconSprite() {
    	
    	int xGrid = 2;
    	int yGrid = 4;
    	
    	
        if (spriteSheet == null) {
            spriteSheet = loadSprite();
        }

        return spriteSheet.getSubimage(xGrid * X_SIZE, yGrid * Y_SIZE, X_SIZE, X_SIZE);
    }
    
    public static BufferedImage getChipSprite(int chip) {
    	
    	while (chip < 0) chip += 6;
    	
    	while (chip > 5) chip -= 6;
    	
    	int xGrid = 3+chip;
    	int yGrid = 4;
    	
    	
        if (spriteSheet == null) {
            spriteSheet = loadSprite();
        }

        return spriteSheet.getSubimage(xGrid * X_SIZE, yGrid * Y_SIZE, X_SIZE, X_SIZE);
    }
    
    
    
    
}