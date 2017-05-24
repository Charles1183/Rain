package cab.game.rain.level.tile;

import cab.game.rain.graphics.Screen;
import cab.game.rain.graphics.Sprite;
import cab.game.rain.level.tile.spawn_level.SpawnFloorTile;
import cab.game.rain.level.tile.spawn_level.SpawnGrassTile;
import cab.game.rain.level.tile.spawn_level.SpawnHedgeTile;
import cab.game.rain.level.tile.spawn_level.SpawnWallTile;
import cab.game.rain.level.tile.spawn_level.SpawnWaterTile;

public class Tile {
	
	public int x, y;
	//creating a sprite for each tile for trouble shooting
	public Sprite sprite;
	
	//Our grass object set to static because grass will never change
	//public static Tile grass = new GrassTile (Sprite.grass);
	//public static Tile flower = new FlowerTile (Sprite.flower);
	//public static Tile rock = new RockTile (Sprite.rock);
	public static Tile voidTile = new VoidTile(Sprite.voidSprite);
	
	public static Tile spawn_grass = new SpawnGrassTile(Sprite.spawn_grass);
	public static Tile spawn_hedge = new SpawnHedgeTile(Sprite.spawn_hedge);
	public static Tile spawn_water = new SpawnWaterTile(Sprite.spawn_water);
	public static Tile spawn_wall1 = new SpawnWallTile(Sprite.spawn_wall1);
	public static Tile spawn_wall2 = new SpawnWallTile(Sprite.spawn_wall2);
	public static Tile spawn_floor = new SpawnFloorTile(Sprite.spawn_floor);
	
	//colors to use the spawn.png layout
	//Grass 0xff000000 black
		//Flower 0xffffffff white
		//Rock 0xff0000ff blue
	public final static int col_spawn_grass = 0xff000000;
	public final static int col_spawn_hedge = 0;  //unused
	public final static int col_spawn_water = 0;  //unused
	public final static int col_spawn_wall1 = 0xffffffff;
	public final static int col_spawn_wall2 = 0xff303030;
	public final static int col_spawn_floor = 0xff0000ff;
	
	//Each Tile object must have a sprite passed to it
	//Example when calling Tile(), Title tile = New Tile(null); This will pass an empty sprite to the tile method.
	public Tile(Sprite sprite){
		this.sprite = sprite;
		
	}
	
	//Tile has it's own render method for efficiency 
	public void render(int x, int y, Screen screen){	
	}
	
	//boolean type methods must return something. Otherwise the class will not work.
	//So them must always return a true or false by default.
	//this method will determine if the player can pass through this tile or not. Default is set to not collidable
	public boolean solid(){
		return false;
	}
	
}
