package uit.ensak.dishwishbackend.service;

import org.springframework.web.multipart.MultipartFile;
import uit.ensak.dishwishbackend.dto.ChefDTO;
import uit.ensak.dishwishbackend.exception.ClientNotFoundException;
import uit.ensak.dishwishbackend.model.Chef;
import uit.ensak.dishwishbackend.model.Client;

import java.io.IOException;
import java.util.List;

public interface IClientService {

    Client getClientById(Long id) throws ClientNotFoundException;

    void saveUserVerificationToken(Client client, String token);

    Client saveClient(Client client);

    void revokeAllUserTokens(Client client);

    Client getClientByEmail(String email);

    List<Client> getAllClients();

    Client updateUser(Long id, ChefDTO updateUserDTO, MultipartFile photo) throws IOException, ClientNotFoundException;

    Client addRoleAndTypeToClient(Long id, String roleName) throws ClientNotFoundException;

    Chef becomeCook(Long id, String roleName, MultipartFile idCard, MultipartFile certificate) throws ClientNotFoundException, IOException;

    void deleteUserAccount(Long id) throws ClientNotFoundException;

    boolean verifyImageExtension(MultipartFile image);

    String saveImage(Long id, MultipartFile image, String basePath) throws IOException;
}
