import {appFetch, config} from "./appFetch.js";

export const findAllCrafts = (onSuccess) =>
    appFetch('/catalog/crafts' , config('GET'), onSuccess);


export const findAllCategories = (onSuccess) =>
    appFetch('/catalog/categories' , config('GET'), onSuccess);

export const findSubcategoriesByCategory = (categoryId, onSuccess)=>
    appFetch(`/catalog/${categoryId}/subcategories`, config('GET'), onSuccess);


export const findProducts = ({craftId, subcategoryId, keywords, productType, page}, onSuccess) => {

    let path=`/catalog/products?page=${page}`;

    path += craftId ? `&craftId=${craftId}`: "";
    path += subcategoryId ? `&subcategoryId=${subcategoryId}`: "";
    path += keywords.length > 0 ? `&keywords=${encodeURIComponent(keywords)}`: "";
    path += productType ? `&productType=${productType}`: "";

    appFetch(path, config('GET'), onSuccess);
}

export const findProductById = (id, onSuccess) =>
    appFetch(`/catalog/products/${id}`, config('GET'), onSuccess);

export const findUserProducts = (username,{page}, onSuccess, onErrors) => {
    appFetch(`/catalog/users/${username}/products?page=${page}`, config('GET'), onSuccess, onErrors);
}

export const findUsers = ({username, page}, onSuccess) =>{

    let path = `/catalog/users?page=${page}`;

    path += username ? `&username=${username}` : "";

    appFetch(path, config('GET'), onSuccess);
}