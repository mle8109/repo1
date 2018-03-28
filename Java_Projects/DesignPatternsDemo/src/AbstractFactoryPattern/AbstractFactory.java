package AbstractFactoryPattern;

public abstract class AbstractFactory {
	abstract Vehicle getVehicle(String vehicle);
	abstract Color getColor(String color);
}
