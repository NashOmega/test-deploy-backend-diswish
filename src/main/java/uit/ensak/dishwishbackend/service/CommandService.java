package uit.ensak.dishwishbackend.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.ensak.dishwishbackend.dto.ChefCommandHistoryDTO;
import uit.ensak.dishwishbackend.dto.ClientCommandHistoryDTO;
import uit.ensak.dishwishbackend.dto.CommandDTO;
import uit.ensak.dishwishbackend.exception.CommandNotFoundException;
import uit.ensak.dishwishbackend.model.Command;
import uit.ensak.dishwishbackend.repository.ClientRepository;
import uit.ensak.dishwishbackend.repository.CommandRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CommandService implements ICommandService {

    private final CommandRepository commandRepository;
    private final ClientRepository clientRepository;

    public List<Command> getAllCommands() {
        return commandRepository.findAll();
    }

    public Command getCommandById(Long id) throws CommandNotFoundException {
        return commandRepository.findById(id).orElseThrow(() -> new CommandNotFoundException("Command by Id " + id + " could not be found."));
    }

    public Command createCommand(Command command) {
        return commandRepository.save(command);
    }

    public Command updateCommand(Long commandId, CommandDTO commandDTO) throws CommandNotFoundException {
        Command existingCommand = commandRepository.findById(commandId)
                .orElseThrow(() -> new CommandNotFoundException("Command by Id " + commandId + " could not be found."));

        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setPropertyCondition(context -> context.getSource() != null);

        modelMapper.map(commandDTO, existingCommand);
        return commandRepository.save(existingCommand);
    }

    public boolean deleteCommand(Long id) {
        if (commandRepository.existsById(id)) {
            commandRepository.deleteById(id);
            return true;
        }
        return false;
    }


    @Transactional
    public boolean assignCommandToChef(Long commandId, Long chefId) {
        int updatedRows = commandRepository.assignCommandToChef(commandId, chefId);
        return updatedRows > 0;
    }

    public List<Command> getCommandsByClientId(Long clientId) {
        return commandRepository.findByClient_Id(clientId);
    }

    public ClientCommandHistoryDTO getClientCommandsHistory(Long clientId) {
        List<Command> commandsInProgress = commandRepository.findByClientIdAndStatus(clientId, "IN_PROGRESS");
        List<Command> commandsFinished = commandRepository.findByClientIdAndStatus(clientId, "FINISHED");

        return new ClientCommandHistoryDTO(commandsInProgress, commandsFinished);
    }

    public ChefCommandHistoryDTO getChefCommandsHistory(Long chefId) {
        List<Command> commandsInProgressForMe = commandRepository.findByClientIdAndStatus(chefId, "IN_PROGRESS");
        List<Command> commandsFinishedForMe = commandRepository.findByClientIdAndStatus(chefId, "FINISHED");
        List<Command> commandsInProgressByMe = commandRepository.findByChefIdAndStatus(chefId, "IN_PROGRESS");
        List<Command> commandsFinishedByMe = commandRepository.findByChefIdAndStatus(chefId, "FINISHED");

        return new ChefCommandHistoryDTO(commandsInProgressForMe, commandsFinishedForMe, commandsInProgressByMe, commandsFinishedByMe);
    }
}
