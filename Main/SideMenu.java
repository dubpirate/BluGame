package Main;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

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

		// System.out.println(counter);

		ArrayList<Item> item = new ArrayList<Item>(player.getInventory());
		int max = item.size();
		int count = 0;
		while (count < max) {
			for (int i = 0; i < (player.getInventory().size() + 2) / 3; i++) {
				for (int j = 0; j < 3; j++) {
					System.out.println(count);
					Image inv = new Image("res/Interactables/"+item.get(count).getName()+".png");
					inv.bind();
					GL11.glBegin(GL11.GL_QUADS);

					GL11.glVertex2f(minX + j * TILESIZEWIDTH, 300 - i * TILESIZEHEIGHT);
					GL11.glTexCoord2f(0, 0);

					GL11.glVertex2f(minX + j * TILESIZEWIDTH, +300 - i * TILESIZEHEIGHT + TILESIZEHEIGHT);
					GL11.glTexCoord2f(1, 0);

					GL11.glVertex2f(minX + j * TILESIZEWIDTH + TILESIZEWIDTH,
							300 - i * TILESIZEHEIGHT + TILESIZEHEIGHT);
					GL11.glTexCoord2f(1, 1);

					GL11.glVertex2f(minX + j * TILESIZEWIDTH + TILESIZEWIDTH, 300 - i * TILESIZEHEIGHT);
					GL11.glTexCoord2f(0, 1);

					GL11.glEnd();
					count++;
					if (count >= max) {
						return;
					}
				}
			}
		}

	}
}
