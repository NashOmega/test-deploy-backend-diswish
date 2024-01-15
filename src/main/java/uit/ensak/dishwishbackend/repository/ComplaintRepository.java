package uit.ensak.dishwishbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uit.ensak.dishwishbackend.model.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
}
