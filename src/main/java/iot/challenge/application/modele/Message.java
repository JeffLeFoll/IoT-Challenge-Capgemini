package iot.challenge.application.modele;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.MongoId;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;


public class Message {

    @MongoId
    private String id = null;
    private Instant timestamp = null;
    private Integer sensorType = null;
    private Long value = null;

    public Message id(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Message timestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Message sensorType(Integer sensorType) {
        this.sensorType = sensorType;
        return this;
    }

    public Integer getSensorType() {
        return sensorType;
    }

    public void setSensorType(Integer sensorType) {
        this.sensorType = sensorType;
    }

    public Message value(Long value) {
        this.value = value;
        return this;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message = (Message) o;
        return Objects.equals(id, message.id) &&
                Objects.equals(timestamp, message.timestamp) &&
                Objects.equals(sensorType, message.sensorType) &&
                Objects.equals(value, message.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timestamp, sensorType, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Message {\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
        sb.append("    sensorType: ").append(toIndentedString(sensorType)).append("\n");
        sb.append("    value: ").append(toIndentedString(value)).append("\n");
        sb.append("}");
        
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

