package Main;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Interactable.Chest;
import Interactable.Coin;
import Interactable.Key;
import Interactable.Onion;
import Items.Item;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Player {
	private int x;
	private int y;
	private int tileWidth;
	private int tileHeight;
	private int health = 3;
	private String currentDirection = "front";
	private Image img;
	private Layer currentLayer;
	private ArrayList<Item> inventory = new ArrayList<Item>();
	private ArrayList<Enemy> enemyList;
	private boolean dead = false;
	private boolean hasOnion = false;

	public Player(int tileHeight, int tileWidth, Layer currentLayer, ArrayList<Enemy> enemyList)
			throws SlickException {
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.enemyList = enemyList;
		Random rand = new Random();
		int n = rand.nextInt(9) + 1;
		x = n;
		n = rand.nextInt(9) + 1;
		y = n;
		img = new Image("res/Sprites/Man2Front.png");
		this.currentLayer = currentLayer;
	}

	public void setCurrentLayer(Layer layer) {
		currentLayer = layer;
	}

	public void setEnemyList(ArrayList<Enemy> enemies) {
		enemyList = enemies;
	}

	public void setHealth(int modifier) {
		health = health + modifier;
		 if (health>3) {
			 health=3;
		 }
		if (health <= 0) {
			dead = true;
		}
	}

	public void draw() {
		img.bind();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glVertex2f(x * tileWidth, y * tileHeight);
		GL11.glTexCoord2f(0, 0);

		GL11.glVertex2f(x * tileWidth, y * tileHeight + tileHeight);
		GL11.glTexCoord2f(1, 0);

		GL11.glVertex2f(x * tileWidth + tileWidth, y * tileHeight + tileHeight);
		GL11.glTexCoord2f(1, 1);

		GL11.glVertex2f(x * tileWidth + tileWidth, y * tileHeight);
		GL11.glTexCoord2f(0, 1);

		GL11.glEnd();
	}
	
	private void setFacing(String direction, Boolean bloody) {
		try {
			if (bloody){
				img = new Image("res/Sprites/Hit"+direction+".png");
			} else {
				img = new Image("res/Sprites/Man2"+direction+".png");
			}
		} catch (SlickException e) {
			System.out.println("Can't setFacing!");
			e.printStackTrace();
		}
	}

	public boolean move() throws SlickException {
		if (dead) {
			img = new Image("res/Sprites/Man2Dead.png");
			return false;
		}
		
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_W) {
					if (y < 9 && !currentLayer.checkPlayerCollision(x * tileWidth, (y + 1) * tileHeight)) {
						y++;
					}
					enemyAttack(x, y);
					currentDirection = "back";
					setFacing("back", false);
					return true;
				} else if (Keyboard.getEventKey() == Keyboard.KEY_D) {
					if (x < 9 && !currentLayer.checkPlayerCollision((x + 1) * tileWidth, (y * tileHeight))) {
						x++;

					}
					enemyAttack(x, y);
					currentDirection = "right";
					setFacing("right", false);
					return true;
				} else if (Keyboard.getEventKey() == Keyboard.KEY_S) {
					if (y > 1 && !currentLayer.checkPlayerCollision((x * tileWidth), (y - 1) * tileHeight)) {
						y--;
					}
					enemyAttack(x, y);
					currentDirection = "front";
					setFacing("front", false);
					return true;
				} else if (Keyboard.getEventKey() == Keyboard.KEY_A) {
					if (x > 1 && !currentLayer.checkPlayerCollision((x - 1) * tileWidth, (y * tileHeight))) {
						x--;
					}
					enemyAttack(x, y);
					currentDirection = "left";
					setFacing("left", false);
					return true;
				}
			}

		}
		return false;
	}

	public void enemyAttack(int x, int y) throws SlickException {
		for (Enemy e : enemyList) {
			if (e.getX() == x && e.getY() == y && e.getLayer().getLevel() == currentLayer.getLevel()) {
				e.setDead();
			}
		}

	}
	
	public void hugged(){
		setHealth(-1);
		setFacing(currentDirection, true);
	}

	public void checkForInteractable() throws SlickException {
		if (Keyboard.getEventKeyState()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
				Item i = null;

				if (currentLayer.checkPlayerInteractable(x * tileWidth, y * tileHeight)) {
					i = currentLayer.checkItemToPickup(x * tileWidth, y * tileHeight);
				}

				if (i == null) {
					int[] c = { x * tileWidth, (y + 1) * tileHeight };
					i = currentLayer.checkItemToPickup(c[0], c[1]);
					if (i instanceof Chest) {
						for (Item item : inventory) {
							if (item instanceof Key && ((Key) item).getCode() == ((Chest) i).getCode()) {
								randomBonus();
								Chest newChest = ((Chest) i).unlock();
								currentLayer.removeItem(i);
								inventory.remove(item);
								currentLayer.getContents().add(newChest);
								break;
							}
						}
					}
				} else {
					inventory.add(i);
					if (i instanceof Onion) {
						hasOnion=true;
					}
					currentLayer.removeItem(i);
				}
			}
		}
	}
	
	private void randomBonus() {
		int c = ThreadLocalRandom.current().nextInt(0,3);
		switch (c){
			case 0:
			case 1:
				health++;
				break;
			case 2:
				inventory.add(new Coin());
				break;
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public ArrayList<Item> getInventory() {
		return inventory;
	}

	public Layer getCurrentLayer() {
		return currentLayer;
	}

	public ArrayList<Enemy> getEnemyList() {
		return enemyList;
	}

	public boolean getDead() {
		return dead;
	}

	public int getHealth() {
		return health;
	}
	
	public boolean getHasOnion() {
		return hasOnion;
	}
}

