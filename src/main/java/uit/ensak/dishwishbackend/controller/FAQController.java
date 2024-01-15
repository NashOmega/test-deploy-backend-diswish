package uit.ensak.dishwishbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uit.ensak.dishwishbackend.exception.ResourceNotFoundException;
import uit.ensak.dishwishbackend.exception.NullFieldException;
import uit.ensak.dishwishbackend.model.FAQ;
import uit.ensak.dishwishbackend.service.FAQService;

import java.util.List;

@RestController
@RequestMapping("/FAQs")
@RequiredArgsConstructor
public class FAQController {

    private final FAQService faqService;

    @GetMapping
    public ResponseEntity<List<FAQ>> getAllFAQs() throws ResourceNotFoundException {
        List<FAQ> faqList = faqService.getAllFAQ();
        if (faqList.isEmpty()) {
            throw new ResourceNotFoundException("No FAQ found");
        }
        return ResponseEntity.ok().body(faqList);
    }

    @PostMapping
    public ResponseEntity<FAQ> create(@RequestBody FAQ faq) throws NullFieldException {
        if (faq == null || faq.getQuestion() == null || faq.getQuestion().isBlank()) {
            throw new NullFieldException("The question can't be empty");
        }
        FAQ createFAQ = faqService.create(faq);
        return new ResponseEntity<>(createFAQ, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FAQ> update(@PathVariable Long id,
                                      @RequestBody FAQ faq) throws NullFieldException, ResourceNotFoundException {
        if (id == null) {
            throw new NullFieldException("The id can't be empty");
        }
        if (faq == null || faq.getQuestion() == null || faq.getQuestion().isBlank()) {
            throw new NullFieldException("The question can't be empty");
        }
        FAQ updateFAQ = faqService.update(id, faq);
        return new ResponseEntity<>(updateFAQ, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {

        faqService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
