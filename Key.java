package Interactable;
import Items.Aesthetic;

public class Key extends Aesthetic {
	int code;
	public Key (int code) {
		super("res/Interactable/Key.png", null);
		this.code = code;
	}
}