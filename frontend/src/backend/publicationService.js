import {appFetch, config} from "./appFetch.js";

export const createPattern = (username, pattern, onSuccess, onErrors) =>
    appFetch(`/publications/${username}/patterns`, config('POST', pattern), onSuccess, onErrors);

export const createPhysical = (username, physical, onSuccess, onErrors) =>
    appFetch(`/publications/${username}/physicals`, config('POST', physical), onSuccess, onErrors);


export const findAddedPatterns = (username, {page}, onSuccess) =>
    appFetch(`/publications/${username}/patterns?page=${page}`, config('GET'), onSuccess);

export const findAddedPhysicals = (username, {page}, onSuccess) =>
    appFetch(`/publications/${username}/physicals?page=${page}`, config('GET'), onSuccess);


export const findPatternById = (username, patternId, onSuccess) =>
    appFetch(`/publications/${username}/patterns/${patternId}`, config('GET'), onSuccess);

export const findPhysicalById = (username, physicalId, onSuccess) =>
    appFetch(`/publications/${username}/physicals/${physicalId}`, config('GET'), onSuccess);


export const editPattern = (username, pattern, onSuccess, onErrors) =>
    appFetch(`/publications/${username}/patterns/${pattern.id}`, config('PUT', pattern),
        onSuccess, onErrors);

export const editPhysical = (username, physical, onSuccess, onErrors) =>
    appFetch(`/publications/${username}/physicals/${physical.id}`, config('PUT', physical),
        onSuccess, onErrors);


export const deleteProduct = (username, productId, onSuccess)=>
    appFetch(`/publications/${username}/products/${productId}`, config('DELETE'),
        onSuccess);