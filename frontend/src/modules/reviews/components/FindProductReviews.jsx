import {useDispatch, useSelector} from "react-redux";
import {useEffect} from "react";
import * as actions from "../actions.js";
import * as selectors from "../selectors.js";
import {FormattedMessage} from "react-intl";
import {Pager} from "../../common/index.js";
import ProductReviews from "./ProductReviews.jsx";
import ReactStars from "react-stars";


const FindUserReviews = ({productId, avgRating}) => {


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
            <div className="mt-2 mb-1">
                <h2 className="retro">
                    <FormattedMessage id="project.reviews.product.header"/>
                </h2>
            </div>

            <div>
                {productReviewSearch && productReviewSearch.result && productReviewSearch.result.items.length>0 ?(
                    <div>
                        <div className="row justify-content-center">
                            <div className=" col-md-4 p-1 mb-3 align-items-center">

                                <h5 className="card-title italic-message text-muted text-center my-2">
                                    <FormattedMessage id="project.reviews.product.avgrating"/>
                                </h5>
                                <div className="d-flex align-items-center justify-content-center">
                                        <ReactStars
                                            value={avgRating}
                                            count={5}
                                            size={30}
                                            edit={false}
                                            color1={"#D2d2d2"}
                                            color2={"#E6895c"}
                                        />
                                        <p className="mb-0 ms-2">{avgRating}
                                            <FormattedMessage id="project.global.fields.of"/> 5
                                        </p>
                                </div>

                            </div>
                        </div>



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
                    <div>
                        <div className="alert alert-secondary text-center italic-message back-color-lilac-pink" role="alert">
                            <FormattedMessage id='project.reviews.product.NotFound'/>
                        </div>
                    </div>


                )}

            </div>
        </div>

    )
}

export default FindUserReviews;