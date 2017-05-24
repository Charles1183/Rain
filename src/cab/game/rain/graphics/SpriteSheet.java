package cab.game.rain.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

//this class reads in a sheet of sprite for referencing later when creating
//individual sprites.
public class SpriteSheet {
	
	private String path;
	public final int SIZE;
	public final int SPRITE_WIDTH, SPRITE_HEIGHT;
	private int width, height;
	public int [] pixels;
	
	//public static SpriteSheet tiles = new SpriteSheet ("/textures/tilesheet.png",256);
	public static SpriteSheet playerImage = new SpriteSheet ("/textures/playersheetX.png",256);
	public static SpriteSheet projectileImage = new SpriteSheet ("/textures/projectilesheet.png", 128);//add this title in the projectile paint file
	public static SpriteSheet spawn_level = new SpriteSheet ("/textures/tilesheet.png",256);//change .png file later
	
	public static SpriteSheet player1 = new SpriteSheet ("/textures/playersheet1.png",192,128);
	public static SpriteSheet player_down = new SpriteSheet(player1,0,0,1,3,32);
	public static SpriteSheet player_up = new SpriteSheet(player1,1,0,1,3,32);
	public static SpriteSheet player_left = new SpriteSheet(player1,2,0,1,3,32);
	public static SpriteSheet player_right = new SpriteSheet(player1,3,0,1,3,32);
	//////////////////////ROLL//////////////////////////////////////////////////////////////////////////////
	public static SpriteSheet player_roll = new SpriteSheet(player1,0,3,3,1,32);
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static SpriteSheet dummy = new SpriteSheet ("/textures/dummysheet.png",192,128);
	public static SpriteSheet dummy_down = new SpriteSheet(dummy,0,0,1,3,32);
	public static SpriteSheet dummy_up = new SpriteSheet(dummy,1,0,1,3,32);
	public static SpriteSheet dummy_left = new SpriteSheet(dummy,2,0,1,3,32);
	public static SpriteSheet dummy_right = new SpriteSheet(dummy,3,0,1,3,32);
	
	private Sprite[] sprites;
	
	public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSize){
		int xx = x*spriteSize;
		int yy = y*spriteSize;
		int w = width*spriteSize;
		int h = height*spriteSize;
		if(width == height){SIZE = width;}
			else{SIZE=-1;}
		SPRITE_WIDTH = w;
		SPRITE_HEIGHT = h;
		pixels = new int [w*h];
		for(int y0 = 0; y0<h; y0++){
			int yp = yy+y0;
			for(int x0 = 0; x0<w; x0++){
				int xp = xx+x0;
				pixels[x0+y0*w] = sheet.pixels[xp+yp*sheet.SPRITE_WIDTH];
			}
			
		}
		int frame =0;
		sprites = new Sprite[width*height];
		for(int ya = 0; ya < height; ya++){
			for(int xa = 0; xa < width; xa++){
				int[] spritePixels = new int[spriteSize*spriteSize];
				for(int y0 = 0; y0 < spriteSize; y0++){
					for(int x0 = 0; x0 < spriteSize; x0++){
						spritePixels[x0+y0*spriteSize] = pixels[(x0+xa*spriteSize)+(y0+ya*spriteSize)*SPRITE_WIDTH];
					}
				}
				Sprite sprite = new Sprite(spritePixels, spriteSize, spriteSize);
				sprites[frame++] = sprite;
			}
		}
		
		
	}
	
	public SpriteSheet(String path, int size){
		this.path = path;
		SIZE = size;
		SPRITE_WIDTH = size;
		SPRITE_HEIGHT = size;
		pixels = new int [SIZE*SIZE];
		//call to the method below that will read in the image and 
		//convert it to a pixel array.
		load();
	}
	
	//for non perfectly symmetrical sheets
	public SpriteSheet(String path, int width, int height){
		this.path = path;
		SIZE = -1;
		SPRITE_WIDTH = width;
		SPRITE_HEIGHT = height;
		pixels = new int [SPRITE_WIDTH*SPRITE_HEIGHT];
		//call to the method below that will read in the image and 
		//convert it to a pixel array.
		load();
		
	}
	
	public Sprite[] getSprites(){
		return sprites;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int[] getPixels() {
		return pixels;
	}
	
	private void load(){
		try {
			System.out.print("Trying to load file:" + path + "....");
			//Reading in the image
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			System.out.println(" Succeeded!");
			width = image.getWidth();
			height = image.getHeight();
			//Translates the scanned image into pixels
			//image.getRGB(starting x, starting y, width, height, rgdArray, offset, scansize which is usually the width)
			pixels = new int [width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e){
			System.out.println(" Failed!");			
		}
	}


}
