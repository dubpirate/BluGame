package Main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.tests.xml.Item;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class SideMenu {
	private static final int TILESIZEHEIGHT = 48;
	private static final int TILESIZEWIDTH = 36;
	
	private int height; 
	private int width;
	
	int health;
	Texture heart;
	Texture heartEmpty;
	
	SideMenu(int health, int width, int height) {
		this.health = health;
		this.height = height;
		this.width = width;
		try {
			heart = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/SideMenu/Heart.png"));
			heartEmpty = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/SideMenu/EmptyHeart.png"));
		} catch (IOException e) {
			System.out.println("Could not find Stairs textures!");
			e.printStackTrace();
		}
	}
	
	
	public void draw(){
		GL11.glColor3f(1, 1, 1);
		heart.bind();

		
		for(int i = 0;i<3;i++) {
			GL11.glBegin(GL11.GL_QUADS);

			GL11.glVertex2f((height/2-TILESIZEWIDTH*2-TILESIZEWIDTH/3)+TILESIZEWIDTH*i, height/2-TILESIZEHEIGHT);
			GL11.glTexCoord2f(0, 0);

			GL11.glVertex2f((height/2-TILESIZEWIDTH*2-TILESIZEWIDTH/3)+TILESIZEWIDTH*i, height/2-TILESIZEHEIGHT + TILESIZEHEIGHT);
			GL11.glTexCoord2f(1, 0);

			GL11.glVertex2f((height/2-TILESIZEWIDTH*2-TILESIZEWIDTH/3)+TILESIZEWIDTH*i + TILESIZEWIDTH, height/2-TILESIZEHEIGHT + TILESIZEHEIGHT);
			GL11.glTexCoord2f(1, 1);

			GL11.glVertex2f((height/2-TILESIZEWIDTH*2-TILESIZEWIDTH/3)+TILESIZEWIDTH*i + TILESIZEWIDTH, height/2-TILESIZEHEIGHT);
			GL11.glTexCoord2f(0, 1);

			GL11.glEnd();
		}
		
		
		
	}
}
