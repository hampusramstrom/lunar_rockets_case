package org.rockets.serializer;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.rockets.model.dto.MessageDto;

public class JsonDeserializerTest {

    @Test
    public void deserialize_happyPath() {
        JsonDeserializer deserializer = new JsonDeserializer();
        // TODO: Retrieve from a JSON file in resources instead.
        String msgJson = "{\"metadata\":{\"channel\":\"0e9e9df5-38fd-52e2-916f-c858b9eb4ea5\",\"messageNumber\":1,\"messageType\":\"RocketLaunched\",\"messageTime\":\"2025-04-02T09:21:50.458989221+02:00\"},\"message\":{\"type\":\"Atlas-H\",\"launchSpeed\":500,\"mission\":\"APOLLO\"}}";
        MessageDto msg = deserializer.deserialize(msgJson, MessageDto.class);
        assertEquals(UUID.fromString("0e9e9df5-38fd-52e2-916f-c858b9eb4ea5"), msg.getMetadata().getChannel());
        assertEquals(1, msg.getMetadata().getMessageNumber());
        assertEquals("RocketLaunched", msg.getMetadata().getMessageType());
        assertEquals(OffsetDateTime.of(2025, 4, 2, 9, 21, 50, 458989221, ZoneOffset.ofHours(2)), msg.getMetadata().getMessageTime());
        assertEquals("{\"type\":\"Atlas-H\",\"launchSpeed\":500,\"mission\":\"APOLLO\"}", msg.getMessage().toString());
    }

}