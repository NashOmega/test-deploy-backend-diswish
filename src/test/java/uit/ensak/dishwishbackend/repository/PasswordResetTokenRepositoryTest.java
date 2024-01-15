package uit.ensak.dishwishbackend.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uit.ensak.dishwishbackend.model.PasswordResetToken;
import uit.ensak.dishwishbackend.repository.PasswordResetTokenRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasswordResetTokenRepositoryTest {

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Test
    void testFindByCode() {
        // Arrange
        String code = "abcd1234";
        PasswordResetToken expectedToken = new PasswordResetToken();
        expectedToken.setCode(code);

        when(passwordResetTokenRepository.findByCode(code)).thenReturn(expectedToken);

        // Act
        PasswordResetToken resultToken = passwordResetTokenRepository.findByCode(code);

        // Assert
        assertNotNull(resultToken);
        assertEquals(expectedToken, resultToken);
        verify(passwordResetTokenRepository, times(1)).findByCode(code);
    }

    @Test
    void testDeleteAllByClientId() {
        // Arrange
        Long clientId = 1L;

        // Act
        passwordResetTokenRepository.deleteAllByClientId(clientId);

        // Assert
        verify(passwordResetTokenRepository, times(1)).deleteAllByClientId(clientId);
    }

    @Test
    void testFindByClientId() {
        // Arrange
        Long clientId = 1L;
        PasswordResetToken expectedToken = new PasswordResetToken();

        when(passwordResetTokenRepository.findByClientId(clientId)).thenReturn(Optional.of(expectedToken));

        // Act
        Optional<PasswordResetToken> resultTokenOptional = passwordResetTokenRepository.findByClientId(clientId);

        // Assert
        assertTrue(resultTokenOptional.isPresent());
        assertEquals(expectedToken, resultTokenOptional.get());
        verify(passwordResetTokenRepository, times(1)).findByClientId(clientId);
    }
}
