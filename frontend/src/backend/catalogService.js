import {appFetch, config} from "./appFetch.js";

export const findAllCrafts = (onSuccess) =>
    appFetch('/catalog/crafts' , config('GET'), onSuccess);


export const findAllCategories = (onSuccess) =>
    appFetch('/catalog/categories' , config('GET'), onSuccess);

export const findSubcategoriesByCategory = (categoryId, onSuccess)=>
    appFetch(`/catalog/${categoryId}/subcategories`, config('GET'), onSuccess);