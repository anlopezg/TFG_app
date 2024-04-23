const getModuleState = state => state.favorite;

export const getFavorites = state =>
    getModuleState(state).favoriteList;

export const getFavorite = state =>
    getModuleState(state).favorite;