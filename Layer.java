package Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import Items.*;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import Aesthetic.*;
import Interactable.Chest;
import Interactable.Coin;
import Interactable.Key;
import Interactable.Onion;

import org.lwjgl.opengl.GL11;

public class Layer {
	private static final int TILESIZEHEIGHT = 48;
	private static final int TILESIZEWIDTH = 36;

	private Texture tile;
	private int drawSize;
	private int height; // screen height
	private int width; // screen width
	private int menuWidth = 100;
	private Texture bg;
	private Texture t;
	private int level; // which layer in the stack is this
	private ArrayList<Item> items = new ArrayList<Item>(); // all the items in this layer
	private Stairs stairsUp; // Stairs objects
	private Stairs stairsDown; //
	private String tileFile;
	private int lay;
	private int[] playerPos;
	private Player player;
	private ArrayList<Item> rawItem = new ArrayList<Item>();
	Map<String, Texture> bgTextures = new HashMap<String, Texture>();
	private final String[] textures = { "botRight", "botWall", "Ground", "leftWall", "rightWall", "topLeft", "topRight",
			"topWall", "botLeft" };

	Layer(Layer prev, int level, String tileFile, ArrayList<Item> rawItems, int width, int height, int lay,
			Player player) throws IOException {
		this.level = level;
		this.lay = lay;
		this.rawItem = rawItems;
		this.height = height;
		this.player = player;
		this.width = width;
		this.drawSize = height / 2 - menuWidth;
		this.tileFile = tileFile;

		if (prev != null) {
			try {
				stairsDown = new Stairs(prev.getStairsUp().getCoords(), tileFile + lay + "/Stairsdown", TILESIZEWIDTH,
						TILESIZEHEIGHT);
			} catch (java.lang.NullPointerException e) {
				System.out.println("Error loading stairs on Layer" + level);
				e.printStackTrace();
			}
		}

		initContents(rawItems);

		stairsUp = newStairsUp();

		for (int i = 0; i < textures.length; i++) {
			String tileName = textures[i];
			tile = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream(tileFile + lay + "/" + tileName + ".png"));
			bgTextures.put(tileName, tile);
		}
	}

	private void initContents(ArrayList<Item> c) {
		for (Item i : c) {
			if (i.getCoords() == null) {
				do {
					i.setCoords(i.genNewCoords());
				} while (checkItemCollisions(i));
				this.items.add(i);
			}
		}
	}

	boolean checkPlayerCollision(int x, int y) {
		int[] playerPos = { x, y };
		for (Item i : items) {
			if (i.collidesWith(playerPos) && ((i instanceof Rock) || (i instanceof Chest)))
				return true;
		}
		return false;
	}

	boolean checkPlayerInteractable(int x, int y) {
		int[] playerPos = { x, y };
		for (Item i : items) {
			if (i.collidesWith(playerPos) && ((i instanceof Key) || (i instanceof Coin) || (i instanceof Onion)))
				return true;
		}
		return false;
	}

	public void removeItem(Item i) {
		items.remove(i);
	}

	Item checkItemToPickup(int x, int y) {
		int[] playerPos = { x, y };
		for (Item i : items) {
			if (i.collidesWith(playerPos)
					&& ((i instanceof Chest) || (i instanceof Key) || (i instanceof Coin) || (i instanceof Onion))) {
				return i;
			}
		}
		return null;
	}


	private boolean checkItemCollisions(Item item) {
		if (stairsDown != null && item.collidesWith(stairsDown.getCoords())) {
			return true;
		}
		for (Item i : items) {
			if (i.collidesWith(item.getCoords()))
				return true;
		}
		return false;
	}

	public int getLevel() {
		return this.level;
	}

	public Stairs getStairsUp() {
		return this.stairsUp;
	}

	public Stairs getStairsDown() {
		return this.stairsDown;
	}

	/**
	 * this method looks for a random place in the room to put the stairs. First:
	 * Choose a random x, y between the bounds of the screen, Second: make sure it
	 * doesn't overlap the other stairs or the items --( if it does, pick a
	 * different random x, y) Third: return the random x,y
	 */

	public Stairs newStairsUp() {
		int[] newStairs = new int[2];
		boolean collision = true;
		do {
			collision = false;
			newStairs[0] = ThreadLocalRandom.current().nextInt(1, 10) * TILESIZEWIDTH;
			newStairs[1] = ThreadLocalRandom.current().nextInt(1, 10) * TILESIZEHEIGHT;

			if (stairsDown != null && stairsDown.getCoords()[0] == newStairs[0]
					&& stairsDown.getCoords()[1] == newStairs[1]) {
				collision = true;
			} else {
				for (Item i : this.items)
					if (i.collidesWith(newStairs) && !(i instanceof Crack) && !(i instanceof Moss)) {
						collision = true;
						break;
					}

			}
		} while (collision);
		return new Stairs(newStairs, tileFile + lay + "/Stairsup", TILESIZEWIDTH, TILESIZEHEIGHT);
	}

	public void draw() throws SlickException {
		drawLayer(); // first draw the floor and walls
		drawStairs(); // then the stairs
		drawItems(); // then the items
	}

	private void drawLayer() throws SlickException {

		GL11.glColor3f(1, 1, 1);

		bgTextures.get("Ground").bind();

		for (int i = TILESIZEHEIGHT; i < height / 2 - TILESIZEHEIGHT / 2; i += TILESIZEHEIGHT) {
			for (int j = TILESIZEWIDTH; j < drawSize; j += TILESIZEWIDTH) {
				GL11.glBegin(GL11.GL_QUADS);

				GL11.glVertex2f(j, i);
				GL11.glTexCoord2f(0, 0);

				GL11.glVertex2f(j, i + TILESIZEHEIGHT);
				GL11.glTexCoord2f(1, 0);

				GL11.glVertex2f(j + TILESIZEWIDTH, i + TILESIZEHEIGHT);
				GL11.glTexCoord2f(1, 1);

				GL11.glVertex2f(j + TILESIZEWIDTH, i);
				GL11.glTexCoord2f(0, 1);

				GL11.glEnd();
			}
		}

		bgTextures.get("leftWall").bind();

		for (int i = TILESIZEHEIGHT; i < height / 2 - TILESIZEHEIGHT / 3; i += TILESIZEHEIGHT) {
			GL11.glBegin(GL11.GL_QUADS);

			GL11.glVertex2f(0, i);
			GL11.glTexCoord2f(0, 0);

			GL11.glVertex2f(0, i + TILESIZEHEIGHT);
			GL11.glTexCoord2f(1, 0);

			GL11.glVertex2f(0 + TILESIZEWIDTH, i + TILESIZEHEIGHT);
			GL11.glTexCoord2f(1, 1);

			GL11.glVertex2f(0 + TILESIZEWIDTH, i);
			GL11.glTexCoord2f(0, 1);

			GL11.glEnd();
		}

		bgTextures.get("rightWall").bind();

		for (int i = TILESIZEHEIGHT; i < height / 2 - TILESIZEHEIGHT / 3; i += TILESIZEHEIGHT) {
			GL11.glBegin(GL11.GL_QUADS);

			GL11.glVertex2f(drawSize - TILESIZEWIDTH / 2, i);
			GL11.glTexCoord2f(0, 0);

			GL11.glVertex2f(drawSize - TILESIZEWIDTH / 2, i + TILESIZEHEIGHT);
			GL11.glTexCoord2f(1, 0);

			GL11.glVertex2f(drawSize - TILESIZEWIDTH / 2 + TILESIZEWIDTH, i + TILESIZEHEIGHT);
			GL11.glTexCoord2f(1, 1);

			GL11.glVertex2f(drawSize - TILESIZEWIDTH / 2 + TILESIZEWIDTH, i);
			GL11.glTexCoord2f(0, 1);

			GL11.glEnd();
		}

		bgTextures.get("topWall").bind();

		for (int i = TILESIZEWIDTH; i < drawSize - TILESIZEWIDTH / 2; i += TILESIZEWIDTH) {
			GL11.glBegin(GL11.GL_QUADS);

			GL11.glVertex2f(i, height / 2 - TILESIZEHEIGHT / 3);
			GL11.glTexCoord2f(0, 0);

			GL11.glVertex2f(i, height / 2 - TILESIZEHEIGHT / 3 + TILESIZEHEIGHT);
			GL11.glTexCoord2f(1, 0);

			GL11.glVertex2f(i + TILESIZEWIDTH, height / 2 - TILESIZEHEIGHT / 3 + TILESIZEHEIGHT);
			GL11.glTexCoord2f(1, 1);

			GL11.glVertex2f(i + TILESIZEWIDTH, height / 2 - TILESIZEHEIGHT / 3);
			GL11.glTexCoord2f(0, 1);

			GL11.glEnd();
		}
		bgTextures.get("botWall").bind();

		for (int i = TILESIZEWIDTH; i < drawSize - TILESIZEWIDTH / 2; i += TILESIZEWIDTH) {
			GL11.glBegin(GL11.GL_QUADS);

			GL11.glVertex2f(i, 0);
			GL11.glTexCoord2f(0, 0);

			GL11.glVertex2f(i, 0 + TILESIZEHEIGHT);
			GL11.glTexCoord2f(1, 0);

			GL11.glVertex2f(i + TILESIZEWIDTH, 0 + TILESIZEHEIGHT);
			GL11.glTexCoord2f(1, 1);

			GL11.glVertex2f(i + TILESIZEWIDTH, 0);
			GL11.glTexCoord2f(0, 1);

			GL11.glEnd();
		}
		bgTextures.get("topLeft").bind();
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glVertex2f(0, height / 2 - TILESIZEHEIGHT / 3);
		GL11.glTexCoord2f(0, 0);

		GL11.glVertex2f(0, height / 2 - TILESIZEHEIGHT / 3 + TILESIZEHEIGHT);
		GL11.glTexCoord2f(1, 0);

		GL11.glVertex2f(0 + TILESIZEWIDTH, height / 2 - TILESIZEHEIGHT / 3 + TILESIZEHEIGHT);
		GL11.glTexCoord2f(1, 1);

		GL11.glVertex2f(0 + TILESIZEWIDTH, height / 2 - TILESIZEHEIGHT / 3);
		GL11.glTexCoord2f(0, 1);

		GL11.glEnd();

		bgTextures.get("topRight").bind();
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glVertex2f(drawSize - TILESIZEWIDTH / 2, height / 2 - TILESIZEHEIGHT / 3);
		GL11.glTexCoord2f(0, 0);

		GL11.glVertex2f(drawSize - TILESIZEWIDTH / 2, height / 2 - TILESIZEHEIGHT / 3 + TILESIZEHEIGHT);
		GL11.glTexCoord2f(1, 0);

		GL11.glVertex2f(drawSize + TILESIZEWIDTH / 2, height / 2 - TILESIZEHEIGHT / 3 + TILESIZEHEIGHT);
		GL11.glTexCoord2f(1, 1);

		GL11.glVertex2f(drawSize + TILESIZEWIDTH / 2, height / 2 - TILESIZEHEIGHT / 3);
		GL11.glTexCoord2f(0, 1);

		GL11.glEnd();

		bgTextures.get("botLeft").bind();
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glVertex2f(0, 0);
		GL11.glTexCoord2f(0, 0);

		GL11.glVertex2f(0, 0 + TILESIZEHEIGHT);
		GL11.glTexCoord2f(1, 0);

		GL11.glVertex2f(0 + TILESIZEWIDTH, 0 + TILESIZEHEIGHT);
		GL11.glTexCoord2f(1, 1);

		GL11.glVertex2f(0 + TILESIZEWIDTH, 0);
		GL11.glTexCoord2f(0, 1);

		GL11.glEnd();

		bgTextures.get("botRight").bind();
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glVertex2f(drawSize - TILESIZEWIDTH / 2, 0);
		GL11.glTexCoord2f(0, 0);

		GL11.glVertex2f(drawSize - TILESIZEWIDTH / 2, 0 + TILESIZEHEIGHT);
		GL11.glTexCoord2f(1, 0);

		GL11.glVertex2f(drawSize + TILESIZEWIDTH / 2, 0 + TILESIZEHEIGHT);
		GL11.glTexCoord2f(1, 1);

		GL11.glVertex2f(drawSize + TILESIZEWIDTH / 2, 0);
		GL11.glTexCoord2f(0, 1);

		GL11.glEnd();

	}

	private void drawStairs() {
		// this draws the stairs down, if there are any.
		if (level != 1) {
			stairsDown.draw();
		}

		// this draws the stairs up, if there are any.
		if (level < 20) {
			stairsUp.draw();
		}

	}

	private void drawItems() {
		for (Item i : this.items) {
			i.draw();
		}
	}

	int getLayer() {
		return lay;
	}

	public ArrayList<Item> getContents() {
		return items;
	}
}