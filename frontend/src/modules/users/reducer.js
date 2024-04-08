import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';

const initialState = {
    user: null
};

const user = (state = initialState.user, action) => {

    switch (action.type) {

        case actionTypes.SIGN_UP_COMPLETED:
            return action.authenticatedUser.user;

        case actionTypes.LOGIN_COMPLETED:
            return action.authenticatedUser.user;

        case actionTypes.LOGOUT:
            return initialState.user;

        case actionTypes.UPDATE_PROFILE_COMPLETED:
            return action.user;

        case actionTypes.USER_BECOMES_SELLER_COMPLETED:
            return action.user;

        case actionTypes.FIND_USER_COMPLETED:
            return action.user;

        case actionTypes.CLEAR_USER:
            return initialState.user;

        default:
            return state;

    }

}

const reducer = combineReducers({
    user
});

export default reducer;


