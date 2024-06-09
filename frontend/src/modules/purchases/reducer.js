import {combineReducers} from 'redux';

import users from '../users';
import * as actionTypes from './actionTypes';

const initialState = {
    shoppingCart: null,
    lastPurchaseId: null,
    purchaseSearch: null,
    purchase: null,
    payment: null
};

const shoppingCart = (state = initialState.shoppingCart, action) => {

    switch (action.type) {

        case users.actionTypes.LOGIN_COMPLETED:
            return action.authenticatedUser.shoppingCart || initialState.shoppingCart;

        case users.actionTypes.SIGN_UP_COMPLETED:
            return action.authenticatedUser.shoppingCart || initialState.shoppingCart;

        case actionTypes.SHOPPING_CART_UPDATED:
            return action.shoppingCart;

        case actionTypes.PURCHASE_COMPLETED:
            return {id: state.id, items: [], totalPrice: 0, totalQuantity: 0};

        default:
            return state;

    }

}

const lastPurchaseId = (state = initialState.lastPurchaseId, action) => {

    switch (action.type) {

        case actionTypes.PURCHASE_COMPLETED:
            return action.purchaseId;

        default:
            return state;

    }

}

const purchaseSearch = (state = initialState.purchaseSearch, action) => {

    switch (action.type) {

        case actionTypes.FIND_PURCHASES_COMPLETED:
            return action.purchaseSearch;

        case actionTypes.CLEAR_PURCHASE_SEARCH:
            return initialState.purchaseSearch;

        default:
            return state;

    }

}

const purchase = (state = initialState.purchase, action) => {

    switch (action.type) {

        case actionTypes.FIND_PURCHASE_COMPLETED:
            return action.purchase;

        case actionTypes.CLEAR_PURCHASE:
            return initialState.purchase;

        default:
            return state;

    }

}

const payment = (state = initialState.payment, action) => {

    switch (action.type) {
        case actionTypes.PAYMENT_CREATED:
            return action.payment;
        default:
            return state;
    }
}

const reducer = combineReducers({
    shoppingCart,
    lastPurchaseId,
    purchaseSearch,
    purchase,
    payment
});

export default reducer;