package org.rockets.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.rockets.factory.RocketInfoDtoFactory;
import org.rockets.model.RocketState;
import org.rockets.model.dto.RocketsDto;
import org.rockets.state.IRocketStates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javafaker.Faker;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class RocketStatesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RocketStatesService.class);
    // We want to ensure not having concurrency issues when changing the state of a rocket.
    private final ConcurrentHashMap<UUID, ReentrantLock> channelLocks = new ConcurrentHashMap<>();
    private final IRocketStates rocketStates;
    private final RocketInfoDtoFactory rocketInfoDtoFactory;

    @Inject
    public RocketStatesService(IRocketStates rocketStates, RocketInfoDtoFactory rocketInfoDtoFactory) {
        this.rocketStates = rocketStates;
        this.rocketInfoDtoFactory = rocketInfoDtoFactory;
    }

    public boolean add(RocketState rocketState) {
        ReentrantLock lock = channelLocks.computeIfAbsent(rocketState.getChannel(), k -> new ReentrantLock());
        lock.lock();
        try {
            return rocketStates.add(rocketState.copy());
        } finally {
            lock.unlock();
        }
    }

    public boolean updateStateForLaunch(RocketState rocketState) {
        ReentrantLock lock = channelLocks.computeIfAbsent(rocketState.getChannel(), k -> new ReentrantLock());
        lock.lock();
        try {
            Optional<RocketState> existingState = rocketStates.get(rocketState.getChannel());
            if (existingState.isEmpty()) {
                LOGGER.error("%s - No existing state for RocketLaunched message: Having message number: %s", rocketState.getChannel(), rocketState.getLastMessageNumber());
                return rocketStates.add(rocketState.copy());
            } else {
                RocketState updatedState = rocketState.copy();
                updatedState.setMission(existingState.get().getMission());
                updatedState.setMissionChangedByCenter(existingState.get().getMissionChangedByCenter());
                return rocketStates.replace(updatedState);
            }
        } finally {
            lock.unlock();
        }
    }

    public int increaseSpeed(UUID channel, int msgNumber, int by) {
        ReentrantLock lock = channelLocks.computeIfAbsent(channel, k -> new ReentrantLock());
        lock.lock();
        try {
            return rocketStates.increaseSpeed(channel, msgNumber, by);
        } finally {
            lock.unlock();
        }
    }

    public int decreaseSpeed(UUID channel, int msgNumber, int by) {
        ReentrantLock lock = channelLocks.computeIfAbsent(channel, k -> new ReentrantLock());
        lock.lock();
        try {
            return rocketStates.decreaseSpeed(channel, msgNumber, by);
        } finally {
            lock.unlock();
        }
    }

    public void changeMission(UUID channel, int msgNumber, String newMission, String center) {
        ReentrantLock lock = channelLocks.computeIfAbsent(channel, k -> new ReentrantLock());
        lock.lock();
        try {
            rocketStates.changeMission(channel, msgNumber, newMission, center);
        } finally {
            lock.unlock();
        }
    }

    public void setExplosionReason(UUID channel, int msgNumber, String explosionReason) {
        ReentrantLock lock = channelLocks.computeIfAbsent(channel, k -> new ReentrantLock());
        lock.lock();
        try {
            rocketStates.setExplosionReason(channel, msgNumber, explosionReason);
        } finally {
            lock.unlock();
        }
    }

    public String generateHumanReadableName() {
        Faker faker = new Faker();
        String humanReadableName = faker.name().fullName();
        while (rocketStates.humanReadableNameExists(humanReadableName)) {
            humanReadableName = faker.name().fullName();
        }
        return humanReadableName;
    }

    /**
     * Sorted on the launch time.
     */
    public List<RocketsDto.RocketInfoDto> getInfoAllActiveRockets() {
        List<RocketState> rStates = rocketStates.getAll();
        List<RocketsDto.RocketInfoDto> rocketInfoDtos = rStates.stream()
                // Remove all states where the rocket hasn't been launched
                .filter(rState -> rState.getLaunchTime() != null)
                // Remove all states where the rocket has exploded
                .filter(rState -> rState.getExplosionReason() == null)
                .map(rState -> rocketInfoDtoFactory.create(rState))
                .sorted(Comparator.comparing(RocketsDto.RocketInfoDto::getLaunchTime))
                .toList();
        return rocketInfoDtos;
    }
}
