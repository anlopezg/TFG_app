const getModuleState = state => state.publications;


export const getPatternSearch = state =>
    getModuleState(state).patternSearch;

export const getPhysicalSearch = state =>
    getModuleState(state).physicalSearch;


export const getCategories = state =>
    getModuleState(state).categories;

export const getCrafts = state =>
    getModuleState(state).crafts;
