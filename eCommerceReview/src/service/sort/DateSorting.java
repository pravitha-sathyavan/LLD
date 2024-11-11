package service.sort;

import model.Review;
import service.SortingStrategy;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DateSorting implements SortingStrategy {
    @Override
    public List<Review> sort(List<Review> reviews) {
        return reviews.stream()
                .sorted(Comparator.comparing(Review::getDatePosted).reversed())
                .collect(Collectors.toList());
    }

}
