import {appFetch, config} from "./appFetch.js";


export const createPhysical = (physical, onSuccess, onErrors) =>
    appFetch(`/publications/physicals/create`, config('POST', physical), onSuccess, onErrors);

export const findAddedPhysicals = ({page}, onSuccess) =>
    appFetch(`/publications/physicals?page=${page}`, config('GET'), onSuccess);


export const findPhysicalById = (physicalId, onSuccess) =>
    appFetch(`/publications/physicals/${physicalId}`, config('GET'), onSuccess);


export const editPhysical = (physical, onSuccess, onErrors) =>
    appFetch(`/publications/physicals/edit/${physical.id}`, config('PUT', physical),
        onSuccess, onErrors);


export const deleteProduct = (productId, onSuccess)=>
    appFetch(`/publications/physicals/delete/${productId}`, config('DELETE'),
        onSuccess);