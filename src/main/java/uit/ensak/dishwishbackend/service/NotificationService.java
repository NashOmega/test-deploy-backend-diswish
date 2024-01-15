package uit.ensak.dishwishbackend.service;

import org.springframework.stereotype.Service;
import uit.ensak.dishwishbackend.exception.ClientNotFoundException;
import uit.ensak.dishwishbackend.model.Chef;
import uit.ensak.dishwishbackend.model.Client;
import uit.ensak.dishwishbackend.model.Command;
import uit.ensak.dishwishbackend.model.Proposition;
import uit.ensak.dishwishbackend.repository.ChefRepository;
import uit.ensak.dishwishbackend.repository.ClientRepository;
import uit.ensak.dishwishbackend.repository.CommandRepository;
import uit.ensak.dishwishbackend.repository.PropositionRepository;

import java.util.List;

@Service
public class NotificationService implements INotificationService{
    private final CommandRepository commandRepository;
    private final ChefRepository chefRepository;
    private final ClientRepository clientRepository;
    private final PropositionRepository propositionRepository;

    public NotificationService(CommandRepository commandRepository,
                               ChefRepository chefRepository,
                               ClientRepository clientRepository,
                               PropositionRepository propositionRepository) {
        this.commandRepository = commandRepository;
        this.chefRepository = chefRepository;
        this.clientRepository = clientRepository;
        this.propositionRepository = propositionRepository;
    }

    @Override
    public List<Command> getChefNotifications(Long chefId) throws ClientNotFoundException {
        Chef chef = chefRepository.findById(chefId)
                .orElseThrow(() -> new ClientNotFoundException("Chef by Id " + chefId + " could not be found."));

        String chefCity = chef.getAddress().getCity().getName();

        return commandRepository.findByCity(chefCity);
    }

    @Override
    public List<Command> getChefConfirmedNotifications(Long chefId) throws ClientNotFoundException {
        Chef chef = chefRepository.findById(chefId)
                .orElseThrow(() -> new ClientNotFoundException("Chef by Id " + chefId + " could not be found."));

        return commandRepository.findByChefIdAndStatus(chef.getId(), "FINISHED");
    }

    @Override
    public List<Proposition> getClientNotifications(Long clientId) throws ClientNotFoundException {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException("User by Id " + clientId + " could not be found."));

        return propositionRepository.findByClient_Id(client.getId());
    }
}
