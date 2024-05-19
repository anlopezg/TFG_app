package es.udc.paproject.backend.rest.controllers;

import es.udc.paproject.backend.model.entities.Review;
import es.udc.paproject.backend.model.exceptions.*;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.ReviewService;
import es.udc.paproject.backend.rest.common.ErrorsDto;
import es.udc.paproject.backend.rest.dtos.BlockDto;
import es.udc.paproject.backend.rest.dtos.ReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

import static es.udc.paproject.backend.rest.dtos.ReviewDto.toReviewDto;
import static es.udc.paproject.backend.rest.dtos.ReviewDto.toReviewDtos;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final static String CANT_REVIEW_TWICE_EXCEPTION_CODE = "project.exceptions.CantReviewTwiceException";
    private final static String NOT_PURCHASED_PRODUCT_EXCEPTION_CODE = "project.exceptions.NotPurchasedProductException";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ReviewService reviewService;


    @ExceptionHandler(CantReviewTwiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDto handleCantReviewTwiceException(CantReviewTwiceException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(CANT_REVIEW_TWICE_EXCEPTION_CODE,
               null, CANT_REVIEW_TWICE_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);

    }

    @ExceptionHandler(NotPurchasedProductException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDto handleNotPurchasedProductException(NotPurchasedProductException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(NOT_PURCHASED_PRODUCT_EXCEPTION_CODE, null,
                NOT_PURCHASED_PRODUCT_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);

    }

    @PostMapping("/publish")
    public ReviewDto publishReview(@RequestAttribute Long userId,
                                   @Validated @RequestBody ReviewDto reviewDto) throws InstanceNotFoundException, CantReviewTwiceException, NotPurchasedProductException {

        return toReviewDto(reviewService.publishReview(userId, reviewDto.getProductId(),
                reviewDto.getRating(), reviewDto.getComment()));
    }

    @GetMapping("/products/{productId}")
    public BlockDto<ReviewDto> findProductReviews(@PathVariable Long productId,  @RequestParam(defaultValue = "0") int page) throws InstanceNotFoundException {

        Block<Review> reviewBlock = reviewService.findProductReviews(productId, page, 3);

        return new BlockDto<>(toReviewDtos(reviewBlock.getItems()), reviewBlock.getExistMoreItems());

    }

    @GetMapping("/users")
    public BlockDto<ReviewDto> findUserReviews(@RequestAttribute Long userId, @RequestParam(defaultValue = "0") int page) throws InstanceNotFoundException {

        Block<Review> reviewBlock = reviewService.findUserReviews(userId, page, 3);

        return new BlockDto<>(toReviewDtos(reviewBlock.getItems()), reviewBlock.getExistMoreItems());

    }

    @PutMapping("/edit/{reviewId}")
    public ReviewDto editReview(@RequestAttribute Long userId, @PathVariable Long reviewId, @RequestBody ReviewDto reviewDto) throws PermissionException, InstanceNotFoundException {

        return toReviewDto(reviewService.editReview(userId, reviewId, reviewDto.getRating(), reviewDto.getComment()));
    }

    @DeleteMapping("/delete/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@RequestAttribute Long userId, @PathVariable Long reviewId) throws PermissionException, InstanceNotFoundException {

        reviewService.deleteReview(userId, reviewId);
    }
}
