package Main;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import Interactable.Chest;
import Interactable.Coin;
import Interactable.Key;
import Interactable.Onion;
import Interactable.Trap;
import Items.Item;

import java.io.IOException;
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
	private Audio hitSound;
	private Audio dyingSound;
	private Audio interactSound;

	public Player(int tileHeight, int tileWidth, Layer currentLayer, ArrayList<Enemy> enemyList) throws SlickException {
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

		try {
			hitSound = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/SoundEffects/Hit.wav"));
			dyingSound = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/SoundEffects/Dying.wav"));
			interactSound = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/SoundEffects/Interact.wav"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setCurrentLayer(Layer layer) {
		currentLayer = layer;
	}

	public void setEnemyList(ArrayList<Enemy> enemies) {
		enemyList = enemies;
	}

	public void setHealth(int modifier) {
		health = health + modifier;
		if (health > 3) {
			health = 3;
		}
		if (health <= 0) {
			dyingSound.playAsSoundEffect(1.0f, 1.0f, false);
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
		currentDirection = direction;
		try {
			if (bloody) {
				img = new Image("res/Sprites/Hit" + direction + ".png");
			} else {
				img = new Image("res/Sprites/Man2" + direction + ".png");
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
					setFacing("back", false);
					trapCheck();
					return true;
				} else if (Keyboard.getEventKey() == Keyboard.KEY_D) {
					if (x < 9 && !currentLayer.checkPlayerCollision((x + 1) * tileWidth, (y * tileHeight))) {
						x++;

					}
					enemyAttack(x, y);
					setFacing("right", false);
					trapCheck();
					return true;
				} else if (Keyboard.getEventKey() == Keyboard.KEY_S) {
					if (y > 1 && !currentLayer.checkPlayerCollision((x * tileWidth), (y - 1) * tileHeight)) {
						y--;
					}
					enemyAttack(x, y);
					setFacing("front", false);
					trapCheck();
					return true;
				} else if (Keyboard.getEventKey() == Keyboard.KEY_A) {
					if (x > 1 && !currentLayer.checkPlayerCollision((x - 1) * tileWidth, (y * tileHeight))) {
						x--;
					}
					enemyAttack(x, y);
					setFacing("left", false);
					trapCheck();
					return true;
				}
			}

		}
		return false;
	}
	
	private void trapCheck() {
		for (Item i : currentLayer.getContents()){
			if (i instanceof Trap) {
				if (i.getCoords()[0] == x*tileWidth && i.getCoords()[1] == y*tileHeight){
					if (!((Trap) i).getTripped()){
						hugged();
						((Trap) i).trip();
					}
				}
			}
		}
	}

	public void enemyAttack(int x, int y) throws SlickException {
		for (Enemy e : enemyList) {
			if (e.getX() == x && e.getY() == y && e.getLayer().getLevel() == currentLayer.getLevel()) {
				e.setDead();
				hitSound.playAsSoundEffect(1.0f, 1.0f, false);
			}
		}

	}

	public void hugged() {
		setHealth(-1);
		setFacing(currentDirection, true);
		hitSound.playAsSoundEffect(1.0f, 1.0f, false);
	}

	public void checkForInteractable() throws SlickException {
		if (Keyboard.getEventKeyState()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
				Item i = null;

				if (currentLayer.checkPlayerInteractable(x * tileWidth, y * tileHeight)) {
					i = currentLayer.checkItemToPickup(x * tileWidth, y * tileHeight);
					interactSound.playAsSoundEffect(1.0f, 1.0f, false);
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
						hasOnion = true;
					}
					currentLayer.removeItem(i);
				}
			}
		}
	}

	private void randomBonus() {
		int c = ThreadLocalRandom.current().nextInt(0, 3);
		switch (c) {
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
