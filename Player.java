package Main;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Player {
	private int x;
	private int y;
	private int width;
	private int height;
	private int tileWidth;
	private int tileHeight;
	private Image img;
	
	public Player(int tileHeight, int tileWidth, int height, int width) throws SlickException {
		this.width = width;
		this.height = height;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		x = 3;
		y = 2;
		img = new Image("res/Sprites/Man1Front.png");
	}
	
	
	public void draw() {
		img.bind();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glVertex2f(x*tileWidth, y*tileHeight);
		GL11.glTexCoord2f(0, 0);

		GL11.glVertex2f(x*tileWidth, y*tileHeight + tileHeight);
		GL11.glTexCoord2f(1, 0);

		GL11.glVertex2f(x*tileWidth + tileWidth, y*tileHeight + tileHeight);
		GL11.glTexCoord2f(1, 1);

		GL11.glVertex2f(x*tileWidth + tileWidth, y*tileHeight);
		GL11.glTexCoord2f(0, 1);

		GL11.glEnd();
	}
	
	public void move() throws SlickException {
		while (Keyboard.next()) {
		    if (Keyboard.getEventKeyState()) {
		        if (Keyboard.getEventKey() == Keyboard.KEY_W) {
		        y++;
		        img = new Image("res/Sprites/Man1Back.png");
		        
		        } else if (Keyboard.getEventKey() == Keyboard.KEY_D) {
			    x++;
			    img = new Image("res/Sprites/Man1Side.png");
		        } else if (Keyboard.getEventKey() == Keyboard.KEY_S) {
			    y--;
			    img = new Image("res/Sprites/Man1Front.png");
			    } else if (Keyboard.getEventKey() == Keyboard.KEY_A) {
			    x--;
			    img = new Image("res/Sprites/Man1Side.png");
			    }
		    }
		}
	}
}