package ValetParkingSystem;

public class Vehicle {
	private String plateID;
	private Size vehicleSize;
	
	public Vehicle(String plateID, Size vehicleSize) {
		super();
		this.plateID = plateID;
		this.vehicleSize = vehicleSize;
	}

	public Size getVehicleSize() {
		return vehicleSize;
	}

	public void setVehicleSize(Size vehicleSize) {
		this.vehicleSize = vehicleSize;
	}

	public String getPlateID() {
		return plateID;
	}

	public void setPlateID(String plateID) {
		this.plateID = plateID;
	}
}

class ParkingSpot {
	private String slotID;
	private Size slotSize;
	private String plateID;
	
	public ParkingSpot(String slotID, Size slotSize, String plateID) {
		super();
		this.slotID = slotID;
		this.slotSize = slotSize;
		this.plateID = plateID;
	}
	
	public String getPlateID() {
		return plateID;
	}

	public void setPlateID(String plateID) {
		this.plateID = plateID;
	}

	public boolean isAvailable() {
		return (plateID == "" || plateID == null);
	}

	public String getSlotID() {
		return slotID;
	}
	public void setSlotID(String slotID) {
		this.slotID = slotID;
	}
	public Size getSlotSize() {
		return slotSize;
	}
	public void setSlotSize(Size slotSize) {
		this.slotSize = slotSize;
	}
}
