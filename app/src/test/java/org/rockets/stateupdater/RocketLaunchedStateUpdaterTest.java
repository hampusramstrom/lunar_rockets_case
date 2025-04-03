package org.rockets.stateupdater;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.rockets.constant.MessageType;
import org.rockets.factory.RocketStateFactory;
import org.rockets.model.Message;
import org.rockets.model.dto.RocketsDto.RocketInfoDto;
import org.rockets.module.RocketsModule;
import org.rockets.serializer.IDeserializer;
import org.rockets.service.RocketStatesService;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class RocketLaunchedStateUpdaterTest {

    @Inject
    private IDeserializer deserializer;
    @Inject
    private RocketStatesService rocketStatesService;
    @Inject
    private RocketStateFactory rocketStateFactory;
    private RocketLaunchedStateUpdater rocketLaunchedStateUpdater;
    private Message msg;

    @Before
    public void setUp() {
        Injector injector = Guice.createInjector(new RocketsModule());
        injector.injectMembers(this);

        rocketLaunchedStateUpdater = new RocketLaunchedStateUpdater(deserializer, rocketStatesService, rocketStateFactory);
        msg = new Message();
        msg.setChannel(UUID.randomUUID());
        msg.setMessageNumber(1);
        msg.setMessageType(MessageType.ROCKET_LAUNCHED);
        msg.setMessage("{\"type\":\"Atlas-H\",\"launchSpeed\":500,\"mission\":\"APOLLO\"}");
        msg.setMessageTime(OffsetDateTime.now());
    }

    @Test
    public void accept_happyPath() {
        assertTrue(rocketLaunchedStateUpdater.accept(msg));
    }

    @Test
    public void apply_happyPath() {
        rocketLaunchedStateUpdater.apply(msg);
        RocketInfoDto updatedState = rocketStatesService.getInfoAllActiveRockets().get(0);

        assertEquals(msg.getChannel(), updatedState.getChannel());
        assertNotNull(updatedState.getHumanReadableName());
    }
}
