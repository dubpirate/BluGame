package Main;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import java.util.Random;

public class Player {
	private int x;
	private int y;
	private int width;
	private int height;
	private int tileWidth;
	private int tileHeight;
	private Image img;
	private Layer currentLayer;

	public Player(int tileHeight, int tileWidth, int width, int height, Layer currentLayer) throws SlickException {
		this.width = width;
		this.height = height;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		Random rand = new Random();
		int n = rand.nextInt(9) + 1;
		x = n;
		n = rand.nextInt(9) + 1;
		y = n;
		img = new Image("res/Sprites/Man2Front.png");
		this.currentLayer = currentLayer;
	}

	public void setCurrentLayer(Layer layer) {
		currentLayer = layer;
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

	public boolean move() throws SlickException {

		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_W) {
					if (y < 9 && !currentLayer.checkPlayerCollision(x*tileWidth, (y+1)*tileHeight)) {
						y++;
					}
					img = new Image("res/Sprites/Man2Back.png");
					return true;
				} else if (Keyboard.getEventKey() == Keyboard.KEY_D) {
						if (x < 9 && !currentLayer.checkPlayerCollision((x+1)*tileWidth, (y*tileHeight))) {
							x++;
						
					}
					img = new Image("res/Sprites/Man2Right.png");
					return true;
				} else if (Keyboard.getEventKey() == Keyboard.KEY_S) {
					if (y > 1&& !currentLayer.checkPlayerCollision((x*tileWidth), (y-1)*tileHeight)) {
						y--;
					}
					img = new Image("res/Sprites/Man2Front.png");
					return true;
				} else if (Keyboard.getEventKey() == Keyboard.KEY_A) {
					if (x > 1 && !currentLayer.checkPlayerCollision((x-1)*tileWidth, (y*tileHeight))) {
						x--;
					}
					img = new Image("res/Sprites/Man2Left.png");
					return true;
				}
			}

		}
		return false;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Layer getCurrentLayer() {
		return currentLayer;
	}
}