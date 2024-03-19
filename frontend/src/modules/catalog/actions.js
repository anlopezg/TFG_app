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