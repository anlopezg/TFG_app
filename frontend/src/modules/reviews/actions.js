import * as actionTypes from './actionTypes';
import backend from '../../backend';


export const reviewPublished = review =>({
    type: actionTypes.REVIEW_PUBLISHED,
    review
});

export const publishReview = (review, onSuccess, onErrors) => dispatch =>
    backend.reviewService.publishReview(review,
        review => {
        dispatch(reviewPublished(review));
        onSuccess();
        },
        onErrors);


/*********************** FIND PRODUCT REVIEWS ***********************/
export const clearProductReviewSearch= () => ({
    type: actionTypes.CLEAR_PRODUCT_REVIEW_SEARCH
});

export const findProductReviewCompleted = productReviewSearch => ({
    type: actionTypes.FIND_PRODUCT_REVIEWS_COMPLETED,
    productReviewSearch
});


export const findProductReviews = criteria => dispatch =>{

    backend.reviewService.findProductReviews(criteria,
        result => dispatch(findProductReviewCompleted({criteria, result})))
}

export const previousFindProductReviewsResultPage = criteria => dispatch => {
    dispatch(clearProductReviewSearch());
    dispatch(findProductReviews({page: criteria.page-1}));
}

export const nextFindProductReviewsResultPage = criteria => dispatch => {
    dispatch(clearProductReviewSearch());
    dispatch(findProductReviews({page: criteria.page+1}));
}

/*********************** FIND USER REVIEWS ***********************/
export const findUserReviewCompleted = userReviewSearch => ({
    type: actionTypes.FIND_USER_REVIEWS_COMPLETED,
    userReviewSearch
});


export const clearUserReviewSearch= () => ({
    type: actionTypes.CLEAR_USER_REVIEWS_SEARCH
});

export const findUserReviews = criteria => dispatch =>{

    backend.reviewService.findUserReviews(criteria,
        result => dispatch(findUserReviewCompleted({criteria, result})))
}

export const previousFindUserReviewsResultPage = criteria => dispatch => {
    dispatch(clearUserReviewSearch());
    dispatch(findUserReviews({page: criteria.page-1}));
}

export const nextFindUserReviewsResultPage = criteria => dispatch => {
    dispatch(clearUserReviewSearch());
    dispatch(findUserReviews({page: criteria.page+1}));
}

/*********************** EDIT REVIEW ***********************/
export const editReviewCompleted = review =>({
    type: actionTypes.EDIT_REVIEW_COMPLETED,
    review
});

export const editReview = (review, onSuccess, onErrors) => dispatch =>
    backend.reviewService.editReview(review,
        review =>{
        dispatch(editReviewCompleted(review));
        onSuccess();
        },
        onErrors);


/*********************** DELETE REVIEW ***********************/
export const deleteReviewCompleted = review =>({
    type: actionTypes.DELETE_REVIEW_COMPLETED,
    review
});

export const deleteReview  = (reviewId, onSuccess)=> dispatch =>
    backend.reviewService.deleteReview(reviewId,
        reviewId => {dispatch(deleteReviewCompleted(reviewId));
            onSuccess();
        }
    );

