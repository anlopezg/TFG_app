import {appFetch, config} from "./appFetch.js";

export const getFavorites = (onSuccess)=>
    appFetch('/products/favorites', config('GET'), onSuccess);

export const markAsFavorite = (productId, onSuccess) =>
    appFetch(`/products/favorites`, config('POST', productId), onSuccess);