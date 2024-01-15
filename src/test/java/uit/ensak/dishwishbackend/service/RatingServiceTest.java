package uit.ensak.dishwishbackend.service;

import org.junit.jupiter.api.Test;
import uit.ensak.dishwishbackend.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RatingServiceTest {

    @Test
    void calculateAverageRating_shouldReturnZeroForEmptyList() {
        List<Rating> emptyList = new ArrayList<>();

        double result = RatingService.calculateAverageRating(emptyList);

        assertEquals(0.0, result);
    }

    @Test
    void calculateAverageRating_shouldReturnCorrectAverage() {
        Client client = Client.builder()
                .id(1L).firstName("meryem").lastName("elhassouni")
                .build();

        Chef chef = new Chef();
        chef.setId(2L);
        chef.setFirstName("johnny");
        chef.setLastName("depp");

        ClientRating rating1 = new ClientRating();
        rating1.setClient(client);
        rating1.setChef(chef);
        rating1.setRating(5.0);

        ClientRating rating2 = new ClientRating();
        rating2.setClient(client);
        rating2.setChef(chef);
        rating2.setRating(3.0);

        List<Rating> ratings = List.of(rating1, rating2);

        double result = RatingService.calculateAverageRating(ratings);

        assertEquals(4, result);
    }

    @Test
    void calculateAverageRating_shouldReturnZeroForNullList() {
        double result = RatingService.calculateAverageRating(null);

        assertEquals(0.0, result);
    }
}

