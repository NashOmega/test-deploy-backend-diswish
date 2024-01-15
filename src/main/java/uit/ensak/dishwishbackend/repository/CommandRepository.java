package uit.ensak.dishwishbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uit.ensak.dishwishbackend.model.Command;
import uit.ensak.dishwishbackend.model.Proposition;

import java.util.List;

public interface CommandRepository extends JpaRepository<Command, Long> {
    List<Command> findByClientIdAndStatus(Long id, String status);
    List<Command> findByChefIdAndStatus(Long id, String status);
    List<Command> findByChefAddressCityName(String chefAddressCityName);
    List<Command> findByCity(String city);
    @Modifying
    @Query("UPDATE Command c SET c.chef.id = :chefId WHERE c.id = :commandId")
    int assignCommandToChef(@Param("commandId") Long commandId, @Param("chefId") Long chefId);

    List<Command> findByClient_Id(Long clientId);

}
