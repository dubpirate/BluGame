import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import Items.Item;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Layer {
	private static final int TILESIZEHEIGHT = 48;
	private static final int TILESIZEWIDTH = 36;

	private Texture tile;
	private int drawSize;
	private int height;
	private int width;
	private int menuWidth = 100;
	private int level; // which layer in the stack is this
	private ArrayList<? extends Item> items;
	private int[] stairsUp;
	private int[] stairsDown;
	Map<String, Texture> bgTextures = new HashMap<String, Texture>();
	private final String[] textures = { "botRight", "botWall", "Ground", "leftWall", "rightWall", "topLeft", "topRight",
			"topWall", "botLeft", "StairsupLeft", "StairsupRight" };

	Layer(int level, String tileFile, ArrayList<? extends Item> items, int width, int height) throws IOException {
		this.level = level;
		this.items = items;
		this.height = height;
		this.width = width;
		this.drawSize = height/2-menuWidth;

		for (int i = 0; i < textures.length; i++) {
			String tileName = textures[i];
			tile = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream(tileFile + tileName + ".PNG"));
			bgTextures.put(tileName, tile);
		}
	}

	public int getLevel() {
		return this.level;
	}

	public void draw() {
		drawLayer();
		drawStairs();
		drawItems();
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
		
		bgTextures.get("StairsupLeft").bind();
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glVertex2f(drawSize/2-TILESIZEWIDTH/4, TILESIZEHEIGHT);
		GL11.glTexCoord2f(0, 0);

		GL11.glVertex2f(drawSize/2-TILESIZEWIDTH/4, TILESIZEHEIGHT + TILESIZEHEIGHT);
		GL11.glTexCoord2f(1, 0);

		GL11.glVertex2f(drawSize/2-TILESIZEWIDTH/4 + TILESIZEWIDTH, TILESIZEHEIGHT + TILESIZEHEIGHT);
		GL11.glTexCoord2f(1, 1);

		GL11.glVertex2f(drawSize/2-TILESIZEWIDTH/4 + TILESIZEWIDTH, TILESIZEHEIGHT);
		GL11.glTexCoord2f(0, 1);

		GL11.glEnd();
		bgTextures.get("StairsupRight").bind();
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glVertex2f(drawSize/2+TILESIZEWIDTH-2-TILESIZEWIDTH/4, TILESIZEHEIGHT);
		GL11.glTexCoord2f(0, 0);

		GL11.glVertex2f(drawSize/2+TILESIZEWIDTH-2-TILESIZEWIDTH/4, TILESIZEHEIGHT + TILESIZEHEIGHT);
		GL11.glTexCoord2f(1, 0);

		GL11.glVertex2f(drawSize/2 + TILESIZEWIDTH+TILESIZEWIDTH-2-TILESIZEWIDTH/4, TILESIZEHEIGHT + TILESIZEHEIGHT);
		GL11.glTexCoord2f(1, 1);

		GL11.glVertex2f(drawSize/2 + TILESIZEWIDTH+TILESIZEWIDTH-2-TILESIZEWIDTH/4, TILESIZEHEIGHT);
		GL11.glTexCoord2f(0, 1);

		GL11.glEnd();
		
	}

	private void drawItems() {

	}
}