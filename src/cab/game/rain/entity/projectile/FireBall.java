package cab.game.rain.entity.projectile;

import cab.game.rain.entity.spawner.ParticleSpawner;
import cab.game.rain.entity.spawner.Spawner;
import cab.game.rain.graphics.Screen;
import cab.game.rain.graphics.Sprite;

public class FireBall extends Projectile{

	public static final int FIRE_RATE = 15;//Higher is slower
	
	public FireBall(double x, double y, double dir) {
		super(x, y, dir);
		range = 200;
		speed = 4;
		damage = 20;
		sprite = Sprite.rotate(Sprite.fireball, angle);//from projectilesheet.png
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}
	
	public void update(){
		//level.tileCollision(x,y,projectile size, where the project image starts along x, same for y)
		// the Offsets are used to calculate the precision collision for the projectile
		if (level.tileCollision((int)(x+nx),(int)(y+ny), 16, 5, 4)){
			level.add(new ParticleSpawner((int)x,(int)y, 60, 55, level));
			remove();
		}
		move();
	}
	
	
	
	protected void move(){
		x += nx;
		y += ny;
		if(distance()>range){remove();};
		//System.out.println("Distance: "+ distance());
	}
	
	private double distance() {
		double dist = 0;
		dist = Math.sqrt(Math.abs((xOrigin - x)*(xOrigin - x)+(yOrigin - y)*(yOrigin - y)));
		return dist;
	}

	public void render(Screen screen){
		screen.renderProjectile((int)x - 10, (int)y - 4, this);
		
	}

}
