import {useEffect} from 'react';
import {useSelector, useDispatch} from 'react-redux';
import {FormattedMessage, FormattedNumber} from 'react-intl';
import {Form, useParams} from 'react-router-dom';

import users from '../../users';
import * as selectors from '../selectors';
import * as actions from '../actions';

import {BackLink, UserLink} from '../../common';
import ProductType from "./ProductType.jsx";
import {Format} from "@cloudinary/url-gen/qualifiers/format";

const ProductDetails = () => {

    const loggedIn = useSelector(users.selectors.isLoggedIn);
    const product = useSelector(selectors.getProduct);
    const crafts = useSelector(selectors.getCrafts);
    const categories = useSelector(selectors.getCategories);
    const dispatch = useDispatch();
    const {id} = useParams();

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

            <div className="container d-flex align-items-start">
                <div className="row">

                    <div className="col-md-6 mx-auto p-5">
                        <img src="/src/crochet_default.jpg" className="img-fluid rounded" alt="Product Image"/>

                        <div className="d-flex align-items-center mt-3">
                            <i className="fa-solid fa-user-tag fa-lg m-1"></i>
                            <UserLink id={product.userId} username={product.username}/>
                        </div>

                        <div className="text-center mt-4">
                            <ProductType productType={product.productType}/>
                        </div>


                    </div>

                    <div className="col-md-6 mx-auto p-5">
                        <div className="text-left">

                            <div className="mb-4">
                                <ProductType productType={product.productType}/>
                            </div>

                            <h3>{product.title}</h3>
                            <p className="text-muted small text-uppercase">
                                <FormattedMessage id="project.products.Product.description"/>
                            </p>
                            <h6>{product.description}</h6>

                            <p className="text-muted small text-uppercase">
                                <FormattedMessage id="project.products.Product.details"/>
                            </p>
                            <p>{product.details}</p>
                            <br/>

                            <p className="text-muted"> {craftNameTranslation(selectors.getCraftName(crafts, product.craftId))} </p>
                            <p className="text-muted">{categoryNameTranslation(selectors.getCategoryNameBySubcategoryId(categories, product.subcategoryId))} -
                                {subcategoryNameTranslation(selectors.getSubcategoryName(categories, product.subcategoryId))}</p>
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