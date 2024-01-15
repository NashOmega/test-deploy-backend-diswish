package uit.ensak.dishwishbackend.service.auth;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import uit.ensak.dishwishbackend.controller.auth.payloads.AuthenticationResponse;
import uit.ensak.dishwishbackend.controller.auth.payloads.RegisterRequest;
import uit.ensak.dishwishbackend.model.Client;
import uit.ensak.dishwishbackend.model.Role;
import uit.ensak.dishwishbackend.security.JwtService;
import uit.ensak.dishwishbackend.service.ClientService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    ClientService clientService;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtService jwtService;

    @Mock
    EmailVerificationService emailVerificationService;

    @InjectMocks
    AuthenticationService authenticationService;

    @Test
    @Disabled
    void AuthenticationService_register_succeed() {
        // given - when
        String email = "abdessamadbounasseh@dishwish.com";
        String password = "0000";

        RegisterRequest request = RegisterRequest.builder()
                .email(email)
                .password(password)
                .build();

        Client client = Client.builder()
                .email(email)
                .password(password)
                .role(Role.CLIENT)
                .build();

        when(clientService.saveClient((any(Client.class))))
                .thenReturn(client);

        authenticationService.register(request);

        // assert
//        assertNotNull(response);
        assertEquals(1, clientService.getAllClients().size());
    }

    @Test
    void AuthenticationService_authenticate_succeed() {
    }

    @Test
    void AuthenticationService_refreshToken_succeed() {
    }
}