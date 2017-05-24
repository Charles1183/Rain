package cab.game.rain.level;

import java.util.Random;

public class RandomLevel extends Level {
	
	private static final Random random = new Random();
	
	//This will make up for the fact that we do not have a default constructor in Level class
	//This will take in width and height and input it back into to the Level constructor which 
	//is what the super keyword will do.
	public RandomLevel(int width, int height){
		super(width, height);
	}
	
	//Since this class and the Level class both have a method named generateLevel()
	//we can use the protected keyword to override the code in the Level class's generateLevel() method.
	protected void generateLevel(){
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				tilesInt[x+y*width] = random.nextInt(4);
			}
		}
	}

}
