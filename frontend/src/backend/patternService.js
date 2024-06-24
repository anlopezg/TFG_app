import {appFetch, config} from "./appFetch.js";

export const createPattern = (pattern, onSuccess, onErrors) =>
    appFetch(`/patterns`, config('POST', pattern), onSuccess, onErrors);

export const findAddedPatterns = ({page}, onSuccess) =>
    appFetch(`/patterns?page=${page}`, config('GET'), onSuccess);

export const findPatternById = (patternId, onSuccess) =>
    appFetch(`/patterns/${patternId}`, config('GET'), onSuccess);

export const editPattern = (pattern, onSuccess, onErrors) =>
    appFetch(`/patterns/${pattern.id}`, config('PUT', pattern),
        onSuccess, onErrors);

export const deletePattern = (patternId, onSuccess)=>
    appFetch(`/patterns/${patternId}`, config('DELETE'),
        onSuccess);


export const getPurchasedPatternById = (patternId, onSuccess) =>
    appFetch(`/patterns/purchased/${patternId}`, config('GET'), onSuccess);

export const getPurchasedPatterns = (onSuccess) =>
    appFetch(`/patterns/purchased`, config('GET'), onSuccess);