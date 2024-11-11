package service.sort;

import model.Review;
import service.SortingStrategy;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RateSorting implements SortingStrategy {
    @Override
    public List<Review> sort(List<Review> reviews) {
        return reviews.stream()
                .sorted(Comparator.comparingInt(review -> review.getRating().getValue()))
                .collect(Collectors.toList());
    }
}

