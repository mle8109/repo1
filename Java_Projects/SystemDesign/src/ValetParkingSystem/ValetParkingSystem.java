package ValetParkingSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ValetParkingSystem {
	public List<ParkingSpot> parkStation = new ArrayList<ParkingSpot>();

	private void initializeParking() {
		for (int i = 0; i < 9; i++) {
			if (i < 3) {
				parkStation.add(new ParkingSpot(Integer.toString(i), Size.SMALL, null));
			} else if (3 <= i && i < 6) {
				parkStation.add(new ParkingSpot(Integer.toString(i), Size.MEDIUM, null));
			} else {
				parkStation.add(new ParkingSpot(Integer.toString(i), Size.LARGE, null));
			}
		}
	}

	public ParkingSpot assignSpot(Vehicle v) {
		ParkingSpot spot = null;
		Size size = v.getVehicleSize();
		switch (size) {
		case SMALL:
			spot = findSpot(size);
			if (spot == null) {
				spot = findSpot(Size.MEDIUM);
				if (spot == null) {
					spot = findSpot(Size.LARGE);
				} else {
					return spot;
				}
			} else {
				return spot;
			}
		case MEDIUM:
			spot = findSpot(size);
			if (spot == null) {
				spot = findSpot(Size.LARGE);
			} else {
				return spot;
			}
		case LARGE:
			return findSpot(size);
		}

		return null;
	}

	private ParkingSpot findSpot(Size size) {
		for (ParkingSpot ps : parkStation) {
			if (ps.getSlotSize().equals(size) && isAvailableSpot(ps)) {
				return ps;
			}
		}
		return null;
	}

	public boolean isAvailableSpot(ParkingSpot spot) {
		return spot.isAvailable();
	}

	private void printParkingStatus() {
		for (ParkingSpot sp : parkStation) {
			System.out.println("SpotID: " + sp.getSlotID() + "\tSize: " + sp.getSlotSize() + "\tisAvailable: "
					+ sp.isAvailable() + "\tPlateID: " + sp.getPlateID());
		}
	}

	private void parkVehicle(Vehicle v) {
		ParkingSpot spot = assignSpot(v);
		if (spot != null) {
			spot.setPlateID(v.getPlateID());
			System.out.println("Car PlateID:" + spot.getPlateID() + " " + " parks at SpotID: " + spot.getSlotID());
		} else {
			System.out.println("No available spot");
		}
	}

	private void unparkVehicle(Vehicle v) {
		for (ParkingSpot ps : parkStation) {
			if (v.getPlateID().equals(ps.getPlateID())) {
				ps.setPlateID(null);
				System.out.println("Vehicle: " + v.getPlateID() + " exits the parking spot: " + ps.getSlotID());
				return;
			}
		}
		System.out.println("Vehicle : " + v.getPlateID() + " does not park here.");
	}

	public static void main(String[] args) {
		boolean waiting = true;
		ValetParkingSystem vps = new ValetParkingSystem();
		vps.initializeParking();

		while (waiting) {
			System.out.println(
					"Want to park a car? Press 'p'. Unpark a car press 'u'. Press 'd' to display parking status. Press 'e' to quit\n");
			Scanner reader = new Scanner(System.in);
			String cmd = reader.nextLine();
//			System.out.println("Your command: " + cmd);

			if (cmd.equalsIgnoreCase("e")) {
				waiting = false;
				System.out.println("Exit the program");
				System.exit(0);
			} else if (cmd.equalsIgnoreCase("d")) {
				vps.printParkingStatus();
			} else if (cmd.equalsIgnoreCase("p") || cmd.equalsIgnoreCase("u")) {
				System.out.println("Enter your car plate: ");
				String plateID = reader.nextLine();
//				System.out.println("Car plate: " + plateID);

				System.out.println("Enter vehicle size. Press 'S' for small, 'M' for medium or 'L' for large vehicle");
				String sizeVehicle = reader.nextLine();
//				System.out.println("Vehicle size: " + sizeVehicle);

				Vehicle v = null;
				if (sizeVehicle.equalsIgnoreCase("s")) {
					v = new Vehicle(plateID, Size.SMALL);
				} else if (sizeVehicle.equalsIgnoreCase("m")) {
					v = new Vehicle(plateID, Size.MEDIUM);
				} else if (sizeVehicle.equalsIgnoreCase("l")) {
					v = new Vehicle(plateID, Size.LARGE);
				}

				if (cmd.equalsIgnoreCase("p")) {
					vps.parkVehicle(v);
				} else if (cmd.equalsIgnoreCase("u")) {
					vps.unparkVehicle(v);
				}
			}
			else {
				System.out.println("Unknown command");
			}
		}
	}

}
