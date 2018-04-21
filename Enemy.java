package Items;

import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Main.Layer;
import Main.Player;
import java.lang.*;

public class Enemy extends Item {
	private Player player;
	private Layer currentLayer;
	private int x;
	private int y;
	Image img;

	public Enemy(String texture, int[] coords, Player player, Layer currentLayer) throws SlickException {
		super(texture, coords);
		this.player = player;
		this.currentLayer = currentLayer;
		x = coords[0]/TILESIZEWIDTH;
		y = coords[1]/TILESIZEHEIGHT;
		img = new Image(texture);
		draw();

	}

	@Override
	public void draw() {
		if (currentLayer.getLevel()==player.getCurrentLayer().getLevel()) {
			img.bind();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glBegin(GL11.GL_QUADS);

			GL11.glVertex2f(x * TILESIZEWIDTH, y * TILESIZEHEIGHT);
			GL11.glTexCoord2f(0, 0);

			GL11.glVertex2f(x * TILESIZEWIDTH, y * TILESIZEHEIGHT + TILESIZEHEIGHT);
			GL11.glTexCoord2f(1, 0);

			GL11.glVertex2f(x * TILESIZEWIDTH + TILESIZEWIDTH, y * TILESIZEHEIGHT + TILESIZEHEIGHT);
			GL11.glTexCoord2f(1, 1);

			GL11.glVertex2f(x * TILESIZEWIDTH + TILESIZEWIDTH, y * TILESIZEHEIGHT);
			GL11.glTexCoord2f(0, 1);

			GL11.glEnd();
		}	
	}

	@Override
	public int[] getCoords() {
		return coords;
	}

	@Override
	public boolean collidesWith(int[] c) {
		return (c[0] == this.coords[0] && c[1] == this.coords[1]);
	}
	
	public void move() throws SlickException {

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

	@Override
	public void setCoords(int[] c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int[] genNewCoords() {
		// TODO Auto-generated method stub
		return null;
	}
}