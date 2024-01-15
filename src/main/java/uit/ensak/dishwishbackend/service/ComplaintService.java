package uit.ensak.dishwishbackend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uit.ensak.dishwishbackend.model.Client;
import uit.ensak.dishwishbackend.model.Complaint;
import uit.ensak.dishwishbackend.repository.ComplaintRepository;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class ComplaintService implements IComplaintService {

    private final ClientService clientService;
    private final ComplaintRepository complaintRepository;
    private final JavaMailSender mailSender;

    @Value("${mail.support}")
    private String supportMail;

    @Override
    public void sendComplaintEmail(String complaintContent) throws MessagingException, UnsupportedEncodingException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        Client client = clientService.getClientByEmail(currentPrincipalName);

        String firstName = client.getFirstName();
        String subject = "Complaint - DishWish";
        if (firstName != null && !firstName.isBlank()) {
            subject = client.getFirstName() + " complaint - DishWish";
        }

        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);

        if (firstName != null && !firstName.isBlank()) {
            messageHelper.setFrom(currentPrincipalName, firstName);
        }
        else {
            messageHelper.setFrom(currentPrincipalName);
        }

        String content = "<p><b>Utilisateur : </b>" + currentPrincipalName + "</p>\n" +
                "<p><b>Message : </b>" + complaintContent + " </p>";

        messageHelper.setTo(supportMail);
        messageHelper.setSubject(subject);
        messageHelper.setText(content, true);
        mailSender.send(message);

        saveComplaint(complaintContent, client);
    }

    private void saveComplaint(String complaintContent, Client client) {
        Complaint complaint = Complaint.builder()
                .content(complaintContent)
                .client(client)
                .build();
        complaintRepository.save(complaint);
    }
}
