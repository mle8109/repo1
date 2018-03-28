package AbstractFactoryPattern;

public class Bicycle implements Vehicle {
	@Override
	public void numTires() {
		System.out.println("Bicycle has 2 tires");
	}
}
