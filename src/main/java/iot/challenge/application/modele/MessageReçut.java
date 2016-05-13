package iot.challenge.application.modele;

import iot.challenge.application.modele.swagger.Message;
import org.jongo.marshall.jackson.oid.MongoId;

public class MessageReçut extends Message {

   /*@MongoId
    private String id = null;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }*/

    public String insertSQL(){
        return "INSERT INTO Messages(id, timestamp, sensorType, value) values(?,strftime('%Y-%m-%dT%H:%M:%fZ', ?),?,?);";
    }
}
