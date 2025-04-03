package org.rockets.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;
import org.rockets.factory.MessageFactory;
import org.rockets.model.dto.MessageDto;

import com.google.gson.JsonElement;

@RunWith(MockitoJUnitRunner.class)
public class MessageTest {
    @Mock
    JsonElement jsonElement;

    @Test
    public void initialize_fromMessageDto_hapyPath() {
        when(jsonElement.toString()).thenReturn("message");

        MessageDto msgDto = new MessageDto();
        MessageDto.Metadata metadata = new MessageDto.Metadata();
        metadata.setChannel(UUID.randomUUID());
        metadata.setMessageNumber(1);
        metadata.setMessageTime(OffsetDateTime.MAX);
        metadata.setMessageType("RocketLaunched");
        msgDto.setMetadata(metadata);
        msgDto.setMessage(jsonElement);

        MessageFactory msgFactory = new MessageFactory();
        Message msg = msgFactory.create(msgDto);

        assertEquals(msgDto.getMetadata().getChannel(), msg.getChannel());
        assertEquals(msgDto.getMetadata().getMessageNumber(), msg.getMessageNumber());
        assertEquals(msgDto.getMetadata().getMessageTime(), msg.getMessageTime());
        assertEquals(msgDto.getMetadata().getMessageType(), msg.getMessageType());
        assertEquals("message", msg.getMessage());
    }
}