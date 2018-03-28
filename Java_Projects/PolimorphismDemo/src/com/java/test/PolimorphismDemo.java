package com.java.test;

import java.util.ArrayList;
import java.util.List;

public class PolimorphismDemo {

	public static void main(String[] args) {
		System.out.println("Testing...");
		List<Shape> arr = new ArrayList<Shape>();
		arr.add(new Square());
		arr.add(new Circle());
		arr.add(new Rectangle());
		
		for (Shape s: arr) {
			s.display();
		}
	}

}
