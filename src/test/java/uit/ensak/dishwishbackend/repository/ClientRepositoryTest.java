package uit.ensak.dishwishbackend.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uit.ensak.dishwishbackend.model.Client;
import uit.ensak.dishwishbackend.model.Role;
import uit.ensak.dishwishbackend.repository.ClientRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientRepositoryTest {

    @Mock
    private ClientRepository clientRepository;

    @Test
    void testFindByIdAndRole() {
        Long clientId = 1L;
        Role role = Role.CHEF;
        Client expectedClient = new Client();
        expectedClient.setId(clientId);
        expectedClient.setRole(role);

        when(clientRepository.findByIdAndRole(clientId, role)).thenReturn(expectedClient);

        Client resultClient = clientRepository.findByIdAndRole(clientId, role);

        assertNotNull(resultClient);
        assertEquals(expectedClient, resultClient);
        verify(clientRepository, times(1)).findByIdAndRole(clientId, role);
    }

    @Test
    void testFindClientByEmail() {
        String email = "test@example.com";
        Client expectedClient = new Client();
        expectedClient.setEmail(email);

        when(clientRepository.findClientByEmail(email)).thenReturn(Optional.of(expectedClient));

        Optional<Client> resultClientOptional = clientRepository.findClientByEmail(email);

        assertTrue(resultClientOptional.isPresent());
        assertEquals(expectedClient, resultClientOptional.get());
        verify(clientRepository, times(1)).findClientByEmail(email);
    }

    @Test
    void testChangeClientType() {
        Long clientId = 1L;

        clientRepository.changeClientType(clientId);

        verify(clientRepository, times(1)).changeClientType(clientId);
    }
}
