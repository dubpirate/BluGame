package Main;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import Interactable.*;
import Items.Item;

public class SideMenu {
	private static final int TILESIZEHEIGHT = 48;
	private static final int TILESIZEWIDTH = 36;
	private int height;

	private int minX;

	int health;
	Texture heart;
	Texture heartEmpty;
	Texture bg;
	int layer;

	SideMenu(int health, int height) {
		this.health = health;
		this.height = height;
		this.minX = height / 2 - TILESIZEWIDTH * 2 - TILESIZEWIDTH / 3;
		try {
			bg = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/Layer1/Side.png"));
			heart = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/SideMenu/Heart.png"));
			heartEmpty = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream("res/SideMenu/EmptyHeart.png"));
		} catch (IOException e) {
			System.out.println("Could not find Stairs textures!");
			e.printStackTrace();
		}
	}

	public void draw(Player player, int layer) throws IOException, SlickException {
		GL11.glColor3f(1, 1, 1);
		bg = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/Layer" + layer + "/Side.png"));
		bg.bind();
		for (int i = 0; i < height; i += TILESIZEHEIGHT) {
			for (int j = 0; j < 4; j++) {
				GL11.glBegin(GL11.GL_QUADS);

				GL11.glVertex2f(minX + j * TILESIZEWIDTH, i);
				GL11.glTexCoord2f(0, 0);

				GL11.glVertex2f(minX + j * TILESIZEWIDTH, i + TILESIZEHEIGHT);
				GL11.glTexCoord2f(1, 0);

				GL11.glVertex2f(minX + j * TILESIZEWIDTH + TILESIZEWIDTH, i + TILESIZEHEIGHT);
				GL11.glTexCoord2f(1, 1);

				GL11.glVertex2f(minX + j * TILESIZEWIDTH + TILESIZEWIDTH, i);
				GL11.glTexCoord2f(0, 1);

				GL11.glEnd();
			}
		}

		heart.bind();

		for (int i = 0; i < 3; i++) {
			if (i < player.getHealth()) {
				heart.bind();
			} else {
				heartEmpty.bind();
			}
			GL11.glBegin(GL11.GL_QUADS);

			GL11.glVertex2f((height / 2 - TILESIZEWIDTH * 2 - TILESIZEWIDTH / 3) + TILESIZEWIDTH * i,
					height / 2 - TILESIZEHEIGHT);
			GL11.glTexCoord2f(0, 0);

			GL11.glVertex2f((height / 2 - TILESIZEWIDTH * 2 - TILESIZEWIDTH / 3) + TILESIZEWIDTH * i,
					height / 2 - TILESIZEHEIGHT + TILESIZEHEIGHT);
			GL11.glTexCoord2f(1, 0);

			GL11.glVertex2f((height / 2 - TILESIZEWIDTH * 2 - TILESIZEWIDTH / 3) + TILESIZEWIDTH * i + TILESIZEWIDTH,
					height / 2 - TILESIZEHEIGHT + TILESIZEHEIGHT);
			GL11.glTexCoord2f(1, 1);

			GL11.glVertex2f((height / 2 - TILESIZEWIDTH * 2 - TILESIZEWIDTH / 3) + TILESIZEWIDTH * i + TILESIZEWIDTH,
					height / 2 - TILESIZEHEIGHT);
			GL11.glTexCoord2f(0, 1);

			GL11.glEnd();
		}
		
		drawInventory(player);
	}
	
	private void drawInventory(Player player){
		int keys = 0;
		int coins = 0;
		int shrek = 0;
		int count = 0;

		for (Item i : player.getInventory()) {
			if (i instanceof Key) keys++;
			else if (i instanceof Coin) coins ++;
			else if (i instanceof Onion) shrek ++;
		}
		
		if (keys > 0){
			count ++;
			drawLine((Item) new Key(0), Integer.toString(keys), count);
		}
		
		if (coins > 0) {
			count ++;
			drawLine((Item) new Coin(), Integer.toString(coins), count);
		}
		
		if (shrek > 0) {
			count ++;
			drawLine((Item) new Onion(), Integer.toString(shrek), count);
		}
	}
	
	private void drawLine(Item item, String number, int y) {
		// drawLine is recieving:
		//  - Key to extract the name and draw it;
		//  - A string of the number (eg '12') to draw it in characters
		//  - The Level that it's being drawn on.
		try {
			Image type = new Image("res/Interactables/"+item.getName()+".png");
			type.bind();
			GL11.glBegin(GL11.GL_QUADS);
		
			GL11.glVertex2f(minX, 300 - y * TILESIZEHEIGHT);
			GL11.glTexCoord2f(0, 0);
		
			GL11.glVertex2f(minX, 300 - y * TILESIZEHEIGHT + TILESIZEHEIGHT);
			GL11.glTexCoord2f(1, 0);
		
			GL11.glVertex2f(minX + TILESIZEWIDTH, 300 - y * TILESIZEHEIGHT + TILESIZEHEIGHT);
			GL11.glTexCoord2f(1, 1);
		
			GL11.glVertex2f(minX + TILESIZEWIDTH, 300 - y * TILESIZEHEIGHT);
			GL11.glTexCoord2f(0, 1);
		
			GL11.glEnd();
			
			Image colon = new Image("res/SideMenu/Colon.png");
			colon.bind();
			GL11.glBegin(GL11.GL_QUADS);
		
			GL11.glVertex2f(minX + (TILESIZEWIDTH), 300 - y * (TILESIZEHEIGHT));
			GL11.glTexCoord2f(0, 0);
		
			GL11.glVertex2f(minX + (TILESIZEWIDTH), 300 - y * TILESIZEHEIGHT + TILESIZEHEIGHT);
			GL11.glTexCoord2f(1, 0);
		
			GL11.glVertex2f(minX + (TILESIZEWIDTH) + TILESIZEWIDTH, 300 - y * TILESIZEHEIGHT + TILESIZEHEIGHT);
			GL11.glTexCoord2f(1, 1);
		
			GL11.glVertex2f(minX + (TILESIZEWIDTH) + TILESIZEWIDTH, 300 - y * TILESIZEHEIGHT);
			GL11.glTexCoord2f(0, 1);
		
			GL11.glEnd();
			
			Image num;
			for (int i = 0; i < number.length(); i++){
			    char c = number.charAt(i);        
			    num = new Image("res/SideMenu/"+c+".png");
				num.bind();
				GL11.glBegin(GL11.GL_QUADS);
		
				GL11.glVertex2f(minX + (i*TILESIZEWIDTH)+(2*TILESIZEWIDTH)-30, 300 - y * TILESIZEHEIGHT);
				GL11.glTexCoord2f(0, 0);
		
				GL11.glVertex2f(minX + (i*TILESIZEWIDTH)+(2*TILESIZEWIDTH)-30, 300 - y * TILESIZEHEIGHT + TILESIZEHEIGHT);
				GL11.glTexCoord2f(1, 0);
		
				GL11.glVertex2f(minX + (i*TILESIZEWIDTH)+(2*TILESIZEWIDTH)-30 + TILESIZEWIDTH, 300 - y * TILESIZEHEIGHT + TILESIZEHEIGHT);
				GL11.glTexCoord2f(1, 1);
		
				GL11.glVertex2f(minX + (i*TILESIZEWIDTH)+(2*TILESIZEWIDTH)-30 + TILESIZEWIDTH, 300 - y * TILESIZEHEIGHT);
				GL11.glTexCoord2f(0, 1);
		
				GL11.glEnd();
				
			}
		} catch (SlickException e) {
			System.out.println("Can't draw inventory!");
			e.printStackTrace();
		}
	}
}
