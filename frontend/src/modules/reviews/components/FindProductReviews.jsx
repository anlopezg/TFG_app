import {useDispatch, useSelector} from "react-redux";
import {useEffect} from "react";
import * as actions from "../actions.js";
import * as selectors from "../selectors.js";
import {FormattedMessage} from "react-intl";
import {Pager} from "../../common/index.js";
import ProductReviews from "./ProductReviews.jsx";


const FindUserReviews = ({productId}) => {


    const productReviewSearch = useSelector(selectors.getProductReviewSearch);
    const dispatch = useDispatch();

    useEffect(() => {

        dispatch(actions.findProductReviews(
            {productId: productId,
                    page:0}));

        return () => dispatch(actions.clearUserReviewSearch());
    }, [dispatch]);

    if(!productReviewSearch){
        return null;
    }


    return(
        <div>
            <div className="mt-2 mb-3">
                <h2 className="retro">
                    <FormattedMessage id="project.reviews.product.header"/>
                </h2>
            </div>

            <div>
                {productReviewSearch && productReviewSearch.result && productReviewSearch.result.items.length>0 ?(
                    <div>
                        <ProductReviews productReviews={productReviewSearch.result.items}/>
                        <Pager
                            back={{
                                enabled: productReviewSearch.criteria.page >= 1,
                                onClick: () => dispatch(actions.previousFindProductReviewsResultPage(productReviewSearch.criteria))}}
                            next={{
                                enabled: productReviewSearch.result.existMoreItems,
                                onClick: () => dispatch(actions.nextFindProductReviewsResultPage(productReviewSearch.criteria))}}/>
                    </div>

                ):(
                    <div className="alert alert-info" role="alert">
                        <FormattedMessage id='project.reviews.product.NotFound'/>
                    </div>

                )}

            </div>
        </div>

    )
}

export default FindUserReviews;