package org.rockets.factory;

import org.rockets.model.RocketState;
import org.rockets.model.dto.RocketStateDto;

public class RocketStateDtoFactory {

    public RocketStateDto create(RocketState rocketState) {
        RocketStateDto rocketStateDto = new RocketStateDto();
        rocketStateDto.setChannel(rocketState.getChannel());
        rocketStateDto.setHumanReadableName(rocketState.getHumanReadableName());
        rocketStateDto.setRocketType(rocketState.getRocketType());
        rocketStateDto.setSpeed(rocketState.getSpeed());
        rocketStateDto.setMission(rocketState.getMission());
        rocketStateDto.setMissionChangedByCenter(rocketState.getMissionChangedByCenter());
        rocketStateDto.setLaunchTime(rocketState.getLaunchTime());
        rocketStateDto.setExplosionReason(rocketState.getExplosionReason());
        rocketStateDto.setLastMessageNumber(rocketState.getLastMessageNumber());

        return rocketStateDto;
    }
}
