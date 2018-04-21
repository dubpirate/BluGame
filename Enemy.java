package Main;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Aesthetic.Rock;
import Interactable.Chest;

import java.lang.*;

public class Enemy {
	private Player player;
	private Layer currentLayer;
	private int x;
	private int y;
	private int tileWidth;
	private int tileHeight;
	private boolean dead = false;
	Image img;

	public Enemy( Player player, Layer currentLayer, int tileWidth, int tileHeight) throws SlickException {
		this.player = player;
		this.currentLayer = currentLayer;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		setPosition();
		img = new Image("res/Sprites/ShadowFront.png");
		draw();

	}

	public void setPosition() {
		boolean flag = false;
		while(!flag) {
			flag = true;
			x = ThreadLocalRandom.current().nextInt(1, 9);
			y = ThreadLocalRandom.current().nextInt(1, 9);
			for (int i=0; i<currentLayer.getContents().size(); i++) {
				if (currentLayer.getContents().get(i) instanceof Rock || currentLayer.getContents().get(i) instanceof Chest) {
					int[] itemCoords = currentLayer.getContents().get(i).getCoords();
					if (x==itemCoords[0]/tileWidth && y==itemCoords[1]/tileHeight) {
						flag = false;
					}
				}
			}
		}
	}
	
	public void setDead() throws SlickException {
		dead = true;
		img = new Image("res/Sprites/ShadowDead.png");
	}
	
	public void draw() {
		if (currentLayer.getLevel()==player.getCurrentLayer().getLevel()) {
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
	}
	
	public void move() throws SlickException {
		if(dead) {
			return;
		}
		if (currentLayer.getLevel()==player.getCurrentLayer().getLevel()) {
			int dx = x - player.getX();
			int dy = y - player.getY();
			if (dx == 0 && dy== 0) {
				if (x<9) {
					x++;
					img = new Image("res/Sprites/ShadowLeft.png");
					//attack here
					return;
				} else if(y<10) {
					y++;
					img = new Image("res/Sprites/ShadowFront.png");
					//attack here
					return;
				}
			}
			if (dx==1 && dy==0) {
				img = new Image("res/Sprites/ShadowLeft.png");
				//attack here
				return;
			}else if (dx==-1 && dy==0) {
				img = new Image("res/Sprites/ShadowRight.png");
				//attack here
				return;
			}else if (dy==-1 && dx==0) {
				img = new Image("res/Sprites/ShadowBack.png");
				//attack here
				return;
			}else if (dy==1 && dx==0) {
				img = new Image("res/Sprites/ShadowFront.png");
				//attack here
				return;
			}
			int max = dx;
			if (Math.abs(dx)<Math.abs(dy)) {
				max = dy;
			}

			if (max == dy) {
				if (dy < 0) {
					y++;
					img = new Image("res/Sprites/ShadowBack.png");
				} else {
					y--;
					img = new Image("res/Sprites/ShadowFront.png");
				}
			} else if (max == dx) {
				if (dx < 0) {
					x++;
					img = new Image("res/Sprites/ShadowRight.png");
				} else {
					x--;
					img = new Image("res/Sprites/ShadowLeft.png");
				}
			}
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Layer getLayer() {
		return currentLayer;
	}
}