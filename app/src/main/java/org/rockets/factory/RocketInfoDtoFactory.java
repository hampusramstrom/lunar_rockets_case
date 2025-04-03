package org.rockets.factory;

import org.rockets.model.RocketState;
import org.rockets.model.dto.RocketsDto;

public class RocketInfoDtoFactory {

    public RocketsDto.RocketInfoDto create(RocketState rocketState) {
        RocketsDto.RocketInfoDto rocketInfoDto = new RocketsDto.RocketInfoDto();
        rocketInfoDto.setChannel(rocketState.getChannel());
        rocketInfoDto.setHumanReadableName(rocketState.getHumanReadableName());
        rocketInfoDto.setLaunchTime(rocketState.getLaunchTime());

        return rocketInfoDto;
    }
}
