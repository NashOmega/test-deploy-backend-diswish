package uit.ensak.dishwishbackend.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uit.ensak.dishwishbackend.dto.PropositionDTO;
import uit.ensak.dishwishbackend.exception.ClientNotFoundException;
import uit.ensak.dishwishbackend.exception.CommandNotFoundException;
import uit.ensak.dishwishbackend.model.Chef;
import uit.ensak.dishwishbackend.model.Client;
import uit.ensak.dishwishbackend.model.Command;
import uit.ensak.dishwishbackend.model.Proposition;
import uit.ensak.dishwishbackend.repository.ChefRepository;
import uit.ensak.dishwishbackend.repository.ClientRepository;
import uit.ensak.dishwishbackend.repository.CommandRepository;
import uit.ensak.dishwishbackend.repository.PropositionRepository;
import uit.ensak.dishwishbackend.service.PropositionService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Disabled
@ExtendWith(MockitoExtension.class)
class PropositionServiceTest {

    @Mock
    private PropositionRepository propositionRepository;

    @Mock
    private CommandRepository commandRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ChefRepository chefRepository;

    @InjectMocks
    private PropositionService propositionService;

    @Test
    void testGetAllPropositions() {
        // Arrange
        List<Proposition> expectedPropositions = Arrays.asList(new Proposition(), new Proposition());
        when(propositionRepository.findAll()).thenReturn(expectedPropositions);

        // Act
        List<Proposition> resultPropositions = propositionService.getAllPropositions();

        // Assert
        assertNotNull(resultPropositions);
        assertEquals(expectedPropositions, resultPropositions);
        verify(propositionRepository, times(1)).findAll();
    }

    @Test
    void testCreateProposition() throws CommandNotFoundException, ClientNotFoundException {
        Client client = Client.builder().id(1L).build();
        Chef chef = new Chef();
        chef.setId(2L);
        Command command = Command.builder().id(3L).build();
        Proposition proposition = new Proposition();
        proposition.setClient(client);
        proposition.setChef(chef);
        proposition.setId(4L);
        proposition.setCommand(command);
        when(commandRepository.findById(any())).thenReturn(Optional.of(new Command()));
        when(clientRepository.findById(any())).thenReturn(Optional.of(new Client()));
        when(clientRepository.findByIdAndRole(any(), any())).thenReturn(new Chef());

        // Act
        Proposition resultProposition = propositionService.createProposition(proposition);

        // Assert
        assertNotNull(resultProposition);
        verify(propositionRepository, times(1)).save(proposition);
    }

    @Test
    void testDeleteProposition() {
        // Arrange
        Long propositionId = 1L;
        when(propositionRepository.existsById(propositionId)).thenReturn(true);

        // Act
        boolean result = propositionService.deleteProposition(propositionId);

        // Assert
        assertTrue(result);
        verify(propositionRepository, times(1)).deleteById(propositionId);
    }

    @Test
    void testUpdateProposition() {
        // Arrange
        Long propositionId = 1L;
        PropositionDTO propositionDTO = new PropositionDTO();
        Proposition existingProposition = new Proposition();
        when(propositionRepository.findById(propositionId)).thenReturn(Optional.of(existingProposition));

        // Act
        Proposition resultProposition = propositionService.updateProposition(propositionId, propositionDTO);

        // Assert
        assertNotNull(resultProposition);
        verify(propositionRepository, times(1)).save(existingProposition);
    }

    @Test
    void testGetPropositionsByClientId() {
        // Arrange
        Long clientId = 1L;
        List<Proposition> expectedPropositions = Arrays.asList(new Proposition(), new Proposition());
        when(propositionRepository.findByClient_Id(clientId)).thenReturn(expectedPropositions);

        // Act
        List<Proposition> resultPropositions = propositionService.getPropositionsByClientId(clientId);

        // Assert
        assertNotNull(resultPropositions);
        assertEquals(expectedPropositions, resultPropositions);
        verify(propositionRepository, times(1)).findByClient_Id(clientId);
    }
}
