package uit.ensak.dishwishbackend.service.auth;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import uit.ensak.dishwishbackend.event.RegistrationCompleteEvent;
import uit.ensak.dishwishbackend.model.Client;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import jakarta.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailVerificationServiceTest {

    @Mock
    private ApplicationEventPublisher publisher;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private EmailVerificationService emailVerificationService;

    @Captor
    private ArgumentCaptor<RegistrationCompleteEvent> eventCaptor;

    @Test
    void sendVerificationEmail_SuccessfulEventPublishing() {
        // Arrange
        Client client = new Client();
        String verificationToken = "validToken";

        when(request.getServerName()).thenReturn("example.com");
        when(request.getServerPort()).thenReturn(80);
        when(request.getContextPath()).thenReturn("/");

        // Act
        emailVerificationService.sendVerificationEmail(client, verificationToken);

        // Assert
        verify(publisher, times(1)).publishEvent(eventCaptor.capture());

        RegistrationCompleteEvent capturedEvent = eventCaptor.getValue();
        assert capturedEvent != null;
        assertSame(client, capturedEvent.getClient());
        assertEquals(verificationToken, capturedEvent.getVerificationToken());
        assertEquals("http://example.com:80//", capturedEvent.getApplicationUrl());
    }
}
