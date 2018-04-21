package Interactable;
import Items.Aesthetic;

public class Key extends Aesthetic {
	int code;
	public Key (int code) {
		super("res/Interactables/Key.png", null);
		this.code = code;
	}
	public int getCode() {
		return code;
	}
}