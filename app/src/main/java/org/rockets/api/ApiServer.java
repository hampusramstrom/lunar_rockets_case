package org.rockets.api;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.rockets.exception.MessageValidatorException;
import org.rockets.factory.MessageFactory;
import org.rockets.factory.RocketStateDtoFactory;
import org.rockets.model.Message;
import org.rockets.model.RocketState;
import org.rockets.model.dto.MessageDto;
import org.rockets.model.dto.RocketStateDto;
import org.rockets.model.dto.RocketsDto;
import org.rockets.serializer.IDeserializer;
import org.rockets.serializer.ISerializer;
import org.rockets.service.MessageRepositoryService;
import org.rockets.service.RocketStatesService;
import org.rockets.state.IRocketStates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import io.javalin.Javalin;

public class ApiServer implements IApiServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiServer.class);
    private final ISerializer serializer;
    private final IDeserializer deserializer;
    private final MessageRepositoryService msgRepositoryService;
    private final IRocketStates rocketStates;
    private final RocketStatesService rocketStatesService;
    private final RocketStateDtoFactory rocketStateDtoFactory;
    private final MessageFactory msgFactory;

    @Inject
    public ApiServer(IDeserializer deserializer, MessageFactory msgFactory, MessageRepositoryService msgRepositoryService, RocketStateDtoFactory rocketStateDtoFactory, IRocketStates rocketStates, RocketStatesService rocketStatesService, ISerializer serializer) {
        this.deserializer = deserializer;
        this.msgFactory = msgFactory;
        this.msgRepositoryService = msgRepositoryService;
        this.rocketStateDtoFactory = rocketStateDtoFactory;
        this.rocketStates = rocketStates;
        this.rocketStatesService = rocketStatesService;
        this.serializer = serializer;
    }

    @Override
    public void run(int port) {
        Javalin app = Javalin.create().start(port);

        app.post("/messages", ctx -> {
            String message = ctx.body();

            MessageDto msgDto = deserializer.deserialize(message, MessageDto.class);
            Message msg = msgFactory.create(msgDto);
            LOGGER.debug(String.format("%s - New message: Number %s with type %s", msg.getChannel(), msg.getMessageNumber(), msg.getMessageType()));
            try {
                msgRepositoryService.add(msg);
            } catch (MessageValidatorException e) {
                ctx.status(400);
                ctx.result(e.getMessage());
            }

            LOGGER.debug("Number of channels: " + msgRepositoryService.getNbrOfChannels());
            LOGGER.debug("Number of messages: " + msgRepositoryService.getNbrOfMessages());
        });

        app.get("/rockets", ctx -> {
            List<RocketsDto.RocketInfoDto> rocketInfoDtos = rocketStatesService.getInfoAllActiveRockets();

            RocketsDto rocketsDto = new RocketsDto();
            rocketsDto.setRockets(rocketInfoDtos);
            String rocketsSerialized = serializer.serialize(rocketsDto);
            ctx.result(rocketsSerialized);
        });

        app.get("/rocket-state/{channel}", ctx -> {
            String channelParam = ctx.pathParam("channel");
            UUID channel;
            try {
                channel = UUID.fromString(channelParam);
            } catch (IllegalArgumentException e) {
                ctx.status(400);
                ctx.result(String.format("A rocket with the channel \"%s\" isn't a UUID.", channelParam));
                return;
            }

            Optional<RocketState> rocketState = rocketStates.get(channel);
            if (rocketState.isEmpty()) {
                ctx.status(404);
                ctx.result(String.format("A rocket with the channel \"%s\" was not found.", channel));
                return;
            }
            RocketStateDto rocketStateDto = rocketStateDtoFactory.create(rocketState.get());
            String rocketStateSerialized = serializer.serialize(rocketStateDto);
            ctx.result(rocketStateSerialized);
        });
    }
}
