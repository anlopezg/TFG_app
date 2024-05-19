import * as actionTypes from './actionTypes';
import backend from '../../backend';

const findFavoritesCompleted = products => ({
    type: actionTypes.FIND_FAVORITES_COMPLETED,
    favoriteList: products
});

export const clearFavorites = () => ({
    type: actionTypes.CLEAR_FAVORITES
});

export const findFavorites = () => dispatch =>{
    backend.favoriteService.getFavorites(
        products => dispatch(findFavoritesCompleted(products)));
}


const markAsFavoriteCompleted = favorite => ({
    type: actionTypes.MARK_AS_FAVORITE_COMPLETED,
    favorite
})

export const markAsFavorite = (favorite, onSuccess, onErrors) => dispatch =>{
    backend.favoriteService.markAsFavorite(favorite,

        favorite => {dispatch(markAsFavoriteCompleted(favorite));
            onSuccess()
        },
        onErrors);
}

const unmarkAsFavoriteCompleted = productId =>({
    type: actionTypes.UNMARK_AS_FAVORITE_COMPLETED,
    productId
})

export const unmarkAsFavorite = (productId, onSuccess) => dispatch =>{
    backend.favoriteService.unmarkAsFavorite(productId,
        productId => {
            dispatch(unmarkAsFavoriteCompleted(productId));
            onSuccess()
        });
}

const findFavoriteCompleted = favorite => ({
    type: actionTypes.FIND_FAVORITE_COMPLETED,
    favorite
})

export const findFavorite = (productId, onSuccess, onErrors) => dispatch =>{
    backend.favoriteService.findFavorite(productId,
        favorite => {
            dispatch(findFavoriteCompleted(favorite));
            onSuccess();
        },
        onErrors
        );
}