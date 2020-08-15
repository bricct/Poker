import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {

    private static BufferedImage spriteSheet, assetSheet, optionsSheet;
    private static final int Y_SIZE = 96;
    private static final int X_SIZE = 69;
    private static final int AX_SIZE = 288;
    private static final int AY_SIZE = 108;
    private static final int OX_SIZE = 186;
    private static final int OY_SIZE = 84;
    private static final int OX_SMALL_SIZE = 18;
    

    public static BufferedImage loadSprite() {

        BufferedImage sprite = null;

        try {
            sprite = ImageIO.read(new File("spritesheet.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return sprite;
    }
    
    public static BufferedImage loadAssets() {

        BufferedImage assets = null;

        try {
            assets = ImageIO.read(new File("assets.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return assets;
    }
    
    public static BufferedImage loadOptionsAssets() {

        BufferedImage options = null;

        try {
        	options = ImageIO.read(new File("options_assets.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return options;
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

	public static BufferedImage getVolumeSprite(int i) {
		
		int xGrid = 11 + i;
    	int yGrid = 4;
    	
    	
        if (spriteSheet == null) {
            spriteSheet = loadSprite();
        }

        return spriteSheet.getSubimage(xGrid * X_SIZE, yGrid * Y_SIZE, X_SIZE, X_SIZE);
		
		
	}
	
	
	public static BufferedImage getButtonSprite() {
		
		int xGrid = 0;
    	int yGrid = 0;
    	
    	
        if (assetSheet == null) {
            assetSheet = loadAssets();
        }

        return assetSheet.getSubimage(xGrid * AX_SIZE, yGrid * AY_SIZE, AX_SIZE, AY_SIZE);
		
		
	}
    
	
	public static BufferedImage getMusicOnSprite() {
			
			int xGrid = 3;
	    	int yGrid = 0;
	    	
	    	
	        if (assetSheet == null) {
	            assetSheet = loadAssets();
	        }
	
	        return assetSheet.getSubimage((xGrid - 1) * AY_SIZE + AX_SIZE, yGrid * AY_SIZE, AY_SIZE, AY_SIZE);
			
			
		}
	
	public static BufferedImage getMusicOffSprite() {
		
		int xGrid = 4;
    	int yGrid = 0;
    	
    	
        if (assetSheet == null) {
            assetSheet = loadAssets();
        }

        return assetSheet.getSubimage((xGrid - 1) * AY_SIZE + AX_SIZE, yGrid * AY_SIZE, AY_SIZE, AY_SIZE);
		
		
	}
	
	public static BufferedImage getOptionsSprite() {
		
		int xGrid = 2;
    	int yGrid = 0;
    	
    	
        if (assetSheet == null) {
            assetSheet = loadAssets();
        }

        return assetSheet.getSubimage((xGrid - 1) * AY_SIZE + AX_SIZE, yGrid * AY_SIZE, AY_SIZE, AY_SIZE);
		
		
	}
	
	public static BufferedImage getCloseSprite() {
		
		int xGrid = 1;
    	int yGrid = 0;
    	
    	
        if (assetSheet == null) {
            assetSheet = loadAssets();
        }

        return assetSheet.getSubimage((xGrid - 1) * AY_SIZE + AX_SIZE, yGrid * AY_SIZE, AY_SIZE, AY_SIZE);
		
		
	}
	
	public static BufferedImage getSaveSprite() {
		
		int xGrid = 5;
    	int yGrid = 0;
    	
    	
        if (assetSheet == null) {
            assetSheet = loadAssets();
        }

        return assetSheet.getSubimage((xGrid - 1) * AY_SIZE + AX_SIZE, yGrid * AY_SIZE, AY_SIZE, AY_SIZE);
		
		
	}
    
	
	
	public static BufferedImage getFormSprite() {
			
			int xGrid = 0;
	    	int yGrid = 0;
	    	
	    	
	        if (optionsSheet == null) {
	        	optionsSheet = loadOptionsAssets();
	        }
	
	        return optionsSheet.getSubimage(xGrid * OX_SIZE, yGrid * OY_SIZE, OX_SIZE, OY_SIZE);
			
			
		}

	public static BufferedImage getColonSprite() {
		
		int xGrid = 1;
		int yGrid = 0;
		
		
	    if (optionsSheet == null) {
	    	optionsSheet = loadOptionsAssets();
	    }
	
	    return optionsSheet.getSubimage((xGrid - 1) * OX_SMALL_SIZE + OX_SIZE, yGrid * AY_SIZE, OX_SMALL_SIZE, OY_SIZE);
		
		
	}
    
    
}