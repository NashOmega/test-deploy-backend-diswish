package uit.ensak.dishwishbackend.service;


import uit.ensak.dishwishbackend.exception.ResourceNotFoundException;
import uit.ensak.dishwishbackend.model.FAQ;

import java.util.List;

public interface IFAQService {

    List<FAQ> getAllFAQ();

    FAQ create(FAQ faq);

    void deleteById(long id);

    FAQ update(Long id, FAQ faq) throws ResourceNotFoundException;
}
