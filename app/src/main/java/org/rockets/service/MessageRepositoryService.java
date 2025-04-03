package org.rockets.service;

import java.util.Set;

import org.rockets.exception.MessageValidatorException;
import org.rockets.model.Message;
import org.rockets.repository.IMessageRepository;
import org.rockets.stateupdater.IStateUpdater;
import org.rockets.validator.IMessageValidator;

import com.google.inject.Inject;

public class MessageRepositoryService {

    private final IMessageRepository msgRepository;
    private final Set<IMessageValidator> msgValidators;
    private final Set<IStateUpdater> stateUpdaters;

    @Inject
    public MessageRepositoryService(IMessageRepository msgRepository, Set<IMessageValidator> msgValidators, Set<IStateUpdater> stateUpdaters) {
        this.msgRepository = msgRepository;
        this.msgValidators = msgValidators;
        this.stateUpdaters = stateUpdaters;
    }

    public boolean add(Message msg) throws MessageValidatorException {
        for (IMessageValidator msgValidator : msgValidators) {
            if (!msgValidator.validate(msg)) {
                return false;
            }
        }

        msgRepository.persist(msg);

        stateUpdaters.stream()
                .filter(stateUpdater -> stateUpdater.accept(msg))
                .forEach(stateUpdater -> stateUpdater.apply(msg));

        return true;
    }

    public int getNbrOfChannels() {
        return msgRepository.getNbrOfChannels();
    }

    public int getNbrOfMessages() {
        return msgRepository.getNbrOfMsgs();
    }
}
