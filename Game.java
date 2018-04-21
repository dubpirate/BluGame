import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
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

	public Game() throws IOException {
		intiGL();
		generateLayers();
		player = new Player(TILESIZEHEIGHT,TILESIZEWIDTH,WIDTH,HEIGHT);
	}
	
	public void update() {
		clearGL();
		
		//inputs(new Input(HEIGHT));
		
		currentLayer.draw();
		player.move();
		player.draw();
		
		Display.update();
	}
	
	private void generateLayers() throws IOException {
		Layer prev = null;
		for (int i = 1; i <= 5; i ++) {	
			layers.add(new Layer(prev, i, "res/Layer"+i+"/", null, WIDTH, HEIGHT));
			prev = layers.get(i-1);
		}
		currentLayer = layers.get(0);
	}
	
	private void inputs(Input input) {
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			close();
		} else if (input.isKeyDown(Input.KEY_X)) {
			if (currentLayer.getLevel() != 4) {
				currentLayer = layers.get(currentLayer.getLevel()+1);
			}
		} else if (input.isKeyDown(Input.KEY_Z)) {
			if (currentLayer.getLevel() != 0) {
				currentLayer = layers.get(currentLayer.getLevel()-1);
			}
		}
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
