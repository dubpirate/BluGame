package Main;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.newdawn.slick.SlickException;

public class EnemyGenerator {
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private int tileHeight;
	private int tileWidth;
	private Player player;
	private ArrayList<Layer> layers;
	
	public EnemyGenerator(Player player, int tileWidth, int tileHeight, ArrayList<Layer> layers) throws SlickException {
		this.player = player;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.layers = layers;
		makeAll();
	}
	
	public void makeAll() throws SlickException {
		int quantity = ThreadLocalRandom.current().nextInt(0,7);
		for (int i=0; i<quantity; i++) {
			int layer = ThreadLocalRandom.current().nextInt(0,4);
			Layer currentLayer = layers.get(layer);
			enemies.add(new Enemy(player,currentLayer,tileWidth, tileHeight));
		}
		quantity = ThreadLocalRandom.current().nextInt(3,8);
		for (int i=0; i<quantity; i++) {
			int layer = ThreadLocalRandom.current().nextInt(5,9);
			Layer currentLayer = layers.get(layer);
			enemies.add(new Enemy(player,currentLayer,tileWidth, tileHeight));
		}
		quantity = ThreadLocalRandom.current().nextInt(7,14);
		for (int i=0; i<quantity; i++) {
			int layer = ThreadLocalRandom.current().nextInt(10,14);
			Layer currentLayer = layers.get(layer);
			enemies.add(new Enemy(player,currentLayer,tileWidth, tileHeight));
		}
		quantity = ThreadLocalRandom.current().nextInt(10,20);
		for (int i=0; i<quantity; i++) {
			int layer = ThreadLocalRandom.current().nextInt(15,19);
			Layer currentLayer = layers.get(layer);
			enemies.add(new Enemy(player,currentLayer,tileWidth, tileHeight));
		}
		player.setEnemyList(enemies);
	}
	
	
	public ArrayList<Enemy> getEnemies(){
		return enemies;
	}
	
}