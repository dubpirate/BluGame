package Main;

import Aesthetic.*;
import Interactable.*;
import Items.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class ContentsGenerator{
	private ArrayList<ArrayList<Item>> lists = new ArrayList<ArrayList<Item>>();
	ContentsGenerator(int layers) {
		makeAll(layers);
	}
	
	/**
	 * The Process:
	 * 	set quantity to random variable of how many of each thing there should be.
	 *  then a new instance of each object is created and put into one of the arrayLists
	 * */
	private void makeAll(int levels) {
		ArrayList<Item> list;
		int l;
		int quantity;
		for (int i = 0; i < levels; i ++) {
			list = new ArrayList<Item>();
			lists.add(list);  
			
		}
		
		// Moss
		quantity = ThreadLocalRandom.current().nextInt(levels, levels*4);
		for (int i = 0; i < quantity; i++) {
			do {
				l = pickLayer(levels);
			}while(l >= 15);
			lists.get(l).add(new Moss());
		}
		
		// Rubble
		quantity = ThreadLocalRandom.current().nextInt(levels, levels*4);
		for (int i = 0; i < quantity; i++) {
			lists.get(pickLayer(levels)).add(new Rubble());
		}

		
		// Rock
		quantity = ThreadLocalRandom.current().nextInt(levels, levels*4);
		for (int i = 0; i < quantity; i++) {
			lists.get(pickLayer(levels)).add(new Rock());
		}


		// Cracks
		quantity = ThreadLocalRandom.current().nextInt(levels, levels*4);
		for (int i = 0; i < quantity; i++) {
			lists.get(pickLayer(levels)).add(new Crack());
		}
		
		// Torches (right)
		quantity = ThreadLocalRandom.current().nextInt(levels, levels*4);
		for (int i = 0; i < quantity; i++) {
			lists.get(pickLayer(levels)).add(new Torch("right"));
		}
		
		// Torches (left)
		quantity = ThreadLocalRandom.current().nextInt(levels, levels*4);
		for (int i = 0; i < quantity; i++) {
			lists.get(pickLayer(levels)).add(new Torch("left"));
		}
		
		// Chests and Keys
		quantity = ThreadLocalRandom.current().nextInt(1, 3);
		for (int i = 0; i < quantity; i++) {
			lists.get(pickLayer(levels-1)+1).add(new Chest(i));
			lists.get(pickLayer(levels-1)+1).add(new Key(i));
		}
		
		// Money
		quantity = ThreadLocalRandom.current().nextInt(levels/4, levels/2);
		for (int i = 0; i < quantity; i++) {
			lists.get(pickLayer(levels)).add(new Coin(i));
		}
				
		// Onion
		quantity = ThreadLocalRandom.current().nextInt(1, 2);
		for (int i = 0; i < quantity; i++) {
			lists.get(19).add(new Onion());
		}
	}
	
	private int pickLayer(int max) {
		int choice;
		do {
			choice = ThreadLocalRandom.current().nextInt(0, max); //picks a random layer to put each item
		} while (lists.get(choice).size() > 25);
		return choice;
	}
	
	public ArrayList<Item> getNextContents() {
		return lists.remove(0);
	}
}