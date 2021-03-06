package Main;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Aesthetic.Rock;
import Interactable.Chest;
import Interactable.Trap;
import Items.Item;

public class Enemy {
	Image img;
	private int x;
	private int y;
	private int tileWidth;
	private int tileHeight;
	private Player player;
	private Layer currentLayer;
	private boolean canLeft;
	private boolean canRight;
	private boolean canTop;
	private boolean canBottom;
	private boolean dead = false;

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
			if (currentLayer.getLevel() > 1) {
				int[] stairsDownCoords = currentLayer.getStairsDown().getCoords();
				if (stairsDownCoords[0] / tileWidth == x && stairsDownCoords[1] / tileHeight == y) {
					flag = false;
				}
			}
			if (currentLayer.getLevel() < 20) {
				int[] stairsUpCoords = currentLayer.getStairsUp().getCoords();
				if (stairsUpCoords[0] / tileWidth == x && stairsUpCoords[1] / tileHeight == y) {
					flag = false;
				}
			}
			for (Enemy e: player.getEnemyList()) {
				if (e.getX()==x && e.getY()==y && e.getLayer().getLevel()==currentLayer.getLevel()) {
					flag = false;
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
	
	private void trapCheck() throws IOException, SlickException {
		for (Item i : currentLayer.getContents()){
			if (i instanceof Trap) {
				if (i.getCoords()[0] == x*tileWidth && i.getCoords()[1] == y*tileHeight){
					if (!((Trap) i).getTripped()){
						setDead();
						((Trap) i).trip();
					}
				}
			}
		}
	}
	
	public void move() throws SlickException, IOException {
		if(dead) {
			return;
		}
		if (currentLayer.getLevel()==player.getCurrentLayer().getLevel()) {
			int dx = x - player.getX();
			int dy = y - player.getY();
			checkSurroundings();
			if (dx==1 && dy==0) {
				hug();
				setFacing("Left", true);
				return;
			}else if (dx==-1 && dy==0) {
				hug();
				setFacing("Right", true);
				return;
			}else if (dy==-1 && dx==0) {
				hug();
				setFacing("Back", true);
				return;
			}else if (dy==1 && dx==0) {
				hug();
				setFacing("Front", true);
				return;
			}
			int max = dx;
			if (Math.abs(dx)<Math.abs(dy)) {
				max = dy;
			}

			if (max == dy) {
				if (dy < 0) {
					if (canTop) {
						y++;
					}
					setFacing("Back", false);
				} else {
					if (canBottom) {
						y--;
					}
					setFacing("Front", false);
				}
			} else if (max == dx) {
				if (dx < 0) {
					if (canRight) {
						x++;
					}
					setFacing("Right", false);
				} else {
					if (canLeft) {
						x--;
					}
					setFacing("Left", false);
				}
			}
		}
		try {
			trapCheck();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void hug() throws IOException, SlickException {
		player.hugged();
	}
	
	private void setFacing(String direction, Boolean attacking) {
		try {
			if (attacking){
				img = new Image("res/Sprites/ShadowAttack"+direction+".png");
			} else {
				img = new Image("res/Sprites/Shadow"+direction+".png");
			}
		} catch (SlickException e) {
			System.out.println("Can't setFacing!");
			e.printStackTrace();
		}
	}
	
	public void checkSurroundings() {
		canLeft = true;
		canRight = true;
		canTop = true;
		canBottom = true;
		for(Item i: currentLayer.getContents()) {
			if (i instanceof Rock || i instanceof Chest) {
				int[] coords = i.getCoords();
				if(coords[0]/tileWidth==x-1 && coords[1]/tileHeight==y) {
					canLeft = false;
				}
				if(coords[0]/tileWidth==x+1 && coords[1]/tileHeight==y) {
					canRight = false;
				}
				if(coords[0]/tileWidth==x && coords[1]/tileHeight==y+1) {
					canTop = false;
				}
				if(coords[0]/tileWidth==x && coords[1]/tileHeight==y-1) {
					canBottom = false;
				}
			}
		}
		for(Enemy e:player.getEnemyList()) {
			if (e.getX()==x-1 && e.getY()==y && e.getLayer().getLevel()==currentLayer.getLevel() && !e.getDead()) {
				canLeft = false;
			}
			if (e.getX()==x+1 && e.getY()==y && e.getLayer().getLevel()==currentLayer.getLevel() && !e.getDead()) {
				canRight = false;
			}
			if (e.getX()==x && e.getY()==y+1 && e.getLayer().getLevel()==currentLayer.getLevel() && !e.getDead()) {
				canTop = false;
			}
			if (e.getX()==x && e.getY()==y-1 && e.getLayer().getLevel()==currentLayer.getLevel() && !e.getDead()) {
				canBottom = false;
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
	
	public boolean getDead() {
		return dead;
	}
}