import {appFetch, config} from "./appFetch.js";

export const createPattern = (pattern, onSuccess, onErrors) =>
    appFetch(`/patterns/create`, config('POST', pattern), onSuccess, onErrors);

export const findAddedPatterns = ({page}, onSuccess) =>
    appFetch(`/patterns/uploaded?page=${page}`, config('GET'), onSuccess);

export const findPatternById = (patternId, onSuccess) =>
    appFetch(`/patterns/uploaded/${patternId}`, config('GET'), onSuccess);

export const editPattern = (pattern, onSuccess, onErrors) =>
    appFetch(`/patterns/edit/${pattern.id}`, config('PUT', pattern),
        onSuccess, onErrors);

export const deletePattern = (patternId, onSuccess)=>
    appFetch(`/patterns/delete/${patternId}`, config('DELETE'),
        onSuccess);


export const getPurchasedPatternById = (patternId, onSuccess) =>
    appFetch(`/patterns/purchased/${patternId}`, config('GET'), onSuccess);

export const getPurchasedPatterns = (onSuccess) =>
    appFetch(`/patterns/purchased`, config('GET'), onSuccess);