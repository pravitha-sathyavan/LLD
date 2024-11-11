import exception.ProductNotExistsException;
import model.Product;
import model.Rating;
import model.ReviewSystem;
import model.SORTTYPE;
import service.ReviewCreationService;
import service.ReviewSystemService;

import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ProductNotExistsException {

        ReviewSystemService reviewSystemService = new ReviewSystemService();
        List<Product> productList = null;
        reviewSystemService.addProducts(productList);
        reviewSystemService.addReview("U1","P1", Rating.FOUR,"good product",null);
        reviewSystemService.addReview("U2","P1", Rating.FIVE,"best product",null);
        reviewSystemService.addReview("U1","P2", Rating.FOUR,"good product",null);
        reviewSystemService.toggleVote("R1","U1");
        reviewSystemService.sortandgetReviews("P1", SORTTYPE.DATE);
    }
}