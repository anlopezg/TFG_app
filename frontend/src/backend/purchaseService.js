import {config, appFetch} from './appFetch';

export const addItemToCart = (shoppingCartId, productId, quantity, onSuccess, onErrors) =>
    appFetch(`/shopping/carts/${shoppingCartId}/addItem`,
        config('POST', {productId, quantity}), onSuccess, onErrors);


export const updateCartItem = (shoppingCartId, productId, quantity, onSuccess, onErrors) =>
    appFetch(`/shopping/carts/${shoppingCartId}/updateItem`,
        config('POST', {productId, quantity}), onSuccess, onErrors);

export const removeCartItem = (shoppingCartId, productId, onSuccess, onErrors) =>
    appFetch(`/shopping/carts/${shoppingCartId}/removeItem`,
        config('POST', {productId}), onSuccess, onErrors);

export const purchaseCart = (shoppingCartId, postalAddress, locality, region, country, postalCode, onSuccess, onErrors) =>
    appFetch(`/shopping/carts/${shoppingCartId}/purchase`,
        config('POST', {postalAddress, locality, region, country, postalCode}), onSuccess, onErrors);

export const findPurchase = (purchaseId, onSuccess) =>
    appFetch(`/shopping/purchases/${purchaseId}`, config('GET'), onSuccess);

export const findPurchases = ({page}, onSuccess) =>
    appFetch(`/shopping/purchases?page=${page}`, config('GET'), onSuccess);