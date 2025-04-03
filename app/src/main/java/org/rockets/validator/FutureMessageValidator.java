
package org.rockets.validator;

import java.util.Optional;

import org.rockets.constant.MessageType;
import org.rockets.exception.MessageValidatorException;
import org.rockets.model.Message;
import org.rockets.model.RocketState;
import org.rockets.state.IRocketStates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class FutureMessageValidator implements IMessageValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(FutureMessageValidator.class);
    private final IRocketStates rocketStates;

    @Inject

    public FutureMessageValidator(IRocketStates rocketStates) {
        this.rocketStates = rocketStates;
    }

    @Override
    public boolean validate(Message msg) throws MessageValidatorException {
        if (MessageType.canBeFirst(msg)) {
            return true;
        }

        Optional<RocketState> rocketState = rocketStates.get(msg.getChannel());
        if (rocketState.isEmpty()) {
            return false;
        }

        int newMsgNbr = msg.getMessageNumber();
        int lastMsgNbr = rocketState.get().getLastMessageNumber();

        if (lastMsgNbr + 1 < newMsgNbr) {
            String logMsg = String.format("%s - Wrong message order: Received a future message. Last message number: %s, New message number %s", msg.getChannel(), lastMsgNbr, newMsgNbr);
            LOGGER.info(logMsg);
            throw new MessageValidatorException(logMsg);
        }
        return true;
    }
}
