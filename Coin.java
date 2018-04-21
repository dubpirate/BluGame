package Interactable;

import Items.*;

public class Coin extends Aesthetic {
	private int code;
	private static String name = "Coin";
	public Coin(int code) {
		super("res/Interactables/Gold.png", null, name);	
		this.code = code;
	}
	
	public int getCode(){
		return code;
	}
	
	public String getName(){
		return name;
	}
	// later, add some methods about unlocking when person with key comes by.
	
}