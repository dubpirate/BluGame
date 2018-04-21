package Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import Items.*;

public class Game {

	private static final int TILESIZEHEIGHT = 48;
	private static final int TILESIZEWIDTH = 36;
	private static final int WIDTH = 1440; // 1440
	private static final int HEIGHT = 960; // 960
	private static ArrayList<Layer> layers = new ArrayList<Layer>();
	private static Layer currentLayer;
	private Player player;
	private SideMenu sm;
	private int timer = 0;
	private int layerNum = 1;
	private boolean isLoading = true;
	Texture t;

	public static void main(String[] args) throws SlickException, Exception {

		Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
		Display.create();
		
		Game game = new Game();
		while (!Display.isCloseRequested()) {
			game.update();
		}
		game.close();
	}

	public Game() throws IOException, SlickException {
		intiGL();
		int levels = 25;
		generateLayers(levels);
		sm = new SideMenu(3, WIDTH, HEIGHT);
	}

	public void update() throws SlickException, IOException {
		clearGL();



		if (timer > 0) {
			timer--;
		}
		currentLayer.draw();
		if(player.move()) {
			for (int i=0; i<currentLayer.getContents().size(); i++) {
				if (currentLayer.getContents().get(i) instanceof Enemy) {
					((Enemy) currentLayer.getContents().get(i)).move();

				}
				
			}
		}
		int[] stairsUp = currentLayer.getStairsUp().getCoords();
		if (stairsUp[0] / TILESIZEWIDTH == player.getX() && stairsUp[1] / TILESIZEHEIGHT == player.getY()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
				if (timer == 0) {
					currentLayer = layers.get(currentLayer.getLevel());
					player.setCurrentLayer(layers.get(currentLayer.getLevel()));
					timer = 100;
				}
			}
		}

		if (currentLayer.getLevel() > 1) {
			int[] stairsDown = currentLayer.getStairsDown().getCoords();
			if (stairsDown[0] / TILESIZEWIDTH == player.getX() && stairsDown[1] / TILESIZEHEIGHT == player.getY()) {
				if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
					if (timer == 0) {
						currentLayer = layers.get(currentLayer.getLevel() - 2);
						player.setCurrentLayer(currentLayer);
						timer = 100;
					}
				}
			}
		}

		currentLayer.draw();
		for (int i = 0; i < currentLayer.getContents().size(); i++) {
			if (currentLayer.getContents().get(i) instanceof Enemy) {
				((Enemy) currentLayer.getContents().get(i)).draw();
			}
		}
		player.draw();
		sm.draw();

		Display.update();

	}

	
	private void generateLayers(int levels) throws IOException, SlickException {
		ContentsGenerator gs = new ContentsGenerator(levels, currentLevel);
		Layer prev = null;
		ArrayList<Item> contents = new ArrayList<Item>();
		for (int i = 1; i <= levels; i++) {
			if (i % 5 == 0 && i < 21) {
				layerNum++;
			}
			
			
			layers.add(new Layer(prev, i, "res/Layer", gs.getNextContents(), WIDTH, HEIGHT, layerNum));

			prev = layers.get(i - 1);
		}
		currentLayer = layers.get(0);
		player = new Player(TILESIZEHEIGHT,TILESIZEWIDTH,WIDTH,HEIGHT, currentLayer);
		int[] coords= {ThreadLocalRandom.current().nextInt(1, 10) * TILESIZEWIDTH,ThreadLocalRandom.current().nextInt(1, 10) * TILESIZEHEIGHT};
		Item n = new Enemy("res/Sprites/ShadowFront.png", coords, player, currentLayer);
		contents.add(n);
	}
	
	private void close() {
		Display.destroy();
		System.exit(0);
	}

	private void intiGL() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 512, 0, 512, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	private void clearGL() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glLoadIdentity();
	}

}