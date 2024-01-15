package uit.ensak.dishwishbackend.service;

import jakarta.mail.MessagingException;
import uit.ensak.dishwishbackend.model.Complaint;

import java.io.UnsupportedEncodingException;

public interface IComplaintService {
    void sendComplaintEmail(String complaintContent) throws MessagingException, UnsupportedEncodingException;
}
