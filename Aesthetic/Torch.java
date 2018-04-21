package Aesthetic;

import java.util.concurrent.ThreadLocalRandom;

import Items.Aesthetic;

public class Torch extends Aesthetic {
	private static String name = "Torch";
	private  String type;
	public Torch(String type) {
		super("res/Aesthetic/"+type+"Torch.png", null, type);
		this.type = type;
	}
	
	@Override
	public int[] genNewCoords(){
		int[] c = new int[2];
				
		if (type.equals("left"))
        {
        	c[0] = 0;
        	c[1] = ThreadLocalRandom.current().nextInt(1, 10)*48;
        } else {
        	c[0] = 9*38+18;
        	c[1] = ThreadLocalRandom.current().nextInt(1, 10)*48;
        }
		
		return c;
	}
}