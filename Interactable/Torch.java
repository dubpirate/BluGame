package Aesthetic;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import Items.Aesthetic;

public class Torch extends Aesthetic {
	private static String name = "Torch";
	private  String type;
	private int x;
	public Torch(String type) {
		super("res/Aesthetic/"+type+"Torch.png", null, type);
		this.type = type;

	}
	
	@Override
	public int[] genNewCoords(){
		int[] c = new int[2];
		x=(int) (Math.random()*9);
	    x+=(x%2==0?1:0);
	    System.out.println(x);
		if (type.equals("left"))
        {
			
        	c[0] = 0;
        	c[1] = x*48;
        } else {
        	c[0] = 9*38+18;
        	c[1] = x*48;
        }
		
		return c;
	}
}