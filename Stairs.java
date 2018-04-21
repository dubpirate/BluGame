package Main;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

public class Stairs {
	private int[] coords;
	private int TILESIZEHEIGHT;
	private int TILESIZEWIDTH;
	String type; // layertype + either "Up" or "Down"
	Texture stair;
	
	Stairs(int[] coords, String type, int tsw, int tsh) {
		this.coords = coords;
		this.type = type;
		this.TILESIZEHEIGHT = tsh;
		this.TILESIZEWIDTH = tsw;
		try {
			stair = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/Stairs/Stairs"+type+".png"));
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
	}
}