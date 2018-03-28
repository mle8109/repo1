package AbstractFactoryPattern;

public class Boat implements Vehicle {
	@Override
	public void numTires() {
		System.out.println("Boat has no tires");
	}
}
