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

export const markAsFavorite = (productId, onSuccess) => dispatch =>{
    backend.favoriteService.markAsFavorite(productId,
        favorite => dispatch(markAsFavoriteCompleted(favorite)));
}