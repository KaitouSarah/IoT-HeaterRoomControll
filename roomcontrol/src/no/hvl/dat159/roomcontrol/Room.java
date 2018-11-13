package no.hvl.dat159.roomcontrol;

public class Room {

	private int heatingState;
	private static double RATE = .0001; // change in temperature per time unit

	private double temperature;
	private long lastsense;

	public Room(int startTempareture) {
		heatingState = -1;
		temperature = startTempareture;
		lastsense = System.currentTimeMillis();
	}

	synchronized public double sense() {
		long timenow = System.currentTimeMillis();
		long timeinterval = timenow - lastsense;
		lastsense = timenow;

		temperature = temperature + (RATE * heatingState * timeinterval);

		return temperature;
	}

	synchronized public void actuate(boolean newHeatingState) {
		sense();

		if (newHeatingState) {
			heatingState = 1;
		} else {
			heatingState = -1;
		}
	}
}
