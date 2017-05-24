package cab.game.rain.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import cab.game.rain.entity.mob.Chaser;
import cab.game.rain.entity.mob.Dummy;
import cab.game.rain.entity.mob.Shooter;
import cab.game.rain.entity.mob.Star;
import cab.game.rain.level.tile.Tile;

public class SpawnLevel extends Level{
	
	public SpawnLevel(String path){
		super(path);
	}
	
	protected void loadLevel(String path) {
		try{
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			int w = width =image.getWidth();
			int h = height = image.getHeight();
			tiles = new int[w*h];
			image.getRGB(0,0,w,h, tiles, 0, w);
		} catch (IOException e){
			e.printStackTrace();
			System.out.println("Exception! Could not load level file!");
		}
		add(new Shooter(20,10));
		add(new Dummy(19,13));
		add(new Chaser(10,10));
		//add(new Star(20,10));
		/*for(int i = 0; i<5;i++){
			add(new Dummy(19,23));
		}*/
		
	}
	
	
	
	protected void generateLevel(){
		
	}
}
