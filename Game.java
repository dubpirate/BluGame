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
	
	private static final int TILESIZE = 48;
	private static final int WIDTH  = TILESIZE * 25;  // 1248
	private static final int HEIGHT = TILESIZE * 20; //  960
	private static ArrayList<Layer> layers = new ArrayList<Layer>();
	private static Layer currentLayer;
	private final String[] textures = {"res/Ground1.png", "res/Ground2.png", "res/Ground3.png", "res/Ground4.png", "res/Ground5.png"};
	
	public static void main(String[] args) throws Exception {

		Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
		Display.create();
		Game game = new Game();
		while (!Display.isCloseRequested()) {
			game.update();
		}
		game.close();
	}

	public Game() {
		intiGL();
		generateLayers();
	}
	
	public void update() {
		clearGL();
		
		inputs(new Input(HEIGHT));
		
		currentLayer.draw();
		
		Display.update();
	}
	
	private void generateLayers() {
		for (int i = 0; i < textures.length; i ++) {	
			layers.add(new Layer(i, textures[i], null, WIDTH, HEIGHT));
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