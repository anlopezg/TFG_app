import {combineReducers} from 'redux';

import app from '../modules/app';
import users from '../modules/users';
import publications from '../modules/publications';
import catalog from '../modules/catalog';
import favorite from "../modules/favorite";

const rootReducer = combineReducers({
    app: app.reducer,
    users: users.reducer,
    publications: publications.reducer,
    catalog: catalog.reducer,
    favorite: favorite.reducer
});

export default rootReducer;
