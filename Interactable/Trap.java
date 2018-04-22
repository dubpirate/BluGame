package Interactable;

import java.io.IOException;

import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import Items.Aesthetic;

public class Trap extends Aesthetic {
	private boolean tripped = false;
	public Trap(){
		super("res/Interactables/TrapSet.png", null, "Trap");
	}
	
	public boolean getTripped(){
		return tripped;
	}
	
	public void trip() {
		try {
			super.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/Interactables/TrapSprung.png"));
		} catch (IOException e) {
			System.out.println("Can't load Sprung trap texture!");
			e.printStackTrace();
		}
		tripped = true;
	}
}