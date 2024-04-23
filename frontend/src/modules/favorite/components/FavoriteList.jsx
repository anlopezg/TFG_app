import {useDispatch, useSelector} from "react-redux";
import {useEffect} from "react";

import * as selectors from '../selectors.js';
import * as actions from '../actions.js';
import {FormattedMessage} from "react-intl";
import Products from "../../catalog/components/Products.jsx";

import * as catalogSelectors from '../../catalog/selectors.js';


const FavoriteList = () =>{

    const favoriteList = useSelector(selectors.getFavorites);
    const dispatch = useDispatch();
    const crafts = useSelector(catalogSelectors.getCrafts);
    const categories = useSelector(catalogSelectors.getCategories);


    useEffect(() => {

        dispatch(actions.findFavorites());

        return () => dispatch(actions.clearFavorites());

    }, [dispatch]);


    if(!favoriteList){
        return null;
    }


    return(
        <div>
            <div>
                <h2 className="retro p-2">
                    <i className="fa-solid fa-heart fa-2xs mx-2"></i>
                    <FormattedMessage id="project.products.Favorite.list"/>
                    <i className="fa-solid fa-heart fa-2xs mx-2"></i>
                </h2>
                <h5 className="text-center">
                    <FormattedMessage id="project.products.Favorite.total"/>
                    {favoriteList.length}
                    <FormattedMessage id="project.products.Favorite.product"/>
                </h5>
            </div>
            <div className="mt-4">
                {favoriteList.length === 0 ? (
                    <div className="alert alert-info back-color-blue" role="alert">
                        <FormattedMessage id="project.products.Favorite.list.empty"/>
                    </div>

                ):(
                    <Products crafts={crafts} categories={categories} products={favoriteList}/>
                    )}
            </div>
        </div>

    );

}

export default FavoriteList;

