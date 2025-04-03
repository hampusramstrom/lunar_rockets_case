package org.rockets.model.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.google.gson.JsonElement;

public class MessageDto {

    private Metadata metadata;
    private JsonElement message;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public JsonElement getMessage() {
        return message;
    }

    public void setMessage(JsonElement message) {
        this.message = message;
    }

    public static class Metadata {

        private UUID channel;
        private int messageNumber;
        private String messageType;
        private OffsetDateTime messageTime;

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

        public String getMessageType() {
            return messageType;
        }

        public void setMessageType(String messageType) {
            this.messageType = messageType;
        }

        public OffsetDateTime getMessageTime() {
            return messageTime;
        }

        public void setMessageTime(OffsetDateTime messageTime) {
            this.messageTime = messageTime;
        }
    }
}
