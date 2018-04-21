package Interactable;

import Items.*;

public class Coin extends Aesthetic {
	private int code;
	public Coin(int code) {
		super("res/Interactables/Gold.png", null);	
		this.code = code;
	}
	
	public int getCode(){
		return code;
	}
	
	// later, add some methods about unlocking when person with key comes by.
	
}