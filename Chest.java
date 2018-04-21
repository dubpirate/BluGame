package Interactable;

import java.io.IOException;

import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import Items.*;

public class Chest extends Aesthetic {
	private int code;
	public Chest(int code) {
		super("res/Interactables/Chest.png", null);	
		this.code = code;
	}
	
	public Chest unlock() {
		try {
			this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/Interactables/openChest.png"));
		} catch (IOException e) {
			System.out.println("Can't find resource " + texture);
			e.printStackTrace();
		}
		return this;
	}
	
	public int getCode(){
		return code;
	}
	
	// later, add some methods about unlocking when person with key comes by.
	
}