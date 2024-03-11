import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';

const initialState = {
    user: null,
    categories: null,
    crafts: null,
    subcategories: null,
    patternSearch: null,
    physicalSearch: null
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

const subcategories = (state= initialState.subcategories, action)=>{

    switch (action.type){
        case actionTypes.FIND_SUBCATEGORIES_COMPLETED:
            return action.subcategories;

        case actionTypes.CLEAR_SUBCATEGORIES:
            return initialState.subcategories;

        default:
            return state;
    }
}

const reducer = combineReducers({
    categories,
    crafts,
    subcategories,
    patternSearch,
    physicalSearch
});

export default reducer;