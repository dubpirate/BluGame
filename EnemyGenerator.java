package Main;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.newdawn.slick.SlickException;

public class EnemyGenerator {
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private int tileHeight;
	private int tileWidth;
	private Player player;
	private int levels;
	private ArrayList<Layer> layers;
	
	public EnemyGenerator(int levels, Player player, int tileWidth, int tileHeight, ArrayList<Layer> layers) throws SlickException {
		this.levels = levels;
		this.player = player;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.layers = layers;
		makeAll();
	}
	
	public void makeAll() throws SlickException {
		int quantity = ThreadLocalRandom.current().nextInt(5,30);
		for (int i=0; i<quantity; i++) {
			int layer = ThreadLocalRandom.current().nextInt(0,levels-1);
			Layer currentLayer = layers.get(layer);
			enemies.add(new Enemy(player,currentLayer,tileWidth, tileHeight));
		}
		player.setEnemyList(enemies);
	}
	
	
	public ArrayList<Enemy> getEnemies(){
		return enemies;
	}
	
}