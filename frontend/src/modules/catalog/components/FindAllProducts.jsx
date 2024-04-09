import {useDispatch, useSelector} from "react-redux";
import {useEffect} from "react";
import catalog from "../index.js";
import {useNavigate} from "react-router-dom";
import * as selectors from "../selectors.js";
import {FormattedMessage} from "react-intl";
import Products from "./Products.jsx";
import {Pager} from "../../common/index.js";
import * as actions from "../actions.js";

const FindAllProducts =()=>{

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const productSearch = useSelector(selectors.getProductSearch);
    const crafts = useSelector(selectors.getCrafts);
    const categories = useSelector(selectors.getCategories);

    useEffect(() => {

        dispatch(actions.findProducts({craftId: null, subcategoryId: null, keywords: '', productType: null, page: 0 }));

    }, [dispatch]);

    if (!productSearch) {
        return null;
    }

    if (productSearch.result.items.length === 0) {
        return (
            <div className="alert alert-danger" role="alert">
                <FormattedMessage id='project.catalog.FindAllProductsResult.noProductsFound'/>
            </div>
        );
    }

    return (

        <div>
            <Products products={productSearch.result.items} crafts={crafts} categories={categories}/>
            <Pager
                back={{
                    enabled: productSearch.criteria.page >= 1,
                    onClick: () => dispatch(actions.previousFindProductsResultPage(productSearch.criteria))}}
                next={{
                    enabled: productSearch.result.existMoreItems,
                    onClick: () => dispatch(actions.nextFindProductsResultPage(productSearch.criteria))}}/>
        </div>

    );


}

export default FindAllProducts;