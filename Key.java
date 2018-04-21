package Interactable;
import Items.Aesthetic;

public class Key extends Aesthetic {
	int code;
	private static String name = "Key";
	public Key (int code) {
		super("res/Interactables/Key.png", null,name);
		this.code = code;
	}
	public int getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
}