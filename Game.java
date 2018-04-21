package Main;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;

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
	private int timer2 = 0;
	private int layerNum = 1;
	private EnemyGenerator eg;
	private boolean timerSet =true;
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
		int levels = 20;
		
		generateLayers(levels);
		do {
			player = new Player(TILESIZEHEIGHT, TILESIZEWIDTH, currentLayer, new ArrayList<Enemy>());
		} while(currentLayer.checkPlayerCollision(player.getX(), player.getY()));
		
		eg = new EnemyGenerator(levels, player, TILESIZEWIDTH, TILESIZEHEIGHT, layers);
		sm = new SideMenu(3, HEIGHT);
	}

	public void update() throws SlickException, IOException {
		clearGL();
		if (timer2 >0) {
			timer2--;
		}
		if (timer > 0) {
			timer--;
		}
		player.checkForInteractable();
		currentLayer.draw();
		if (player.move()) {
			for (int i = 0; i < eg.getEnemies().size(); i++) {
				eg.getEnemies().get(i).move();
			}
		}

		int[] stairsUp = currentLayer.getStairsUp().getCoords();
		if (!player.getDead()) {
			if (stairsUp[0] / TILESIZEWIDTH == player.getX() && stairsUp[1] / TILESIZEHEIGHT == player.getY()) {
				if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
					if (timer == 0) {
						currentLayer = layers.get(currentLayer.getLevel());
						player.setCurrentLayer(layers.get(currentLayer.getLevel() - 1));
						timer = 80;
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
							timer = 80;
						}
					}
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
		for (int i = 0; i < eg.getEnemies().size(); i++) {
			eg.getEnemies().get(i).draw();
		}
		player.draw();
		sm.draw(player, currentLayer.getLayer());
		if (player.getHasOnion()) {
			if(timerSet) {
				timer2=500;
				timerSet = false;
			}
			win();
			if (timer2==0) {
				close();
			}
		} else if (player.getDead()) {
			if(timerSet) {
				timer2=2000;
				timerSet = false;
			}
			lose();
			if (timer2==0) {
				close();
			}
		}

		Display.update();

	}

	private void generateLayers(int levels) throws IOException, SlickException {
		ContentGenerator gs = new ContentGenerator(levels);
		Layer prev = null;
		for (int i = 1; i <= levels; i++) {
			if (((i-1) % 5) == 0 && i < 21 && (i-1)!=0) {
				layerNum++;
			}

			layers.add(new Layer(prev, i, "res/Layer", gs.getNextContents(), WIDTH, HEIGHT, layerNum, player));
			if (i != 0) {
				prev = layers.get(i-1);
			}
		}
		currentLayer = layers.get(0);

	}

	private void close() {
		Display.destroy();
		System.exit(0);
	}
	
	private void win() throws SlickException {
		Image img = new Image("res/SideMenu/youwin.png");
		img.bind();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glVertex2f(11 * TILESIZEWIDTH , 8 * TILESIZEHEIGHT);
		GL11.glTexCoord2f(0, 0);

		GL11.glVertex2f(11 * TILESIZEWIDTH , 8 * TILESIZEHEIGHT + TILESIZEHEIGHT);
		GL11.glTexCoord2f(1, 0);

		GL11.glVertex2f(11 * TILESIZEWIDTH  + TILESIZEWIDTH*4 , 8 * TILESIZEHEIGHT + TILESIZEHEIGHT);
		GL11.glTexCoord2f(1, 1);

		GL11.glVertex2f(11 * TILESIZEWIDTH  + TILESIZEWIDTH*4 , 8 * TILESIZEHEIGHT);
		GL11.glTexCoord2f(0, 1);

		GL11.glEnd();
	}
	
	private void lose() throws SlickException {
		Image img = new Image("res/SideMenu/youlose.png");
		img.bind();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glVertex2f(11 * TILESIZEWIDTH , 8 * TILESIZEHEIGHT);
		GL11.glTexCoord2f(0, 0);

		GL11.glVertex2f(11 * TILESIZEWIDTH , 8 * TILESIZEHEIGHT + TILESIZEHEIGHT);
		GL11.glTexCoord2f(1, 0);

		GL11.glVertex2f(11 * TILESIZEWIDTH  + TILESIZEWIDTH*4 , 8 * TILESIZEHEIGHT + TILESIZEHEIGHT);
		GL11.glTexCoord2f(1, 1);

		GL11.glVertex2f(11 * TILESIZEWIDTH + TILESIZEWIDTH*4 , 8 * TILESIZEHEIGHT);
		GL11.glTexCoord2f(0, 1);

		GL11.glEnd();
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