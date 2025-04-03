package org.rockets.model.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public class RocketStateDto {

    private UUID channel;
    private String humanReadableName;
    private String rocketType;
    private int speed;
    private String mission;
    private String missionChangedByCenter;
    private OffsetDateTime launchTime;
    private String explosionReason;
    private int lastMessageNumber;

    public UUID getChannel() {
        return channel;
    }

    public void setChannel(UUID channel) {
        this.channel = channel;
    }

    public String getHumanReadableName() {
        return humanReadableName;
    }

    public void setHumanReadableName(String humanReadableName) {
        this.humanReadableName = humanReadableName;
    }

    public String getRocketType() {
        return rocketType;
    }

    public void setRocketType(String rocketType) {
        this.rocketType = rocketType;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getMissionChangedByCenter() {
        return missionChangedByCenter;
    }

    public void setMissionChangedByCenter(String missionChangedByCenter) {
        this.missionChangedByCenter = missionChangedByCenter;
    }

    public OffsetDateTime getLaunchTime() {
        return launchTime;
    }

    public void setLaunchTime(OffsetDateTime launchTime) {
        this.launchTime = launchTime;
    }

    public String getExplosionReason() {
        return explosionReason;
    }

    public void setExplosionReason(String explosionReason) {
        this.explosionReason = explosionReason;
    }

    public int getLastMessageNumber() {
        return lastMessageNumber;
    }

    public void setLastMessageNumber(int lastMessageNumber) {
        this.lastMessageNumber = lastMessageNumber;
    }
}
