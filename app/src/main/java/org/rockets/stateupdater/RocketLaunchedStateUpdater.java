package org.rockets.stateupdater;

import org.rockets.constant.MessageType;
import org.rockets.factory.RocketStateFactory;
import org.rockets.model.Message;
import org.rockets.model.RocketState;
import org.rockets.model.dto.RocketLaunchedMessageDto;
import org.rockets.serializer.IDeserializer;
import org.rockets.service.RocketStatesService;

import com.google.inject.Inject;

public class RocketLaunchedStateUpdater implements IStateUpdater {

    private final RocketStatesService rocketStatesService;
    private final IDeserializer deserializer;
    private final RocketStateFactory rocketStateFactory;

    @Inject
    public RocketLaunchedStateUpdater(IDeserializer deserializer, RocketStatesService rocketStatesService, RocketStateFactory rocketStateFactory) {
        this.deserializer = deserializer;
        this.rocketStatesService = rocketStatesService;
        this.rocketStateFactory = rocketStateFactory;
    }

    @Override
    public boolean accept(Message message) {
        return MessageType.isRocketLaunched(message);
    }

    @Override
    public void apply(Message message) {
        RocketLaunchedMessageDto rocketLaunchedMsg = deserializer.deserialize(message.getMessage(), RocketLaunchedMessageDto.class);

        RocketState rocketState = rocketStateFactory.create(message, rocketLaunchedMsg);
        rocketState.setHumanReadableName(rocketStatesService.generateHumanReadableName());
        // A MissionChanged message might have arrived as the first message.
        if (message.getMessageNumber() == 1) {
            rocketStatesService.add(rocketState);
        } else {
            rocketStatesService.updateStateForLaunch(rocketState);
        }
    }
}
