import {FormattedDate, FormattedMessage, FormattedTime} from "react-intl";
import PropTypes from "prop-types";
import ReactStars from 'react-stars';

const ProductReviews = ({ productReviews }) => {
    
    return(
        <div className="row justify-content-center">
            {productReviews.map(productReview => (
                <div key={productReview.id} className="col-lg-10 col-md-10">

                    <div className="card shopping-card card-body mb-4">
                        <div className="d-flex flex-column flex-md-row justify-content-between align-items-md-center">
                            <h5 className="font-bold">
                                <FormattedMessage id="project.reviews.review.madeBy"/>
                                {productReview.username}
                            </h5>
                            <small>
                                <FormattedMessage id="project.reviews.review.onDate"/>
                                <FormattedDate value={new Date(productReview.date)}/>
                            </small>
                        </div>
                        <div className="mt-1">
                            <div className="show-star-rating mb-3">
                                <ReactStars value={productReview.rating}
                                            count={5}
                                            edit={false}
                                            size={24}
                                            color1={"#f6f6ee"}
                                            color2={"#E6895C"}/>
                            </div>

                            <p>{productReview.comment}</p>
                            
                        </div>
                    </div>
                </div>
            ))}


        </div>
    )
};

ProductReviews.propTypes ={
    productReviews: PropTypes.array.isRequired
};

export default ProductReviews;