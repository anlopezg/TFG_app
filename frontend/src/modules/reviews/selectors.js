const getModuleState = state => state.reviews;

export const getUserReviewSearch = state =>
   getModuleState(state).userReviewSearch;

export const getReview = state =>
    getModuleState(state).review;

