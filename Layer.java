import java.util.ArrayList;
import java.io.IOException;
import org.newdawn.slick.Input;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Layer {
	private Texture tile;
	private int height;
	private int width;
	private int level; // which layer in the stack is this
	private ArrayList<? extends Item> items;
	private int[] stairsUp;
	private int[] stairsDown;
	
	Layer(int level, String tileFile, ArrayList<? extends Item> items, int width, int height){
		this.level = level;
		this.items = items;
		this.height = height;
		this.width = width;
		
		try {
			tile = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(tileFile));
		} catch(IOException e) {
			System.out.println("Can't find TILE!");
			e.printStackTrace();
		}
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public void draw(){
		drawLayer();
		drawStairs();
		drawItems();
	}
	
	private void drawLayer() {
		
		for (int i = 0; i < height; i += 16) {
			for (int j = 0; j < width; j += 12) {
				GL11.glBegin(GL11.GL_QUADS);

				GL11.glVertex2f(j, i);
				GL11.glTexCoord2f(0, 0);

				GL11.glVertex2f(j,i+16);
				GL11.glTexCoord2f(1, 0);

				GL11.glVertex2f(j+12,i+16);
				GL11.glTexCoord2f(1, 1);

				GL11.glVertex2f(j+12, i);
				GL11.glTexCoord2f(0, 1);

				GL11.glEnd();
			}
		}
		
	}
	
	private void drawStairs(){
		
	}
	
	private void drawItems() {
		
	}
}