package uit.ensak.dishwishbackend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import uit.ensak.dishwishbackend.model.City;
import uit.ensak.dishwishbackend.model.Diet;
import uit.ensak.dishwishbackend.service.DietService;
import uit.ensak.dishwishbackend.service.CityService;

@SpringBootApplication
public class DishWishBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DishWishBackendApplication.class, args);
    }

@Bean
CommandLineRunner run(DietService dietService, CityService cityService) {
    return args -> {
        dietService.save(new Diet("Aucun"));
        dietService.save(new Diet("Végétarien"));
        dietService.save(new Diet("Végétalien"));
        dietService.save(new Diet("Flexitarien"));
        dietService.save(new Diet("hypotoxique"));
        dietService.save(new Diet("Sans gluten"));
        dietService.save(new Diet("sans sucre ajouté"));
        dietService.save(new Diet("Sans produits laitiers"));
        dietService.save(new Diet("crudivore"));
        dietService.save(new Diet("Hypocalorique"));
        dietService.save(new Diet("Alcalin"));
        dietService.save(new Diet("Pescétarien"));
        dietService.save(new Diet("Méditerranéen"));
        dietService.save(new Diet("Paléo"));
        dietService.save(new Diet("Cétogène"));
        dietService.save(new Diet("DASH"));
        dietService.save(new Diet("Anti-inflammatoire"));
        dietService.save(new Diet("Atkins"));
        dietService.save(new Diet("Weight Watchers"));
        dietService.save(new Diet("Mangeur intuitif"));
        dietService.save(new Diet("FODMAP"));
        dietService.save(new Diet("TLC (Therapeutic Lifestyle Changes)"));
        dietService.save(new Diet("Okinawa"));
        dietService.save(new Diet("cétogène ciblé"));
        dietService.save(new Diet("Régime low carb"));

        cityService.save(new City("Casablanca"));
        cityService.save(new City("Rabat"));
        cityService.save(new City("Tanger"));
        cityService.save(new City("Kénitra"));
        cityService.save(new City("Fès"));
        cityService.save(new City("Tétouan"));
        cityService.save(new City("Marrakech"));
        cityService.save(new City("Salé"));
        cityService.save(new City("Mohammedia"));
        cityService.save(new City("Mèknes"));
        cityService.save(new City("Settat"));
        cityService.save(new City("Agadir"));
        cityService.save(new City("Oujda"));
        cityService.save(new City("Dakhla"));
        cityService.save(new City("Laâyoune"));
        cityService.save(new City("Nador"));
        cityService.save(new City("Al Hoceima"));
        cityService.save(new City("Essaouira"));
        cityService.save(new City("El Jadida"));
        cityService.save(new City("Taza"));
        // Ajoutez d'autres villes ici
    };
}


}
