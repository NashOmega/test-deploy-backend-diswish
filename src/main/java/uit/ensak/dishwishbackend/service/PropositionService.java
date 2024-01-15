package uit.ensak.dishwishbackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import uit.ensak.dishwishbackend.dto.CommandDTO;
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

import java.util.List;

import static uit.ensak.dishwishbackend.model.Role.CHEF;

@Service
public class PropositionService {

    private final PropositionRepository propositionRepository;
    private final CommandRepository commandRepository;
    private final ClientRepository clientRepository;
    private final ChefRepository chefRepository;

    public PropositionService(
            PropositionRepository propositionRepository,
            CommandRepository commandRepository,
            ClientRepository clientRepository,
            ChefRepository chefRepository
    ) {
        this.propositionRepository = propositionRepository;
        this.commandRepository = commandRepository;
        this.clientRepository = clientRepository;
        this.chefRepository = chefRepository;
    }

    public List<Proposition> getAllPropositions() {
        return propositionRepository.findAll();
    }

    public Proposition createProposition(Proposition proposition) throws CommandNotFoundException, ClientNotFoundException {
        Long commandId = proposition.getCommand().getId();
        Long clientId = proposition.getClient().getId();
        Long chefId = proposition.getChef().getId();

        Command command = commandRepository.findById(commandId)
                .orElseThrow(() -> new CommandNotFoundException("Command not found with ID: " + commandId));
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with ID: " + clientId));
        Client chef = clientRepository.findByIdAndRole(chefId, CHEF);

        if (command != null && client != null && chef != null) {
            proposition.setCommand(command);
            proposition.setClient(client);


            return propositionRepository.save(proposition);
        } else {
            // Handle case when one or more entities are not found
            return null;
        }
    }

    public boolean deleteProposition(Long id) {
        if (propositionRepository.existsById(id)) {
            propositionRepository.deleteById(id);
            return true;
        }
        return false;
    }


    public Proposition updateProposition(Long propositionId, PropositionDTO propositionDTO) {
        Proposition existingProposition = propositionRepository.findById(propositionId)
                .orElseThrow(() -> new EntityNotFoundException("Proposition not found with id: " + propositionId));

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setPropertyCondition(context -> context.getSource() != null);

        modelMapper.map(propositionDTO, existingProposition);
        return propositionRepository.save(existingProposition);
    }

    public List<Proposition> getPropositionsByClientId(Long clientId) {
        return propositionRepository.findByClient_Id(clientId);
    }

}
