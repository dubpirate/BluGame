package Interactable;

import Items.*;

public class Map extends Aesthetic {
	private int code;
	public Map(int code) {
		super("res/Interactables/Map.png", null);	
		this.code = code;
	}
	
	public int getCode(){
		return code;
	}
	
	// later, add some methods about unlocking when person with key comes by.
	
}