package cab.game.rain.level;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import cab.game.rain.entity.Entity;
import cab.game.rain.entity.mob.Player;
import cab.game.rain.entity.particle.Particle;
import cab.game.rain.entity.projectile.Projectile;
import cab.game.rain.entity.spawner.ParticleSpawner;
import cab.game.rain.entity.spawner.Spawner;
import cab.game.rain.graphics.Screen;
import cab.game.rain.level.tile.Tile;
import  cab.game.rain.util.Vector2i;

public class Level {
	
	protected int width, height;
	protected int [] tilesInt;
	protected int [] tiles;
	
	private List<Entity> entities = new ArrayList<Entity>();//a dynamic array of objects
	private List<Projectile> projectile = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();
	
	private List<Player> players = new ArrayList<Player>();
	
	private Comparator<Node> nodeSorter = new Comparator<Node>(){
		public int compare(Node n0, Node n1){
			if(n1.fCost<n0.fCost){return +1;}
			if(n1.fCost>n0.fCost){return -1;}
			return 0;
		}
	};
	
	
	public static Level spawn = new SpawnLevel("/textures/levelsheet2.png");//will have to create a levels folder in the res folder and spawn.png
	
	public Level(int width, int height){
		this.width = width;
		this.height = height;
		tilesInt = new int[width*height];
		generateLevel();
	}
	
	public Level(String path){
		loadLevel(path);
		generateLevel();
		
	}

	protected void generateLevel() {
		
	}
	
	protected void loadLevel(String path) {
		
	}
	
	public void update(){
		for (int i =0; i<entities.size(); i++){
			entities.get(i).update();
		}
		for (int i =0; i<projectile.size(); i++){
			projectile.get(i).update();
		}
		for (int i =0; i<particles.size(); i++){
			particles.get(i).update();
		}
		for (int i =0; i<players.size(); i++){
			players.get(i).update();
		}
		remove();
	}
	
	private void remove(){
		for (int i =0; i<entities.size(); i++){
			if(entities.get(i).isRemoved()){entities.remove(i);}
		}
		for (int i =0; i<projectile.size(); i++){
			if(projectile.get(i).isRemoved()){projectile.remove(i);}
		}
		for (int i =0; i<particles.size(); i++){
			if(particles.get(i).isRemoved()){particles.remove(i);}
		}
		for (int i =0; i<players.size(); i++){
			if(players.get(i).isRemoved()){players.remove(i);}
		}
	}
	
	public List<Projectile> getProjectile(){
		return projectile;
	}
	
	private void time(){
		
	}
	
	public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset){
		boolean solid = false;
		for (int c = 0; c<4; c++){
			int xt = (x-c%2*size + xOffset)>>4;//precision collision. Change the last two numbers for greater precision
			int yt = (y-c/2*size + yOffset)>>4;//precision collision. Change the last two numbers for greater precision
			if(getTile(xt, yt).solid()){solid = true;}
		}
		return solid;
	}
	
	//Sets up the Pins for the rendering of the map for moving out side the screen
	public void render(int xScroll, int yScroll, Screen screen){
		screen.setOffset(xScroll, yScroll);
		//shifting right by the tile size which is 16,4^2, along the x coordinate
		//The x coordinate for the top left most corner of the screen
		int x0 = xScroll >> 4;
		//The x coordinate for the bottom right most corner of the screen
		int x1 = (xScroll + screen.width+16) >> 4;
		//shifting right by the tile size which is 16,4^2, along the y coordinate
		//The y coordinate for the top left most corner of the screen
		int y0 = yScroll >> 4;
		//The x coordinate for the bottom right most corner of the screen
		int y1 = (yScroll + screen.height+16) >> 4;
		
		for (int y = y0; y<y1; y++){
			for (int x = x0; x<x1; x++){
				getTile(x, y).render(x, y, screen);
			}
		}
		
		for (int i =0; i<entities.size(); i++){
			entities.get(i).render(screen);
		}
		for (int i =0; i<projectile.size(); i++){
			projectile.get(i).render(screen);
		}
		for (int i =0; i<particles.size(); i++){
			particles.get(i).render(screen);
		}
		for (int i =0; i<players.size(); i++){
			players.get(i).render(screen);
		}
		
	}
	
	public void add(Entity e){
		e.init(this);
		if(e instanceof Particle){
			particles.add((Particle)e);
		}else if(e instanceof Projectile){
			projectile.add((Projectile)e);
		}else if(e instanceof Player){
			players.add((Player)e);
		}else if(e instanceof ParticleSpawner){
			
		}else{
			entities.add(e);
		}
		
	}
	
	public List<Player> getPlayer(){
		return players;
	}
	
	public Player getPlayerAt(int index){
		return players.get(index);
	}
	
	public Player getClientPlayer(){
		return players.get(0);
	}
	
	public List<Node> findPath(Vector2i start, Vector2i goal){
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		Node current = new Node(start, null, 0, getDistance(start, goal));
		openList.add(current);
		while(openList.size()>0){
			Collections.sort(openList, nodeSorter);
			current = openList.get(0);
			if(current.tile.equals(goal)){
				List<Node> path = new ArrayList<Node>();
				while(current.parent != null){
					path.add(current);
					current = current.parent;
				}
				openList.clear();
				closedList.clear();
				return path;
			}
			openList.remove(current);
			closedList.add(current);
			for(int i = 0; i <9; i++){
				if(i == 4){continue;}
				int x = current.tile.getX();
				int y = current.tile.getY();
				int xi = (i%3)-1;
				int yi = (i/3)-1;
				Tile at = getTile(x+xi, y+yi);
				if(at==null){continue;}
				if(at.solid()){continue;}
				Vector2i a = new Vector2i(x+xi, y+yi);
				double gCost = current.gCost + (getDistance(current.tile, a)==1?1:0.95);
				double hCost = getDistance(a, goal);
				Node node = new Node(a, current, gCost, hCost);
				if(vecInList(closedList, a)&&gCost>=node.gCost){continue;}
				if(!vecInList(openList, a)||gCost<node.gCost){openList.add(node);}
			}
			
		}
		closedList.clear();
		return null;
	}
	
	private boolean vecInList(List<Node> list, Vector2i vector){
		for(Node n : list){
			if(n.tile.equals(vector)){return true;}
		}
		return false;
	}
	
	private double getDistance(Vector2i tile, Vector2i goal){
		double dx = tile.getX() - goal.getX();
		double dy = tile.getY() - goal.getY();
		return Math.sqrt(dx*dx+dy*dy);
	}
	
	public List<Entity> getEntities(Entity e, int radius){
		List<Entity> result = new ArrayList<Entity>();
		int ex = e.getX();
		int ey = e.getY();
		for (int i = 0; i < entities.size(); i++){
			Entity entity = entities.get(i);
			if(entity.equals(e)){continue;}
			int x = entity.getX();
			int y = entity.getY();
			
			int dx = Math.abs(x-ex);
			int dy = Math.abs(y-ey);
			double distance = Math.sqrt((dx*dx)+(dy*dy));
			if(distance <= radius){result.add(entity);}	
		}
		return result;
	}
	
	public List<Player> getPlayers(Entity e, int radius){
		List<Player> result = new ArrayList<Player>();
		int ex = (int)e.getX();
		int ey = (int)e.getY();
		for(int i = 0; i < players.size(); i++){
			Player player = players.get(i);
			int x = (int)player.getX();
			int y = (int)player.getY();
			
			int dx = (int)Math.abs(x-ex);
			int dy = (int)Math.abs(y-ey);
			double distance = Math.sqrt((dx*dx)+(dy*dy));
			if(distance <= radius){result.add(player);}	
		}
		return result;
	}
	
	//Grass 0xff000000 black
	//Wall1 0xffffffff white
	//Floor 0xff0000ff blue
	public Tile getTile(int x, int y){
		if (x<0||y<0 || x>=width||y>=height){return Tile.voidTile;}
		//locates the current tile we are rendering 
		if (tiles[x+y*width] == Tile.col_spawn_floor){ return Tile.spawn_floor;}
		if (tiles[x+y*width] == Tile.col_spawn_grass){ return Tile.spawn_grass;}
		if (tiles[x+y*width] == Tile.col_spawn_hedge){ return Tile.spawn_hedge;}
		if (tiles[x+y*width] == Tile.col_spawn_wall1){ return Tile.spawn_wall1;}
		if (tiles[x+y*width] == Tile.col_spawn_wall2){ return Tile.spawn_wall2;}
		if (tiles[x+y*width] == Tile.col_spawn_water){ return Tile.spawn_water;}
		//must return a value no matter what happens for the method to run
		return Tile.voidTile;
	}

}
