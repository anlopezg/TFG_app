import {useEffect} from 'react';
import {useDispatch} from 'react-redux';

import Header from './Header';
import Body from './Body';
import Footer from './Footer';
import users from '../../users';
import catalog from "../../catalog";

import { Elements } from '@stripe/react-stripe-js';
import { loadStripe } from '@stripe/stripe-js';

const stripePromise = loadStripe('pk_test_51PMuwQAurJ6JByYJsjn74RcLRCS2FjCbRtFJpphu4LRvlD0tJ0ZU4gVhGeLyVuw8wdKAjqKEjf1R7A4ROLm1xEfF00e8fX87eM');


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
                <Elements stripe={stripePromise}>
                    <Body/>
                </Elements>

                <Footer/>
            </div>
    );

}
    
export default App;
