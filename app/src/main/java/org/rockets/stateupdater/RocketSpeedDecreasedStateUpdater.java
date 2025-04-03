package org.rockets.stateupdater;

import org.rockets.constant.MessageType;
import org.rockets.model.Message;
import org.rockets.model.dto.RocketSpeedDecreasedMessageDto;
import org.rockets.serializer.IDeserializer;
import org.rockets.service.RocketStatesService;

import com.google.inject.Inject;

public class RocketSpeedDecreasedStateUpdater implements IStateUpdater {

    @Inject
    private RocketStatesService rocketStatesService;
    @Inject

    private IDeserializer deserializer;

    @Override
    public boolean accept(Message message) {
        return MessageType.isRocketSpeedDecreased(message);
    }

    @Override
    public void apply(Message message) {
        RocketSpeedDecreasedMessageDto rocketSpeedDecreasedMsg = deserializer.deserialize(message.getMessage(), RocketSpeedDecreasedMessageDto.class);
        rocketStatesService.decreaseSpeed(message.getChannel(), message.getMessageNumber(), rocketSpeedDecreasedMsg.getBy());
    }
}
