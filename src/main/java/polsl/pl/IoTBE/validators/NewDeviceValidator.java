package polsl.pl.IoTBE.validators;


import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;
import polsl.pl.IoTBE.common.ChannelTypes;
import polsl.pl.IoTBE.common.MqttConfigValues;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class NewDeviceValidator {

    public boolean validateMac(String mac){


        String regex = "^([0-9A-Fa-f]{2}[:])"
                + "{5}([0-9A-Fa-f]{2})|"
                + "([0-9a-fA-F]{4}\\."
                + "[0-9a-fA-F]{4}\\."
                + "[0-9a-fA-F]{4})$";

        Pattern p = Pattern.compile(regex);
        if (mac == null)
        {
            return false;
        }

        Matcher m = p.matcher(mac);

        return m.matches();

    }

    public String validatateJsonConfig(JSONObject config) {
        String result = null;
        if(config == null){
            result = "No config json";
            return result;
        }

        JSONArray jsonArray = new JSONArray();
        try{
            jsonArray = config.getJSONArray(MqttConfigValues.configChannelName);
        }
        catch (Exception ex)
        {
            result = "No channel array in Json";
            return result;
        }

        for(int i=0; i<jsonArray.length(); i++)
        {
            String channelNumberString = "";
            try{
                channelNumberString = jsonArray.optJSONObject(i).get(MqttConfigValues.configPortName).toString();
            }
            catch (Exception ex){
                result = "No port in json";
                return result;
            }

            try{

                Integer.parseInt(channelNumberString);
            }
            catch(Exception ex){
                result = "Port is not an integer: " + channelNumberString;
                return result;
        }



            String channelType ="";
            try{
                channelType = jsonArray.optJSONObject(i).get(MqttConfigValues.configTypeName).toString();
            }
            catch (Exception ex){
                result = "No channel type in json";
                return result;
            }

           boolean matchFound = false;
            for (ChannelTypes type : ChannelTypes.values()) {
                if(channelType.equals(type.toString()))
                    matchFound = true;
            }
            if(matchFound == false){
                result = "Channel type " + channelType + " doesnt match supported types"  ;
                return result;
            }

        }

        result = "OK";

        return result;
    }
}
