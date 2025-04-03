package org.rockets.validator;

import org.rockets.exception.MessageValidatorException;
import org.rockets.model.Message;

public interface IMessageValidator {

    boolean validate(Message msg) throws MessageValidatorException;
}
