package uit.ensak.dishwishbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uit.ensak.dishwishbackend.model.FAQ;

public interface FAQRepository extends JpaRepository<FAQ, Long> {
    FAQ findAllById(Long id);
}
