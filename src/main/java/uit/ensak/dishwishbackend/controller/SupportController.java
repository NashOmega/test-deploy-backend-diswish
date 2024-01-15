package uit.ensak.dishwishbackend.controller;

import jakarta.mail.MessagingException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.ensak.dishwishbackend.model.Complaint;
import uit.ensak.dishwishbackend.service.ComplaintService;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/support")
@RequiredArgsConstructor
public class SupportController {

    private final ComplaintService complaintService;

    @PostMapping("/complaint")
    public ResponseEntity<?> sendComplaint(@RequestBody ComplaintPayload payload) throws MessagingException, UnsupportedEncodingException {
        if (payload == null || payload.getContent() == null || payload.getContent().isBlank()) {
            throw new IllegalArgumentException("Complaint can not be empty");
        }
        complaintService.sendComplaintEmail(payload.getContent());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

@Getter
class ComplaintPayload {
    private String content;
}
