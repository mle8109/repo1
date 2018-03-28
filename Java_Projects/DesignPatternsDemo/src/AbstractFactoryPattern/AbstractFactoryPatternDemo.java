package AbstractFactoryPattern;

public class AbstractFactoryPatternDemo {

	public static void main(String[] args) {
		VehicleFactory factory = new VehicleFactory();
		Vehicle car = factory.getVehicle("car");
		car.numTires();
		Vehicle bicycle = factory.getVehicle("bicycle");
		bicycle.numTires();
		Vehicle boat = factory.getVehicle("boat");
		boat.numTires();
		
		Color red = factory.getColor("red");
		red.paint();
		Color blue = factory.getColor("blue");
		blue.paint();		
	}

}
