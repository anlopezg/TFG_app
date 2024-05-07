import {useState} from 'react';
import PropTypes from 'prop-types';
import {FormattedMessage, FormattedNumber} from 'react-intl';

import {ProductLink} from '../../common';
import {useSelector} from "react-redux";
import * as selectors from "../../catalog/selectors.js";
import {Form} from "react-router-dom";

const ShoppingItem = ({shoppingItemListId, item, edit, onUpdateQuantity,
    onRemoveItem, onBackendErrors}) => {

    const [quantity, setQuantity] = useState(item.quantity);
    let form;

    const categories = useSelector(selectors.getCategories);

    const handleSubmit = event => {

        event.preventDefault();

        if (form.checkValidity()) {

            onUpdateQuantity(shoppingItemListId,
                item.productId, quantity,
                () => onBackendErrors(null),
                backendErrors => {
                    setQuantity(item.quantity);
                    onBackendErrors(backendErrors);
                });

        } else {

            onBackendErrors(null);
            form.classList.add('was-validated');

        }

    }

    const handleRemoveItem = () => {

        onRemoveItem(shoppingItemListId,
            item.productId,
            () => onBackendErrors(null),
            backendErrors => onBackendErrors(backendErrors));

    }

    return (
        <div className="col-md-12">
            <div className="card shopping-card mb-4">
                <div className="card-body">
                    <div className="row">

                        <div className="col-md-2">
                            <img src={item.mainImageUrl} className="card-img-top rounded" alt={item.productName}/>
                        </div>

                        <div className="col-md-2 d-flex flex-column justify-content-center">
                            <p className="small text-muted mb-2">
                                <FormattedMessage id="project.products.Product.product"/>
                            </p>
                            <p className="lead fw-normal mb-0">
                                <ProductLink id={item.productId} title={item.productName}/>
                            </p>
                        </div>

                        <div className="col-md-2 d-flex flex-column justify-content-center">
                            <p className="small text-muted mb-2">
                                <FormattedMessage id="project.catalog.Category.field"/>
                            </p>
                            <p className="lead fw-normal mb-0">
                                {selectors.getSubcategoryName(categories, item.categoryId)}
                            </p>
                        </div>

                        <div className="col-md-2 d-flex flex-column justify-content-center">
                            <p className="small text-muted mb-2">
                                <FormattedMessage id="project.products.Product.price"/>
                            </p>
                            <p className="lead fw-normal mb-0">
                                <FormattedNumber value={item.productPrice} style="currency" currency="EUR"/>
                            </p>
                        </div>

                        {edit && (
                            <div className="col-md-2 d-flex flex-column justify-content-center">
                                <form ref={node => form = node}
                                      className="needs-validation"
                                      noValidate onSubmit={e => handleSubmit(e)}>

                                    <div className=" d-flex flex-column justify-content-center">
                                        <p className="small text-muted mb-2">
                                            <FormattedMessage id="project.products.Product.amount"/>
                                        </p>
                                        <div className="input-group mb-3">
                                            <button className="btn btn-outline-secondary" type="button"
                                                    onClick={() => setQuantity(Math.max(1, quantity - 1))}>
                                                <i className="fas fa-minus"></i>
                                            </button>
                                            <input type="number" className="form-control text-center"
                                                   value={quantity}
                                                   onChange={e => setQuantity(Number(e.target.value))}
                                                   min="1" />
                                            <button className="btn btn-outline-secondary" type="button"
                                                    onClick={() => setQuantity(quantity + 1)}>
                                                <i className="fas fa-plus"></i>
                                            </button>
                                        </div>
                                        <button className="btn button-lilac mt-2" type="button"
                                                onClick={() => onUpdateQuantity(shoppingItemListId, item.productId, quantity)}>
                                            <FormattedMessage id="project.global.buttons.update"/>
                                        </button>
                                    </div>

                                </form>
                            </div>


                        )}

                        {edit && (
                            <div className="col-md-2 mt-2 d-flex align-items-center justify-content-center">
                                <button type="button" className="btn btn-danger"
                                        onClick={() => handleRemoveItem()}>
                                    <i className="fas fa-trash fa-lg"></i>
                                </button>
                            </div>
                        )}

                        {!edit && (
                            <div className="col-md-2 d-flex flex-column justify-content-center">
                                <p className="small text-muted mb-2">
                                    <FormattedMessage id="project.products.Product.amount"/>
                                </p>
                                <p className="lead fw-normal mb-0">
                                    {item.quantity}
                                </p>
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );


}

ShoppingItem.propTypes = {
    shoppingItemListId: PropTypes.number.isRequired,
    item: PropTypes.object.isRequired,
    edit: PropTypes.bool,
    onUpdateQuantity: PropTypes.func,
    onRemoveItem: PropTypes.func,
    onBackendErrors: PropTypes.func
}

export default ShoppingItem;