import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';

const initialState = {
    favoriteList: null,
    favorite: null
}

const favoriteList = (state = initialState.favoriteList, action)=>{
    switch (action.type){

        case actionTypes.FIND_FAVORITES_COMPLETED:
            return action.favoriteList;

        case actionTypes.CLEAR_FAVORITES:
            return initialState.favoriteList;

        default:
            return state;
    }
}

const favorite = (state = initialState.favorite, action) =>{

    switch (action.type) {

        case actionTypes.MARK_AS_FAVORITE_COMPLETED:
            return action.favorite;

        case actionTypes.FIND_FAVORITE_COMPLETED:
            return action.favorite;

        case actionTypes.UNMARK_AS_FAVORITE_COMPLETED:
            return initialState.favorite;

        default:
            return state;
    }
}

const reducer = combineReducers({
    favoriteList,
    favorite
});


export default reducer;