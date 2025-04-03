package org.rockets.model.dto;

import java.util.List;
import java.util.UUID;

public class MissionChangedMessageDto {

    private String center;
    private List<UUID> channels;
    private String newMission;

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public List<UUID> getChannels() {
        return channels;
    }

    public void setChannels(List<UUID> channels) {
        this.channels = channels;
    }

    public String getNewMission() {
        return newMission;
    }

    public void setNewMission(String newMission) {
        this.newMission = newMission;
    }
}
