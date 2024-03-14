import {appFetch, config} from "./appFetch.js";

export const findAllCrafts = (onSuccess) =>
    appFetch('/publications/crafts' , config('GET'), onSuccess);


export const findAllCategories = (onSuccess) =>
    appFetch('/publications/categories' , config('GET'), onSuccess);

export const findSubcategoriesByCategory = (categoryId, onSuccess)=>
    appFetch(`/publications/${categoryId}/subcategories`, config('GET'), onSuccess);



export const createPattern = (pattern, onSuccess, onErrors) =>
    appFetch('/publications/patterns', config('POST', pattern), onSuccess, onErrors);

export const createPhysical = (physical, onSuccess, onErrors) =>
    appFetch('/publications/physicals', config('POST', physical), onSuccess, onErrors);


export const findAddedPatterns = ({page}, onSuccess) =>
    appFetch(`/publications/patterns?page=${page}`, config('GET'), onSuccess);

export const findAddedPhysicals = ({page}, onSuccess) =>
    appFetch(`/publications/physicals?page=${page}`, config('GET'), onSuccess);


export const findPatternById = (patternId, onSuccess) =>
    appFetch(`/publications/patterns/${patternId}`, config('GET'), onSuccess);

export const findPhysicalById = (physicalId, onSuccess) =>
    appFetch(`/publications/physicals/${physicalId}`, config('GET'), onSuccess);