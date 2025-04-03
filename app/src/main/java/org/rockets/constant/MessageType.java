package org.rockets.constant;

import org.rockets.model.Message;

public final class MessageType {

    public static final String ROCKET_LAUNCHED = "RocketLaunched";
    public static final String ROCKET_SPEED_INCREASED = "RocketSpeedIncreased";
    public static final String ROCKET_SPEED_DECREASED = "RocketSpeedDecreased";
    public static final String ROCKET_EXPLODED = "RocketExploded";
    public static final String MISSION_CHANGED = "MissionChanged";

    private MessageType() {
    }

    public static boolean isRocketLaunched(Message message) {
        return ROCKET_LAUNCHED.equals(message.getMessageType());
    }

    public static boolean isRocketSpeedIncreased(Message message) {
        return ROCKET_SPEED_INCREASED.equals(message.getMessageType());
    }

    public static boolean isRocketSpeedDecreased(Message message) {
        return ROCKET_SPEED_DECREASED.equals(message.getMessageType());
    }

    public static boolean isRocketExploded(Message message) {
        return ROCKET_EXPLODED.equals(message.getMessageType());
    }

    public static boolean isMissionChanged(Message message) {
        return MISSION_CHANGED.equals(message.getMessageType());
    }

    public static boolean canBeFirst(Message message) {
        return ROCKET_LAUNCHED.equals(message.getMessageType()) || MISSION_CHANGED.equals(message.getMessageType());
    }
}
