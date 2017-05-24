package cab.game.rain.entity.mob;

import java.util.List;

import cab.game.rain.Game;
import cab.game.rain.entity.Entity;
import cab.game.rain.entity.projectile.FireBall;
import cab.game.rain.entity.projectile.Projectile;
import cab.game.rain.graphics.AnimatedSprite;
import cab.game.rain.graphics.Screen;
import cab.game.rain.graphics.Sprite;
import cab.game.rain.graphics.SpriteSheet;
import cab.game.rain.graphics.ui.UILabel;
import cab.game.rain.graphics.ui.UIManager;
import cab.game.rain.graphics.ui.UIPanel;
import cab.game.rain.input.Keyboard;
import cab.game.rain.input.Mouse;
import cab.game.rain.util.Vector2i;

public class Player extends Mob{
	////////////////ROLL/////////////////////////////////////////////////
	private double timer = 0;
	private boolean cd;
	private int cooldown = 60;
	///////////////////////////////////////////////////////////////////////
	private Keyboard input;
	private Sprite sprite;
	private int anim = 0;
	public boolean walking = false;
	//AnimatedSprite(SpriteSheet sheet, int width, int height, int length)
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down,32,32,3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up,32,32,3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left,32,32,3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right,32,32,3);
	private AnimatedSprite roll = new AnimatedSprite(SpriteSheet.player_roll,32,32,3);
	
	
	private AnimatedSprite animSprite = down;
	private AnimatedSprite animSprite_2 = up;
	private int fireRate = 0;
	
	private UIManager ui;
	
	//This will start a player at a default location
	public Player(Keyboard input){
		this.input = input;
		//in cause the dir is not = 0 sprite will equal player_forward
		sprite = Sprite.player_forward;
		
	}
	
	//This will start a player at a specific location
	public Player(int x, int y, Keyboard input){
		this.x = x;
		this.y = y;
		this.input = input;
		//in cause the dir is not = 0 sprite will equal player_forward
		sprite = Sprite.player_forward;
		fireRate = FireBall.FIRE_RATE;
		
		ui = Game.getUIManager();
		UIPanel panel = new UIPanel(new Vector2i((300-80)*3,0), new Vector2i(80*3, 168*3));
		ui.addPanel(panel);
		panel.addComponent(new UILabel(new Vector2i(10, 30),"Hello!!!").setColor(0));
	}
	
	
	public void update(){
		if(walking){animSprite.update();}
		else{animSprite.setFrame(0);}
		if(fireRate > 0){fireRate--;}
		double xa = 0;
		double ya = 0;
		////////////////////////////////////////////////
		double yr = 0;
		double xr = 0;
		///////////////////////////////////////////////
		double speed = 2.5;
		
		if (input.up){
			animSprite = up;
			animSprite_2 = up;
			ya-=speed;
			
		}else if (input.down){
			animSprite = down;
			animSprite_2 = down;
			ya+=speed;
			
		}
		if (input.left){
			animSprite = left;
			animSprite_2 = left;
			xa-=speed;
			
		}else if (input.right){
			animSprite = right;
			animSprite_2 = right;
			xa+=speed;
			
		}
		if (xa != 0 || ya != 0){
			move(xa, ya);
			walking = true;
			}else{
				walking = false;
			}
		///////////////////ROLL//////////////////////////////////////////////////////
		
			if (input.roll){
				animSprite = roll;
				timer++;
				if(timer >30){
					animSprite = animSprite_2;
					if(cooldown==60){cd = true;}
				}
			}
			if(cd){cooldown--; if(cooldown==0){cd=false; timer=0; cooldown=60;}}
			
			System.out.println(timer);
		
		//////////////////////////////////////////////////////////////////////////////
			
		/*else if (input.down){
			animSprite = down;
			ya+=speed;
			
		}
		if (input.left){
			animSprite = left;
			xa-=speed;
			
		}else if (input.right){
			animSprite = right;
			xa+=speed;
			
		}*/
		clear();
		updateShooting();
	}
	
	private void clear() {
		for(int i = 0; i<level.getProjectile().size(); i++){
			Projectile p = level.getProjectile().get(i);
			if (p.isRemoved()){level.getProjectile().remove(i);}
		}
		
	}

	//Shooting direction using the mouse
	private void updateShooting() {
		if (Mouse.getButton() == 1 && fireRate <= 0){
			//300 X 168 are the dimensions of the screen. Player will always be in the middle.
			double dx = Mouse.getX() - Game.getWindowWidth()/2;
			double dy = Mouse.getY() - Game.getWindowHeigth()/2;
			double dir = Math.atan2(dy, dx);
			shoot(x, y, dir);
			fireRate = FireBall.FIRE_RATE;
		}
		
	}

	public void render(Screen screen){
		sprite = animSprite.getSprite();
		//minus 16 centers the player
		screen.renderMob(x-16, y-16, sprite, -1);
		
	}

}
