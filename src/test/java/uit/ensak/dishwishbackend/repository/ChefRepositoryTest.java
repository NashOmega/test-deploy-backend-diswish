package uit.ensak.dishwishbackend.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uit.ensak.dishwishbackend.model.Address;
import uit.ensak.dishwishbackend.model.Chef;
import uit.ensak.dishwishbackend.model.City;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ChefRepositoryTest {
    private final ChefRepository chefRepository;
    private final AddressRepository addressRepository;
    private final CityRepository cityRepository;

    @Autowired
    public  ChefRepositoryTest (ChefRepository chefRepository, AddressRepository addressRepository, CityRepository cityRepository) {
        this.chefRepository = chefRepository;
        this.addressRepository = addressRepository;
        this.cityRepository = cityRepository;
    }
    @Test
    public void ChefRepository_FindByFirstNameContaining_ReturnListOfChef(){
        Chef chef1 = new Chef();
        Chef chef2 = new Chef();
        Chef chef3 = new Chef();
        City city = City.builder().name("Fes").build();
        Address address = Address.builder().id(1L).city(city).build();
        chef1.setId(1L);
        chef2.setId(2L);
        chef3.setId(3L);
        chef1.setFirstName("Fesa");
        chef2.setFirstName("Fesa");
        chef3.setAddress(address);

        cityRepository.save(city);
        addressRepository.save(address);

        chefRepository.save(chef1);
        chefRepository.save(chef2);
        chefRepository.save(chef3);

        List<Chef> returnChefs = chefRepository.findByFirstNameContainingOrLastNameContainingOrAddressCityNameContaining("Fes", "Fes","Fes");
        System.out.println(returnChefs);

        Assertions.assertNotNull(returnChefs);
        Assertions.assertEquals(3, returnChefs.size());
    }
}
