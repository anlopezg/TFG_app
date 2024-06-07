const getModuleState = state => state.patterns;

export const getPatternSearch = state =>
    getModuleState(state).patternSearch;

export const getPattern = state =>
    getModuleState(state).pattern;


export const getPurchasedPatterns = state =>
    getModuleState(state).purchasedPatterns;
