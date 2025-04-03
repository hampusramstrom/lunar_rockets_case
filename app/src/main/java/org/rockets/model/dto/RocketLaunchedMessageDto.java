package org.rockets.model.dto;

public class RocketLaunchedMessageDto {

    private String type;
    private int launchSpeed;
    private String mission;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLaunchSpeed() {
        return launchSpeed;
    }

    public void setLaunchSpeed(int launchSpeed) {
        this.launchSpeed = launchSpeed;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }
}
