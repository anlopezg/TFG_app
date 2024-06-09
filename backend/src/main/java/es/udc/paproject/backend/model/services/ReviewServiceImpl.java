package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.daos.ProductDao;
import es.udc.paproject.backend.model.daos.PurchaseItemDao;
import es.udc.paproject.backend.model.daos.ReviewDao;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.PurchaseItem;
import es.udc.paproject.backend.model.entities.Review;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.exceptions.CantReviewTwiceException;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.NotPurchasedProductException;
import es.udc.paproject.backend.model.exceptions.PermissionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService{


    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private PurchaseItemDao purchaseItemDao;

    @Autowired
    private PermissionChecker permissionChecker;


    @Override
    public Review createReview(Long userId, Long productId, int rating, String comment) throws InstanceNotFoundException, CantReviewTwiceException, NotPurchasedProductException {

        User user = permissionChecker.checkUser(userId);
        Product product = permissionChecker.checkActiveProduct(productId);

        // Check if the User has made a Purchase on the Product
        Optional<PurchaseItem> purchaseItem = purchaseItemDao.findByProduct_IdAndPurchase_User_Id(productId, userId);
        if(!purchaseItem.isPresent()){
            throw new NotPurchasedProductException();
        }

        // Check if the product has already been reviewed by this user
        if(reviewDao.existsReviewByUserAndProduct(user, product)){
            throw new CantReviewTwiceException();
        }


        Review review = new Review(user, product, rating, comment, LocalDateTime.now());
        product.getReviews().add(review);

        product.calculateAvgRating();

        productDao.save(product);

        return reviewDao.save(review);
    }

    @Override
    @Transactional(readOnly=true)
    public Block<Review> findProductReviews(Long productId, int page, int size) throws InstanceNotFoundException {

        permissionChecker.checkActiveProduct(productId);

        Slice<Review> slice = reviewDao.findByProductIdOrderByDateDesc(productId, PageRequest.of(page, size));

        return new Block<>(slice.getContent(), slice.hasNext());
    }

    @Override
    @Transactional(readOnly=true)
    public Block<Review> findUserReviews(Long userId, int page, int size) throws InstanceNotFoundException {

        User user = permissionChecker.checkUser(userId);
        Slice<Review> slice = reviewDao.findByUserIdOrderByDateDesc(user.getId(), PageRequest.of(page, size));

        return new Block<>(slice.getContent(), slice.hasNext());

    }

    @Override
    public Review editReview(Long userId, Long reviewId, int newRating, String newComment) throws PermissionException, InstanceNotFoundException {

        Review review = permissionChecker.checkReviewExistsAndBelongsTo(reviewId, userId);

        review.setRating(newRating);
        review.setComment(newComment);
        review.setDate(LocalDateTime.now());

        Review updatedReview = reviewDao.save(review);
        review.getProduct().calculateAvgRating();

        return updatedReview;
    }

    @Override
    public void deleteReview(Long userId, Long reviewId) throws PermissionException, InstanceNotFoundException {

        Review review = permissionChecker.checkReviewExistsAndBelongsTo(reviewId, userId);

        Product product = review.getProduct();

        reviewDao.delete(review);

        product.calculateAvgRating();
    }

}
