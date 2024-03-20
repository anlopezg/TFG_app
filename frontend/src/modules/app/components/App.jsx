import {useEffect} from 'react';
import {useDispatch} from 'react-redux';

import Header from './Header';
import Body from './Body';
import Footer from './Footer';
import users from '../../users';
import publications from "../../publications";
import catalog from "../../catalog";

const App = () => {

    const dispatch = useDispatch();

    useEffect(() => {

        dispatch(users.actions.tryLoginFromServiceToken(
            () => dispatch(users.actions.logout())));

        dispatch(catalog.actions.findAllCrafts());
        dispatch(catalog.actions.findAllCategories());
    
    }, [dispatch]);

    return (
        <div>
            <Header/>
            <Body/>
            <Footer/>
        </div>
    );

}
    
export default App;
