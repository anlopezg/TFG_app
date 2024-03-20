const getModuleState = state => state.publications;

export const getPatternSearch = state =>
    getModuleState(state).patternSearch;

export const getPhysicalSearch = state =>
    getModuleState(state).physicalSearch;


export const getPattern = state =>
    getModuleState(state).pattern;

export const getPhysical = state =>
    getModuleState(state).physical;



