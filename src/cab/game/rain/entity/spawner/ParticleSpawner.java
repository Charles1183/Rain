package cab.game.rain.entity.spawner;

import cab.game.rain.entity.particle.Particle;
import cab.game.rain.entity.spawner.Spawner.Type;
import cab.game.rain.level.Level;

public class ParticleSpawner extends Spawner{
	
	private int life;

	public ParticleSpawner(int x, int y, int life, int amount, Level level) {
		super(x, y, Type.PARTICLE, amount, level);
		this.life = life;
		for(int i = 0; i<amount;i++){
			level.add(new Particle(x,y,life));
		}
	}

}
