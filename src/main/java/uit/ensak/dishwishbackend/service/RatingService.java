package uit.ensak.dishwishbackend.service;

import uit.ensak.dishwishbackend.model.Rating;

import java.util.List;

public class RatingService {
    public static double calculateAverageRating(List<Rating> ratings) {
        if (ratings == null || ratings.isEmpty()) {
            return 0.0;
        }

        double sum = ratings.stream()
                .mapToDouble(Rating::getRating)
                .sum();

        return sum / ratings.size();
    }
}
