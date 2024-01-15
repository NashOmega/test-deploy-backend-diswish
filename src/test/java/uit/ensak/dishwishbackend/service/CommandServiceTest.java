package uit.ensak.dishwishbackend.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;
import uit.ensak.dishwishbackend.dto.ChefCommandHistoryDTO;
import uit.ensak.dishwishbackend.dto.ClientCommandHistoryDTO;
import uit.ensak.dishwishbackend.dto.CommandDTO;
import uit.ensak.dishwishbackend.exception.CommandNotFoundException;
import uit.ensak.dishwishbackend.model.Chef;
import uit.ensak.dishwishbackend.model.Command;
import uit.ensak.dishwishbackend.repository.CommandRepository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javax.persistence.Query;



@ExtendWith(MockitoExtension.class)
class CommandServiceTest {

    @Mock
    private EntityManager entityManager;
    @Mock
    private CommandRepository commandRepository;

    @InjectMocks
    private CommandService commandService;

    @Test
    void testGetAllCommands_ReturnsListOfCommands() {
        // Given
        Command command1 = new Command();
        Command command2 = new Command();
        List<Command> mockCommands = Arrays.asList(command1, command2);
        when(commandRepository.findAll()).thenReturn(mockCommands);

        // When
        List<Command> result = commandService.getAllCommands();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(command1, result.get(0));
        assertEquals(command2, result.get(1));
    }

    @Test
    void testGetAllCommands_WhenRepositoryFails_ThrowsException() {
        // Given
        when(commandRepository.findAll()).thenThrow(new RuntimeException("Repository error"));

        // When/Then
        assertThrows(RuntimeException.class, () -> commandService.getAllCommands());
    }

    @Test
    void testGetCommandById_ExistingId_ReturnsCommand() throws CommandNotFoundException {
        // Given
        Long commandId = 1L;
        Command mockCommand = new Command();
        when(commandRepository.findById(commandId)).thenReturn(Optional.of(mockCommand));

        // When
        Command result = commandService.getCommandById(commandId);

        // Then
        assertNotNull(result);
        assertEquals(mockCommand, result);
    }

    @Test
    void testGetCommandById_NonExistingId_ThrowsException() {
        // Given
        Long nonExistingCommandId = 100L;
        when(commandRepository.findById(nonExistingCommandId)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(CommandNotFoundException.class, () -> commandService.getCommandById(nonExistingCommandId));
    }


    @Test
    void testCreateCommand_ReturnsCommand() {
        // Given
        Command command = new Command();
        when(commandRepository.save(command)).thenReturn(command);

        // When
        Command result = commandService.createCommand(command);

        // Then
        assertNotNull(result);
        assertEquals(command, result);
    }

    @Test
    void testCreateCommand_CallsSave() {
        // Given
        Command command = new Command();

        // When
        commandService.createCommand(command);

        // Then
        verify(commandRepository, times(1)).save(any());// the save method of
        // commandRepository is called exactly once (times(1)) with any argument (any()).
    }

    @Test
    void commandService_GetClientCommandsHistory_ReturnClientCommandHistoryDTO(){
        Command command1 = mock(Command.class);
        Command command2 = mock(Command.class);
        Command command3 = mock(Command.class);
        Command command4 = mock(Command.class);
        Command command5 = mock(Command.class);

        List<Command> commands1 = new ArrayList<>();
        List<Command> commands2 = new ArrayList<>();
        commands1.add(command1);
        commands1.add(command2);
        commands2.add(command3);
        commands2.add(command4);
        commands2.add(command5);

        when(commandRepository.findByClientIdAndStatus(1L,"IN_PROGRESS")).thenReturn(commands1);
        when(commandRepository.findByClientIdAndStatus(1L,"FINISHED")).thenReturn(commands2);

        ClientCommandHistoryDTO commandHistory = commandService.getClientCommandsHistory(1L);

        Assertions.assertNotNull(commandHistory);
        Assertions.assertEquals(2, commandHistory.getCommandsInProgress().size());
        Assertions.assertEquals(3, commandHistory.getCommandsFinished().size());
    }
    @Test
    void commandService_GetChefCommandsHistory_ReturnChefCommandHistoryDTO(){
        Command command1 = mock(Command.class);
        Command command2 = mock(Command.class);
        Command command3 = mock(Command.class);
        Command command4 = mock(Command.class);
        Command command5 = mock(Command.class);
        Command command6 = mock(Command.class);

        List<Command> commandsInProgressForMe = new ArrayList<>();
        List<Command> commandsFinishedForMe = new ArrayList<>();
        List<Command> commandsInProgressByMe = new ArrayList<>();
        List<Command> commandsFinishedByMe = new ArrayList<>();

        commandsInProgressForMe.add(command1);
        commandsInProgressForMe.add(command2);
        commandsFinishedForMe.add(command3);
        commandsInProgressByMe.add(command4);
        commandsFinishedByMe.add(command5);
        commandsFinishedByMe.add(command6);


        when(commandRepository.findByClientIdAndStatus(1L,"IN_PROGRESS")).thenReturn(commandsInProgressForMe);
        when(commandRepository.findByChefIdAndStatus(1L,"IN_PROGRESS")).thenReturn(commandsInProgressByMe);
        when(commandRepository.findByClientIdAndStatus(1L,"FINISHED")).thenReturn(commandsFinishedForMe);
        when(commandRepository.findByChefIdAndStatus(1L,"FINISHED")).thenReturn(commandsFinishedByMe);

        ChefCommandHistoryDTO commandHistory = commandService.getChefCommandsHistory(1L);

        Assertions.assertNotNull(commandHistory);
        Assertions.assertEquals(2, commandHistory.getCommandsInProgressForMe().size());
        Assertions.assertEquals(1, commandHistory.getCommandsFinishedForMe().size());
        Assertions.assertEquals(1, commandHistory.getCommandsInProgressByMe().size());
        Assertions.assertEquals(2, commandHistory.getCommandsFinishedByMe().size());
    }

    @Test
    void testUpdateCommand_ExistingCommand_ReturnsUpdatedCommand() throws CommandNotFoundException {
        // Given
        Long commandId = 1L;
        CommandDTO commandDTO = new CommandDTO();
        commandDTO.setTitle("UpdatedCommandTitle");
        commandDTO.setDescription("UpdatedCommandDescription");
        commandDTO.setServing("UpdatedServing");
        commandDTO.setAddress("UpdatedAddress");
        commandDTO.setDeadline("UpdatedDeadline");
        commandDTO.setPrice("UpdatedPrice");
        commandDTO.setStatus("UpdatedStatus");

        Chef updatedChef = new Chef();
        updatedChef.setFirstName("UpdatedChefName");
        commandDTO.setChef(updatedChef);

        Command existingCommand = new Command();
        existingCommand.setId(commandId);
        existingCommand.setTitle("OldCommandName");
        existingCommand.setDescription("OldCommandDescription");

        when(commandRepository.findById(commandId)).thenReturn(Optional.of(existingCommand));
        when(commandRepository.save(existingCommand)).thenReturn(existingCommand);

        // When
        Command result = commandService.updateCommand(commandId, commandDTO);

        // Then
        assertNotNull(result);
        assertEquals(commandId, result.getId());
        assertEquals(commandDTO.getTitle(), result.getTitle());
        assertEquals(commandDTO.getDescription(), result.getDescription());
        assertEquals(commandDTO.getServing(), result.getServing());
        assertEquals(commandDTO.getAddress(), result.getAddress());
        assertEquals(commandDTO.getDeadline(), result.getDeadline());
        assertEquals(commandDTO.getPrice(), result.getPrice());
        assertEquals(commandDTO.getStatus(), result.getStatus());
        assertEquals(updatedChef, result.getChef());
    }

    @Test
    void testUpdateCommand_NonExistingCommand_ThrowsException() {
        // Given
        Long commandId = 1L;
        CommandDTO commandDTO = new CommandDTO();

        when(commandRepository.findById(commandId)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(CommandNotFoundException.class, () -> commandService.updateCommand(commandId, commandDTO));
    }

    @Test
    void testDeleteCommand_ExistingCommand_ReturnsTrue() {
        // Given
        Long commandId = 1L;

        when(commandRepository.existsById(commandId)).thenReturn(true);

        // When
        boolean result = commandService.deleteCommand(commandId);

        // Then
        assertTrue(result);
        verify(commandRepository, times(1)).deleteById(commandId);
    }

    @Test
    void testDeleteCommand_NonExistingCommand_ReturnsFalse() {
        // Given
        Long commandId = 1L;

        when(commandRepository.existsById(commandId)).thenReturn(false);

        // When
        boolean result = commandService.deleteCommand(commandId);

        // Then
        assertFalse(result);
        verify(commandRepository, never()).deleteById(commandId);
    }

    /*@Test
    @DirtiesContext
    void testAssignCommandToChef() {
        // Given
        Long commandId = 1L;
        Long chefId = 2L;
        int expectedUpdatedRows = 1;

        Query queryMock = mock(Query.class);
        when(entityManager.createQuery(any(String.class))).thenReturn(queryMock);
        when(queryMock.setParameter(any(String.class), any())).thenReturn(queryMock);
        when(queryMock.executeUpdate()).thenReturn(expectedUpdatedRows);

        // When
        boolean result = commandService.assignCommandToChef(commandId, chefId);

        // Then
        assertEquals(true, result);
        verify(entityManager).createQuery(any(String.class));
        verify(queryMock).setParameter(eq("chefId"), eq(chefId));
        verify(queryMock).setParameter(eq("commandId"), eq(commandId));
        verify(queryMock).executeUpdate();
    }*/

}
