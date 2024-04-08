import {FormattedMessage} from 'react-intl';
import {useSelector} from "react-redux";

import {ProductFilter, FindAllProducts} from "../../catalog/index.js";
import * as selectors from "../../catalog/selectors.js";


const Home = () => {

    const productSearch = useSelector(selectors.getProductSearch);

    return (
        <div>
            <div className="text-center p-3">
                <h3 className="logo-link">
                    <FormattedMessage id="project.app.Home.welcome"/>
                </h3>
            </div>

            <div>
                <ul className="navbar-nav mr-auto">
                    <li>
                        <ProductFilter/>
                    </li>
                </ul>
            </div>

            <div className="mt-5 mb-2">
                    <FindAllProducts/>
            </div>

        </div>
    )
}

export default Home;
