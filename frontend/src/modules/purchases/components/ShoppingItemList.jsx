import {useState} from 'react';
import PropTypes from 'prop-types';
import {FormattedMessage, FormattedNumber} from 'react-intl';

import ShoppingItem from './ShoppingItem.jsx';
import {Errors} from '../../common';

const ShoppingItemList = ({list, edit, onUpdateQuantity, onRemoveItem, addReview}) => {

    const [backendErrors, setBackendErrors] = useState(null);

    return (
        <div>
            <Errors errors={backendErrors} onClose={() => setBackendErrors(null)}/>

            {list.items.length === 0 ? (
                <div className="alert alert-info" role="alert">
                    <FormattedMessage id='project.shopping.ShoppingCart.empty'/>
                </div>
            ) : (
                <div>

                    <div className="row">
                        {list.items.map(item => (
                            <ShoppingItem
                                shoppingItemListId={list.id}
                                key={item.productId}
                                item={item}
                                edit={edit}
                                onUpdateQuantity={onUpdateQuantity}
                                onRemoveItem={onRemoveItem}
                                onBackendErrors={setBackendErrors}
                                addReview={addReview}
                            />
                        ))}
                    </div>

                    <div className="card mb-5 shopping-card">
                        <div className="card-body">
                            <h5 className="text-center">
                                <FormattedMessage id='project.shopping.ShoppingCart.totalPrice'/>{': '}
                                <FormattedNumber value={list.totalPrice} style="currency" currency="EUR"/>
                            </h5>
                        </div>
                    </div>
                </div>
                )}
        </div>
    );

}

ShoppingItemList.propTypes = {
    list: PropTypes.object.isRequired,
    edit: PropTypes.bool,
    onUpdateQuantity: PropTypes.func,
    onRemoveItem: PropTypes.func
}

export default ShoppingItemList;