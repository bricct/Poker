package ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import game.Card;

/**
 * Main graphic class for all the graphics of the game.
 *
 * This class includes all custom card graphics, fonts,
 * board ui, and chips.
 */
public class Sprite {

    private static BufferedImage spriteSheet, assetSheet, optionsSheet, youWin, youLose;
    private static final int Y_SIZE = 96;
    private static final int X_SIZE = 69;
    private static final int AX_SIZE = 288;
    private static final int AX_BIG = 360;
    private static final int AY_SIZE = 108;
    private static final int OX_SIZE = 186;
    private static final int OY_SIZE = 84;
    private static final int OX_SMALL_SIZE = 18;

    /**
     * Loads a sprite image and returns it as a buffered image
     * @return buffered image of the sprite
     */
    public static BufferedImage loadSprite() {

        BufferedImage sprite = null;

        try {
            sprite = ImageIO.read(new File("resources/sprites/spritesheet.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return sprite;
    }

    /**
     * Loads a asset image and returns it as a buffered image
     * @return buffered image of the asset
     */
    public static BufferedImage loadAssets() {

        BufferedImage assets = null;

        try {
            assets = ImageIO.read(new File("resources/sprites/assets.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return assets;
    }

    /**
     * Loads a options asset image and returns it as a buffered image
     * @return buffered image of the options asset
     */
    public static BufferedImage loadOptionsAssets() {

        BufferedImage options = null;

        try {
        	options = ImageIO.read(new File("resources/sprites/optionsAssets.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return options;
    }

    /**
     * Returns a buffered image of a card
     * @param card to turn into graphic
     * @return buffered image of the card
     */
    public static BufferedImage getSprite(Card card) {

    	int xGrid = 14-card.value();
    	int yGrid = card.suit()-1;


        if (spriteSheet == null) {
            spriteSheet = loadSprite();
        }

        return spriteSheet.getSubimage(xGrid * X_SIZE, yGrid * Y_SIZE, X_SIZE, Y_SIZE);
    }

    /**
     * @return buffered image of the back of the card
     */
    public static BufferedImage getBackSprite() {

    	int xGrid = 1;
    	int yGrid = 4;


        if (spriteSheet == null) {
            spriteSheet = loadSprite();
        }

        return spriteSheet.getSubimage(xGrid * X_SIZE, yGrid * Y_SIZE, X_SIZE, Y_SIZE);
    }

    /**
     * @return buffered image of the deck sleeve
     */
    public static BufferedImage getDeckSprite() {

    	int xGrid = 0;
    	int yGrid = 4;


        if (spriteSheet == null) {
            spriteSheet = loadSprite();
        }

        return spriteSheet.getSubimage(xGrid * X_SIZE, yGrid * Y_SIZE, X_SIZE, Y_SIZE);
    }

    /**
     * @return buffered image of the icon
     */
    public static BufferedImage getIconSprite() {

    	int xGrid = 2;
    	int yGrid = 4;


        if (spriteSheet == null) {
            spriteSheet = loadSprite();
        }

        return spriteSheet.getSubimage(xGrid * X_SIZE, yGrid * Y_SIZE, X_SIZE, X_SIZE);
    }

    /**
     * @param chip value of chips
     * @return buffered image of the that is the value
     */
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

    /**
     * @param i value of volume
     * @return buffered image of the volume sprite in font
     */
	public static BufferedImage getVolumeSprite(int i) {

		int xGrid = 11 + i;
    	int yGrid = 4;


        if (spriteSheet == null) {
            spriteSheet = loadSprite();
        }

        return spriteSheet.getSubimage(xGrid * X_SIZE, yGrid * Y_SIZE, X_SIZE, X_SIZE);


	}

	/**
     * @return buffered image of the button
     */
	public static BufferedImage getButtonSprite() {

		int xGrid = 0;
    	int yGrid = 1;


        if (assetSheet == null) {
            assetSheet = loadAssets();
        }

        return assetSheet.getSubimage(xGrid * AX_SIZE, yGrid * AY_SIZE, AX_SIZE, AY_SIZE);


	}

	/**
     * @return buffered image of the big button
     */
	public static BufferedImage getBigButtonSprite() {

		int xGrid = 1;
    	int yGrid = 1;


        if (assetSheet == null) {
            assetSheet = loadAssets();
        }

        return assetSheet.getSubimage(xGrid * AX_SIZE, yGrid * AY_SIZE, AX_BIG, AY_SIZE);


	}

	/**
     * @return buffered image of the music sprite
     */
	public static BufferedImage getMusicOnSprite() {

		int xGrid = 2;
    	int yGrid = 0;


        if (assetSheet == null) {
            assetSheet = loadAssets();
        }

        return assetSheet.getSubimage(xGrid  * AY_SIZE, yGrid * AY_SIZE, AY_SIZE, AY_SIZE);


		}

    /**
     * @return buffered image of the music off sprite
     */
	public static BufferedImage getMusicOffSprite() {

		int xGrid = 3;
    	int yGrid = 0;


        if (assetSheet == null) {
            assetSheet = loadAssets();
        }

        return assetSheet.getSubimage(xGrid  * AY_SIZE, yGrid * AY_SIZE, AY_SIZE, AY_SIZE);


	}

    /**
     * @return buffered image of the options icon
     */
	public static BufferedImage getOptionsSprite() {

		int xGrid = 1;
    	int yGrid = 0;


        if (assetSheet == null) {
            assetSheet = loadAssets();
        }

        return assetSheet.getSubimage(xGrid  * AY_SIZE, yGrid * AY_SIZE, AY_SIZE, AY_SIZE);


	}

    /**
     * @return buffered image of the close icon
     */
	public static BufferedImage getCloseSprite() {

		int xGrid = 0;
    	int yGrid = 0;


        if (assetSheet == null) {
            assetSheet = loadAssets();
        }

        return assetSheet.getSubimage(xGrid  * AY_SIZE, yGrid * AY_SIZE, AY_SIZE, AY_SIZE);


	}

    /**
     * @return buffered image of the save icon
     */
	public static BufferedImage getSaveSprite() {

		int xGrid = 4;
    	int yGrid = 0;


        if (assetSheet == null) {
            assetSheet = loadAssets();
        }

        return assetSheet.getSubimage(xGrid  * AY_SIZE, yGrid * AY_SIZE, AY_SIZE, AY_SIZE);


	}

	/**
     * @return buffered image of the return icon
     */
	public static BufferedImage getReturnSprite() {

		int xGrid = 5;
    	int yGrid = 0;


        if (assetSheet == null) {
            assetSheet = loadAssets();
        }

        return assetSheet.getSubimage(xGrid  * AY_SIZE, yGrid * AY_SIZE, AY_SIZE, AY_SIZE);


	}

	/**
     * @return buffered image of the form
     */
	public static BufferedImage getFormSprite() {

		int xGrid = 0;
    	int yGrid = 0;


        if (optionsSheet == null) {
        	optionsSheet = loadOptionsAssets();
        }

        return optionsSheet.getSubimage(xGrid * OX_SIZE, yGrid * OY_SIZE, OX_SIZE, OY_SIZE);


	}

    /**
     * @return buffered image of the colon
     */
	public static BufferedImage getColonSprite() {

		int xGrid = 1;
		int yGrid = 0;


	    if (optionsSheet == null) {
	    	optionsSheet = loadOptionsAssets();
	    }

	    return optionsSheet.getSubimage((xGrid - 1) * OX_SMALL_SIZE + OX_SIZE, yGrid * AY_SIZE, OX_SMALL_SIZE, OY_SIZE);
	}

    /**
     * @return buffered image of the Up Arrow
     */
	public static BufferedImage getArrowUpSprite() {

		int xGrid = 2;
		int yGrid = 0;


	    if (optionsSheet == null) {
	    	optionsSheet = loadOptionsAssets();
	    }

	    return optionsSheet.getSubimage((xGrid - 1) * OX_SMALL_SIZE + OX_SIZE, yGrid * AY_SIZE, OX_SMALL_SIZE, OX_SMALL_SIZE);
	}

    /**
     * @return buffered image of the Down Arrow
     */
	public static BufferedImage getArrowDownSprite() {

		int xGrid = 3;
		int yGrid = 0;


	    if (optionsSheet == null) {
	    	optionsSheet = loadOptionsAssets();
	    }

	    return optionsSheet.getSubimage((xGrid - 1) * OX_SMALL_SIZE + OX_SIZE, yGrid * AY_SIZE, OX_SMALL_SIZE, OX_SMALL_SIZE);
	}

    public static BufferedImage getWin() {
    	if (youWin == null);

        try {
            youWin = ImageIO.read(new File("resources/sprites/youWin.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return youWin;
    }

    public static BufferedImage getLose() {
    	if (youLose == null);

        try {
            youLose = ImageIO.read(new File("resources/sprites/youLose.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return youLose;
    }





}
