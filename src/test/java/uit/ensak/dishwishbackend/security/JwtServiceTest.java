package uit.ensak.dishwishbackend.security;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import uit.ensak.dishwishbackend.model.Client;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        String secretKey = "e0c352481de147d859946af7610bbe1c1626d4e939d828295977491197b67914";
        Long jwtExpiration = 86400000L;
        Long refreshExpiration = 604800000L;

        ReflectionTestUtils.setField(jwtService, "SECRET_KEY", secretKey);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", jwtExpiration);
        ReflectionTestUtils.setField(jwtService, "refreshExpiration", refreshExpiration);
    }

    @Test
    void JwtService_buildToken_succeed() {
        // given
        Client client = new Client();

        // when
        String token = jwtService.buildToken(new HashMap<>(), client, 0);

        // then
        assertThat(token).isNotBlank();
        assertEquals(112, token.length());
    }

    @Test
    void JwtService_generateToken_succeed() {
        // given
        Client client = new Client();

        // when
        String token = jwtService.generateToken(client);

        // then
        assertThat(token).isNotBlank();
        assertEquals(112, token.length());
    }

    @Test
    void JwtService_generateTokenWithClaims_succeed() {
        // given
        Client client = new Client();

        // when
        String token = jwtService.generateToken(new HashMap<>(), client);

        // then
        assertThat(token).isNotBlank();
        assertEquals(112, token.length());
    }

    @Test
    void JwtService_extractUsername_succeed() {
        // given
        String userEmail = "abdessamadbounasseh@gmail.com";

        Client client = Client.builder()
                .email(userEmail).build();

        String token = jwtService.generateToken(client);

        // when
        String email = jwtService.extractUsername(token);

        // then
        assertThat(email).isEqualTo(userEmail);
    }

    @Test
    void JwtService_generateRefreshToken_succeed() {
        // given
        Client client = new Client();

        // when
        String token = jwtService.generateRefreshToken(client);

        // then
        assertThat(token).isNotBlank();
        assertEquals(112, token.length());
    }

    @Test
    void JwtService_isTokenValid_returnTrue() {
        // given
        String userEmail = "abdessamadbounasseh@gmail.com";

        Client client = Client.builder()
                .email(userEmail).build();

        String token = jwtService.generateToken(client);

        // when
        boolean isTokenValid = jwtService.isTokenValid(token, client);

        // then
        assertTrue(isTokenValid);
    }

    @Test
    void JwtService_isTokenValid_returnFalse() {
        // given
        String userEmail = "abdessamadbounasseh@gmail.com";

        Client client = Client.builder()
                .email(userEmail).build();

        Client otherClient = Client.builder().build();

        String token = jwtService.generateToken(client);

        // when
        boolean isTokenValid = jwtService.isTokenValid(token, otherClient);

        // then
        assertFalse(isTokenValid);
    }

    @Test
    void JwtService_isTokenExpired_returnFalse() {
        // given
        String userEmail = "abdessamadbounasseh@gmail.com";

        Client client = Client.builder()
                .email(userEmail).build();

        String token = jwtService.generateToken(client);

        // when
        boolean isTokenExpired = jwtService.isTokenExpired(token);

        // then
        assertFalse(isTokenExpired);
    }

    @Test
    void JwtService_isTokenExpired_returnTrue() {
        // given
        String userEmail = "abdessamadbounasseh@gmail.com";

        Client client = Client.builder()
                .email(userEmail).build();

        String token = jwtService.buildToken(new HashMap<>(), client, 0);

        // when
        boolean isTokenExpired = jwtService.isTokenExpired(token);

        // then
        assertTrue(isTokenExpired);
    }
}