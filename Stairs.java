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

public class Stairs {
	int[] coords;
	int TILESIZEHEIGHT;
	int TILESIZEWIDTH;
	String type; // layertype + either "Up" or "Down"
	Texture stair;
	
	Stairs(int[] coords, String type, int tsw, int tsh) {
		this.coords = coords;
		this.type = type;
		this.TILESIZEHEIGHT = tsh;
		this.TILESIZEWIDTH = tsw;
		try {
			stair = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/Layer1/Stairsup.png"));
		} catch (IOException e) {
			System.out.println("Could not find Stairs textures!");
			e.printStackTrace();
		}
	}
	
	public int[] getCoords() {
		return this.coords;
	}
	
	public void draw(){
		stair.bind();
		
		GL11.glEnable(GL11.GL_BLEND); 
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glVertex2f(coords[0], coords[1]);
		GL11.glTexCoord2f(0, 0);

		GL11.glVertex2f(coords[0] , coords[1] + TILESIZEHEIGHT);
		GL11.glTexCoord2f(1, 0);

		GL11.glVertex2f(coords[0]  + TILESIZEWIDTH, coords[1] + TILESIZEHEIGHT);
		GL11.glTexCoord2f(1, 1);

		GL11.glVertex2f(coords[0]  + TILESIZEWIDTH, coords[1]);
		GL11.glTexCoord2f(0, 1);

		GL11.glEnd();
		
		/*right.bind();
		
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glVertex2f(coords[0] + TILESIZEWIDTH - 1 - TILESIZEWIDTH/2, coords[1]);
		GL11.glTexCoord2f(0, 0);

		GL11.glVertex2f(coords[0]+TILESIZEWIDTH-1-TILESIZEWIDTH/2, coords[1] + TILESIZEHEIGHT);
		GL11.glTexCoord2f(1, 0);

		GL11.glVertex2f(coords[0] + TILESIZEWIDTH+TILESIZEWIDTH-1-TILESIZEWIDTH/2, coords[1] + TILESIZEHEIGHT);
		GL11.glTexCoord2f(1, 1);

		GL11.glVertex2f(coords[0] + TILESIZEWIDTH+TILESIZEWIDTH-1-TILESIZEWIDTH/2, coords[1]);
		GL11.glTexCoord2f(0, 1);

		GL11.glEnd();
		*/
	}
}
