package polsl.pl.IoTBE.mqtt;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqttController {
    @Autowired
    MqttGateway mqtGateway;

    @GetMapping("/sendMessage")
    public ResponseEntity<?> publish(String topic, String payload) {

        try {
            mqtGateway.senToMqtt(payload, topic);
            return ResponseEntity.ok("Success");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.ok("fail");
        }
    }
};
