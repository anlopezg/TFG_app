import {appFetch, config} from "./appFetch.js";

export const createPattern = (pattern, onSuccess, onErrors) =>
    appFetch(`/publications/patterns`, config('POST', pattern), onSuccess, onErrors);

export const createPhysical = (physical, onSuccess, onErrors) =>
    appFetch(`/publications/physicals`, config('POST', physical), onSuccess, onErrors);


export const findAddedPatterns = ({page}, onSuccess) =>
    appFetch(`/publications/patterns?page=${page}`, config('GET'), onSuccess);

export const findAddedPhysicals = ({page}, onSuccess) =>
    appFetch(`/publications/physicals?page=${page}`, config('GET'), onSuccess);


export const findPatternById = (patternId, onSuccess) =>
    appFetch(`/publications/patterns/${patternId}`, config('GET'), onSuccess);

export const findPhysicalById = (physicalId, onSuccess) =>
    appFetch(`/publications/physicals/${physicalId}`, config('GET'), onSuccess);


export const editPattern = (pattern, onSuccess, onErrors) =>
    appFetch(`/publications/patterns/${pattern.id}`, config('PUT', pattern),
        onSuccess, onErrors);

export const editPhysical = (physical, onSuccess, onErrors) =>
    appFetch(`/publications/physicals/${physical.id}`, config('PUT', physical),
        onSuccess, onErrors);


export const deleteProduct = (productId, onSuccess)=>
    appFetch(`/publications/products/${productId}`, config('DELETE'),
        onSuccess);