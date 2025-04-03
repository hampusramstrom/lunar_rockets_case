package org.rockets.model.dto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class RocketsDto {

    private List<RocketInfoDto> rockets;

    public List<RocketInfoDto> getRockets() {
        return rockets;
    }

    public void setRockets(List<RocketInfoDto> rockets) {
        this.rockets = rockets;
    }

    public static class RocketInfoDto {

        private String humanReadableName;
        private UUID channel;
        private OffsetDateTime launchTime;

        public String getHumanReadableName() {
            return humanReadableName;
        }

        public void setHumanReadableName(String humanReadableName) {
            this.humanReadableName = humanReadableName;
        }

        public UUID getChannel() {
            return channel;
        }

        public void setChannel(UUID channel) {
            this.channel = channel;
        }

        public OffsetDateTime getLaunchTime() {
            return launchTime;
        }

        public void setLaunchTime(OffsetDateTime launchTime) {
            this.launchTime = launchTime;
        }
    }
}
