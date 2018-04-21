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
	private void makeAll(int layers) {
		ArrayList<Item> list;
		int quantity;
		for (int i = 0; i < layers; i ++) {
			System.out.println("making layer arraysl");
			list = new ArrayList<Item>();
			lists.add(list);  
		}
		
		// Moss
		quantity = ThreadLocalRandom.current().nextInt(layers, layers*4);
		System.out.println("Moss quan:" + quantity);
		for (int i = 0; i < quantity; i++) {
			System.out.println("Adding Moss to layer");
			lists.get(pickLayer()).add(new Moss());
		}
		
		// Rubble
		quantity = ThreadLocalRandom.current().nextInt(layers, layers*4);
		System.out.println("Moss quan:" + quantity);
		for (int i = 0; i < quantity; i++) {
			System.out.println("Adding Moss to layer");
			lists.get(pickLayer()).add(new Rubble());
		}

		
		// Rock
		quantity = ThreadLocalRandom.current().nextInt(layers, layers*2);
		System.out.println("Moss quan:" + quantity);
		for (int i = 0; i < quantity; i++) {
			System.out.println("Adding Moss to layer");
			lists.get(pickLayer()).add(new Rock());
		}


		// Cracks
		quantity = ThreadLocalRandom.current().nextInt(layers, layers*4);
		for (int i = 0; i < quantity; i++) {
			lists.get(pickLayer()).add(new Crack());
		}
		
		// Torches (right)
		quantity = ThreadLocalRandom.current().nextInt(layers, layers*2);
		for (int i = 0; i < quantity; i++) {
			lists.get(pickLayer()).add(new Torch("right"));
		}
		
		// Torches (left)
		quantity = ThreadLocalRandom.current().nextInt(layers,layers*2);
		for (int i = 0; i < quantity; i++) {
			lists.get(pickLayer()).add(new Torch("left"));
		}
		
		// Chests and Keys
		quantity = ThreadLocalRandom.current().nextInt(1, 3);
		for (int i = 0; i < quantity; i++) {
			lists.get(pickLayer()).add(new Chest(i));
			// lists.get(pickLayer()).add(new Torch("left"));
		}
		
		// Enemy
		quantity = ThreadLocalRandom.current().nextInt(layers/4, layers/2);
		for (int i = 0; i < quantity; i++) {
			lists.get(pickLayer()).add(new Enemy());
		}
		
		// Money
		quantity = ThreadLocalRandom.current().nextInt(layers/4, layers/2);
		for (int i = 0; i < quantity; i++) {
			lists.get(pickLayer()).add(new Coin(i));
		}
	}
	
	private int pickLayer() {
		int choice;
		do {
			choice = ThreadLocalRandom.current().nextInt(0, 25); //picks a random layer to put each item
		} while (lists.get(choice).size() > 25);
		return choice;
	}
	
	public ArrayList<Item> getNextContents() {
		return lists.remove(0);
	}
}