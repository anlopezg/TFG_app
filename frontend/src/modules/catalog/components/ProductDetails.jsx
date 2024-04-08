import {useEffect} from 'react';
import {useSelector, useDispatch} from 'react-redux';
import {FormattedMessage, FormattedNumber} from 'react-intl';
import {Form, useParams} from 'react-router-dom';

import users from '../../users';
import * as selectors from '../selectors';
import * as actions from '../actions';

import {BackLink, UserLink} from '../../common';

const ProductDetails = () => {

    const loggedIn = useSelector(users.selectors.isLoggedIn);
    const product = useSelector(selectors.getProduct);
    const crafts = useSelector(selectors.getCrafts);
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

    return (

        <div>

            <BackLink/>

            <div className="container d-flex align-items-start">
                <div className="row">

                    <div className="col-md-6 mx-auto p-5">
                        <img src="/src/crochet_default.jpg" className="img-fluid rounded" alt="Product Image"/>
                    </div>

                    <div className="col-md-6 mx-auto p-5">
                        <div className="text-left">

                            <h3>{product.title}</h3>
                            <p className="text-muted">{product.subcategoryId}</p>

                            <h2 className="mb-3">{product.price} €</h2>

                            <h6>{product.description}</h6>
                        </div>

                        <br/>
                        <div className="d-flex align-items-center">
                            <i className="fa-solid fa-user-tag fa-lg m-1"></i>
                            <UserLink id={product.userId} username={product.username}/>
                        </div>

                        <br/>
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