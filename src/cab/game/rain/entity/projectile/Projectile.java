package cab.game.rain.entity.projectile;

import cab.game.rain.entity.Entity;
import cab.game.rain.graphics.Sprite;

public abstract class Projectile extends Entity{
	
	protected final double xOrigin, yOrigin;
	protected double angle;
	protected Sprite sprite;
	protected double x, y;//will override the x and y in Entity because it has higher priority.
	protected double nx, ny;
	protected double distance;
	protected double speed, range, damage;
	
	public Projectile(double x, double y, double dir){
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		this.x = x;
		this.y = y;
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public int getSpriteSize(){
		return sprite.SIZE;
	}
	
	
	protected void move(){
		
	}

}
