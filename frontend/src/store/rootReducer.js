import {combineReducers} from 'redux';

import app from '../modules/app';
import users from '../modules/users';
import publications from '../modules/publications';
import catalog from '../modules/catalog';
import purchases from '../modules/purchases';
import reviews from '../modules/reviews';
import patterns from '../modules/patterns';

const rootReducer = combineReducers({
    app: app.reducer,
    users: users.reducer,
    publications: publications.reducer,
    catalog: catalog.reducer,
    purchases: purchases.reducer,
    reviews: reviews.reducer,
    patterns: patterns.reducer
});

export default rootReducer;
