package Interactable;

import Items.*;

public class Coin extends Aesthetic {
	private static String name = "Coin";
	public Coin() {
		super("res/Interactables/Coin.png", null, name);
	}
	public String getName(){
		return name;
	}
	// later, add some methods about unlocking when person with key comes by.
}