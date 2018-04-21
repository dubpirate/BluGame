package Interactable;

import Items.*;

public class Map extends Aesthetic {
	private int code;
	private static String name = "Map";
	public Map(int code) {
		super("res/Interactables/Map.png", null, name);	
		this.code = code;
	}
	
	public int getCode(){
		return code;
	}
	
	// later, add some methods about unlocking when person with key comes by.
	
}