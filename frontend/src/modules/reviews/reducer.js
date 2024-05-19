import {combineReducers} from 'redux';
import * as actionTypes from './actionTypes';

const initialState = {
    review: null,
    userReviewSearch: null
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

const reducer = combineReducers({
    review,
    userReviewSearch
});

export default reducer;