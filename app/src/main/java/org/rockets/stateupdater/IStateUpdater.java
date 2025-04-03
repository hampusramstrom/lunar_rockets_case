package org.rockets.stateupdater;

import org.rockets.model.Message;

public interface IStateUpdater {
    boolean accept(Message message);
    void apply(Message message);
}
