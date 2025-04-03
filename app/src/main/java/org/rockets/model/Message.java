package org.rockets.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public class Message {

    private UUID channel;
    private int messageNumber;
    private OffsetDateTime messageTime;
    private String messageType;
    private String message; // Serialized JSON

    public UUID getChannel() {
        return channel;
    }

    public void setChannel(UUID channel) {
        this.channel = channel;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(int messageNumber) {
        this.messageNumber = messageNumber;
    }

    public OffsetDateTime getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(OffsetDateTime messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
