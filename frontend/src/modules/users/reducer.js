import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';

const initialState = {
    user: null,
    stripeAccount: null,
    userStore:null
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

const userStore = (state = initialState.userStore, action) =>{
    switch (action.type) {

        case actionTypes.FIND_USER_BY_USERNAME_COMPLETED:
            return {...state,
                foundUser: action.payload}

        case actionTypes.CLEAR_FOUND_USER:
            return initialState.userStore

        default:
            return state;
    }
}

const stripeAccount = (state = initialState.stripeAccount, action)=>{
    switch (action.type){

        case actionTypes.GET_STRIPE_ACCOUNT_COMPLETED:
            return action.stripeAccount;

        case actionTypes.GET_STRIPE_ACCOUNT_FAILURE:
            return null;


        case actionTypes.CREATE_STRIPE_ACCOUNT_COMPLETED:
            return action.stripeAccount;

        case actionTypes.CLEAR_STRIPE_ACCOUNT:
            return initialState.stripeAccount;

        default:
            return state;
    }
}

const reducer = combineReducers({
    user,
    stripeAccount,
    userStore
});

export default reducer;


