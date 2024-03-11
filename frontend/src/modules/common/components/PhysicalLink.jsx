import PropTypes from 'prop-types';

import {Link} from 'react-router-dom';

const PhysicalLink = ({id, title}) => {

    return (
        <Link to={`/publications/product-details/${id}`}>
            {title}
        </Link>
    );

}

PhysicalLink.propTypes = {
    id: PropTypes.number.isRequired,
    title: PropTypes.string.isRequired,
};

export default PhysicalLink;