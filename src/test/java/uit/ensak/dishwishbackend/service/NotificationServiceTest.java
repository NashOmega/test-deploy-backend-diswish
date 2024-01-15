package uit.ensak.dishwishbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uit.ensak.dishwishbackend.exception.ClientNotFoundException;
import uit.ensak.dishwishbackend.model.*;
import uit.ensak.dishwishbackend.repository.ChefRepository;
import uit.ensak.dishwishbackend.repository.ClientRepository;
import uit.ensak.dishwishbackend.repository.CommandRepository;
import uit.ensak.dishwishbackend.repository.PropositionRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {
    @Mock
    private CommandRepository commandRepository;

    @Mock
    private ChefRepository chefRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PropositionRepository propositionRepository;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testGetChefNotifications() throws ClientNotFoundException {
        Long chefId = 1L;
        Chef chef = new Chef();
        chef.setId(chefId);
        chef.setAddress(new Address(2L, "address", new City("City"), "3.14F 3.14F"));

        when(chefRepository.findById(chefId)).thenReturn(Optional.of(chef));
        when(commandRepository.findByCity("City")).thenReturn(Arrays.asList(new Command(), new Command()));

        List<Command> notifications = notificationService.getChefNotifications(chefId);

        assertNotNull(notifications);
        assertEquals(2, notifications.size());
        verify(chefRepository, times(1)).findById(chefId);
        verify(commandRepository, times(1)).findByCity("City");
    }

    @Test
    void testGetChefNotificationsClientNotFoundException() {
        Long chefId = 1L;

        when(chefRepository.findById(chefId)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> notificationService.getChefNotifications(chefId));
        verify(chefRepository, times(1)).findById(chefId);
        verify(commandRepository, never()).findByCity(anyString());
    }

    @Test
    void testGetClientNotifications() throws ClientNotFoundException {
        Long clientId = 1L;
        Client client = new Client();
        client.setId(clientId);

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(propositionRepository.findByClient_Id(clientId)).thenReturn(Arrays.asList(new Proposition(), new Proposition()));

        List<Proposition> notifications = notificationService.getClientNotifications(clientId);

        assertNotNull(notifications);
        assertEquals(2, notifications.size());
        verify(clientRepository, times(1)).findById(clientId);
        verify(propositionRepository, times(1)).findByClient_Id(clientId);
    }

    @Test
    void testGetClientNotificationsClientNotFoundException() {
        Long clientId = 1L;

        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> notificationService.getClientNotifications(clientId));
        verify(clientRepository, times(1)).findById(clientId);
        verify(propositionRepository, never()).findByClient_Id(anyLong());
    }

    @Test
    void getChefConfirmedNotifications_Success() throws ClientNotFoundException {
        Long chefId = 1L;
        Chef mockChef = new Chef();
        mockChef.setId(chefId);

        List<Command> expectedNotifications = Collections.singletonList(new Command());

        when(chefRepository.findById(chefId)).thenReturn(Optional.of(mockChef));
        when(commandRepository.findByChefIdAndStatus(chefId, "FINISHED")).thenReturn(expectedNotifications);

        List<Command> result = notificationService.getChefConfirmedNotifications(chefId);

        verify(chefRepository).findById(chefId);
        verify(commandRepository).findByChefIdAndStatus(chefId, "FINISHED");
        assertEquals(expectedNotifications, result);
    }

}
