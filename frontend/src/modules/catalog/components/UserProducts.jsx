import {useDispatch, useSelector} from "react-redux";
import {FormattedMessage} from "react-intl";
import Products from "./Products.jsx";
import {Errors, Pager} from "../../common/index.js";
import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";

import * as actions from "../actions.js";
import * as selectors from "../selectors.js";
import * as userActions from "../../users/actions.js";
import * as userSelectors from "../../users/selectors.js";


const UserProducts = () => {

    const productSearch = useSelector(selectors.getProductSearch);
    const crafts = useSelector(selectors.getCrafts);
    const categories = useSelector(selectors.getCategories);
    const dispatch = useDispatch();
    const { username } = useParams();
    const [backendErrors, setBackendErrors] = useState(null);
    const user = useSelector(userSelectors.getUser);

    useEffect(() => {

        dispatch(userActions.findUserByUsername(username,));

        dispatch(actions.findUserProducts(username, {page:0},
            errors => setBackendErrors(errors),
            ));



    },[dispatch]);


    if (!productSearch) {
        return null;
    }


    return (
        <div className="container">
            <Errors errors={backendErrors}
                    onClose={()=> setBackendErrors(null)} />

            <div className="row">
                <div className="col-md-12">
                    <div id="content" className="content content-full-width">

                        <div className="profile">
                            <div className="profile-header">
                                <div className="profile-header-cover"></div>

                            <div className="profile-header-content">
                                <div className="profile-header-img">
                                    <img src="/src/profile_icon_crochet.png" alt="Profile icon"/>
                                </div>

                                <div className="profile-header-info">
                                    <h4 className="text-left">{username}</h4>
                                    <p>{user.bio}</p>
                                    <p>{user.language}/ {user.country} </p>
                                </div>
                            </div>

                            <ul className="profile-header-tab nav nav-tabs">
                                <li className="nav-item text-uppercase">
                                    <a className="nav-link_ active disabled">
                                        <FormattedMessage id="project.catalog.UserProducts.All"/>
                                    </a>
                                </li>
                                <li className="nav-item text-uppercase">
                                    <a className="nav-link_ disabled">
                                        <FormattedMessage id="project.products.Product.heading"/>
                                    </a>
                                </li>
                                <li className="nav-item text-uppercase">
                                    <a className="nav-link_  disabled">
                                        <FormattedMessage id="project.products.Pattern.heading"/>
                                    </a>
                                </li>

                            </ul>

                            </div>
                        </div>


                        <div className="profile-content">

                            {productSearch.result.items.length === 0 ?(
                                <div className="alert alert-info back-color-blue" role="alert">
                                    {username}
                                    <FormattedMessage id='project.catalog.UserProducts.noProductsFound'/>
                                </div>
                            ):(
                                <div>
                                    <Products products={productSearch.result.items} crafts={crafts} categories={categories}/>
                                    <Pager
                                        back={{
                                        enabled: productSearch.criteria.page >= 1,
                                        onClick: () => dispatch(actions.previousFindProductsResultPage(productSearch.criteria))}}
                                        next={{
                                                enabled: productSearch.result.existMoreItems,
                                                onClick: () => dispatch(actions.nextFindProductsResultPage(productSearch.criteria))}}/>

                                </div>
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </div>



    );

}

export default UserProducts;