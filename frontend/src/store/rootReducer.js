import {combineReducers} from 'redux';

import app from '../modules/app';
import users from '../modules/users';
import publications from '../modules/publications';

const rootReducer = combineReducers({
    app: app.reducer,
    users: users.reducer,
    publications: publications.reducer
});

export default rootReducer;
