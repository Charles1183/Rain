package cab.game.rain.level.tile.spawn_level;

import cab.game.rain.graphics.Screen;
import cab.game.rain.graphics.Sprite;
import cab.game.rain.level.tile.Tile;

public class SpawnGrassTile extends Tile{

	public SpawnGrassTile(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y,Screen screen){
		//the this key word in the screen.renderTiles(x, y, this) refers to the class is it apart of.
		//In other word this class GrassTile is being passed to the renderTile method in the Screen class.
		//"<<" increases by 4^2=16 (same as multiplying by 16) and ">>" decreases by 4^2=16 (same as dividing by 16)
		//This to convert from pixel precision to tile precision
		screen.renderTile(x << 4, y << 4, this);
	}

}
