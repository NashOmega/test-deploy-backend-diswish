package uit.ensak.dishwishbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uit.ensak.dishwishbackend.model.Proposition;

import java.util.List;


public interface PropositionRepository extends JpaRepository<Proposition, Long> {
    List<Proposition> findByClient_Id(Long clientId);
}
