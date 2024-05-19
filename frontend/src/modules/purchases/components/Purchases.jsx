import {FormattedMessage, FormattedDate, FormattedTime} from 'react-intl';
import PropTypes from 'prop-types';

import PurchaseLink from './PurchaseLink';
import {Link} from "react-router-dom";

const Purchases = ({purchases}) => (

    <table className="table  table-hover">

        <thead>
            <tr >
                <th scope="col" className="text-center">
                    <FormattedMessage id='project.shopping.Purchases.order'/>
                </th>
                <th scope="col" className="text-center">
                    <FormattedMessage id='project.global.fields.date'/>
                </th>
                <th scope="col">

                </th>
            </tr>
        </thead>

        <tbody>
        {purchases.map(purchase =>
            <tr key={purchase.id}>
                <td className="text-center"># <PurchaseLink id={purchase.id}/></td>
                <td className="text-center">
                    <FormattedDate value={new Date(purchase.date)}/> - <FormattedTime value={new Date(purchase.date)}/>
                </td>
                <td className="text-center">
                    <Link to={`/shopping/purchase-details/${purchase.id}`} className="btn button-lilac mt-2">
                        <FormattedMessage id="project.global.fields.view-details"/>
                    </Link>
                </td>
            </tr>
        )}
        </tbody>

    </table>

);

Purchases.propTypes = {
    purchases: PropTypes.array.isRequired
};

export default Purchases;