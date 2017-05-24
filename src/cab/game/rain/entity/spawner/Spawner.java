package cab.game.rain.entity.spawner;

import cab.game.rain.entity.Entity;
import cab.game.rain.entity.particle.Particle;
import cab.game.rain.level.Level;

public class Spawner extends Entity{
	
	//enum creates a custom variable Type equal MOB and PARICLE
	//No need to make it static. All extends classes can access it without setting it to static.
	public enum Type{
		MOB, PARTICLE
	}
	
	private Type type;
	
	public Spawner(int x, int y, Type type, int amount, Level level){
		init(level);
		this.x=x;
		this.y=y;
		this.type = type;
	}

}
