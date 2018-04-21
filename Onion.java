package Interactable;

import Items.*;

public class Onion extends Aesthetic {
	private static String name = "Onion";
	public Onion(){
		super("res/Interactables/Onion.png", null, name);
	}
	
	public String getName(){
		return "Onion";
	}
}