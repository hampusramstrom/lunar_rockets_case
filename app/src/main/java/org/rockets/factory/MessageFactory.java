package org.rockets.factory;

import org.rockets.model.Message;
import org.rockets.model.dto.MessageDto;

public class MessageFactory {

    public Message create(MessageDto msgDto) {
        Message msg = new Message();
        msg.setChannel(msgDto.getMetadata().getChannel());
        msg.setMessageNumber(msgDto.getMetadata().getMessageNumber());
        msg.setMessageTime(msgDto.getMetadata().getMessageTime());
        msg.setMessageType(msgDto.getMetadata().getMessageType());
        msg.setMessage(msgDto.getMessage().toString());

        return msg;
    }
}
