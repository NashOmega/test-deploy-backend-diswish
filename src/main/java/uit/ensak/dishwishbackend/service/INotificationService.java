package uit.ensak.dishwishbackend.service;

import uit.ensak.dishwishbackend.exception.ClientNotFoundException;
import uit.ensak.dishwishbackend.model.Command;
import uit.ensak.dishwishbackend.model.Proposition;

import java.util.List;

public interface INotificationService {
    List<Command> getChefNotifications(Long chefId) throws ClientNotFoundException;

    List<Command> getChefConfirmedNotifications(Long chefId) throws ClientNotFoundException;

    List<Proposition> getClientNotifications(Long clientId) throws ClientNotFoundException;
}
