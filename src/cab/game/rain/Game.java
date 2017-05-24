package cab.game.rain;

//Canvas gives us a blank rectangular space(window) 
//where our application can manipulate (draw stuff to) stuff.
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
//import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

import javax.swing.JFrame;

import cab.game.rain.entity.mob.Player;
import cab.game.rain.graphics.Font;
import cab.game.rain.graphics.Screen;
import cab.game.rain.graphics.Sprite;
import cab.game.rain.graphics.SpriteSheet;
import cab.game.rain.graphics.ui.UIManager;
import cab.game.rain.input.Keyboard;
import cab.game.rain.input.Mouse;
import cab.game.rain.level.Level;
import cab.game.rain.level.RandomLevel;
import cab.game.rain.level.SpawnLevel;
import cab.game.rain.level.TileCoordinate;

//Implementing Runnable makes it so we can use the this key word
//We also must implement a run method in the class that implements
//Runnable, or we will get an error message.
public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private static int width = 300;
	private static int height = width/16*9;
	private static int scale = 3;
	
	//Used for access by other methods in the class
	//public static String title = "Rain";

	//Thread is a process inside a process
	private Thread thread;
	private JFrame frame;
	private Keyboard key;
	private Level level;
	private Player player;
	private boolean running = false;
	
	private static UIManager uiManager;
	
	private Screen screen;
	private Font font;
	//Creating the image
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	//Accessing the image
	//DataBufferInt cast the values as a DataBufferInt class converting the values to an integer 
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	//Constructor for Game
	public Game(){
		Dimension size = new Dimension(width*scale, height*scale);
		//setPreferredSize is in Component witch is extended by Canvas
		setPreferredSize(size);
		
		screen = new Screen(width, height);

		uiManager = new UIManager();
		
		frame = new JFrame();
		
		//Null pointer exception will be hint if the key variable is assigned a value
		//after the variable is passed into addKeyListener method.
		key = new Keyboard();
		
		//New random level in tile size
		//level = new RandomLevel(64,64);
		level = Level.spawn;
		TileCoordinate playerSpawn = new TileCoordinate(15,15);
		player = new Player(playerSpawn.x(), playerSpawn.y(), key);
		//To load the new preset level none random
		level.add(player);
		
		font  = new Font();
	
		addKeyListener(key);
		
		Mouse mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		
	}
	
	public static int getWindowWidth(){
		return width * scale;
	}
	
	public static int getWindowHeigth(){
		return height * scale;
	}
	
	public static UIManager getUIManager(){
		return uiManager;
	}
	
	//Synchronized will help keep threads form colliding in memory
	public synchronized void start(){
		running = true;
		//using this means that thread is attached to this instance of game.
		//it is like using newGame().
		thread = new Thread(this, "Display");
		//.start will start the new thread object
		thread.start();
	}
	
	public synchronized void stop(){
		running = false;
		try{
			//.join() joins all the threads together
			thread.join();
		}catch(InterruptedException e){
			//prints stack when we fail to capture the threads
			e.printStackTrace();
		}
	}
	
	//This method will run when we start up the game
	//because this class (Game) implements Runnable.
	//It does not have to be called, run will run by default
	//This is the game loop
	public void run(){
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		//nanoseconds per second / how many times we want update to execute
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		
		//this will make the game window active when opened
		//making it so you don't have to click on the window for the game to receive input.
		//(part of component class)
		requestFocus();
		while (running){
			long now = System.nanoTime();
			delta += (now-lastTime)/ns;
			lastTime = now;
			while (delta >= 1){
				update();
				updates++;
				delta--;
			}
			//For game graphics
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer >1000){
				timer += 1000;
				//System.out.println(updates + "ups, "+frames + " fps");
				frame.setTitle("Rain"+"  |  "+updates+" ups, "+frames+" fps");
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	//For game logic
	public void update(){
		key.update();
		level.update();
		uiManager.update();
	}
	
	public void render(){
		//Takes the stored frame form the BufferStrategy method from Canvas
		//and stores it in a variable for use later.
		BufferStrategy bs = getBufferStrategy();
		
		//Creates a BufferStrategy if bs is empty
		if (bs == null){
			//set the buffering to store 3 frames
			//One is the displayed frame and two are stored frames
			//No notable performance increase for anything >3
			createBufferStrategy(3);
			return;
		}
		//Will clear the Canvas before rendering it.
		//If the clear came after the render the window would come up blank
		screen.clear();
		
		//Used in centering the player on the screen
		int xScroll = player.getX() - screen.width/2;//>>>>>>changed form double to int
		int yScroll = player.getY() - screen.height/2;//>>>>> changed form double to int
		
		//render is our drawing to the empty Canvas
		//>>>>>>>>>>>screen.render(x,y);
		level.render(xScroll, yScroll, screen);
		
		////>>>>>>font.render(50, 50, -4, "Hello!!!", screen);
		////>>>>>screen.renderSheet(40,40,SpriteSheet.player_down,false);
		for (int i=0; i < pixels.length; i++){
			pixels[i]=screen.pixels[i];
		}
		
		//Draws a link between graphics that you can draw data to
		//and the BufferStrategy.
		//Creating a contexts for the Drawing Buffer
		Graphics g = bs.getDrawGraphics();
		//All graphical stuff must be written here
		//After DrawGraphics and before dispose
		
		/*g.setColor(Color.BLACK);
		//This fills the shape(our window)
		//The first two values are the starting point(default is set to the left)
		//Next two values are the length and height of the shape(window)
		g.fillRect(0, 0, getWidth(), getHeight());*/
		
		
		//Will draw image to the screen
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
		uiManager.render(g);
		//this will show the x & y coordinates on the screen
		
		/////>>>>>>>>g.setColor(Color.WHITE);
		/////>>>>>>>>g.setFont(new Font("Aerial",1,15));
		/////>>>>>>>>g.drawString("X: "+player.getX()+" Y: "+player.getY(), 750, 475);
		
		//Test if Mouse is on screen
		//g.fillRect(Mouse.getX()-10, Mouse.getY()-10, 32, 32);
		//Test mouse button
		//g.drawString("Button: "+ Mouse.getButton(), 60, 60);
		
		//Dispose deletes all graphics and releases all system resources
		//Removes unwanted graphics
		g.dispose();
		
		//Shows the next buffered frame
		bs.show();
	}
	
	
	//can not access non-static variables form a static method
	public static void main(String[] args){
		Game game = new Game();
		//Make sure first that the frame can not be resized
		game.frame.setResizable(false);
		
		//Window title
		//The title can be set outside of main
		//the render method will display the title
		//--->>game.frame.setTitle(Game.title);
		
		//Adding something into the frame(window)
		game.frame.add(game);
		//Set the size of frame to be same size as game
		game.frame.pack();
		//To close and exit game when the window's x button is click
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Centers the frame in the window
		game.frame.setLocationRelativeTo(null);
		//Makes the frame visible
		game.frame.setVisible(true);
		
		game.start();
	}
}
