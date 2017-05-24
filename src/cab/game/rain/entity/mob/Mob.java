package cab.game.rain.entity.mob;

import java.util.ArrayList;
import java.util.List;

import cab.game.rain.entity.Entity;
import cab.game.rain.entity.particle.Particle;
import cab.game.rain.entity.projectile.FireBall;
import cab.game.rain.entity.projectile.Projectile;
import cab.game.rain.graphics.Screen;
import cab.game.rain.graphics.Sprite;

public abstract class Mob extends Entity{
	
	//Protected means that this sprite can only be accessed by the Mob class and it's subclasses
	//dir is for the changing of the sprite direction graphics
	protected boolean moving = false;
	protected boolean walking = false;
	
	protected enum Direction{
		UP, DOWN, LEFT, RIGHT
		
	}
	
	protected Direction dir;
	
	public void move(double xa,double ya){
		//System.out.println("SIZE: "+level.getProjectile().size());
		if (xa != 0 && ya != 0){
			move (xa,0);
			move (0,ya);
			return;
		}
		
		if (xa > 0){dir = Direction.RIGHT;}
		if (xa < 0){dir = Direction.LEFT;}
		if (ya > 0){dir = Direction.DOWN;}
		if (ya < 0){dir = Direction.UP;}
		
		while(xa!=0){
			if(Math.abs(xa)>1){
				if(!collision(abs(xa), ya)){
					this.x += abs(xa);
				}
				xa -= abs(xa);
			}else{
				if(!collision(abs(xa), ya)){
					this.x += abs(xa);
				}
				xa = 0;
			}
		}
		
		while(ya!=0){
			if(Math.abs(ya)>1){
				if(!collision(xa, abs(ya))){
					this.y += abs(ya);
				}
				ya -= abs(ya);
			}else{
				if(!collision(xa, abs(ya))){
					this.y += abs(ya);
				}
				ya = 0;
			}
		}
	}
	
	private int abs(double value){
		if(value<0){return -1;}
		return 1;
	}
	
	public abstract void update();
	
	public abstract void render(Screen screen);
	
	protected void shoot(double x, double y, double dir){
		Projectile f = new FireBall(x, y, dir);
		level.add(f);
	}
	
	private boolean collision(double xa, double ya){
		boolean solid = false;
		for (int c = 0; c<4; c++){
			double xt = ((x+xa)-c%2*15)/16;//precision collision. Change the last two numbers for greater precision
			double yt = ((y+ya)-c/2*15)/16;//precision collision. Change the last two numbers for greater precision
			int ix = (int)Math.ceil(xt);//Math.ceil() rounds up
			int iy = (int)Math.ceil(yt);
			if(c%2==0){ix=(int)Math.floor(xt);}//Math.floor() rounds down
			if(c/2==0){iy=(int)Math.floor(yt);}
			if(level.getTile(ix, iy).solid()){solid = true;}
		}
		return solid;
	}
	

}
