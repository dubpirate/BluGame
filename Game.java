import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;


public class Game{
	
	public static void main(String[] args) throws Exception{
		Display.setDisplayMode(new DisplayMode(1000,750));
		Display.create();
		Game game = new Game();
		while(!Display.isCloseRequested()) {
			game.update();
		}
		game.close();
	}
	
	public Game() {
		intiGL();
		try {
			boxTexture = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("res/yes.jpg"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	Texture boxTexture;
	
	public void update() {
		clearGL();
		
		boxTexture.bind();
		
		
		GL11.glColor3f(1,1,1);
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glVertex2f(100,100);
		GL11.glTexCoord2f(0,0);
		
		GL11.glVertex2f(140,100);
		GL11.glTexCoord2f(1,0);
		
		GL11.glVertex2f(140,140);
		GL11.glTexCoord2f(1,1);
		
		GL11.glVertex2f(100,140);
		GL11.glTexCoord2f(0,1);
		
		GL11.glEnd();
		
		Display.update();
	}
	
	public void close() {
		Display.destroy();
		System.exit(0);
	}
	
	public void intiGL() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0,512,0,512,1,-1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
	
	public void clearGL() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glLoadIdentity();
	}
	
}