import {appFetch, config} from "./appFetch.js";

export const publishReview = (review, onSuccess, onErrors) =>
    appFetch('/reviews/publish', config('POST', review), onSuccess, onErrors);


export const findProductReviews = ({productId, page}, onSuccess)=>
    appFetch(`/reviews/products/${productId}?page=${page}`, config('GET'), onSuccess);

export const findUserReviews = ({page}, onSuccess) =>
    appFetch(`/reviews/users?page=${page}`, config('GET'), onSuccess);

export const editReview = ({review}, onSuccess, onErrors) =>
    appFetch(`/reviews/edit/${review.id}`, config('PUT', review), onSuccess, onErrors);

export const deleteReview = (reviewId, onSuccess) =>
    appFetch(`/reviews/delete/${reviewId}`, config('DELETE'), onSuccess);