package uit.ensak.dishwishbackend.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import uit.ensak.dishwishbackend.model.VerificationToken;
import uit.ensak.dishwishbackend.repository.TokenRepository;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogoutServiceTest {

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private LogoutService logoutService;

    @Test
    void logoutRevokesToken() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);

        String jwt = "valid-jwt-token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
        VerificationToken storedToken = new VerificationToken();
        when(tokenRepository.findByToken(jwt)).thenReturn(Optional.of(storedToken));

        logoutService.logout(request, response, authentication);

        verify(tokenRepository).save(argThat(argument -> argument == storedToken && argument.isRevoked() && argument.isExpired()));
    }

    @Test
    void logoutSkipsIfNoAuthorizationHeader() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);

        when(request.getHeader("Authorization")).thenReturn(null);

        logoutService.logout(request, response, authentication);

        verify(tokenRepository, never()).findByToken(anyString());
        verify(tokenRepository, never()).save(any());
    }

    @Test
    void logoutSkipsIfInvalidAuthorizationHeader() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);

        when(request.getHeader("Authorization")).thenReturn("InvalidHeaderFormat");

        logoutService.logout(request, response, authentication);

        verify(tokenRepository, never()).findByToken(anyString());
        verify(tokenRepository, never()).save(any());
    }
}
