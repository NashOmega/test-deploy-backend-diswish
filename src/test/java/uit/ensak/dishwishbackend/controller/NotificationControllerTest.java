package uit.ensak.dishwishbackend.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uit.ensak.dishwishbackend.controller.NotificationController;
import uit.ensak.dishwishbackend.exception.ClientNotFoundException;
import uit.ensak.dishwishbackend.model.Command;
import uit.ensak.dishwishbackend.model.Proposition;
import uit.ensak.dishwishbackend.service.NotificationService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @Test
    void testGetChefNotifications() throws ClientNotFoundException {
        // Arrange
        Long chefId = 1L;
        List<Command> expectedNotifications = Arrays.asList(new Command(), new Command());

        when(notificationService.getChefNotifications(chefId)).thenReturn(expectedNotifications);

        // Act
        ResponseEntity<List<Command>> responseEntity = notificationController.getChefNotifications(chefId);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedNotifications, responseEntity.getBody());
        verify(notificationService, times(1)).getChefNotifications(chefId);
    }

    @Test
    void testGetClientNotifications() throws ClientNotFoundException {
        // Arrange
        Long clientId = 1L;
        List<Proposition> expectedNotifications = Arrays.asList(new Proposition(), new Proposition());

        when(notificationService.getClientNotifications(clientId)).thenReturn(expectedNotifications);

        // Act
        ResponseEntity<List<Proposition>> responseEntity = notificationController.getClientNotifications(clientId);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedNotifications, responseEntity.getBody());
        verify(notificationService, times(1)).getClientNotifications(clientId);
    }

    @Test
    void testGetChefConfirmedNotifications() throws ClientNotFoundException {
        // Arrange
        Long chefId = 1L;
        List<Command> expectedNotifications = Arrays.asList(new Command(), new Command());

        when(notificationService.getChefConfirmedNotifications(chefId)).thenReturn(expectedNotifications);

        // Act
        ResponseEntity<List<Command>> responseEntity = notificationController.getChefConfirmedNotifications(chefId);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedNotifications, responseEntity.getBody());
        verify(notificationService, times(1)).getChefConfirmedNotifications(chefId);
    }
}
