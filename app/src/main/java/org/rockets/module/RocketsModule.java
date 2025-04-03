package org.rockets.module;

import org.rockets.api.ApiServer;
import org.rockets.api.IApiServer;
import org.rockets.repository.IMessageRepository;
import org.rockets.repository.InMemoryMessageRepository;
import org.rockets.serializer.IDeserializer;
import org.rockets.serializer.ISerializer;
import org.rockets.serializer.JsonDeserializer;
import org.rockets.serializer.JsonSerializer;
import org.rockets.state.IRocketStates;
import org.rockets.state.InMemoryRocketStates;
import org.rockets.stateupdater.IStateUpdater;
import org.rockets.stateupdater.MissionChangedStateUpdater;
import org.rockets.stateupdater.RocketExplodedStateUpdater;
import org.rockets.stateupdater.RocketLaunchedStateUpdater;
import org.rockets.stateupdater.RocketSpeedDecreasedStateUpdater;
import org.rockets.stateupdater.RocketSpeedIncreasedStateUpdater;
import org.rockets.validator.DuplicateMessageValidator;
import org.rockets.validator.FirstMessageTypeValidator;
import org.rockets.validator.FutureMessageValidator;
import org.rockets.validator.IMessageValidator;
import org.rockets.validator.PreviousMessageValidator;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

public class RocketsModule extends AbstractModule {
 
    @Override
    protected void configure() {
        bind(IApiServer.class).to(ApiServer.class);
        bind(IMessageRepository.class).to(InMemoryMessageRepository.class);
        bind(ISerializer.class).to(JsonSerializer.class);
        bind(IDeserializer.class).to(JsonDeserializer.class);
        bind(IRocketStates.class).to(InMemoryRocketStates.class);

        Multibinder<IStateUpdater> stateUpdaterBinder = Multibinder.newSetBinder(binder(), IStateUpdater.class);
        stateUpdaterBinder.addBinding().to(RocketLaunchedStateUpdater.class);
        stateUpdaterBinder.addBinding().to(RocketSpeedIncreasedStateUpdater.class);
        stateUpdaterBinder.addBinding().to(RocketSpeedDecreasedStateUpdater.class);
        stateUpdaterBinder.addBinding().to(RocketExplodedStateUpdater.class);
        stateUpdaterBinder.addBinding().to(MissionChangedStateUpdater.class);

        Multibinder<IMessageValidator> messageValidatorBinder = Multibinder.newSetBinder(binder(), IMessageValidator.class);
        messageValidatorBinder.addBinding().to(DuplicateMessageValidator.class);
        messageValidatorBinder.addBinding().to(FirstMessageTypeValidator.class);
        messageValidatorBinder.addBinding().to(PreviousMessageValidator.class);
        messageValidatorBinder.addBinding().to(FutureMessageValidator.class);
    }
}