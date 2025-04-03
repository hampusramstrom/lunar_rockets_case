package org.rockets.stateupdater;

import org.rockets.constant.MessageType;
import org.rockets.model.Message;
import org.rockets.model.dto.RocketSpeedIncreasedMessageDto;
import org.rockets.serializer.IDeserializer;
import org.rockets.service.RocketStatesService;

import com.google.inject.Inject;

public class RocketSpeedIncreasedStateUpdater implements IStateUpdater {

    @Inject
    private RocketStatesService rocketStatesService;
    @Inject
    private IDeserializer deserializer;

    @Override
    public boolean accept(Message message) {
        return MessageType.isRocketSpeedIncreased(message);
    }

    @Override
    public void apply(Message message) {
        RocketSpeedIncreasedMessageDto rocketSpeedIncreasedMsg = deserializer.deserialize(message.getMessage(), RocketSpeedIncreasedMessageDto.class);
        rocketStatesService.increaseSpeed(message.getChannel(), message.getMessageNumber(), rocketSpeedIncreasedMsg.getBy());
    }
}
