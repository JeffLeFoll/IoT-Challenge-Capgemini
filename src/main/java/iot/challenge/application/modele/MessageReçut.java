package iot.challenge.application.modele;

import iot.challenge.application.modele.swagger.Message;
import org.jongo.marshall.jackson.oid.MongoId;

public class MessageReçut extends Message {

    public String insertSQL(){
        return "INSERT INTO Messages(id, timestamp, sensorType, value) values(?,strftime('%Y-%m-%dT%H:%M:%fZ', ?),?,?);";
    }
}
