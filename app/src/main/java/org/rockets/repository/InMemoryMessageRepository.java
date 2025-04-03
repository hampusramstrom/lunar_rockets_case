package org.rockets.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.rockets.model.Message;

// TODO: Extend class with a HashMap.
public class InMemoryMessageRepository implements IMessageRepository {

    private static final Map<UUID, Map<Integer, Message>> REPOSITORY = new HashMap<>();

    @Override
    public void persist(Message message) {
        REPOSITORY.putIfAbsent(message.getChannel(), new HashMap<>());
        REPOSITORY.get(message.getChannel()).put(message.getMessageNumber(), message);
    }

    @Override
    public Optional<Message> get(UUID channel, int messageNumber) {
        return Optional.ofNullable(REPOSITORY.get(channel)).map(inner -> inner.get(messageNumber));
    }

    @Override
    public int getNbrOfMsgs() {
        int nbrOfMsgs = 0;
        for (Map<Integer, Message> inner : REPOSITORY.values()) {
            nbrOfMsgs += inner.size();
        }
        return nbrOfMsgs;
    }

    @Override
    public int getNbrOfChannels() {
        return REPOSITORY.size();
    }
}
