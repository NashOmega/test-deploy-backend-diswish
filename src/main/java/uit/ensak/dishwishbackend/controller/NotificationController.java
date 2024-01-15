package uit.ensak.dishwishbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.ensak.dishwishbackend.exception.ClientNotFoundException;
import uit.ensak.dishwishbackend.model.Command;
import uit.ensak.dishwishbackend.model.Proposition;
import uit.ensak.dishwishbackend.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/chef/{chefId}")
    public ResponseEntity<List<Command>> getChefNotifications(@PathVariable Long chefId) throws ClientNotFoundException {
        return ResponseEntity.ok(notificationService.getChefNotifications(chefId));
    }

    @GetMapping("/chef/confirmed/{chefId}")
    public ResponseEntity<List<Command>> getChefConfirmedNotifications(@PathVariable Long chefId) throws ClientNotFoundException {
        return ResponseEntity.ok(notificationService.getChefConfirmedNotifications(chefId));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Proposition>> getClientNotifications(@PathVariable Long clientId) throws ClientNotFoundException {
        return ResponseEntity.ok(notificationService.getClientNotifications(clientId));
    }
}
