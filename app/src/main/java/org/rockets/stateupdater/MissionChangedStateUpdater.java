package org.rockets.stateupdater;

import org.rockets.constant.MessageType;
import org.rockets.factory.RocketStateFactory;
import org.rockets.model.Message;
import org.rockets.model.RocketState;
import org.rockets.model.dto.MissionChangedMessageDto;
import org.rockets.serializer.IDeserializer;
import org.rockets.service.RocketStatesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class MissionChangedStateUpdater implements IStateUpdater {

    private static final Logger LOGGER = LoggerFactory.getLogger(MissionChangedStateUpdater.class);
    private final RocketStatesService rocketStatesService;
    private final IDeserializer deserializer;
    private final RocketStateFactory rocketStateFactory;

    @Inject
    public MissionChangedStateUpdater(IDeserializer deserializer, RocketStatesService rocketStatesService, RocketStateFactory rocketStateFactory) {
        this.deserializer = deserializer;
        this.rocketStatesService = rocketStatesService;
        this.rocketStateFactory = rocketStateFactory;
    }

    @Override
    public boolean accept(Message message) {
        return MessageType.isMissionChanged(message);
    }

    @Override
    public void apply(Message message) {
        MissionChangedMessageDto missionChangedMsg = deserializer.deserialize(message.getMessage(), MissionChangedMessageDto.class);
        missionChangedMsg.getChannels().forEach(channel -> {
            // A MissionChanged message might arrive as the first message.
            if (message.getMessageNumber() == 1) {
                RocketState rocketState = rocketStateFactory.create(message, channel, missionChangedMsg);
                rocketStatesService.add(rocketState);
                LOGGER.debug(String.format("%s - MissionChanged message arrived first: Message number: %s", channel, message.getMessageNumber()));
            } else {
                rocketStatesService.changeMission(channel, message.getMessageNumber(), missionChangedMsg.getNewMission(), missionChangedMsg.getCenter());
            }
        });
    }
}
