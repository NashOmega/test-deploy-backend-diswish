package uit.ensak.dishwishbackend.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uit.ensak.dishwishbackend.model.Proposition;
import uit.ensak.dishwishbackend.repository.PropositionRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PropositionRepositoryTest {

    @Mock
    private PropositionRepository propositionRepository;

    @Test
    void testFindByClient_Id() {
        Long clientId = 1L;
        List<Proposition> expectedPropositions = Arrays.asList(new Proposition(), new Proposition());

        when(propositionRepository.findByClient_Id(clientId)).thenReturn(expectedPropositions);

        List<Proposition> resultPropositions = propositionRepository.findByClient_Id(clientId);

        assertNotNull(resultPropositions);
        assertEquals(expectedPropositions, resultPropositions);
        verify(propositionRepository, times(1)).findByClient_Id(clientId);
    }
}
