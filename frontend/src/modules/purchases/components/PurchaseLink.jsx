import {Link} from 'react-router-dom';
import PropTypes from 'prop-types';

const PurchaseLink = ({id}) => {

    return (
        <Link className="link-secondary link-underline link-underline-opacity-50" to={`/shopping/purchase-details/${id}`}>
            {id}
        </Link>
    );

}

PurchaseLink.propTypes = {
    id: PropTypes.number.isRequired
};

export default PurchaseLink;