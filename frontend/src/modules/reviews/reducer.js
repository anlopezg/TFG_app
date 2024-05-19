import {combineReducers} from 'redux';
import * as actionTypes from './actionTypes';

const initialState = {
    review: null,
    userReviewSearch: null,
    productReviewSearch: null
};

const review = (state = initialState.review, action) => {
    switch (action.type) {

        default:
            return state;
    }
};

const userReviewSearch = (state = initialState.userReviewSearch, action) => {
    switch (action.type) {
        case actionTypes.FIND_USER_REVIEWS_COMPLETED:
            return action.userReviewSearch;

        case actionTypes.CLEAR_USER_REVIEWS_SEARCH:
            return initialState.userReviewSearch;

        default:
            return state;
    }
};

const productReviewSearch = (state = initialState.productReviewSearch, action) => {
    switch (action.type) {
        case actionTypes.FIND_PRODUCT_REVIEWS_COMPLETED:
            return action.productReviewSearch;

        case actionTypes.CLEAR_PRODUCT_REVIEW_SEARCH:
            return initialState.productReviewSearch;

        default:
            return state;
    }
};

const reducer = combineReducers({
    review,
    userReviewSearch,
    productReviewSearch
});

export default reducer;