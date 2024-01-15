package uit.ensak.dishwishbackend.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uit.ensak.dishwishbackend.model.Command;
import uit.ensak.dishwishbackend.repository.CommandRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommandRepositoryTest {

    @Mock
    private CommandRepository commandRepository;

    @Test
    void testFindByClientIdAndStatus() {
        // Arrange
        Long clientId = 1L;
        String status = "IN_PROGRESS";
        List<Command> expectedCommands = Arrays.asList(new Command(), new Command());

        when(commandRepository.findByClientIdAndStatus(clientId, status)).thenReturn(expectedCommands);

        // Act
        List<Command> resultCommands = commandRepository.findByClientIdAndStatus(clientId, status);

        // Assert
        assertNotNull(resultCommands);
        assertEquals(expectedCommands, resultCommands);
        verify(commandRepository, times(1)).findByClientIdAndStatus(clientId, status);
    }

    @Test
    void testFindByChefIdAndStatus() {
        // Arrange
        Long chefId = 1L;
        String status = "IN_PROGRESS";
        List<Command> expectedCommands = Arrays.asList(new Command(), new Command());

        when(commandRepository.findByChefIdAndStatus(chefId, status)).thenReturn(expectedCommands);

        // Act
        List<Command> resultCommands = commandRepository.findByChefIdAndStatus(chefId, status);

        // Assert
        assertNotNull(resultCommands);
        assertEquals(expectedCommands, resultCommands);
        verify(commandRepository, times(1)).findByChefIdAndStatus(chefId, status);
    }

    @Test
    void testFindByChefAddressCityName() {
        // Arrange
        String cityName = "City";
        List<Command> expectedCommands = Arrays.asList(new Command(), new Command());

        when(commandRepository.findByChefAddressCityName(cityName)).thenReturn(expectedCommands);

        // Act
        List<Command> resultCommands = commandRepository.findByChefAddressCityName(cityName);

        // Assert
        assertNotNull(resultCommands);
        assertEquals(expectedCommands, resultCommands);
        verify(commandRepository, times(1)).findByChefAddressCityName(cityName);
    }

    @Test
    void testFindByCity() {
        // Arrange
        String city = "City";
        List<Command> expectedCommands = Arrays.asList(new Command(), new Command());

        when(commandRepository.findByCity(city)).thenReturn(expectedCommands);

        // Act
        List<Command> resultCommands = commandRepository.findByCity(city);

        // Assert
        assertNotNull(resultCommands);
        assertEquals(expectedCommands, resultCommands);
        verify(commandRepository, times(1)).findByCity(city);
    }

    @Test
    void testAssignCommandToChef() {
        // Arrange
        Long commandId = 1L;
        Long chefId = 2L;

        when(commandRepository.assignCommandToChef(commandId, chefId)).thenReturn(1);

        // Act
        int result = commandRepository.assignCommandToChef(commandId, chefId);

        // Assert
        assertEquals(1, result);
        verify(commandRepository, times(1)).assignCommandToChef(commandId, chefId);
    }

    @Test
    void testFindByClient_Id() {
        // Arrange
        Long clientId = 1L;
        List<Command> expectedCommands = Arrays.asList(new Command(), new Command());

        when(commandRepository.findByClient_Id(clientId)).thenReturn(expectedCommands);

        // Act
        List<Command> resultCommands = commandRepository.findByClient_Id(clientId);

        // Assert
        assertNotNull(resultCommands);
        assertEquals(expectedCommands, resultCommands);
        verify(commandRepository, times(1)).findByClient_Id(clientId);
    }
}
