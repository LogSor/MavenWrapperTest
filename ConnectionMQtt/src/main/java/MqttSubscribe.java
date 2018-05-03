import com.sun.jmx.snmp.Timestamp;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttSubscribe implements MqttCallback {


    String topic        = "timeStamp";
    int qos             = 2;
    String broker       = "tcp://172.17.0.2:1883";
    String clientId     = "Mqtt2";
    MemoryPersistence persistence = new MemoryPersistence();


    public MqttSubscribe() {
    }

    public static void  main(String[] args) {
        new MqttSubscribe().subscibe();
    }

    public void subscibe(){


        try {
            MqttClient client = new MqttClient(broker,clientId,persistence);
            client.connect();
            client.setCallback(this);
            System.out.println("Subscribe connected");
            client.subscribe(topic,qos);

        } catch(MqttException e) {
            e.printStackTrace();
        }
    }

    public void connectionLost(Throwable throwable) {

    }

    public void messageArrived(String s, MqttMessage mqttMessage) throws MqttException {
        //String time = new Timestamp(System.currentTimeMillis().)
        System.out.println("Topic:\t" +topic +"\n"
        +"Message:\t"+new String(mqttMessage.getPayload()));
    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}