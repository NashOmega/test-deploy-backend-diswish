package uit.ensak.dishwishbackend.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uit.ensak.dishwishbackend.model.City;
import uit.ensak.dishwishbackend.repository.CityRepository;

@Service
@AllArgsConstructor
@Transactional
public class CityService {
    private final CityRepository cityRepository;
    public City save(City city){
        return cityRepository.save(city);
    }
}
