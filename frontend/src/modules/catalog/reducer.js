import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';

const initialState = {
    categories: null,
    crafts: null,
    productSearch: null,
    product: null,
    userSearch: null
};


/*********************** CRAFTS, CATEGORIES ***********************/
const categories = (state = initialState.categories, action) =>{
    switch (action.type){
        case actionTypes.FIND_ALL_CATEGORIES_COMPLETED:
            return action.categories;
        default:
            return state;
    }
}

const crafts = (state = initialState.crafts, action)=>{
    switch (action.type){
        case actionTypes.FIND_ALL_CRAFTS_COMPLETED:
            return action.crafts;
        default:
            return state;
    }
}

const productSearch = (state = initialState.productSearch, action) => {

    switch (action.type) {

        case actionTypes.FIND_PRODUCTS_COMPLETED:
            return action.productSearch;

        case actionTypes.CLEAR_PRODUCT_SEARCH:
            return initialState.productSearch;

        default:
            return state;

    }

}

const product = (state = initialState.product, action) => {

    switch (action.type) {

        case actionTypes.FIND_PRODUCT_BY_ID_COMPLETED:
            return action.product;

        case actionTypes.CLEAR_PRODUCT:
            return initialState.product;

        default:
            return state;

    }

}

const userSearch = (state = initialState.userSearch, action) => {

    switch (action.type) {

        case actionTypes.FIND_USERS_COMPLETED:
            return action.userSearch;

        case actionTypes.CLEAR_USER_SEARCH:
            return initialState.userSearch;

        default:
            return state;

    }

}

const reducer = combineReducers({
    categories,
    crafts,
    productSearch,
    product,
    userSearch
});

export default reducer;