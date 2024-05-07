import {useSelector} from 'react-redux';
import {FormattedMessage} from 'react-intl';

import * as selectors from '../selectors';
import PurchaseLink from './PurchaseLink.jsx';

const PurchaseCompleted = () => {

    const purchaseId = useSelector(selectors.getLastPurchaseId);

    if (!purchaseId) {
        return null;
    }

    return (
        <div className="d-flex justify-content-center align-items-center">
            <div className="card success-card">
                <div className="card-header">
                    <FormattedMessage id="project.shopping.Purchases.completed"/>
                </div>
                <div className="card-body">
                    <FormattedMessage id="project.shopping.Purchases.completed.message"/>
                    <PurchaseLink id={purchaseId}/>
                </div>

            </div>
        </div>

    );

}

export default PurchaseCompleted;