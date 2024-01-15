package uit.ensak.dishwishbackend.service.auth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uit.ensak.dishwishbackend.exception.VerificationTokenNotFoundException;
import uit.ensak.dishwishbackend.model.VerificationToken;
import uit.ensak.dishwishbackend.repository.TokenRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VerificationTokenServiceTest {

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private VerificationTokenService verificationTokenService;

    @Test
    void getByToken_ExistingToken_ReturnsToken() throws VerificationTokenNotFoundException {
        String tokenValue = "validToken";
        VerificationToken expectedToken = new VerificationToken();
        expectedToken.setToken(tokenValue);

        when(tokenRepository.findByToken(tokenValue)).thenReturn(Optional.of(expectedToken));

        VerificationToken actualToken = verificationTokenService.getByToken(tokenValue);

        assertNotNull(actualToken);
        assertEquals(expectedToken, actualToken);

        verify(tokenRepository, times(1)).findByToken(tokenValue);
    }

    @Test
    void getByToken_NonExistingToken_ThrowsException() {
        String tokenValue = "nonExistingToken";

        when(tokenRepository.findByToken(tokenValue)).thenReturn(Optional.empty());

        assertThrows(VerificationTokenNotFoundException.class,
                () -> verificationTokenService.getByToken(tokenValue));

        verify(tokenRepository, times(1)).findByToken(tokenValue);
    }
}
