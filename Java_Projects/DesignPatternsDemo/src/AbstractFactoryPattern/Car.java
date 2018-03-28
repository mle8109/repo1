package AbstractFactoryPattern;

public class Car implements Vehicle {
	@Override
	public void numTires() {
		System.out.println("Car has 4 tires");
	}
}
