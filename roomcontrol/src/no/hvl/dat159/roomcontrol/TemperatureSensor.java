package no.hvl.dat159.roomcontrol;

public class TemperatureSensor implements Runnable {

	private Room room;

	public TemperatureSensor(Room room) {

		this.room = room;
	}

	public double read() {

		return room.sense();
	}

	@Override
	public void run() {

	}
}
