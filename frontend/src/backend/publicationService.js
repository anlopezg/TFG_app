import {appFetch, config} from "./appFetch.js";

export const createPattern = (username, pattern, onSuccess, onErrors) =>
    appFetch(`/publications/${username}/patterns`, config('POST', pattern), onSuccess, onErrors);

export const createPhysical = (physical, onSuccess, onErrors) =>
    appFetch('/publications/physicals', config('POST', physical), onSuccess, onErrors);


export const findAddedPatterns = (username, {page}, onSuccess) =>
    appFetch(`/publications/${username}/patterns?page=${page}`, config('GET'), onSuccess);

export const findAddedPhysicals = ({page}, onSuccess) =>
    appFetch(`/publications/physicals?page=${page}`, config('GET'), onSuccess);


export const findPatternById = (username, patternId, onSuccess) =>
    appFetch(`/publications/${username}/patterns/${patternId}`, config('GET'), onSuccess);

export const findPhysicalById = (physicalId, onSuccess) =>
    appFetch(`/publications/physicals/${physicalId}`, config('GET'), onSuccess);


export const editPattern = (username, pattern, onSuccess, onErrors) =>
    appFetch(`/publications/${username}/patterns/${pattern.id}`, config('PUT', pattern),
        onSuccess, onErrors);

export const editPhysical = (physical, onSuccess, onErrors) =>
    appFetch(`/publications/physicals/${physical.id}`, config('PUT', physical),
        onSuccess, onErrors);