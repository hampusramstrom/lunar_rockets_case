package org.rockets.repository;

import java.util.Optional;
import java.util.UUID;

import org.rockets.model.Message;

public interface IMessageRepository {
    void persist(Message message);
    Optional<Message> get(UUID channel, int messageNumber);
    int getNbrOfChannels();
    int getNbrOfMsgs();
}
