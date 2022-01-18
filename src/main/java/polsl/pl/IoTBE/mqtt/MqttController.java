package polsl.pl.IoTBE.mqtt;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import polsl.pl.IoTBE.common.MqttConfigValues;

@Component
public class MqttController {
    @Autowired
    MqttGateway mqtGateway;

    public boolean publish(String topic, String payload)  {

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("topic", topic);
            jsonObject.put("payload", payload);
            String topicTrimed = jsonObject.get("topic").toString();
            String payloadTrimed = jsonObject.get("payload").toString();



            mqtGateway.senToMqtt(cleanTextContent(payloadTrimed), cleanTextContent(topicTrimed));
            System.out.println("succes");
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private static String cleanTextContent(String text)
    {
        // strips off all non-ASCII characters
        text = text.replaceAll("[^\\x00-\\x7F]", "");

        // erases all the ASCII control characters
        text = text.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");

        // removes non-printable characters from Unicode
        text = text.replaceAll("\\p{C}", "");

        return text.trim();
    }
};
