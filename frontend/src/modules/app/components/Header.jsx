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

        <nav className="navbar navbar-expand-lg navbar-light back-color-blue">
            <div className="row col-md-3">
                <Link className="navbar-brand logo-link" to="/" >Yarn Crafters</Link>
            </div>

            <div className="row col-md-6 align-items-center">
                <UserFilter/>
            </div>



            <button className="navbar-toggler" type="button" 
                data-toggle="collapse" data-target="#navbarSupportedContent" 
                aria-controls="navbarSupportedContent" aria-expanded="false" 
                aria-label="Toggle navigation">
                <span className="navbar-toggler-icon"></span>
            </button>



            <div className="collapse navbar-collapse" id="navbarSupportedContent">

                <ul className="navbar-nav mr-auto">
                </ul>
                
                {userName ? 

                <ul className="navbar-nav">

                    {isSeller &&
                        <li className="nav-item dropdown" style={{ marginRight: '20px' }}>
                            <a className="dropdown-toggle nav-link" href="/"
                               data-toggle="dropdown">
                                <span className="fa-solid fa-store"></span>&nbsp;
                                <FormattedMessage id="project.products.MyStore.heading"/>
                            </a>
                            <div className="dropdown-menu dropdown-menu-right back-color-grey">
                                <Link className="dropdown-item" to="/publications/products">
                                    <FormattedMessage id="project.products.Product.heading"/>
                                </Link>

                                <Link className="dropdown-item" to="/publications/patterns">
                                    <FormattedMessage id="project.products.Pattern.heading"/>
                                </Link>
                            </div>

                        </li>
                    }

                    <li className="nav-item dropdown">

                        <a className="dropdown-toggle nav-link" href="/"
                            data-toggle="dropdown">
                            <span className="fa-solid fa-user"></span>&nbsp;
                            {userName}
                        </a>
                        <div className="dropdown-menu dropdown-menu-right back-color-grey">
                            <Link className="dropdown-item" to="/users/view-profile">
                                <FormattedMessage id="project.users.ViewProfile.title"/>
                            </Link>
                            <Link className="dropdown-item" to="/users/change-password">
                                <FormattedMessage id="project.users.ChangePassword.title"/>
                            </Link>

                            <Link className="dropdown-item" to="/publications/products">
                                <FormattedMessage id="project.products.MyStore.heading"/>
                            </Link>

                            <div className="dropdown-divider"></div>
                            <Link className="dropdown-item" to="/users/logout">
                                <FormattedMessage id="project.app.Header.logout"/>
                            </Link>
                        </div>

                    </li>

                </ul>
                
                :

                <ul className="navbar-nav">
                    <li className="nav-item">
                        <Link className="nav-link" to="/users/login">
                            <FormattedMessage id="project.users.Login.title"/>
                        </Link>
                    </li>
                </ul>
                
                }

            </div>
        </nav>

    );

};

export default Header;
