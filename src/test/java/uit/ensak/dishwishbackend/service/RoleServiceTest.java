package uit.ensak.dishwishbackend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uit.ensak.dishwishbackend.exception.ClientNotFoundException;
import uit.ensak.dishwishbackend.model.Client;
import uit.ensak.dishwishbackend.model.Role;
import uit.ensak.dishwishbackend.repository.ClientRepository;
import uit.ensak.dishwishbackend.service.RoleService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private RoleService roleService;

    @Test
    void testAddRoleToUser() throws ClientNotFoundException {
        String email = "test@example.com";
        String roleName = "CHEF";
        Role role = Role.valueOf(roleName);
        Client client = new Client();
        client.setEmail(email);

        when(clientRepository.findClientByEmail(email)).thenReturn(Optional.of(client));

        roleService.addRoleToUser(email, roleName);

        assertEquals(role, client.getRole());
        verify(clientRepository, times(1)).findClientByEmail(email);
    }

    @Test
    void testAddRoleToUserClientNotFoundException() {
        String email = "nonexistent@example.com";
        String roleName = "CHEF";

        when(clientRepository.findClientByEmail(email)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> roleService.addRoleToUser(email, roleName));
        verify(clientRepository, times(1)).findClientByEmail(email);
    }
}
