import {useEffect} from 'react';
import {useSelector, useDispatch} from 'react-redux';
import {FormattedMessage, FormattedDate, FormattedTime} from 'react-intl';
import {useParams} from 'react-router-dom';

import * as actions from '../actions';
import * as selectors from '../selectors';
import ShoppingItemList from './ShoppingItemList';
import {BackLink} from '../../common';
import PurchaseLink from "./PurchaseLink.jsx";

const PurchaseDetails = () => {

    const {id} = useParams();
    const purchase = useSelector(selectors.getPurchase);
    const dispatch = useDispatch();

    useEffect(() => {

        if (!Number.isNaN(id)) {
            dispatch(actions.findPurchase(id));
        }

        return () => dispatch(actions.clearPurchase());

    }, [id, dispatch]);

    if (!purchase) {
        return null;
    }

    console.log("Purchase: ", purchase);

    return (

        <div>

            <div className="mb-3">
                <BackLink/>
            </div>

            <div className="card shopping-card text-center">
                <div className="card-body">
                    <h5 className="card-title mb-3">
                        <FormattedMessage id='project.shopping.Purchases.order'/>: {purchase.id}
                    </h5>
                    <h6 className="card-subtitle text-muted mb-2">
                        <FormattedDate value={new Date(purchase.date)}/> - <FormattedTime value={new Date(purchase.date)}/>
                    </h6>
                    <p className="card-text mt-3">
                        {purchase.postalAddress}, {purchase.locality} {purchase.postalCode}
                    </p>
                    <p className="card-text">
                        {purchase.region}, {purchase.country}
                    </p>
                </div>
            </div>

            <div className="mt-4">
                <ShoppingItemList list={purchase}/>
            </div>


        </div>

    );

}

export default PurchaseDetails;