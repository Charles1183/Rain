package cab.game.rain.entity.mob;

import cab.game.rain.graphics.AnimatedSprite;
import cab.game.rain.graphics.Screen;
import cab.game.rain.graphics.Sprite;
import cab.game.rain.graphics.SpriteSheet;

public class Dummy extends Mob{
	
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.dummy_down,32,32,3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.dummy_up,32,32,3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.dummy_left,32,32,3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.dummy_right,32,32,3);
	
	private AnimatedSprite animSprite = down;
	
	private int time = 0;
	private int ya = 0;
	private int xa = 0;
	
	public Dummy(int x, int y){
		this.x = x << 4;
		this.y = y << 4;
		sprite = animSprite;
	}

	public void update() {
		time++;//will be in multiples of 60
		if(time%(random.nextInt(50) + 30) == 0){
			xa = random.nextInt(3)-1;
			ya = random.nextInt(3)-1;
			if(random.nextInt(4) == 0){
				xa = 0;
				ya = 0;
			}
		}
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
		if (xa != 0 || ya != 0){
			move(xa, ya);
			walking = true;
			}else{
				walking = false;
			}
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob(x, y, this);
	}

}
