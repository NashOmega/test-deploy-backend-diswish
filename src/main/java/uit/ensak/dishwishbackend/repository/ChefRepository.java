package uit.ensak.dishwishbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uit.ensak.dishwishbackend.model.Chef;
import uit.ensak.dishwishbackend.model.Role;

import java.util.List;

@Repository
public interface ChefRepository extends JpaRepository<Chef, Long> {
    List<Chef> findByFirstNameContainingOrLastNameContainingOrAddressCityNameContaining(String query1, String query2, String query3);
    Chef findByIdAndRole(Long id, Role role);
}
