import PropTypes from "prop-types";
import {useState} from "react";
import {useSelector, useDispatch} from "react-redux";
import {useNavigate} from 'react-router-dom';
import {FormattedMessage} from 'react-intl';

import {Errors} from '../../common';
import * as actions from '../actions';
import * as selectors from '../selectors';



const AddItemToCart = ({productId}) =>{

    const shoppingCart = useSelector(selectors.getShoppingCart);
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [quantity, setQuantity] = useState(1);
    const [backendErrors, setBackendErrors] = useState(null);
    let form;

    const handleSubmit = event =>{

        event.preventDefault();

        if (form.checkValidity()) {

            dispatch(actions.addItemToCart(shoppingCart.id,
                productId, quantity,
                () => navigate('/shopping/cart'),
                errors => setBackendErrors(errors)));

        } else {

            setBackendErrors(null);
            form.classList.add('was-validated');

        }
    }

    return (
        <div>
            <Errors errors={backendErrors} onClose={() => setBackendErrors(null)}/>
            <form ref={node => form = node}
                  className="needs-validation" noValidate
                  onSubmit={e => handleSubmit(e)}>
                <div className="form-group row col-md-4">
                    <label className="small text-muted mb-2 text-uppercase" htmlFor="quantity">
                        <FormattedMessage id="project.products.Product.amount"/>
                    </label>
                    <div className="input-group">
                        <button className="btn" type="button"
                                onClick={() => setQuantity(Math.max(1, quantity - 1))}>
                            <i className="fas fa-minus"></i>
                        </button>
                        <input type="number" id="quantity text-center" className="form-control"
                               value={quantity}
                               onChange={e => setQuantity(Number(e.target.value))}
                               autoFocus
                               min="1" />
                        <button className="btn  button-plus" type="button"
                                onClick={() => setQuantity(quantity + 1)}>
                            <i className="fas fa-plus "></i>
                        </button>
                        <div className="invalid-feedback">
                            <FormattedMessage id='project.global.validator.incorrectQuantity'/>
                        </div>
                    </div>
                </div>
                <div className="form-group row">
                    <div className="text-center mt-5">
                        <button className="btn button-lime text-uppercase">
                            <FormattedMessage id="project.catalog.Product.AddCart"/>
                        </button>
                    </div>
                </div>
            </form>
        </div>
    );

}

AddItemToCart.propTypes = {
    productId: PropTypes.number.isRequired
};

export default AddItemToCart;