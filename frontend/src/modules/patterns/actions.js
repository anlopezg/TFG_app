import * as actionTypes from "./actionTypes.js";
import backend from "../../backend/index.js";

export const findAddedPatternsCompleted = patternSearch => ({
    type: actionTypes.FIND_ADDED_PATTERNS_COMPLETED,
    patternSearch
});

export const clearAddedPatternSearch= () => ({
    type: actionTypes.CLEAR_ADDED_PATTERN_SEARCH
});

export const findAddedPatterns = criteria => dispatch =>{

    backend.patternService.findAddedPatterns(criteria,
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

const patternCreated  = pattern =>({
    type: actionTypes.PATTERN_CREATED,
    pattern
});

export const createPattern = (pattern, onSuccess, onErrors) => dispatch =>
    backend.patternService.createPattern(pattern,
        pattern => {
            dispatch(patternCreated(pattern));
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

export const findPatternById = (patternId) => dispatch => {
    backend.patternService.findPatternById(patternId, pattern =>{
        dispatch(findPatternCompleted(pattern));
    });
}


/*********************** EDIT ***********************/
export const editPatternCompleted = pattern =>({
    type: actionTypes.EDIT_PATTERN_COMPLETED,
    pattern
});

export const editPattern = (pattern, onSuccess, onErrors) => dispatch =>

    backend.patternService.editPattern(pattern,
        pattern => {
            dispatch(editPatternCompleted(pattern));
            onSuccess();
        },
        onErrors);

/*********************** DELETE ***********************/
export const deletePatternCompleted = patternId =>({
    type: actionTypes.DELETE_PATTERN_COMPLETED,
    patternId
});

export const deletePattern  = (patternId, onSuccess)=> dispatch =>
    backend.patternService.deletePattern(patternId,
        patternId => {dispatch(deletePatternCompleted(patternId));
            onSuccess();
        }
    );



/*********************** PURCHASED PATTERNS ***********************/

export const getPurchasedPatternsCompleted = patterns => ({
    type: actionTypes.GET_PURCHASED_PATTERNS_COMPLETED,
    payload: { purchasedPatterns: patterns }
});


export const clearPurchasedPatternsSearch= () => ({
    type: actionTypes.CLEAR_PURCHASED_PATTERN_SEARCH
});

export const getPurchasedPatterns = () => dispatch => {
    backend.patternService.getPurchasedPatterns(
        patterns => {
            console.log('Patterns fetched from backend:', patterns);
            dispatch(getPurchasedPatternsCompleted(patterns));
        }
    );
};

export const getPurchasedPatternById = (patternId) => dispatch => {

    backend.patternService.getPurchasedPatternById(patternId, pattern =>{
        dispatch(findPatternCompleted(pattern));
    });
}

