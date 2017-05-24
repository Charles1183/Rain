package cab.game.rain.graphics;

//This class accesses individual sprites on the sprite sheet and
//creates the sprite.
public class Sprite {
	
	public final int SIZE;
	private int x, y;
	private int width, height;
	public int [] pixels;
	//variable for allowing multiple SpriteSheets
	public SpriteSheet sheet;
	
	//this line creates the sprite
	//creates a new static instance of the sprite class
	//new Sprite(size of sprite, start x, start y, )
	//public static Sprite grass = new Sprite(16, 0, 0, SpriteSheet.tiles);//SpriteSheet.tiles will access the image cached by SpriteSheet class
	//public static Sprite flower = new Sprite(16, 1, 0, SpriteSheet.tiles);
	//public static Sprite rock = new Sprite(16, 2, 0, SpriteSheet.tiles);
	
	public static Sprite voidSprite = new Sprite(16, 0x006CFA);
	
	//Spawn Level Sprites here:
	public static Sprite spawn_grass = new Sprite(16, 0, 0, SpriteSheet.spawn_level);
	public static Sprite spawn_hedge = new Sprite(16, 0, 1, SpriteSheet.spawn_level);
	public static Sprite spawn_water = new Sprite(16, 3, 0, SpriteSheet.spawn_level);
	public static Sprite spawn_wall1 = new Sprite(16, 2, 0, SpriteSheet.spawn_level);
	public static Sprite spawn_wall2 = new Sprite(16, 2, 1, SpriteSheet.spawn_level);
	public static Sprite spawn_floor = new Sprite(16, 1, 0, SpriteSheet.spawn_level);
	
	//Player Sprites here:
	public static Sprite player_forward = new Sprite(32,0,0,SpriteSheet.playerImage);
	public static Sprite player_back = new Sprite(32,1,0,SpriteSheet.playerImage);
	public static Sprite player_left = new Sprite(32,2,0,SpriteSheet.playerImage);
	public static Sprite player_right = new Sprite(32,3,0,SpriteSheet.playerImage);
	
	public static Sprite player_forward_1 = new Sprite(32,0,1, SpriteSheet.playerImage);//animate the up walking
	public static Sprite player_forward_2 = new Sprite(32,0,2, SpriteSheet.playerImage);//animate the up walking
	
	public static Sprite player_back_1 = new Sprite(32,1,1, SpriteSheet.playerImage);
	public static Sprite player_back_2 = new Sprite(32,1,2, SpriteSheet.playerImage);
	
	public static Sprite player_left_1 = new Sprite(32,2,1, SpriteSheet.playerImage);
	public static Sprite player_left_2 = new Sprite(32,2,2, SpriteSheet.playerImage);
	
	public static Sprite player_right_1 = new Sprite(32,3,1, SpriteSheet.playerImage);
	public static Sprite player_right_2 = new Sprite(32,3,2, SpriteSheet.playerImage);
	
	///////////////////////////////////ROLL////////////////////////////////////////////////////////////////////////////////////////
	public static Sprite player_roll_1 = new Sprite(32,0,3, SpriteSheet.playerImage);
	public static Sprite player_roll_2 = new Sprite(32,1,3, SpriteSheet.playerImage);
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static Sprite dummy = new Sprite(32,0,0,SpriteSheet.dummy_down);

	//Projectile Sprites here:
	public static Sprite fireball = new Sprite(16, 0, 0, SpriteSheet.projectileImage);
	//public static Sprite fireball_2 = new Sprite(16, 1, 0, SpriteSheet.projectileImage);//add this title in the projectile paint file
	
	//Particles Sprite here:
	public static Sprite particles_normal = new Sprite(3, 0xAAAAAA);
	
	/*//Player sprite ep.45 Game Programming. Reading in a 32 by 32 pixel sprite in 16 pixel chunks
	public static Sprite player0 = new Sprite(16, 0, 0, SpriteSheet.tiles); //chunk 1 read in ".....= new Sprite(size, x starting, y starting, SpriteSheet.titles)"
	public static Sprite player1 = new Sprite(16, 1, 0, SpriteSheet.tiles);//chunk 2
	public static Sprite player2 = new Sprite(16, 0, 1, SpriteSheet.tiles);//chunk 3
	public static Sprite player3 = new Sprite(16, 1, 1, SpriteSheet.tiles);//chunk 4
	
	//the title size is change by 32 pixels. so the location start will have to been in 32 by 32 pixel size tiles.
	public static Sprite player = new Sprite(32,0,0,SpriteSheet.tiles);*/
	
	protected Sprite(SpriteSheet sheet, int width, int height){
		SIZE = (width == height) ? width : -1;//one line if statement. SIZE = if(width is equal to height)? then set SIZE to width else(:) set SIZE to -1;
		this.width = width;
		this.height = height;
		this.sheet = sheet;
	}
	
	public Sprite(int size, int x, int y, SpriteSheet sheet){
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		this.x = x*size;
		this.y = y*size;
		this.sheet = sheet;
		load();
	}
	
	public Sprite(int width, int height, int color){
		SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int[width*height];
		setColor(color);
	}
	
	//Creates a new pixels array for the voidSprite
	public Sprite(int size, int color){
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE*SIZE];
		setColor(color);
	}
	
	public Sprite(int[] pixels, int width, int height) {
		SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		this.pixels = new int[pixels.length];
		for(int i = 0; i < pixels.length; i++){
			this.pixels[i] = pixels[i];
		}
	}
	
	public static Sprite rotate(Sprite sprite, double angle){
		return new Sprite(rotate(sprite.pixels, sprite.width, sprite.height, angle), sprite.width, sprite.height);
	}
	
	private static int[] rotate(int[] pixels, int width, int height, double angle){
		int[] result = new int[width * height];
		
		double nx_x = rotX(-angle, 1.0, 0.0);
		double nx_y = rotY(-angle, 1.0, 0.0);
		double ny_x = rotX(-angle, 0.0, 1.0);
		double ny_y = rotY(-angle, 0.0, 1.0);
		
		double x0 = rotX(-angle, -width/2.0, -height/2.0)+width/2.0;
		double y0 = rotY(-angle, -width/2.0, -height/2.0)+height/2.0;
		
		for(int y = 0; y<height; y++){
			double x1 = x0;
			double y1 = y0;
			for(int x = 0; x<width; x++){
				int xx = (int)x1;
				int yy = (int)y1;
				int col = 0;
				if(xx<0 || xx>=width || yy<0 || yy>=height){col = 0xffffffff;}
				else{col = pixels[xx+yy*width];}
				result[x+y*width] = col;
				x1+=nx_x;
				y1+=nx_y;
			}
			x0+=ny_x;
			y0+=ny_y;
		}
		
		return result;
	}
	
	private static double rotX(double angle, double x, double y){
		double cos = Math.cos(angle - Math.PI/2);
		double sin = Math.sin(angle - Math.PI/2);
		return x*cos+y*-sin;
		
	}
	
	private static double rotY(double angle, double x, double y){
		double cos = Math.cos(angle - Math.PI/2);
		double sin = Math.sin(angle - Math.PI/2);
		return x*sin+y*cos;
		
	}
	
	public static Sprite[] split(SpriteSheet sheet) {
		int amount = (sheet.getWidth()*sheet.getHeight())/(sheet.SPRITE_WIDTH*sheet.SPRITE_HEIGHT);
		Sprite[] sprites = new Sprite[amount];
		int current = 0;
		int[] pixels = new int[sheet.SPRITE_WIDTH*sheet.SPRITE_HEIGHT];
		
		for(int yp = 0; yp < sheet.getWidth()/sheet.SPRITE_HEIGHT; yp++){
			for(int xp = 0; xp < sheet.getHeight()/sheet.SPRITE_WIDTH; xp++){
				
				for(int y = 0; y < sheet.SPRITE_HEIGHT; y++){
					for(int x = 0; x < sheet.SPRITE_WIDTH; x++){
						int xo = x + xp * sheet.SPRITE_WIDTH;
						int yo = y + yp * sheet.SPRITE_HEIGHT;
						pixels[x+y*sheet.SPRITE_HEIGHT] = sheet.getPixels()[xo + yo * sheet.getWidth()];
					}
				}
				
				sprites[current] = new Sprite(pixels, sheet.SPRITE_WIDTH, sheet.SPRITE_HEIGHT);
				current++;
			}
		}
		
		return sprites;
	}
	

	//Assigns a chosen color to the voidSprite pixels array
	private void setColor(int color) {
		for (int i = 0; i < width*height; i++){
			pixels[i] = color;
		}
		
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}

	private void load(){
		for (int y = 0; y< height; y++){
			for (int x = 0; x< width; x++){
				//extracting the appropriate sprite from the spritesheet class
				pixels[x+y*width] = sheet.pixels[(x+this.x)+(y+this.y)*sheet.SPRITE_WIDTH];
			}
		}
	}


}
