package org.rockets.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.rockets.model.RocketState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Singleton;

// TODO: Extend class with a HashMap.
@Singleton
public class InMemoryRocketStates implements IRocketStates {
    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryRocketStates.class);

    private final Map<UUID, RocketState> state;

    public InMemoryRocketStates() {
        this.state = new HashMap<>();
    }

    @Override
    public boolean add(RocketState rocketState) {
        RocketState existingRocket = state.putIfAbsent(rocketState.getChannel(), rocketState);
        return existingRocket == null;
    }

    @Override
    public boolean replace(RocketState rocketState) {
        if (state.containsKey(rocketState.getChannel())) {
            return false;
        }
        state.put(rocketState.getChannel(), rocketState);
        return true;
    }

    @Override
    public List<RocketState> getAll() {
        // Make a copy to avoid ConcurrentModificationException
        List<RocketState> rocketStates = new ArrayList<>();
        rocketStates.addAll(state.values());
        return rocketStates;
    }

    @Override
    public Optional<RocketState> get(UUID channel) {
        return Optional.ofNullable(state.get(channel));
    }

    @Override
    public int increaseSpeed(UUID channel, int msgNumber, int by) {
        updateLastMsgNbr(channel, msgNumber);
        int newSpeed = state.get(channel).getSpeed() + by;
        state.get(channel).setSpeed(newSpeed);
        return newSpeed;
    }

    @Override
    public int decreaseSpeed(UUID channel, int msgNumber, int by) {
        updateLastMsgNbr(channel, msgNumber);
        int newSpeed = state.get(channel).getSpeed() - by;
        state.get(channel).setSpeed(newSpeed);
        return newSpeed;
    }

    @Override
    public void changeMission(UUID channel, int msgNumber, String newMission, String center) {
        updateLastMsgNbr(channel, msgNumber);
        state.get(channel).setMission(newMission);
        state.get(channel).setMissionChangedByCenter(center);
    }

    @Override
    public boolean humanReadableNameExists(String name) {
        return state.values().stream().anyMatch(rocket -> name.equals(rocket.getHumanReadableName()));
    }

    @Override
    public void setExplosionReason(UUID channel, int msgNumber, String explosionReason) {
        updateLastMsgNbr(channel, msgNumber);
        state.get(channel).setExplosionReason(explosionReason);
    }

    private void updateLastMsgNbr(UUID channel, int newMsgNbr) {
        int lastMsgNbr = state.get(channel).getLastMessageNumber();
        if (lastMsgNbr > newMsgNbr) {
            LOGGER.warn(String.format("%s - Wrong message order: Received a previous message. Last message number: %s, New message number %s", channel, lastMsgNbr, newMsgNbr));
        } else if (lastMsgNbr + 1 < newMsgNbr) {
            LOGGER.warn(String.format("%s - Wrong message order: Received a future message. Last message number: %s, New message number %s", channel, lastMsgNbr, newMsgNbr));
        }
        state.get(channel).setLastMessageNumber(newMsgNbr);
    }
}
