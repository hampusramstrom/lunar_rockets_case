package org.rockets.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.rockets.factory.RocketInfoDtoFactory;
import org.rockets.model.RocketState;
import org.rockets.model.dto.RocketsDto.RocketInfoDto;
import org.rockets.module.RocketsModule;
import org.rockets.state.IRocketStates;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class RocketStatesServiceTest {

    @Inject
    private IRocketStates rocketStates;
    @Inject
    private RocketInfoDtoFactory rocketInfoDtoFactory;

    private RocketStatesService rocketStatesService;
    private RocketState startState;

    public RocketStatesServiceTest() {
    }

    @Before
    public void setUp() {
        Injector injector = Guice.createInjector(new RocketsModule());
        injector.injectMembers(this);

        rocketStatesService = new RocketStatesService(rocketStates, rocketInfoDtoFactory);
        startState = new RocketState();
        startState.setChannel(UUID.randomUUID());
        startState.setLastMessageNumber(1);
        startState.setLaunchTime(OffsetDateTime.now());
        startState.setRocketType("Falcon-9");
        startState.setSpeed(500);
        startState.setMission("ARTEMIS");
        startState.setHumanReadableName("human1");
    }

    @Test
    public void add_happyPath() {
        rocketStatesService.add(startState);

        assertEquals(startState.getChannel(), rocketStates.get(startState.getChannel()).get().getChannel());
    }

    @Test
    public void increaseSpeed_happyPath() {
        rocketStatesService.add(startState);
        rocketStatesService.increaseSpeed(startState.getChannel(), startState.getLastMessageNumber() + 1, 1);
        RocketState updatedState = rocketStates.get(startState.getChannel()).get();

        assertEquals(startState.getLastMessageNumber() + 1, updatedState.getLastMessageNumber());
        assertEquals(startState.getSpeed() + 1, updatedState.getSpeed());
    }

    @Test
    public void decreaseSpeed_happyPath() {
        rocketStatesService.add(startState);
        rocketStatesService.decreaseSpeed(startState.getChannel(), startState.getLastMessageNumber() + 1, 1);
        RocketState updatedState = rocketStates.get(startState.getChannel()).get();

        assertEquals(startState.getLastMessageNumber() + 1, updatedState.getLastMessageNumber());
        assertEquals(startState.getSpeed() - 1, updatedState.getSpeed());
    }

    @Test
    public void changeMission_happyPath() {
        rocketStatesService.add(startState);

        RocketState startState2 = new RocketState();
        startState2.setChannel(UUID.randomUUID());
        startState2.setLastMessageNumber(1);
        startState2.setMission("ARTEMIS");
        rocketStatesService.add(startState2);

        rocketStatesService.changeMission(startState.getChannel(), startState.getLastMessageNumber() + 1, "mission", "center");
        RocketState updatedState = rocketStates.get(startState.getChannel()).get();

        assertEquals(startState.getLastMessageNumber() + 1, updatedState.getLastMessageNumber());
        assertEquals("mission", updatedState.getMission());
        assertEquals("center", updatedState.getMissionChangedByCenter());
    }

    @Test
    public void setExplosionReason_happyPath() {
        rocketStatesService.add(startState);
        rocketStatesService.setExplosionReason(startState.getChannel(), startState.getLastMessageNumber() + 1, "explosion reason");
        RocketState updatedState = rocketStates.get(startState.getChannel()).get();

        assertEquals(startState.getLastMessageNumber() + 1, updatedState.getLastMessageNumber());
        assertEquals("explosion reason", updatedState.getExplosionReason());
    }

    @Test
    public void getInfoAllActiveRockets_happyPath() {
        rocketStatesService.add(startState);

        RocketState startState2 = new RocketState();
        startState2.setChannel(UUID.randomUUID());
        startState2.setLastMessageNumber(1);
        startState2.setLaunchTime(startState.getLaunchTime().minusSeconds(1));
        startState2.setHumanReadableName("human2");
        rocketStatesService.add(startState2);

        RocketState startState3 = new RocketState();
        startState3.setChannel(UUID.randomUUID());
        startState3.setLastMessageNumber(1);
        startState3.setLaunchTime(startState.getLaunchTime().plusSeconds(1));
        startState3.setHumanReadableName("human3");
        rocketStatesService.add(startState3);

        RocketState notLaunchedState = new RocketState();
        notLaunchedState.setChannel(UUID.randomUUID());
        notLaunchedState.setLaunchTime(null);

        RocketState explodedState = new RocketState();
        explodedState.setChannel(UUID.randomUUID());
        explodedState.setLaunchTime(OffsetDateTime.now());
        explodedState.setExplosionReason("explosion reason");

        List<RocketInfoDto> rocketInfos = rocketStatesService.getInfoAllActiveRockets();

        assertEquals(3, rocketInfos.size());

        RocketInfoDto rocketInfo1 = rocketInfos.get(0);
        assertEquals(startState2.getChannel(), rocketInfo1.getChannel());
        assertEquals(startState2.getLaunchTime(), rocketInfo1.getLaunchTime());
        assertEquals(startState2.getHumanReadableName(), rocketInfo1.getHumanReadableName());

        RocketInfoDto rocketInfo2 = rocketInfos.get(1);
        assertEquals(startState.getChannel(), rocketInfo2.getChannel());
        assertEquals(startState.getLaunchTime(), rocketInfo2.getLaunchTime());
        assertEquals(startState.getHumanReadableName(), rocketInfo2.getHumanReadableName());

        RocketInfoDto rocketInfo3 = rocketInfos.get(2);
        assertEquals(startState3.getChannel(), rocketInfo3.getChannel());
        assertEquals(startState3.getLaunchTime(), rocketInfo3.getLaunchTime());
        assertEquals(startState3.getHumanReadableName(), rocketInfo3.getHumanReadableName());
    }

    @Test
    public void shortRocketJourney_happyPath() {
        rocketStatesService.add(startState);
        int lastMsgNbr = startState.getLastMessageNumber();
        rocketStatesService.increaseSpeed(startState.getChannel(), ++lastMsgNbr, 100);
        rocketStatesService.decreaseSpeed(startState.getChannel(), ++lastMsgNbr, 5);
        rocketStatesService.changeMission(startState.getChannel(), ++lastMsgNbr, "mission", "center");
        rocketStatesService.setExplosionReason(startState.getChannel(), ++lastMsgNbr, "explosion reason");

        RocketState updatedState = rocketStates.get(startState.getChannel()).get();
        assertEquals(lastMsgNbr, updatedState.getLastMessageNumber());
        assertEquals(startState.getSpeed() + 100 - 5, updatedState.getSpeed());
        assertEquals("mission", updatedState.getMission());
        assertEquals("center", updatedState.getMissionChangedByCenter());
        assertEquals("explosion reason", updatedState.getExplosionReason());
    }
}
