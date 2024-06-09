const getModuleState = state => state.purchases;

export const getShoppingCart = state =>
    getModuleState(state).shoppingCart;

export const getLastPurchaseId = state =>
    getModuleState(state).lastPurchaseId;

export const getPurchaseSearch = state =>
    getModuleState(state).purchaseSearch;

export const getPurchase = state =>
    getModuleState(state).purchase;

export const getPayment = state =>
    getModuleState(state).payment;