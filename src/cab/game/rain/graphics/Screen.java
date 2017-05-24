package cab.game.rain.graphics;

import java.util.Random;

import cab.game.rain.entity.mob.Chaser;
import cab.game.rain.entity.mob.Dummy;
import cab.game.rain.entity.mob.Mob;
import cab.game.rain.entity.mob.Player;
import cab.game.rain.entity.mob.Star;
import cab.game.rain.entity.projectile.Projectile;
import cab.game.rain.level.tile.Tile;

public class Screen {
	
	public int width, height;
	public int[] pixels;
	public final int MAP_SIZE = 64;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;
	private final int ALPHA_COL = 0xffffffff;
	
	public int xOffset, yOffset;
	
	private int time = 0;
	
	public int[] tiles = new int[MAP_SIZE * MAP_SIZE];
	
	private Random random = new Random();
	
	public Screen(int width, int height){
		//this. refers to the width and height variable in this class
		this.width = width;
		this.height = height;
		pixels = new int [width*height];
		
		for (int i = 0; i < MAP_SIZE*MAP_SIZE; i++){
			//randomize the color of titles form black [0] to white [ffffff]
			tiles[i]=random.nextInt(0xffffff);
			
			//This sets index in tiles to black
			//tiles[0]=0;
		}
	}
	
	public void clear(){
		for (int i = 0; i < pixels.length; i++){
			pixels[i] = 0;
		}
	}
	
	public void renderSheet(int xp, int yp, SpriteSheet sheet, boolean fixed){
		if (fixed){
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y<sheet.SPRITE_HEIGHT; y++){
			int ya=y+yp;
			for (int x = 0; x<sheet.SPRITE_WIDTH; x++){
				int xa=x+xp;
				if(xa<0||xa>=width || ya<0||ya>=height){continue;}
				pixels[xa+ya*width]=sheet.pixels[x+y*sheet.SPRITE_WIDTH];
			}
		}
	}
	
	public void renderTextCharacter(int xp, int yp, Sprite sprite, int color, boolean fixed){
		if (fixed){
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y<sprite.getHeight(); y++){
			int ya=y+yp;
			for (int x = 0; x<sprite.getWidth(); x++){
				int xa=x+xp;
				if(xa<0||xa>=width || ya<0||ya>=height){continue;}
				int col = sprite.pixels[x+y*sprite.getWidth()];
				if(col != ALPHA_COL){pixels[xa+ya*width]=col;}
			}
		}
	}
	
	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed){
		if (fixed){
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y<sprite.getHeight(); y++){
			int ya=y+yp;
			for (int x = 0; x<sprite.getWidth(); x++){
				int xa=x+xp;
				if(xa<0||xa>=width || ya<0||ya>=height){continue;}
				int col = sprite.pixels[x+y*sprite.getWidth()];
				if(col != ALPHA_COL){pixels[xa+ya*width]=col;}
			}
		}
	}

	
	
//________________Keep for Notes________________________________________________________________________________________________________
	
	/*public void render(int xOffSet, int yOffSet){
		for (int y = 0; y < height; y++ ){
			int yp = y+yOffSet;
			if (y < 0 || y >= height) continue;
			for(int x = 0; x < width; x++){
				int xp = x+xOffSet;
				if (x < 0 || x >= width) continue;*/
				
//----------------------------------------------------------------------------------------				
				/*
				//32 pixel size for tile
				//>> is a bitwise operator for optimization
				//>> this means x and y will shift over by the following number (4)
				//& is a bitwise operator that will set the value back to 0 once a operation
				//exceeds the specified number (& is used to repeat the array).
				int tileIndex = ((xx>>4)&MAP_SIZE_MASK) + ((yy>>4)&MAP_SIZE_MASK)*64;
				*/
//------------------------------------------------------------------------------------------
				
				
				
				//x and y represent the coordinates in the window
				//To use hex type 0x in front of the hex color number
				//>>>>>>>pixels[xp+yp*width] = tiles[tileIndex];//use Sprite.grass.pixels[(x&15)+(y&15)*Sprite.grass.SIZE]; in place of tiles[tilesIndex]

			//}
		//}
	//}
//______________________________________________________________________________________________________________________________
	
	
	//offset render method for tiles in relation to the player's position
	public void renderTile(int xp, int yp, Tile tile){
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < tile.sprite.SIZE; y++){
			int ya = y + yp;
			for  (int x =0; x<tile.sprite.SIZE; x++){
				int xa = x + xp;
				if (xa < -tile.sprite.SIZE || xa >=width || ya < 0 || ya >= height){ break; } //might have to switch to ya >= width
				if (xa<0){xa=0;}
				pixels[xa+ya*width] = tile.sprite.pixels[x+y*tile.sprite.SIZE];
			}
		}
	}
	
	public void renderProjectile(int xp, int yp, Projectile p){
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < p.getSpriteSize(); y++){
			int ya = y + yp;
			for  (int x =0; x<p.getSpriteSize(); x++){
				int xa = x + xp;
				if (xa < - p.getSpriteSize() || xa >=width || ya < 0 || ya >= height){ break; } //might have to switch to ya >= width
				if (xa<0){xa=0;}
				//pixels[xa+ya*width] = sprite.pixels[x+y*sprite.SIZE];
				int col = p.getSprite().pixels[x+y*p.getSpriteSize()];
				//have to use two extra fs to compensate for the alpha color option at the BufferImage class uses
				//here is where the white in the player image is not loaded 
				if (col != ALPHA_COL){pixels[xa+ya*width] = col;}
			}
		}
	}
	
	public void renderMob(int xp, int yp, Mob mob){
		xp -= xOffset;
		yp -= yOffset;
		time++;
		if(time%4000==0){time = 0;}
		for (int y = 0; y < 32; y++){
			int ya = y + yp;
			int ys = y;
			for  (int x =0; x<32; x++){
				int xa = x + xp;
				int xs = x;
				if (xa < -32 || xa >=width || ya < 0 || ya >= height){ break; }
				if (xa<0){xa=0;}
				
				int col = mob.getSprite().pixels[xs+ys*32];
				if ((mob instanceof Dummy)&&col==0xff000000){col=0xfff3ff00;}
				if ((mob instanceof Star)&&col==0xff000000){col=0xffffffff;}//white for no color
				if ((time%4000<1000)&&(mob instanceof Chaser)&&col==0xff000000){col=0xffff0000;}//red
					else if (((time%4000>=1000)&&(time%4000<2000))&&(mob instanceof Chaser)&&col==0xff000000){col=0xffFF00DE;}//purple
					else if (((time%4000>=2000)&&(time%4000<3000))&&(mob instanceof Chaser)&&col==0xff000000){col=0xff04FF00;}//green
					else if ((mob instanceof Chaser)&&col==0xff000000){col=0xff00ffff;}//teal
				//have to use two extra fs to compensate for the alpha color option at the BufferImage class uses
				//here is where the white in the player image is not loaded 
				if (col != ALPHA_COL){pixels[xa+ya*width] = col;}
				
			}
		}
	}
	
	public void renderMob(int xp, int yp, Sprite sprite, int flip){
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < 32; y++){
			int ya = y + yp;
			int ys = y;
			for  (int x =0; x<32; x++){
				int xa = x + xp;
				int xs = x;
				if (xa < -32 || xa >=width || ya < 0 || ya >= height){ break; }
				if (xa<0){xa=0;}
				
				int col = sprite.pixels[xs+ys*32];
				//have to use two extra fs to compensate for the alpha color option at the BufferImage class uses
				//here is where the white in the player image is not loaded 
				if (col != ALPHA_COL){pixels[xa+ya*width] = col;}
				
			}
		}
	}
	
	public void drawRect(int xp, int yp, int width, int height, int col, boolean fixed) {
		if(fixed){
			xp -= xOffset;
			yp -= yOffset;
		}
		for(int x = xp; x<xp+width; x++){
			if(x<0 || yp>=this.height || yp>=this.height){continue;}
			if(yp>0){pixels[x + yp * this.width] = col;}
			if(yp + height >= this.height){continue;}
			if(yp+height>0){pixels[x + (yp + height) * this.width] = col;}
		}
		
		for(int y = yp; y<=yp+height; y++){
			if(xp>=this.width || y<0 || y>=this.height){continue;}
			if(xp>0){pixels[xp + y * this.width] = col;}
			if(xp + width >= this.width){continue;}
			if(xp+width>0){pixels[(xp + width) + y * this.width] = col;}
		}
	}
	
	public void setOffset(int xOffset, int yOffset){
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}


}

