import * as actionTypes from './actionTypes';
import * as selectors from "./selectors.js";
import backend from '../../backend';

/*********************** ADDED PRODUCTS SEARCH ***********************/
export const findAddedPatternsCompleted = patternSearch => ({
    type: actionTypes.FIND_ADDED_PATTERNS_COMPLETED,
    patternSearch
});

export const clearAddedPatternSearch= () => ({
    type: actionTypes.CLEAR_ADDED_PATTERN_SEARCH
});

export const findAddedPatterns = criteria => dispatch =>{

    backend.publicationService.findAddedPatterns(criteria,
            result => dispatch(findAddedPatternsCompleted({criteria, result})))
}

export const previousFindAddedPatternsResultPage = criteria => dispatch => {
    dispatch(clearAddedPatternSearch());
    dispatch(findAddedPatterns({page: criteria.page-1}));
}

export const nextFindAddedPatternsResultPage = criteria => dispatch => {
    dispatch(clearAddedPatternSearch());
    dispatch(findAddedPatterns({page: criteria.page+1}));
}

export const findAddedPhysicalsCompleted = physicalSearch => ({
    type: actionTypes.FIND_ADDED_PHYSICALS_COMPLETED,
    physicalSearch
});

export const clearAddedPhysicalSearch= () => ({
    type: actionTypes.CLEAR_ADDED_PHYSICAL_SEARCH
});


export const findAddedPhysicals = criteria => dispatch =>{

    backend.publicationService.findAddedPhysicals(criteria,
        result => dispatch(findAddedPhysicalsCompleted({criteria, result})))
}

export const previousFindAddedPhysicalsResultPage = criteria => dispatch => {
    dispatch(clearAddedPhysicalSearch());
    dispatch(findAddedPhysicals({page: criteria.page-1}));
}

export const nextFindAddedPhysicalsResultPage = criteria => dispatch => {
    dispatch(clearAddedPhysicalSearch());
    dispatch(findAddedPhysicals({page: criteria.page+1}));
}


/*********************** PRODUCTS CREATION ***********************/
const patternCreated  = pattern =>({
    type: actionTypes.PATTERN_CREATED,
    pattern
});

export const createPattern = (pattern, onSuccess, onErrors) => dispatch =>
    backend.publicationService.createPattern(pattern,
        pattern => {
            dispatch(patternCreated(pattern));
            onSuccess();
        },
        onErrors);

const physicalCreated = (physical) => ({
    type: actionTypes.PHYSICAL_CREATED,
    physical
})

export const createPhysical = (physical, onSuccess, onErrors) => dispatch =>
    backend.publicationService.createPhysical(physical,
        physical => {
        dispatch(physicalCreated(physical));
        onSuccess();
        },
        onErrors);


/*********************** PATTERN DETAILS ***********************/
export const clearPattern = () =>({
    type:actionTypes.CLEAR_PATTERN
});

export const findPatternCompleted = pattern => ({
    type: actionTypes.FIND_PATTERN_COMPLETED,
    pattern
})

export const findPatternById = patternId => dispatch => {
    backend.publicationService.findPatternById(patternId, pattern =>{
        dispatch(findPatternCompleted(pattern));
    });
}

/*********************** PHYSICAL DETAILS ***********************/
export const clearPhysical = () =>({
    type:actionTypes.CLEAR_PHYSICAL
});

export const findPhysicalCompleted = physical => ({
    type: actionTypes.FIND_PHYSICAL_COMPLETED,
    physical
})

export const findPhysicalById = physicalId => dispatch => {
    backend.publicationService.findPhysicalById(physicalId, physical =>{
        dispatch(findPhysicalCompleted(physical));
    });
}


/*********************** EDIT PATTERN ***********************/
export const editPatternCompleted = pattern =>({
    type: actionTypes.EDIT_PATTERN_COMPLETED,
    pattern
});

export const editPattern = (pattern, onSuccess, onErrors) => dispatch =>

    backend.publicationService.editPattern(pattern,
        pattern => {
            dispatch(editPatternCompleted(pattern));
            onSuccess();
        },
        onErrors);

/*********************** EDIT PHYSICAL ***********************/

export const editPhysicalCompleted = physical =>({
    type: actionTypes.EDIT_PHYSICAL_COMPLETED,
    physical
});

export const editPhysical = (physical, onSuccess, onErrors)=> dispatch =>
    backend.publicationService.editPhysical(physical,
        physical => {
            dispatch(editPhysicalCompleted(physical));
            onSuccess();
        },
        onErrors);






const findProductsCompleted = productSearch => ({
    type: actionTypes.FIND_PRODUCTS_COMPLETED,
    productSearch
});


export const clearProductSearch = () => ({
    type: actionTypes.CLEAR_PRODUCT_SEARCH
});

export const findAddedProducts = criteria => dispatch => {
    backend.publicationService.findAddedProducts(criteria,
        result => dispatch(findProductsCompleted({criteria, result})));
}

export const findProductById = id => dispatch => {
    backend.publicationService.findByProductId(id,
        product => dispatch(findProductByIdCompleted(product)));
}

export const clearProduct = () => ({
    type: actionTypes.CLEAR_PRODUCT
});

const findAllCategoriesCompleted = categories => ({
    type: actionTypes.FIND_ALL_CATEGORIES_COMPLETED,
    categories
});

export const findAllCategories = () => (dispatch, getState) => {

    const categories = selectors.getCategories(getState());

    if (!categories) {

        backend.publicationService.findAllCategories(
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
        backend.publicationService.findAllCrafts(
            crafts => dispatch(findAllCraftsCompleted(crafts))
        );
    }
}