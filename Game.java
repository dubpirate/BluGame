package Main;

import java.io.IOException;
import java.util.ArrayList;

import Items.*;
import org.newdawn.slick.SlickException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;


public class Game{
	
	private static final int TILESIZEHEIGHT = 48;
	private static final int TILESIZEWIDTH = 36;
	private static final int WIDTH  = 1440;   // 1440
	private static final int HEIGHT = 960; //  960
	private static ArrayList<Layer> layers = new ArrayList<Layer>();
	private static Layer currentLayer;
	private Player player; 
	
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
		generateLayers();
		player = new Player(TILESIZEHEIGHT,TILESIZEWIDTH,WIDTH,HEIGHT);
	}
	
	public void update() throws SlickException {
		clearGL();
		
		currentLayer.draw();
		player.move();
		player.draw();
		
		Display.update();
	}
	
	private void generateLayers() throws IOException {
		Layer prev = null;
		Layer next = null;
		ArrayList<Item> contents;
		for (int i = 1; i <= 5; i ++) {	
			contents =  new ArrayList<Item>();
			next = new Layer(prev, i, "res/Layer"+i+"/", contents, WIDTH, HEIGHT);
			layers.add(next);
			prev = layers.get(i-1);
		}
		currentLayer = layers.get(0);
	}
	
	private void generateContents() {
		ContentsGenerator contentsGenerator = new ContentsGenerator();
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