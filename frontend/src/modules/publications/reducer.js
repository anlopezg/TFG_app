import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';

const initialState = {
    user: null,
    categories: null,
    crafts: null,
    patternSearch: null,
    physicalSearch: null,
    pattern: null,
    physical: null
};


/*********************** ADDED PRODUCTS SEARCH ***********************/
const patternSearch = (state = initialState.patternSearch, action)=>{

    switch (action.type){

        case actionTypes.FIND_ADDED_PATTERNS_COMPLETED:
            return action.patternSearch;

        case actionTypes.CLEAR_ADDED_PATTERN_SEARCH:
            return initialState.patternSearch;

        default:
            return state;
    }
}

const physicalSearch = (state = initialState.physicalSearch, action) => {
    switch (action.type){

        case actionTypes.FIND_ADDED_PHYSICALS_COMPLETED:
                return action.physicalSearch;

        case actionTypes.CLEAR_ADDED_PHYSICAL_SEARCH:
            return initialState.physicalSearch;

        default:
            return state;
    }
}

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

/*********************** PATTERN DETAILS ***********************/
const pattern = (state = initialState.pattern, action) =>{
    switch (action.type){
        case actionTypes.FIND_PATTERN_COMPLETED:
            return action.pattern;

        case actionTypes.EDIT_PATTERN_COMPLETED:
            return action.pattern;

        case actionTypes.CLEAR_PATTERN:
            return initialState.pattern;



        default:
            return state;
    }
}

/*********************** PHYSICAL DETAILS ***********************/
const physical=(state = initialState.physical, action) =>{
    switch (action.type){
        case actionTypes.FIND_PHYSICAL_COMPLETED:
            return action.physical;

        case actionTypes.CLEAR_PHYSICAL:
            return initialState.physical;

        case actionTypes.EDIT_PHYSICAL_COMPLETED:
            return action.physical;

        default:
            return state;
    }
}


const reducer = combineReducers({
    categories,
    crafts,
    patternSearch,
    physicalSearch,
    pattern,
    physical
});

export default reducer;