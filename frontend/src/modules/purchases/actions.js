import backend from '../../backend';
import * as actionTypes from './actionTypes';

const shoppingCartUpdated = shoppingCart => ({
    type: actionTypes.SHOPPING_CART_UPDATED,
    shoppingCart
});

export const addItemToCart = (shoppingCartId, productId, quantity,
                                  onSuccess, onErrors) => dispatch =>
    backend.purchaseService.addItemToCart(shoppingCartId, productId,
        quantity, shoppingCart => {
            dispatch(shoppingCartUpdated(shoppingCart));
            onSuccess();
        },
        onErrors);

export const updateCartItem = (shoppingCartId, productId,
                                               quantity, onSuccess, onErrors) => dispatch =>
    backend.purchaseService.updateCartItem(shoppingCartId,
        productId, quantity, shoppingCart => {
            dispatch(shoppingCartUpdated(shoppingCart));
            onSuccess();
        },
        onErrors);

export const removeCartItem = (shoppingCartId, productId, onSuccess,
                                       onErrors) => dispatch =>
    backend.purchaseService.removeCartItem(shoppingCartId,
        productId, shoppingCart => {
            dispatch(shoppingCartUpdated(shoppingCart));
            onSuccess();
        },
        onErrors);


const purchaseCompleted = (purchaseId) => ({
    type: actionTypes.PURCHASE_COMPLETED,
    purchaseId
});


export const purchase = (shoppingCartId, postalAddress,locality, region, country, postalCode,  onSuccess,
                    onErrors) => dispatch =>
    backend.purchaseService.purchaseCart(shoppingCartId, postalAddress, locality, region, country, postalCode,
            purchase => {

            dispatch(purchaseCompleted(purchase.id));
            onSuccess(purchase);
        },
        onErrors);

/******************************************* PAYMENT ************************************************/
const paymentCompleted = (payment) => ({
    type: actionTypes.PAYMENT_CREATED,
    payment
})

export const processPaymentForPurchase = (purchaseId, paymentMethodId, onSuccess, onErrors) => dispatch =>
    backend.purchaseService.processPaymentForPurchase(purchaseId, paymentMethodId,
        payment => {
            console.log("Received purchaseId:", purchaseId);
            dispatch(paymentCompleted(payment));
            onSuccess();
        },
        onErrors);



/******************************************* FIND PURCHASES ************************************************/

const findPurchasesCompleted = purchaseSearch => ({
    type: actionTypes.FIND_PURCHASES_COMPLETED,
    purchaseSearch
});

export const clearPurchaseSearch = () => ({
    type: actionTypes.CLEAR_PURCHASE_SEARCH
});

export const findPurchases = criteria => dispatch => {

    backend.purchaseService.findPurchases(criteria,
        result => dispatch(findPurchasesCompleted({criteria, result})));

}

export const previousFindPurchasesResultPage = criteria => dispatch => {
    dispatch(clearPurchaseSearch());
    dispatch(findPurchases({page: criteria.page-1}));
}

export const nextFindPurchasesResultPage = criteria => dispatch => {
    dispatch(clearPurchaseSearch());
    dispatch(findPurchases({page: criteria.page+1}));
}

const findPurchaseCompleted = purchase => ({
    type: actionTypes.FIND_PURCHASE_COMPLETED,
    purchase
});

export const clearPurchase = () => ({
    type: actionTypes.CLEAR_PURCHASE
});

export const findPurchase = purchaseId => dispatch => {
    backend.purchaseService.findPurchase(purchaseId, purchase => {
        dispatch(findPurchaseCompleted(purchase));
    });
}



