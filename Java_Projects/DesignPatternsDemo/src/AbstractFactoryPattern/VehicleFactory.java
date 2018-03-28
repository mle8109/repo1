package AbstractFactoryPattern;

public class VehicleFactory extends AbstractFactory {

	@Override
	Vehicle getVehicle(String vehicle) {
		if (vehicle.equalsIgnoreCase("car")) {
			return new Car();
		}
		else if (vehicle.equalsIgnoreCase("bicycle")) {
			return new Bicycle();
		}
		else if (vehicle.equalsIgnoreCase("boat")) {
			return new Boat();
		}
		return null;
	}

	@Override
	Color getColor(String color) {
		if (color.equalsIgnoreCase("red")) {
			return new Red();
		}
		else if (color.equalsIgnoreCase("blue")) {
			return new Blue();
		}
		return null;
	}
}
