import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';

const initialState = {
    categories: null,
    crafts: null
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

const reducer = combineReducers({
    categories,
    crafts
});

export default reducer;