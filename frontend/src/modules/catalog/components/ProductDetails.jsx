import {useEffect, useState} from 'react';
import {useSelector, useDispatch} from 'react-redux';
import {FormattedMessage, FormattedNumber} from 'react-intl';
import {Form, Link, useNavigate, useParams} from 'react-router-dom';

import users from '../../users';
import * as selectors from '../selectors';
import * as actions from '../actions';
import * as favoriteActions from '../../favorite/actions.js';
import * as favoriteSelectors from '../../favorite/selectors.js';
import {IsFavorite} from "../../favorite/index.js";

import {BackLink, Errors, UserLink} from '../../common';
import ProductType from "./ProductType.jsx";
import ImagesCarousel from "./ImagesCarousel.jsx";
import Heart from "react-heart";
import {AddItemToCart} from "../../purchases/index.js";
import FavoriteHeart from "../../favorite/components/FavoriteHeart.jsx";
import FindProductReviews from "../../reviews/components/FindProductReviews.jsx";

const ProductDetails = () => {

    const loggedIn = useSelector(users.selectors.isLoggedIn);
    const user = useSelector(users.selectors.getUser);
    const product = useSelector(selectors.getProduct);
    const crafts = useSelector(selectors.getCrafts);
    const categories = useSelector(selectors.getCategories);
    const dispatch = useDispatch();
    const {id} = useParams();
    const navigate = useNavigate();
    const [backendErrors, setBackendErrors] = useState(null);

    // Heart state
    const [active, setActive] = useState(false);
    const favorite = useSelector(favoriteSelectors.getFavorite);

    useEffect(() => {

        const productId = Number(id);

        if (!Number.isNaN(productId)) {
            dispatch(actions.findProductById(productId));
        }

        return () => dispatch(actions.clearProduct());

    }, [id, dispatch]);

    if (!product) {
        return null;
    }

    if(!categories){
        return null;
    }

    if(!crafts){
        return null;
    }

    const craftNameTranslation = (craftName) =>{
        return <FormattedMessage id={`project.catalog.Crafts.${craftName}`}/>
    }

    const categoryNameTranslation = (categoryName) =>{
        return <FormattedMessage id={`project.catalog.Categories.${categoryName}`}/>
    }

    const subcategoryNameTranslation = (subcategoryName) =>{

        return <FormattedMessage id={`project.catalog.Subcategories.${subcategoryName}`}/>
    }

    return (

        <div>

            <BackLink/>
            <Errors errors={backendErrors} onClose={() => setBackendErrors(null)}/>

            <div className="container d-flex align-items-start">
                <div className="row">

                    <div className="col-md-6 mx-auto p-5">

                        <ImagesCarousel images={product.imagesUrl}/>

                        <div className="seller-info d-flex justify-content-between align-items-center px-3 py-2">
                            <div className="align-items-center">
                                <div className="font-weight-bold">
                                    <i className="fa-solid fa-user-tag fa-lg m-1"></i>
                                    {product.username}</div>

                                <div className="mt-2 text-muted">City, Country</div>

                            </div>
                            <div className="align-items-center">

                                <Link to={`/catalog/${product.username}/products`} className="btn button-lilac mt-2">
                                    <FormattedMessage id="project.catalog.Users.visit"/>
                                </Link>

                            </div>
                        </div>

                    </div>

                    <div className="col-md-6 mx-auto p-5">
                        <div className="text-left">

                            <div className="mb-4 d-flex justify-content-between align-items-center">
                                <ProductType productType={product.productType}/>

                                {loggedIn ? (
                                        <div>
                                            <div className="mt-3" style={{ width: "2rem" }}>
                                                <FavoriteHeart productId={product.id}/>
                                            </div>

                                        </div>


                                    ) : null}


                            </div>

                            <h3 className="retro mb-4">{product.title}</h3>
                            <p className="text-muted small text-uppercase">
                                <FormattedMessage id="project.products.Product.description"/>
                            </p>
                            <h6>{product.description}</h6>


                            <p className="text-muted small text-uppercase mt-4">
                                <FormattedMessage id="project.catalog.Category.field"/>
                            </p>
                            <p> {craftNameTranslation(selectors.getCraftName(crafts, product.craftId))} / {categoryNameTranslation(selectors.getCategoryNameBySubcategoryId(categories, product.subcategoryId))} -
                                {subcategoryNameTranslation(selectors.getSubcategoryName(categories, product.subcategoryId))}
                            </p>

                        </div>

                        <br/>
                        <div className="text-right">
                            <h2 className="mb-3">{product.price} €</h2>
                        </div>

                        {loggedIn &&
                            <div>
                                <br/>
                                <AddItemToCart productId={product.id} maxAmount={product.amount}/>

                            </div>
                        }
                    </div>

                    <div className="col-md-12 mt-3">
                        <hr/>
                        <FindProductReviews productId={product.id} avgRating={product.avgRating}/>
                    </div>
                </div>

            </div>




        </div>

    );

}

export default ProductDetails;