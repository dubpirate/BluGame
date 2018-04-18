import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

public class Start {
	private static int screenWidth = 640;
	private static int screenHeight = 480;
	public static void main(String[] args) {
		// GLFW allows us to draw windows, without it, we can't do nothing
		if (!glfwInit()) {
			throw new IllegalStateException("Failed to Initialise GLFW!");
		}
		
		// This ---
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		
		// initialise a new window using the screen size fields.
		long window = GLFW.glfwCreateWindow(screenWidth, screenHeight, "BluJam", 0, 0);
		if (window == 0) {
			throw new IllegalStateException("Failed to Create Window!");
		}
		
		//
		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (videoMode.width() - screenWidth / 2), (videoMode.height() - screenWidth / 2));
	
		glfwShowWindow(window);
		
		// make the openGL context
		glfwMakeContextCurrent(window);
		
		GL.createCapabilities();
		
		// change this to whatever color we want the screen to be when it clears
		// glClearColor(float red, float green, float blue, float alpha);		
		while (!glfwWindowShouldClose(window)) {
			glfwPollEvents();
			
			// sets every pixel to whatever is set in glClearColor (currently black)
			glClear(GL_COLOR_BUFFER_BIT);
			
			glfwSwapBuffers(window);
		}
		
		// clears everything out of the memory
		glfwTerminate();
	}
}
