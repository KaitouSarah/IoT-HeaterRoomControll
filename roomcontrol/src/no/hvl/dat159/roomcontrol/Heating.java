package no.hvl.dat159.roomcontrol;

public class Heating {

	private Room room;
	private double idealTemperature;
	private MQTTPubHeating pubHeating = new MQTTPubHeating();


	public Heating(Room room, double idealTemperature) {
		this.room = room;
		this.idealTemperature = idealTemperature;
	}

	public void write(boolean newvalue) {
		room.actuate(newvalue);

		if (newvalue) {
			pubHeating.publish(true);
		} else {
			pubHeating.publish(false);
		}
	}

	public Boolean wantsHeat(double temperature) {
		return temperature < idealTemperature;
	}

	public void receiveTemperature(double temperature) {
		write(wantsHeat(temperature));
	}
}
