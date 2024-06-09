package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.Review;
import es.udc.paproject.backend.model.exceptions.CantReviewTwiceException;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.NotPurchasedProductException;
import es.udc.paproject.backend.model.exceptions.PermissionException;

public interface ReviewService {

    /**
     * A user can publish a review on a product that they previously bought
     * @param userId The user's id
     * @param productId The product's id
     * @param rating The review rating
     * @param comment The review comment
     * @return The created review
     * @throws InstanceNotFoundException The user and/or the product don't exist
     * @throws CantReviewTwiceException A user cannot review a Product Twice
     * @throws NotPurchasedProductException A user cannot review a product they have not bought
     */
    Review createReview(Long userId, Long productId, int rating, String comment) throws InstanceNotFoundException, CantReviewTwiceException, NotPurchasedProductException;


    /**
     * Finds all the reviews of a specific product
     *
     * @param productId The product's id
     * @param page Index of the page to return
     * @param size Number of reviews to be included in each page
     * @return All the reviews made by the user paginated
     * @throws InstanceNotFoundException The product doesn't exist
     */
    Block<Review> findProductReviews(Long productId, int page, int size) throws InstanceNotFoundException;

    /**
     * Finds all the reviews of a specific user
     * @param userId The user's id
     * @param page Index of the page to return
     * @param size Number of reviews to be included in each page
     * @return All the reviews made by the user paginated
     * @throws InstanceNotFoundException The user doesn't exist
     */
    Block<Review> findUserReviews(Long userId, int page, int size) throws InstanceNotFoundException;

    /**
     * Edits an existing review by updating its rating and comment.
     *
     * @param userId The owner's user id
     * @param reviewId The existing review's id
     * @param newRating The new rating to be set
     * @param newComment The new comment to be set
     * @return The updated Review
     * @throws PermissionException If the user's not the owner of the review
     * @throws InstanceNotFoundException If no reviews with the given id exists
     */
    Review editReview(Long userId, Long reviewId, int newRating, String newComment) throws PermissionException, InstanceNotFoundException;


    /**
     * Deletes an existing review.
     * @param userId The owner's user id
     * @param reviewId The review's id to be deleted
     * @throws PermissionException If the user's not the owner of the review
     * @throws InstanceNotFoundException If no reviews with the given id exists
     */
    void deleteReview(Long userId, Long reviewId) throws PermissionException, InstanceNotFoundException;
}
