import {FormattedMessage} from 'react-intl';

import {FindAllProducts, FilterTab} from "../../catalog/index.js";



const Home = () => {

    return (
        <div>
            <div className="text-center p-3">
                <h3 className="logo-link">
                    <FormattedMessage id="project.app.Home.welcome"/>
                </h3>
            </div>
            <div>
                <FilterTab/>
            </div>

            <div className="mt-5 mb-2">
                    <FindAllProducts/>
            </div>

        </div>
    )
}

export default Home;
