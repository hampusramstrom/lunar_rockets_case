package org.rockets.stateupdater;

import org.rockets.constant.MessageType;
import org.rockets.model.Message;
import org.rockets.model.dto.RocketExplodedMessageDto;
import org.rockets.serializer.IDeserializer;
import org.rockets.service.RocketStatesService;

import com.google.inject.Inject;

public class RocketExplodedStateUpdater implements IStateUpdater {

    @Inject
    private RocketStatesService rocketStatesService;
    @Inject
    private IDeserializer deserializer;

    @Override
    public boolean accept(Message message) {
        return MessageType.isRocketExploded(message);
    }

    @Override
    public void apply(Message message) {
        RocketExplodedMessageDto rocketLaunchedMsg = deserializer.deserialize(message.getMessage(), RocketExplodedMessageDto.class);
        rocketStatesService.setExplosionReason(message.getChannel(), message.getMessageNumber(), rocketLaunchedMsg.getReason());
    }
}
