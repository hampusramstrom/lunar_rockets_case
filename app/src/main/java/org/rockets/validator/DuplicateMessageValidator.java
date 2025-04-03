
package org.rockets.validator;

import java.util.Optional;

import org.rockets.model.Message;
import org.rockets.repository.IMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class DuplicateMessageValidator implements IMessageValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(DuplicateMessageValidator.class);
    private final IMessageRepository msgRepository;

    @Inject

    public DuplicateMessageValidator(IMessageRepository msgRepository) {
        this.msgRepository = msgRepository;
    }

    @Override
    public boolean validate(Message msg) {
        Optional<Message> existingMsg = msgRepository.get(msg.getChannel(), msg.getMessageNumber());
        existingMsg.ifPresent(eMsg -> LOGGER.warn(String.format("%s - Duplicate message: Number %s", eMsg.getChannel(), eMsg.getMessageNumber())));
        return existingMsg.isEmpty();
    }
}
