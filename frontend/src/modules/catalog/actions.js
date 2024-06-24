import * as actionTypes from './actionTypes';
import * as selectors from "./selectors.js";
import backend from '../../backend';

const findAllCategoriesCompleted = categories => ({
    type: actionTypes.FIND_ALL_CATEGORIES_COMPLETED,
    categories
});

export const findAllCategories = () => (dispatch, getState) => {

    const categories = selectors.getCategories(getState());

    if (!categories) {

        backend.catalogService.findAllCategories(
            categories => dispatch(findAllCategoriesCompleted(categories))
        );

    }

}
const findAllCraftsCompleted = crafts =>({
    type: actionTypes.FIND_ALL_CRAFTS_COMPLETED,
    crafts
});

export const findAllCrafts = () =>(dispatch, getState)=>{
    const crafts = selectors.getCrafts(getState());

    if(!crafts){
        backend.catalogService.findAllCrafts(
            crafts => dispatch(findAllCraftsCompleted(crafts))
        );
    }
}


/************************** FIND PRODUCTS ************************/
const findProductsCompleted = productSearch => ({
    type: actionTypes.FIND_PRODUCTS_COMPLETED,
    productSearch
});

export const findProducts = criteria => dispatch =>{

    dispatch(clearProductSearch());
    backend.catalogService.findProducts(criteria,
        result => dispatch(findProductsCompleted({criteria, result})));
}

export const previousFindProductsResultPage = criteria =>
    findProducts({...criteria, page: criteria.page-1});

export const nextFindProductsResultPage = criteria =>
    findProducts({...criteria, page: criteria.page+1});


export const clearProductSearch = () => ({
    type: actionTypes.CLEAR_PRODUCT_SEARCH
});

const findProductByIdCompleted = product => ({
    type: actionTypes.FIND_PRODUCT_BY_ID_COMPLETED,
    product
});

export const findProductById = id => dispatch => {
    backend.catalogService.findProductById(id,
        product => dispatch(findProductByIdCompleted(product)));
}

export const clearProduct = () => ({
    type: actionTypes.CLEAR_PRODUCT
});

export const findUserProducts = (username, criteria, onErrors) => dispatch => {
    backend.catalogService.findUserProducts(username, criteria,
        result =>
            dispatch(findProductsCompleted({criteria, result})),
                onErrors)
        };



const findUsersCompleted = userSearch => ({
    type: actionTypes.FIND_USERS_COMPLETED,
    userSearch
});

export const findUsers = criteria => dispatch =>{

    dispatch(clearUserSearch());
    backend.catalogService.findUsers(criteria,
        result => dispatch(findUsersCompleted({criteria, result})));
}

export const previousFindUsersResultPage = criteria =>
    findUsers({...criteria, page: criteria.page-1});

export const nextFindUsersResultPage = criteria =>
    findUsers({...criteria, page: criteria.page+1});


export const clearUserSearch = () => ({
    type: actionTypes.CLEAR_USER_SEARCH
});




/************************** FAVORITES ************************/

const findFavoritesCompleted = products => ({
    type: actionTypes.FIND_FAVORITES_COMPLETED,
    favoriteList: products
});

export const clearFavorites = () => ({
    type: actionTypes.CLEAR_FAVORITES
});

export const findFavorites = () => dispatch =>{
    backend.catalogService.getFavorites(
        products => dispatch(findFavoritesCompleted(products)));
}


const markAsFavoriteCompleted = favorite => ({
    type: actionTypes.MARK_AS_FAVORITE_COMPLETED,
    favorite
})

export const markAsFavorite = (favorite, onSuccess, onErrors) => dispatch =>{
    backend.catalogService.markAsFavorite(favorite,

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
    backend.catalogService.unmarkAsFavorite(productId,
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
    backend.catalogService.findFavorite(productId,
        favorite => {
            dispatch(findFavoriteCompleted(favorite));
            onSuccess();
        },
        onErrors
    );
}