import PropTypes from 'prop-types';

import {Link} from 'react-router-dom';

const PatternLink = ({id, title}) => {

    return (
        <Link className="product-link-light" to={`/publications/patterns/${id}`}>
            {title}
        </Link>
    );

}

PatternLink.propTypes = {
    id: PropTypes.number.isRequired,
    title: PropTypes.string.isRequired,
};

export default PatternLink;