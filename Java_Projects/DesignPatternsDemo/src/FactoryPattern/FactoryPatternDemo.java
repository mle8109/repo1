package FactoryPattern;

public class FactoryPatternDemo {
	public static void main(String[] args) {
		System.out.println("FactoryPatternDemo");
		ShapeFactory factory = new ShapeFactory();
		
		Shape circle = factory.getShape("circle");
		circle.draw();
		
		Shape square = factory.getShape("square");
		square.draw();
		
		Shape rectangle = factory.getShape("rectangle");
		rectangle.draw();
	}
}
