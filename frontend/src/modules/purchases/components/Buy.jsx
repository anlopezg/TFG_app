import {useSelector} from 'react-redux';

import ShoppingItemList from './ShoppingItemList';
import PurchaseForm from './PurchaseForm';
import * as selectors from '../selectors';
import {FormattedMessage} from "react-intl";

const Buy = () => {

    const cart = useSelector(selectors.getShoppingCart);

    if (cart.items.length === 0) {
        return null;
    }

    return (
        <div>
            <div className="row mt-2 align-items-center">
                <div className="col-md-6">
                    <h3 className="retro text-center">
                        <FormattedMessage id="project.shopping.Purchase.review"/>
                    </h3>
                    <ShoppingItemList list={cart}/>
                </div>

                <div className="col-md-6">
                    <PurchaseForm shoppingCartId={cart.id}/>
                </div>
            </div>


        </div>
    );

}

export default Buy;