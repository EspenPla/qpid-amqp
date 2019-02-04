package io.sesam.amqp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author 100tsa
 */
public class Message {
    @JsonProperty("_id")
    private String id;
    @JsonProperty("payload")
    private String payload;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Message() {
    }

    public Message(String id, String payload) {
        this.id = id;
        this.payload = payload;
    }
    
    
}
