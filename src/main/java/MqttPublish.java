import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import sun.awt.windows.ThemeReader;

public class MqttPublish extends Thread {

    public void run(){

        String topic        = "Time";
        String content      = "Try";
        int qos             = 2;
        String broker       = "tcp://172.17.0.2:1883";
        String clientId     = "Mqtt";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            MqttClient client = new MqttClient(broker, clientId,persistence);

            System.out.println("Connection: "+broker);
            client.connect(connOpts);

            System.out.println("Connected");

            System.out.println("Publishing message: "+content);

            MqttMessage message = new MqttMessage(content.getBytes());

            message.setQos(qos);
            while (true){
                client.publish(topic, message);
                System.out.println("Message published");
                Thread.sleep(500);
            }

            //client.disconnect();

            //System.out.println("Disconnected");

            //System.exit(0);
        } catch(MqttException me) {
            me.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
