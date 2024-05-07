import {appFetch, config} from "./appFetch.js";

export const getFavorites = (onSuccess)=>
    appFetch('/products/favorites', config('GET'), onSuccess);

export const markAsFavorite = (favorite, onSuccess, onErrors) =>
    appFetch('/products/favorites', config('POST', favorite), onSuccess, onErrors);

export const findFavorite = (productId, onSuccess, onErrors) =>
    appFetch(`/products/favorites/${productId}`, config('GET'), onSuccess, onErrors);
