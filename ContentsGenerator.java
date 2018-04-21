package Main;

import Items.*;
import Main.*;
import Aesthetics.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

import Items.*;

public class ContentsGenerator{
	private ArrayList<ArrayList<Item>> lists;
	ContentsGenerator() {
		
	}
	
	/**
	 * The Process:
	 * 	set quantity to random variable of how many of each thing there should be.
	 *  then a new instance of each object is created and put into one of the arrayLists
	 * */
	private void makeAll() {
		ArrayList<Item> list;
		for (int i = 0; i < 5; i ++) {
			list = new ArrayList<Item>();
			lists.add(list);  
		}
		
		int quantity;
		
		// Moss
		quantity = ThreadLocalRandom.current().nextInt(5, 21);
		for (int i = 0; i < quantity; i++) {
			lists.get(pickLayer()).add(new Moss());
		}
		
		// Cracks
		quantity = ThreadLocalRandom.current().nextInt(5, 21);
		for (int i = 0; i < quantity; i++) {
			lists.get(pickLayer()).add(new Crack());
		}
		
		// Torches (right)
		quantity = ThreadLocalRandom.current().nextInt(1, 5);
		for (int i = 0; i < quantity; i++) {
			lists.get(pickLayer()).add(new Torch("right"));
		}
		
		// Torches (left)
		quantity = ThreadLocalRandom.current().nextInt(1, 5);
		for (int i = 0; i < quantity; i++) {
			lists.get(pickLayer()).add(new Torch("left"));
		}
	}
	
	private int pickLayer() {
		int choice;
		do {
			choice = ThreadLocalRandom.current().nextInt(0, 5);
		} while (lists.get(choice).size() < 25);
		return choice;
	}
	
	public ArrayList<Item> getNextContents() {
		return lists.remove(0);
	}
}