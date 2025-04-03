
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

public class FirstMessageTypeValidator implements IMessageValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(FirstMessageTypeValidator.class);
    private final IRocketStates rocketStates;

    @Inject

    public FirstMessageTypeValidator(IRocketStates rocketStates) {
        this.rocketStates = rocketStates;
    }

    @Override
    public boolean validate(Message msg) throws MessageValidatorException {
        if (MessageType.canBeFirst(msg)) {
            return true;
        }

        int newMsgNbr = msg.getMessageNumber();

        Optional<RocketState> rocketState = rocketStates.get(msg.getChannel());
        if (rocketState.isEmpty()) {
            String logMsg = String.format("%s - Wrong message number: Received message is not a RocketLaunched message but have message number %s. Message type: %s", msg.getChannel(), newMsgNbr, msg.getMessageType());
            LOGGER.info(logMsg);
            throw new MessageValidatorException(logMsg);
        }

        return true;
    }
}
