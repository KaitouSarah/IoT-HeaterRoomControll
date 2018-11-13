package no.hvl.dat159.roomcontrol;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


public class MQTTPubHeating {

    private Logindetails login = new Logindetails();
    private String topic = "Heating";
    private int qos = 1;
    private String broker = login.getBroker();
    private String clientId = "MQTT_Temperature_PUB";
    private String username = login.getUsername();
    private String password = login.getPassword();

    private MqttClient publisherClient;

    public MQTTPubHeating() {
        connect();
    }

    public void publish(boolean heatingOn) {
        try {
            String heaterStatus;
            if (heatingOn) {
                heaterStatus = "ON";
            } else {
                heaterStatus = "OFF";
            }
            MqttMessage message = new MqttMessage(heaterStatus.getBytes());
            message.setQos(qos);
            publisherClient.publish(topic, message);
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    private void connect() {

        MemoryPersistence persistence = new MemoryPersistence();

        try {
            publisherClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName(username);
            connOpts.setPassword(password.toCharArray());
            System.out.println("Connecting to broker: " + broker);
            publisherClient.connect(connOpts);
            System.out.println("Connected");

        } catch (MqttException e) {
            System.out.println("reason " + e.getReasonCode());
            System.out.println("msg " + e.getMessage());
            System.out.println("loc " + e.getLocalizedMessage());
            System.out.println("cause " + e.getCause());
            System.out.println("excep " + e);
            e.printStackTrace();
        }
    }
}
