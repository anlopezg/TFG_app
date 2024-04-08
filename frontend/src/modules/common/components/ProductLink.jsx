import PropTypes from 'prop-types';

import {Link} from 'react-router-dom';

const ProductLink = ({id, title}) => {

    return (
        <Link className="product-link" to={`/catalog/product-details/${id}`}>
            {title}
        </Link>
    );

}

ProductLink.propTypes = {
    id: PropTypes.number.isRequired,
    title: PropTypes.string.isRequired,
};

export default ProductLink;