package iot.challenge.application.modele.swagger;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.time.Instant;
import java.util.Objects;





@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2016-05-13T11:12:43.255Z")
public class Message   {
  
  private String id = null;
  private Instant timestamp = null;
  private Integer sensorType = null;
  private Long value = null;

  
  /**
   * Identifiant unique du message envoy\u00E9 par le capteur, un controle des doublons doit \u00EAtre effectu\u00E9 (max 64 chars).
   **/
  public Message id(String id) {
    this.id = id;
    return this;
  }

  
  @ApiModelProperty(value = "Identifiant unique du message envoy\u00E9 par le capteur, un controle des doublons doit \u00EAtre effectu\u00E9 (max 64 chars).")
  @JsonProperty("id")
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  
  /**
   * Horaire de la fabrication du message par le capteur (format RFC3339). Ce timestamp fait fois lors calcul de la synth\u00E8se.
   **/
  public Message timestamp(Instant timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  
  @ApiModelProperty(value = "Horaire de la fabrication du message par le capteur (format RFC3339). Ce timestamp fait fois lors calcul de la synth\u00E8se.")
  @JsonProperty("timestamp")
  public Instant getTimestamp() {
    return timestamp;
  }
  public void setTimestamp(Instant timestamp) {
    this.timestamp = timestamp;
  }

  
  /**
   * Type de capteur \u00E0 l'origine de l'emission du message.
   **/
  public Message sensorType(Integer sensorType) {
    this.sensorType = sensorType;
    return this;
  }

  
  @ApiModelProperty(value = "Type de capteur \u00E0 l'origine de l'emission du message.")
  @JsonProperty("sensorType")
  public Integer getSensorType() {
    return sensorType;
  }
  public void setSensorType(Integer sensorType) {
    this.sensorType = sensorType;
  }

  
  /**
   * Valeur transmise par le capteur.
   **/
  public Message value(Long value) {
    this.value = value;
    return this;
  }

  
  @ApiModelProperty(value = "Valeur transmise par le capteur.")
  @JsonProperty("value")
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

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

