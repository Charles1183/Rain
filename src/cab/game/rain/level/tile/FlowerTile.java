package cab.game.rain.level.tile;

import cab.game.rain.graphics.Screen;
import cab.game.rain.graphics.Sprite;

public class FlowerTile extends Tile {

	public FlowerTile(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y,Screen screen){
		//the this key word in the screen.renderTiles(x, y, this) refers to the class is it apart of.
		//In other word this class FlowerTile is being passed to the renderTile method in the Screen class.
		//"<<" increases by 4^2=16 (same as multiplying by 16) and ">>" decreases by 4^2=16 (same as dividing by 16)
		//This to convert from pixel precision to tile precision
		screen.renderTile(x << 4, y << 4, this);
	}

}
