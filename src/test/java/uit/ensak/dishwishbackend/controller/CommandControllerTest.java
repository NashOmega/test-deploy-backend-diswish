package uit.ensak.dishwishbackend.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uit.ensak.dishwishbackend.DishWishBackendApplication;
import uit.ensak.dishwishbackend.model.Command;
import uit.ensak.dishwishbackend.service.CommandService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = DishWishBackendApplication.class)
@WebMvcTest(CommandController.class)
@Disabled
class CommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CommandService commandService;

    @InjectMocks
    private CommandController commandController;

    @Test
    void testGetAllCommands() throws Exception {
        // Given
        Command command1 = new Command();
        Command command2 = new Command();
        List<Command> mockCommands = Arrays.asList(command1, command2);

        when(commandService.getAllCommands()).thenReturn(mockCommands);

        // When/Then
        mockMvc.perform(get("/commands")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(command1.getId()))
                .andExpect(jsonPath("$[1].id").value(command2.getId()));
    }

    @Test
    void testGetCommandDetails() throws Exception {
        // Given
        Long commandId = 1L;
        Command mockCommand = new Command();
        mockCommand.setId(commandId);

        when(commandService.getCommandById(commandId)).thenReturn(mockCommand);

        // When/Then
        mockMvc.perform(get("/commands/{commandId}", commandId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(commandId));
    }
}
