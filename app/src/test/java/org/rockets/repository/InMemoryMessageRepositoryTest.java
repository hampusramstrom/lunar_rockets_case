package org.rockets.repository;

import java.util.UUID;

import org.rockets.model.Message;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class InMemoryMessageRepositoryTest {

    @Test
    public void persistAndGet_happyPath() {
        InMemoryMessageRepository msgRepository = new InMemoryMessageRepository();
        Message msg1channel1 = new Message();
        msg1channel1.setChannel(UUID.randomUUID());
        msg1channel1.setMessageNumber(1);
        msgRepository.persist(msg1channel1);

        Message msg2channel1 = new Message();
        msg2channel1.setChannel(msg1channel1.getChannel());
        msg2channel1.setMessageNumber(2);
        msgRepository.persist(msg2channel1);

        Message msg1channel2 = new Message();
        msg1channel2.setChannel(UUID.randomUUID());
        msg1channel2.setMessageNumber(1);
        msgRepository.persist(msg1channel2);

        Message msg2channel2 = new Message();
        msg2channel2.setChannel(msg1channel2.getChannel());
        msg2channel2.setMessageNumber(2);
        msgRepository.persist(msg2channel2);

        Message persistedMsg1Channel1 = msgRepository.get(msg1channel1.getChannel(), msg1channel1.getMessageNumber()).get();
        Message persistedMsg2Channel1 = msgRepository.get(msg2channel1.getChannel(), msg2channel1.getMessageNumber()).get();
        Message persistedMsg1Channel2 = msgRepository.get(msg1channel2.getChannel(), msg1channel2.getMessageNumber()).get();
        Message persistedMsg2Channel2 = msgRepository.get(msg2channel2.getChannel(), msg2channel2.getMessageNumber()).get();

        assertEquals(msg1channel1.getChannel(), persistedMsg1Channel1.getChannel());
        assertEquals(msg2channel1.getChannel(), persistedMsg2Channel1.getChannel());
        assertEquals(msg1channel2.getChannel(), persistedMsg1Channel2.getChannel());
        assertEquals(msg2channel2.getChannel(), persistedMsg2Channel2.getChannel());
    }
}
