package sample;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable,MqttCallback {

    @FXML
    private TextArea textAreaHours;
    @FXML
    private TextArea textAreaMin;
    @FXML
    private TextArea textAreaSec;


    private MemoryPersistence persistence = new MemoryPersistence();
    //private String message;
    private int dataSeconde     = 0;
    private int dataMinute      = 0;
    private int dataHours       = 0;

    public void initialize(URL location, ResourceBundle resources) {

    }

    //Show data seconde
    private void showTextHours() throws InterruptedException{
        while (true){
            textAreaHours.appendText("Test hours\n");
            textAreaHours.appendText(dataHours+"\n");
            dataHours=0;
            Thread.sleep(360000);
        }
    }

    //Show data Minute
    private void showTextMinutes() throws InterruptedException{
        while (true){
            textAreaMin.appendText("Test minute \n");
            textAreaMin.appendText(dataMinute+"\n");
            dataMinute=0;
            Thread.sleep(60000);
        }
    }

    //Show data Seconde
    private void showTextSeconde() throws InterruptedException {

        while(true){

            textAreaSec.appendText("Test seconde \n");
            textAreaSec.appendText(dataSeconde+"\n");
            dataSeconde=0;
            Thread.sleep(1000);

        }
    }

    @FXML
    public void showText(ActionEvent actionEvent) throws InterruptedException {

        String topic        = "Time";
        int qos             = 2;
        String broker       = "tcp://172.17.0.2:1883";
        String clientId     = "Mqtt2";

        System.out.println("Begin");

        try {
            MqttClient client = new MqttClient(broker,clientId,persistence);
            client.connect();
            client.setCallback(this);
            System.out.println("Subscribe connected");
            client.subscribe(topic,qos);

        Task<Void> taskSeconde =  new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            showTextSeconde();
            return null;
            }
        };

        Thread threadSeconde = new Thread(taskSeconde);
        threadSeconde.setDaemon(true);
        threadSeconde.start();

        Task<Void> taskMinute =  new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                showTextMinutes();
                return null;
            }
        };

        Thread threadMinute = new Thread(taskMinute);
        threadMinute.setDaemon(true);
        threadMinute.start();

        Task<Void> taskHours =  new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                showTextHours();
                return null;
            }
        };

        Thread threadHours = new Thread(taskHours);
        threadHours.setDaemon(true);
        threadHours.start();

        }catch(MqttException e) {
            e.printStackTrace();
        }
    }


    public void connectionLost(Throwable throwable) {

    }

    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        dataSeconde++;
        dataMinute++;
        dataHours++;
       // message=new String(mqttMessage.getPayload());
    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }



}
