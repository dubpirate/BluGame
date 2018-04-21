package Items;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public abstract class Item {
	protected static final int TILESIZEHEIGHT = 48;
	protected static final int TILESIZEWIDTH = 36;
	protected Texture texture;
	protected int[] coords;
	
	Item(String texture, int[] coords, String name){
		try {
			this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(texture));
		} catch (IOException e) {
			System.out.println("Can't find resource" + texture);
			e.printStackTrace();
		}
		
		this.coords = coords;
	} 
	
	public abstract void draw();
	
	public abstract int[] getCoords();
	
	public abstract void setCoords(int[] c);
	
	public abstract int[] genNewCoords();
	
	/**
	 * This method checks if it exists in the same coords as coords passed.
	 *  @param c stands for x-y coords of the thing it might be colliding with.
	 *  @return boolean; true if there is a collision.
	 * */
	public abstract boolean collidesWith(int[] c);

	public abstract String getName();
}