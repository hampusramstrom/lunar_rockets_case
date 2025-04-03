package org.rockets.service;

import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InOrder;
import org.mockito.Mock;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;
import org.rockets.exception.MessageValidatorException;
import org.rockets.model.Message;
import org.rockets.repository.IMessageRepository;
import org.rockets.stateupdater.IStateUpdater;
import org.rockets.validator.IMessageValidator;

@RunWith(MockitoJUnitRunner.class)
public class MessageRepositoryServiceTest {

    private MessageRepositoryService msgRepositoryService;
    private Message msg;
    @Mock
    private IMessageRepository msgRepository;
    @Mock
    private IMessageValidator msgValidator;
    @Mock
    private IStateUpdater stateUpdater;

    @Before
    public void setup() {
        msgRepositoryService = new MessageRepositoryService(msgRepository, Set.of(msgValidator), Set.of(stateUpdater));
        msg = new Message();
        msg.setChannel(UUID.randomUUID());
        msg.setMessageNumber(1);
    }

    @Test
    public void add_happyPath() throws MessageValidatorException {
        when(msgValidator.validate(msg)).thenReturn(true);
        when(stateUpdater.accept(any())).thenReturn(true);

        boolean res = msgRepositoryService.add(msg);

        InOrder inOrder = inOrder(msgValidator, msgRepository, stateUpdater);
        inOrder.verify(msgValidator).validate(msg);
        inOrder.verify(msgRepository).persist(msg);
        inOrder.verify(stateUpdater).accept(msg);
        inOrder.verify(stateUpdater).apply(msg);

        assertTrue(res);
    }

    @Test
    public void add_sadPath() throws MessageValidatorException {
        when(msgValidator.validate(any())).thenReturn(false);

        boolean res = msgRepositoryService.add(msg);

        verify(msgValidator).validate(msg);
        verifyNoInteractions(msgRepository);
        verifyNoInteractions(stateUpdater);

        assertFalse(res);
    }
}
