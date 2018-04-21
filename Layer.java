package Main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.tests.xml.Item;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Layer {
	private static final int TILESIZEHEIGHT = 48;
	private static final int TILESIZEWIDTH = 36;

	private Texture tile;
	private int drawSize;
	private int height; // screen height
	private int width; //  screen width
	private int menuWidth = 100;
	private Texture bg;
	private int level; // which layer in the stack is this
	private ArrayList<? extends Item> items; // all the items in this layer
	private Stairs stairsUp;    // Stairs objects
	private Stairs stairsDown; //
	Map<String, Texture> bgTextures = new HashMap<String, Texture>();
	private final String[] textures = { "botRight", "botWall", "Ground2", "leftWall", "rightWall", "topLeft", "topRight",
			"topWall", "botLeft"};

	Layer(Layer prev, int level, String tileFile, ArrayList<? extends Item> items, int width, int height) throws IOException {
		this.level = level;
		this.items = items;
		this.height = height;
		this.width = width;
		this.drawSize = height/2-menuWidth;
		
		if (prev != null) {
			try {
			stairsDown = new Stairs(prev.getStairsUp().getCoords(), "res/Layer" + level + "/Stairsdown", TILESIZEWIDTH, TILESIZEHEIGHT);
			} catch (java.lang.NullPointerException e) {
				System.out.println("Error loading stairs on Layer" + level);
				e.printStackTrace();
			}
		}
		
		if (level != 5) {
			stairsUp = newStairsUp();
		}
		bg = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/sideMenuBG.png"));
		for (int i = 0; i < textures.length; i++) {
			String tileName = textures[i];
			tile = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(tileFile + tileName + ".png"));
			bgTextures.put(tileName, tile);
		}
	}

	public int getLevel() {
		return this.level;
	}
	
	public Stairs getStairsUp() {
		return this.stairsUp;
	}
	
	
	/**
	 * this method looks for a random place in the room to put the stairs.
	 * First: Choose a random x, y between the bounds of the screen,
	 * Second: make sure it doesn't overlap the other stairs or the items
	 *  --( if it does, pick a different random x, y)
	 * Third: return the random x,y
	 */
	public Stairs newStairsUp() {
		int[] newStairs = new int[2];
		boolean collision = true;
		do {
			collision = false;
			newStairs[0] = ThreadLocalRandom.current().nextInt(1, 10)*TILESIZEWIDTH;
			newStairs[1] = ThreadLocalRandom.current().nextInt(1, 10)*TILESIZEHEIGHT;
			
			// for (item in items)
				// if collisions with x, y
					// collision = true;
					// break;
				//
			//
		} while (collision);
		
		return new Stairs(newStairs, "res/Layer" + level + "/Stairsup", TILESIZEWIDTH, TILESIZEHEIGHT);
	}

	public void draw() throws SlickException {
		drawLayer();     // first draw the floor and walls
		drawStairs();   //  then the stairs
		drawItems();   //   then the items
	}

	private void drawLayer() throws SlickException {

		GL11.glColor3f(1, 1, 1);

		bgTextures.get("Ground2").bind();

		for (int i = TILESIZEHEIGHT; i < height/2-TILESIZEHEIGHT/2; i += TILESIZEHEIGHT) {
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

		for (int i = TILESIZEHEIGHT; i < height/2-TILESIZEHEIGHT/3; i += TILESIZEHEIGHT) {
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

		for (int i = TILESIZEHEIGHT; i < height/2-TILESIZEHEIGHT/3; i += TILESIZEHEIGHT) {
			GL11.glBegin(GL11.GL_QUADS);

			GL11.glVertex2f(drawSize-TILESIZEWIDTH/2, i);
			GL11.glTexCoord2f(0, 0);

			GL11.glVertex2f(drawSize-TILESIZEWIDTH/2, i + TILESIZEHEIGHT);
			GL11.glTexCoord2f(1, 0);

			GL11.glVertex2f(drawSize-TILESIZEWIDTH/2 + TILESIZEWIDTH, i + TILESIZEHEIGHT);
			GL11.glTexCoord2f(1, 1);

			GL11.glVertex2f(drawSize-TILESIZEWIDTH/2 + TILESIZEWIDTH, i);
			GL11.glTexCoord2f(0, 1);

			GL11.glEnd();
		}

		bgTextures.get("topWall").bind();

		for (int i = TILESIZEWIDTH; i < drawSize-TILESIZEWIDTH/2; i += TILESIZEWIDTH) {
			GL11.glBegin(GL11.GL_QUADS);

			GL11.glVertex2f(i, height/2-TILESIZEHEIGHT/3);
			GL11.glTexCoord2f(0, 0);

			GL11.glVertex2f(i, height/2-TILESIZEHEIGHT/3 + TILESIZEHEIGHT);
			GL11.glTexCoord2f(1, 0);

			GL11.glVertex2f(i + TILESIZEWIDTH, height/2-TILESIZEHEIGHT/3 + TILESIZEHEIGHT);
			GL11.glTexCoord2f(1, 1);

			GL11.glVertex2f(i + TILESIZEWIDTH, height/2-TILESIZEHEIGHT/3);
			GL11.glTexCoord2f(0, 1);

			GL11.glEnd();
		}
		bgTextures.get("botWall").bind();

		for (int i = TILESIZEWIDTH; i < drawSize-TILESIZEWIDTH/2; i += TILESIZEWIDTH) {
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

		GL11.glVertex2f(0, height/2-TILESIZEHEIGHT/3);
		GL11.glTexCoord2f(0, 0);

		GL11.glVertex2f(0, height/2-TILESIZEHEIGHT/3 + TILESIZEHEIGHT);
		GL11.glTexCoord2f(1, 0);

		GL11.glVertex2f(0 + TILESIZEWIDTH, height/2-TILESIZEHEIGHT/3 + TILESIZEHEIGHT);
		GL11.glTexCoord2f(1, 1);

		GL11.glVertex2f(0 + TILESIZEWIDTH, height/2-TILESIZEHEIGHT/3);
		GL11.glTexCoord2f(0, 1);

		GL11.glEnd();

		bgTextures.get("topRight").bind();
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glVertex2f(drawSize-TILESIZEWIDTH/2, height/2-TILESIZEHEIGHT/3);
		GL11.glTexCoord2f(0, 0);

		GL11.glVertex2f(drawSize-TILESIZEWIDTH/2, height/2-TILESIZEHEIGHT/3 + TILESIZEHEIGHT);
		GL11.glTexCoord2f(1, 0);

		GL11.glVertex2f(drawSize+TILESIZEWIDTH/2, height/2-TILESIZEHEIGHT/3 + TILESIZEHEIGHT);
		GL11.glTexCoord2f(1, 1);

		GL11.glVertex2f(drawSize+TILESIZEWIDTH/2, height/2-TILESIZEHEIGHT/3);
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

		GL11.glVertex2f(drawSize-TILESIZEWIDTH/2, 0);
		GL11.glTexCoord2f(0, 0);

		GL11.glVertex2f(drawSize-TILESIZEWIDTH/2, 0 + TILESIZEHEIGHT);
		GL11.glTexCoord2f(1, 0);

		GL11.glVertex2f(drawSize+TILESIZEWIDTH/2, 0 + TILESIZEHEIGHT);
		GL11.glTexCoord2f(1, 1);

		GL11.glVertex2f(drawSize+TILESIZEWIDTH/2, 0);
		GL11.glTexCoord2f(0, 1);

		GL11.glEnd();
	

	}

	private void drawStairs() {
		// this draws the stairs down, if there are any.
		if (level != 1) {
			stairsDown.draw();
		}
		
		// this draws the stairs down, if there are any.
		if (level != 5) {
			stairsUp.draw();
		}
		
	}

	private void drawItems() {

	}
}