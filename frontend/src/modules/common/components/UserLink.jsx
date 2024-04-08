import PropTypes from 'prop-types';

import {Link} from 'react-router-dom';
import ProductLink from "./ProductLink.jsx";

const UserLink = ({id, username}) =>{

    return (

        <Link className="user-link text-center" to={`/catalog/${username}/products`}>

            {username}
        </Link>
    );
};

UserLink.propTypes = {
    id: PropTypes.number.isRequired,
    username: PropTypes.string.isRequired,
};

export default UserLink;