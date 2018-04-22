package Main;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class TitleScreen {
	public TitleScreen() {

	}

	public double getCursorPosX() {
		int xpos = Mouse.getX();
		return xpos;
	}

	public double getCursorPosY() {
		int ypos = Mouse.getY();
		return ypos;
	}

	public boolean checkPressed() {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
					return true;
				}
			}
		}
		return false;
	}
}
