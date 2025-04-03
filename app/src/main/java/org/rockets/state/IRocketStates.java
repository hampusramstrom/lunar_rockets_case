package org.rockets.state;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.rockets.model.RocketState;

/**
* Any change to a state by a message should also increment the last message number.
* Not included in the names of the methods to keep the length down,
* but they all have a <i>int msgNumber</i> parameter.
*
* Also for all state changing methods,
* RocketStatesService should be used to handle concurrency correctly.
*/
public interface IRocketStates {
    boolean add(RocketState rocketState);
    boolean replace(RocketState rocketState);
    List<RocketState> getAll();
    Optional<RocketState> get(UUID channel);
    int increaseSpeed(UUID channel, int msgNumber, int by);
    int decreaseSpeed(UUID channel, int msgNumber, int by);
    void changeMission(UUID channel, int msgNumber, String newMission, String center);
    /** 
     * To check if a human readable name for a rocket alreadt exists in the state.
     * @param name the human readable name
     * @return true if the human readable name already exists
    */
    boolean humanReadableNameExists(String name);
    void setExplosionReason(UUID channel, int msgNumber, String explosionReason);
}
