package Main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import Aesthetics.*;

import org.lwjgl.opengl.GL11;
import Items.*;

public class Layer {
	private static final int TILESIZEHEIGHT = 48;
	private static final int TILESIZEWIDTH = 36;

	private Texture tile;
	private int drawSize;
	private int height; // screen height
	private int width; //  screen width
	private int menuWidth = 100;
	private int level; // which layer in the stack is this
	private ArrayList<Item> contents = new ArrayList<Item>(); // items and aesthetic features of this level
	private Stairs stairsUp;    // Stairs objects
	private Stairs stairsDown; //
	Map<String, Texture> bgTextures = new HashMap<String, Texture>();
	private final String[] textures = { "botRight", "botWall", "Ground", "leftWall", "rightWall", "topLeft", "topRight",
			"topWall", "botLeft"};
	
	Layer(Layer prev, int level, String tileFile, ArrayList<Item> rawContents, int width, int height) throws IOException {
		this.level = level;
		this.height = height;
		this.width = width;
		this.drawSize = height/2-menuWidth;
		
		System.out.println("Donwstairs");
		if (prev != null) {
			try {
			stairsDown = new Stairs(prev.getStairsUp().getCoords(), "down", TILESIZEWIDTH, TILESIZEHEIGHT);
			} catch (java.lang.NullPointerException e) {
				System.out.println("Error loading stairs on Layer" + level);
				e.printStackTrace();
			}
		}
		
		System.out.println("making torch");
		Item n = new Torch("left");
		rawContents.add(n);
		
		System.out.println("initting contents");
		initContents(rawContents);
		
		System.out.println("making upstairs");
		if (level != 5) {
			stairsUp = newStairsUp();
		}
		
		System.out.println("loading textures");
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
			
			if (stairsDown != null && stairsDown.getCoords()[0] != newStairs[0] && stairsDown.getCoords()[1] != newStairs[1]) {
				collision = true;
			} else{
			
			// for (item in items)
				// if collisions with x, y
					// collision = true
					// break;
				//
			//
			}
		} while (collision);
		
		return new Stairs(newStairs, "up", TILESIZEWIDTH, TILESIZEHEIGHT);
	}

	private void initContents(ArrayList<Item> c){
		for (Item i : c) {
			if (i.getCoords() == null) {
				do {
					i.setCoords(i.genNewCoords());
				} while(checkItemCollisions(i));
				System.out.println("Dong");
				this.contents.add(i);
			}
		}
	}
	
	private boolean checkItemCollisions(Item item) {
		if (stairsDown != null && item.collidesWith(stairsDown.getCoords())) {
			return true;
		}
		for (Item i : contents) {
			if (i.collidesWith(item.getCoords())) return true;
		}
		return false;
	}
	
	public void draw() {
		drawLayer();     // first draw the floor and walls
		drawStairs();   //  then the stairs
		drawContents();
	}

	private void drawLayer() {

		GL11.glColor3f(1, 1, 1);


		bgTextures.get("Ground").bind();

		for (int i = TILESIZEHEIGHT; i < height/2-TILESIZEHEIGHT/3; i += TILESIZEHEIGHT) {
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

			GL11.glVertex2f(drawSize, i);
			GL11.glTexCoord2f(0, 0);

			GL11.glVertex2f(drawSize, i + TILESIZEHEIGHT);
			GL11.glTexCoord2f(1, 0);

			GL11.glVertex2f(drawSize + TILESIZEWIDTH, i + TILESIZEHEIGHT);
			GL11.glTexCoord2f(1, 1);

			GL11.glVertex2f(drawSize + TILESIZEWIDTH, i);
			GL11.glTexCoord2f(0, 1);

			GL11.glEnd();
		}

		bgTextures.get("topWall").bind();

		for (int i = TILESIZEWIDTH; i < drawSize; i += TILESIZEWIDTH) {
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

		for (int i = TILESIZEWIDTH; i < drawSize; i += TILESIZEWIDTH) {
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

		GL11.glVertex2f(drawSize, height/2-TILESIZEHEIGHT/3);
		GL11.glTexCoord2f(0, 0);

		GL11.glVertex2f(drawSize, height/2-TILESIZEHEIGHT/3 + TILESIZEHEIGHT);
		GL11.glTexCoord2f(1, 0);

		GL11.glVertex2f(drawSize + TILESIZEWIDTH, height/2-TILESIZEHEIGHT/3 + TILESIZEHEIGHT);
		GL11.glTexCoord2f(1, 1);

		GL11.glVertex2f(drawSize + TILESIZEWIDTH, height/2-TILESIZEHEIGHT/3);
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

		GL11.glVertex2f(drawSize, 0);
		GL11.glTexCoord2f(0, 0);

		GL11.glVertex2f(drawSize, 0 + TILESIZEHEIGHT);
		GL11.glTexCoord2f(1, 0);

		GL11.glVertex2f(drawSize + TILESIZEWIDTH, 0 + TILESIZEHEIGHT);
		GL11.glTexCoord2f(1, 1);

		GL11.glVertex2f(drawSize + TILESIZEWIDTH, 0);
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

	private void drawContents() {
		for (Item i : this.contents) { 
			i.draw();
		}
	}
}