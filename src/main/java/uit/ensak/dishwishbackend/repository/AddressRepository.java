package uit.ensak.dishwishbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uit.ensak.dishwishbackend.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
