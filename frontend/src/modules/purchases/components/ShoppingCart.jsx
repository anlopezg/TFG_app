import {useSelector, useDispatch} from 'react-redux';
import {FormattedMessage} from 'react-intl';
import {useNavigate} from 'react-router-dom';

import ShoppingItemList from './ShoppingItemList';
import * as selectors from '../selectors';
import * as actions from '../actions';

const ShoppingCart = () => {

    const cart = useSelector(selectors.getShoppingCart);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    return (

        <div>
            <div>
                <h2 className="retro">
                    <FormattedMessage id="project.shopping.Cart.title"/>
                </h2>
            </div>

            <ShoppingItemList list={cart} edit
                              onUpdateQuantity={(...args) => dispatch(actions.updateCartItem(...args))}
                              onRemoveItem={(...args) => dispatch(actions.removeCartItem(...args))}/>
            {cart.items.length > 0 &&
                <div className="justify-content-center">
                    <div className="text-center">
                        <button type="button" className="btn button-lilac bigger bold-label"
                                onClick={() => navigate('/shopping/purchase')}>
                            <FormattedMessage id="project.shopping.ShoppingCart.proceed"/>
                            <i className="ms-2 fa-solid fa-bag-shopping"></i>
                        </button>
                    </div>
                </div>
            }
        </div>

    );

}
export default ShoppingCart;