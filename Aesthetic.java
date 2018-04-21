package Items;

import java.util.concurrent.ThreadLocalRandom;

import org.lwjgl.opengl.GL11;

public class Aesthetic extends Item{
	public Aesthetic(String texture, int [] coords, String name) {
		super(texture, coords, name);
	}
	
	@Override
	public void draw() {
		texture.bind();
		
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
	
	@Override
	public int[] getCoords(){ return coords; }
	
	@Override
	public void setCoords(int[] c) {this.coords = c;}

	@Override
	public int[] genNewCoords() {
		int [] c = new int[2];
		c[0] = ThreadLocalRandom.current().nextInt(1, 10)*TILESIZEWIDTH;
		c[1] = ThreadLocalRandom.current().nextInt(1, 10)*TILESIZEHEIGHT;
		return c;
	}
	
	@Override
	public boolean collidesWith(int[] c) {
		return (c[0] == this.coords[0] && c[1] == this.coords[1]);
	}

	@Override
	public String getName() {
		return null;
		
	}
}