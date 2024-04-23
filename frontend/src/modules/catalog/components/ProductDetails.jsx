import {useEffect} from 'react';
import {useSelector, useDispatch} from 'react-redux';
import {FormattedMessage, FormattedNumber} from 'react-intl';
import {Form, Link, useNavigate, useParams} from 'react-router-dom';

import users from '../../users';
import * as selectors from '../selectors';
import * as actions from '../actions';
import * as favoriteActions from '../../favorite/actions.js';

import {BackLink, UserLink} from '../../common';
import ProductType from "./ProductType.jsx";
import {Format} from "@cloudinary/url-gen/qualifiers/format";
import ImagesCarousel from "./ImagesCarousel.jsx";

const ProductDetails = () => {

    const loggedIn = useSelector(users.selectors.isLoggedIn);
    const user = useSelector(users.selectors.getUser);
    const product = useSelector(selectors.getProduct);
    const crafts = useSelector(selectors.getCrafts);
    const categories = useSelector(selectors.getCategories);
    const dispatch = useDispatch();
    const {id} = useParams();
    const navigate = useNavigate();

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

    const handleFavoriteClick = () => {
        if (loggedIn) {
            dispatch(favoriteActions.markAsFavorite({productId: toNumber(id)}));
        } else {
            navigate('/users/login');
        }
    };

    const toNumber = value => value.length > 0 ? Number(value) : null;


    return (

        <div>

            <BackLink/>

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
                                <div>
                                    <span className="mr-2 mt-2">Total Rating</span>
                                </div>
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

                                <button type="button" className="btn btn-secondary heart-button mt-3" onClick={handleFavoriteClick}>
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" className="bi bi-heart heart-icon" viewBox="0 0 16 16">
                                        <path d="m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143q.09.083.176.171a3 3 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15"/>
                                    </svg>
                                </button>
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

                        <div className="text-center mt-5">
                            <button className="btn button-lime text-uppercase">
                                <FormattedMessage id="project.catalog.Product.AddCart"/>
                            </button>
                        </div>
                    </div>
                </div>

            </div>


            {loggedIn &&
                <div>
                    <br/>

                </div>
            }

        </div>

    );

}

export default ProductDetails;