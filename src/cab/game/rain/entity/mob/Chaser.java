package cab.game.rain.entity.mob;

import java.util.List;

import cab.game.rain.entity.mob.Mob.Direction;
import cab.game.rain.graphics.AnimatedSprite;
import cab.game.rain.graphics.Screen;
import cab.game.rain.graphics.Sprite;
import cab.game.rain.graphics.SpriteSheet;

public class Chaser extends Mob{
	
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.dummy_down,32,32,3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.dummy_up,32,32,3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.dummy_left,32,32,3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.dummy_right,32,32,3);
	
	private AnimatedSprite animSprite = down;
	
	private double ya = 0;
	private double xa = 0;
	private double speed = 1.2;//can take in decimal values
	
	public Chaser(int x, int y){
		this.x = x<<4;
		this.y = y<<4;
		sprite = down;
	}
	private void move(){
		xa = 0;
		ya = 0;
		List<Player> players = level.getPlayers(this, 70);
		if(players.size()>0){
			Player player = players.get(0);
			if (x<player.getX()){xa+=speed;}
			if (y<player.getY()){ya+=speed;}
		
			if (x>player.getX()){xa-=speed;}
			if (y>player.getY()){ya-=speed;}
		}
		
		if (xa != 0 || ya != 0){
			move(xa, ya);
			walking = true;
		}else{
			walking = false;
		}
	}

	public void update() {
		move();
		if(walking){animSprite.update();}
		else{animSprite.setFrame(0);}
		if (ya < 0){
			animSprite = up;
			dir = Direction.UP;
		}else if (ya > 0){
			animSprite = down;
			dir = Direction.DOWN;
		}
		if (xa < 0){
			animSprite = left;
			dir = Direction.LEFT;
		}else if (xa > 0){
			animSprite = right;
			dir = Direction.RIGHT;
		}
		
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob(x-16, y-16, this);
		
	}

}
