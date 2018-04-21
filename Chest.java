package Interactable;

import Items.*;

public class Chest extends Aesthetic {
	private int code;
	public Chest(int code) {
		super("res/Interactables/Chest.png", null);	
		this.code = code;
	}
	
	public int getCode(){
		return code;
	}
	
	// later, add some methods about unlocking when person with key comes by.
	
}