import {useSelector} from 'react-redux';
import {Form, Link} from 'react-router-dom';
import {FormattedMessage} from 'react-intl';
import '../../../styles.css';

import {UserFilter} from "../../catalog/index.js";
import users from '../../users';

const Header = () => {
    const userName = useSelector(users.selectors.getUserName);
    const isSeller = useSelector(users.selectors.isSeller);

    return (
        <nav className="navbar navbar-expand-lg navbar-light back-color-blue ps-4">
            <div className="row col-md-4">
                <Link className="navbar-brand logo-link" to="/">Yarn Crafters</Link>
            </div>

            <button className="navbar-toggler" type="button"
                    aria-controls="navbarSupportedContent" aria-expanded="false"
                    aria-label="Toggle navigation" onClick={() => document.getElementById("navbarSupportedContent").classList.toggle("show")}>
                <span className="navbar-toggler-icon"></span>
            </button>

            <div className="collapse navbar-collapse" id="navbarSupportedContent">

                {userName ? (
                    <ul className="navbar-nav ms-auto">
                        {isSeller && (
                            <li className="nav-item dropdown" style={{ marginRight: '20px' }}>
                                <a className="dropdown-toggle nav-link" href="#" role="button"
                                   id="storeDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                                    <span className="fa-solid fa-store"></span>&nbsp;
                                    <FormattedMessage id="project.products.MyStore.heading"/>
                                </a>
                                <ul className="dropdown-menu dropdown-menu-end" aria-labelledby="storeDropdown">
                                    <li><Link className="dropdown-item" to="/publications/products">
                                        <FormattedMessage id="project.products.Product.heading"/>
                                    </Link></li>
                                    <li><Link className="dropdown-item" to="/publications/patterns">
                                        <FormattedMessage id="project.products.Pattern.heading"/>
                                    </Link></li>
                                </ul>
                            </li>
                        )}
                        <li className="nav-item dropdown">
                            <a className="dropdown-toggle nav-link" href="#" role="button"
                               id="userDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                                <span className="fa-solid fa-user"></span>&nbsp;
                                {userName}
                            </a>
                            <ul className="dropdown-menu dropdown-menu-end" aria-labelledby="userDropdown">
                                <li><Link className="dropdown-item" to="/users/view-profile">
                                    <FormattedMessage id="project.users.ViewProfile.title"/>
                                </Link></li>
                                <li><Link className="dropdown-item" to="/users/change-password">
                                    <FormattedMessage id="project.users.ChangePassword.title"/>
                                </Link></li>
                                <li><Link className="dropdown-item" to="/publications/products">
                                    <FormattedMessage id="project.products.MyStore.heading"/>
                                </Link></li>
                                <li><hr className="dropdown-divider"/></li>
                                <li><Link className="dropdown-item" to="/users/logout">
                                    <FormattedMessage id="project.app.Header.logout"/>
                                </Link></li>
                            </ul>
                        </li>
                    </ul>
                ) : (
                    <ul className="navbar-nav ms-auto">
                        <li className="nav-item">
                            <Link className="nav-link" to="/users/login">
                                <FormattedMessage id="project.users.Login.title"/>
                            </Link>
                        </li>
                    </ul>
                )}
            </div>
        </nav>
    );
};

export default Header;
