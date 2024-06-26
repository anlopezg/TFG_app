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

    const userStore = useSelector(userSelectors.getUserStore);


    useEffect(() => {

        dispatch(userActions.findUserByUsername(username));

        dispatch(actions.findUserProducts(username, {page:0},
            errors => setBackendErrors(errors),
            ));

        return ()=>{
            dispatch(userActions.clearFoundUser());
            dispatch(actions.clearProductSearch());
        };



    },[dispatch, username]);

    if (!userStore) {
        return null;
    }

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
                                    <h3 className="text-left retro">{username}</h3>
                                    <p>{userStore.foundUser.firstName}, {userStore.foundUser.bio}</p>
                                    <p>{userStore.foundUser.region}/ {userStore.foundUser.country} </p>
                                </div>
                            </div>

                            <ul className="profile-header-tab nav nav-tabs">
                                <li className="nav-item text-uppercase">
                                    <a className="nav-link_ active disabled">
                                        <FormattedMessage id="project.catalog.UserProducts.All"/>
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