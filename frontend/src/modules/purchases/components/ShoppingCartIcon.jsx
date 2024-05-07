import {useSelector} from 'react-redux';

import * as selectors from '../selectors';

const ShoppingCartIcon = () => {

    const shoppingCart = useSelector(selectors.getShoppingCart);

    return (
        <div>
            <span className="fa-solid fa-cart-shopping"></span>&nbsp;
            ({shoppingCart.totalQuantity})
        </div>
    );

}

export default ShoppingCartIcon;