import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';

const initialState = {
    patternSearch: null,
    pattern: null,
    purchasedPatterns: []
};

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


/*********************** PATTERN DETAILS ***********************/
const pattern = (state = initialState.pattern, action) =>{
    switch (action.type){

        case actionTypes.PATTERN_CREATED:
            return action.pattern;

        case actionTypes.FIND_PATTERN_COMPLETED:
            return action.pattern;

        case actionTypes.EDIT_PATTERN_COMPLETED:
            return action.pattern;

        case actionTypes.DELETE_PATTERN_COMPLETED:
            return initialState.pattern;

        case actionTypes.CLEAR_PATTERN:
            return initialState.pattern;

        default:
            return state;
    }
}

const purchasedPatterns = (state = initialState.purchasedPatterns, action) => {
    switch (action.type) {
        case actionTypes.GET_PURCHASED_PATTERNS_COMPLETED:
            return action.payload.purchasedPatterns;
        case actionTypes.CLEAR_PURCHASED_PATTERN_SEARCH:
            return initialState.purchasedPatterns;
        default:
            return state;
    }
};


const reducer = combineReducers({
    patternSearch,
    pattern,
    purchasedPatterns
});

export default reducer;

