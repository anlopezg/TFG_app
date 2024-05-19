package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.Review;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.stream.Collectors;

import static es.udc.paproject.backend.rest.dtos.PurchaseConversor.toMillis;


public class ReviewDto {


    public interface AllValidations {}

    public interface UpdateValidations {}

    private Long id;
    private String username;
    private Long productId;
    private int rating;
    private String comment;
    private Long date;

    public ReviewDto() {}

    public ReviewDto(Long id, String username, Long productId, int rating, String comment, Long date) {
        this.id = id;
        this.username = username;
        this.productId = productId;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public final static ReviewDto toReviewDto(Review review){
        return new ReviewDto(review.getId(), review.getUser().getUsername(), review.getProduct().getId(),
                review.getRating(), review.getComment(), toMillis(review.getDate()));
    }

    public final static List<ReviewDto> toReviewDtos(List<Review> reviews){
        return reviews.stream().map(o-> toReviewDto(o)).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @NotNull(groups = {ProductDto.AllValidations.class})
    @Min(value = 0, groups = {AllValidations.class})
    @Max(value = 5, groups = {AllValidations.class})
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @NotNull(groups = {ProductDto.AllValidations.class})
    @Size(min=1, max=200, groups={ProductDto.AllValidations.class})
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
