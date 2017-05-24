package cab.game.rain.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//You have to click on the inside of the window to make this work
public class Keyboard implements KeyListener {
	
	//The below array must have equal to or greater then the highest key number you
	//will be using. Since the W key is has number 87 which is the highest.
	//We could sent the size to 87.
	private boolean[] keys = new boolean[120];
	public boolean up, down, left, right, roll;
	
	public void update(){
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		roll = keys[KeyEvent.VK_SPACE];
		
		//System.out.println(up);
		//System.out.println(down);
		//System.out.println(left);
		//System.out.println(right);
		
	}
		
	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

}
