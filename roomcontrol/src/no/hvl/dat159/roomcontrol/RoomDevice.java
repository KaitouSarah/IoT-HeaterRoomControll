package no.hvl.dat159.roomcontrol;

import org.eclipse.paho.client.mqttv3.MqttException;

public class RoomDevice {
	public static void main(String[] args) throws MqttException {
		Room room = new Room(20);
		Display display = new Display();
		Heating heating = new Heating(room, 19);

		TemperatureSensor sensor = new TemperatureSensor(room);

		MQTTPubTemperature sensorpub = new MQTTPubTemperature(sensor);

		new MQTTSubTemperature(heating, display);

		try {
			Thread temperaturePublisher = new Thread(sensorpub);

			// TODO: add heating subscriber running in its own thread
			temperaturePublisher.start();

			temperaturePublisher.join();

		} catch (Exception ex) {

			System.out.println("RoomDevice: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
