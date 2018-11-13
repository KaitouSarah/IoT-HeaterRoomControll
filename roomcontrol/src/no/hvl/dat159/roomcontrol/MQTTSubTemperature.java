package no.hvl.dat159.roomcontrol;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MQTTSubTemperature implements MqttCallback {
	private Display display;
	private Heating heating;
	private Logindetails login = new Logindetails();

	public MQTTSubTemperature(Heating heating, Display display) throws MqttException {
		this.heating = heating;
		this.display = display;

		String topic = "Temperature";
		int qos = 1; // 1 - This client will acknowledge to the Device Gateway that messages are received
		String broker = login.getBroker();
		String clientId = "MQTT_Temperature_SUB";
		String username = login.getUsername();
		String password = login.getPassword();

		MqttConnectOptions connectOptions = new MqttConnectOptions();
		connectOptions.setCleanSession(true);
		connectOptions.setUserName(username);
		connectOptions.setPassword(password.toCharArray());

		System.out.println("Connecting to broker: " + broker);

		MqttClient client = new MqttClient(broker, clientId, new MemoryPersistence());
		client.setCallback(this);
		client.connect(connectOptions);
		System.out.println("Connected");

		client.subscribe(topic, qos);
		System.out.println("Subscribed to message");
	}

	/**
	 * @see MqttCallback#connectionLost(Throwable)
	 */
	public void connectionLost(Throwable cause) {
		System.out.println("Connection lost because: " + cause);
		System.exit(1);

	}

	/**
	 * @see MqttCallback#messageArrived(String, MqttMessage)
	 */
	public void messageArrived(String topic, MqttMessage message) {
		String payload = new String(message.getPayload());

		String displayMessage = String.format("[%s] %s", topic, payload);

		display.write(displayMessage);

		heating.receiveTemperature(Double.parseDouble(payload));
	}

	/**
	 * @see MqttCallback#deliveryComplete(IMqttDeliveryToken)
	 */
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub

	}

	public static void main(String args[]) throws MqttException {
		Display display = new Display();
		Room room = new Room(20);
		Heating heating = new Heating(room, 19);

		new MQTTSubTemperature(heating, display);
	}
}