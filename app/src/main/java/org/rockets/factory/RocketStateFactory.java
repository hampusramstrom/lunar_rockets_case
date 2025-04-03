package org.rockets.factory;

import java.util.UUID;

import org.rockets.model.Message;
import org.rockets.model.RocketState;
import org.rockets.model.dto.MissionChangedMessageDto;
import org.rockets.model.dto.RocketLaunchedMessageDto;

public class RocketStateFactory {

    public RocketState create(Message msg, RocketLaunchedMessageDto rocketLaunchedMsg) {
        RocketState rocketState = new RocketState();
        rocketState.setChannel(msg.getChannel());
        rocketState.setRocketType(rocketLaunchedMsg.getType());
        rocketState.setSpeed(rocketLaunchedMsg.getLaunchSpeed());
        rocketState.setMission(rocketLaunchedMsg.getMission());
        rocketState.setLaunchTime(msg.getMessageTime());
        rocketState.setLastMessageNumber(msg.getMessageNumber());

        return rocketState;
    }

    public RocketState create(Message msg, UUID channel, MissionChangedMessageDto missionChangedMsg) {
        RocketState rocketState = new RocketState();
        rocketState.setChannel(channel);
        rocketState.setMission(missionChangedMsg.getNewMission());
        rocketState.setMissionChangedByCenter(missionChangedMsg.getCenter());
        rocketState.setLastMessageNumber(msg.getMessageNumber());

        return rocketState;
    }
}
