import {useSelector, useDispatch} from 'react-redux';
import {FormattedMessage} from 'react-intl';

import * as actions from '../actions';
import * as selectors from '../selectors';
import {Pager} from '../../common';
import {useEffect} from "react";
import Purchases from "./Purchases.jsx";
import PurchaseLink from "./PurchaseLink.jsx";
import {Link} from "react-router-dom";

const FindPurchasesResult = () => {

    const purchaseSearch = useSelector(selectors.getPurchaseSearch);
    const dispatch = useDispatch();

    useEffect(() => {

        dispatch(actions.findPurchases({page: 0}));

        return () => dispatch(actions.clearPurchaseSearch());

    }, [dispatch]);

    if (!purchaseSearch) {
        return null;
    }

    if (purchaseSearch.result.items.length === 0) {
        return (
            <div className="alert alert-info" role="alert">
                <FormattedMessage id='project.shopping.Purchases.notFound'/>
            </div>
        );
    }

    return (

        <div>

            <div className="d-flex justify-content-end my-1">
                <Link to={`/reviews/find-user-reviews`} className="btn button-coral">
                    <FormattedMessage id="project.reviews.User.seeReviews"/>
                    <i className="fa-solid fa-arrow-right mx-2"></i>
                </Link>
            </div>


            <div className="mt-2 mb-3">
                <h2 className="retro">
                    <FormattedMessage id="project.shopping.Purchases.header"/>
                </h2>
            </div>

            <Purchases purchases={purchaseSearch.result.items}/>
            <Pager
                back={{
                    enabled: purchaseSearch.criteria.page >= 1,
                    onClick: () => dispatch(actions.previousFindPurchasesResultPage(purchaseSearch.criteria))}}
                next={{
                    enabled: purchaseSearch.result.existMoreItems,
                    onClick: () => dispatch(actions.nextFindPurchasesResultPage(purchaseSearch.criteria))}}/>

        </div>

    );

}

export default FindPurchasesResult;