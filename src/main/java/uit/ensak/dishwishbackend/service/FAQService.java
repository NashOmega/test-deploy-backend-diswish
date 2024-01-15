package uit.ensak.dishwishbackend.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uit.ensak.dishwishbackend.exception.ResourceNotFoundException;
import uit.ensak.dishwishbackend.model.FAQ;
import uit.ensak.dishwishbackend.repository.FAQRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class FAQService implements IFAQService {

    private final FAQRepository faqRepository;

    @Override
    public List<FAQ> getAllFAQ() {
        return faqRepository.findAll();
    }

    @Override
    public FAQ create(FAQ faq) {
        return faqRepository.save(faq);
    }

    @Override
    public void deleteById(long id) {
        faqRepository.deleteById(id);
    }

    @Override
    public FAQ update(Long id, FAQ faq) throws ResourceNotFoundException {
        FAQ question = faqRepository.findAllById(id);
        if (question == null){
            throw new ResourceNotFoundException("FAQ with id "+id+" can't be found");
        }
        question.setId(id);
        question.setQuestion(faq.getQuestion());
        question.setResponse(faq.getResponse());
        return faqRepository.save(question);
    }
}
