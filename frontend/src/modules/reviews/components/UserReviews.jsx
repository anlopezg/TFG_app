import {FormattedDate, FormattedMessage, FormattedTime} from "react-intl";
import PropTypes from "prop-types";
import ReactStars from 'react-stars';
import {Link} from "react-router-dom";

const UserReviews = ({ userReviews }) => {


    return(
        <div className="row justify-content-center">
            {userReviews.map(userReview => (
                <div key={userReview.id} className="col-lg-6 col-md-6">

                    <div className="card shopping-card card-body mb-4">
                        <div className="d-flex justify-content-between align-items-center">
                            <div className="show-star-rating mb-3">
                                <ReactStars value={userReview.rating}
                                            count={5}
                                            edit={false}
                                            size={24}
                                            color1={"#f6f6ee"}
                                            color2={"#DE7C7C"}/>
                            </div>
                            <small>
                                <FormattedDate value={new Date(userReview.date)}/> - <FormattedTime
                                value={new Date(userReview.date)}/>
                            </small>
                        </div>
                        <div className="mt-1">

                            <p>{userReview.comment}</p>

                            <div className="mt-4 d-flex justify-content-between">

                                <Link to={`/reviews/delete-review/${userReview.id}`} className="btn btn-danger">
                                    <FormattedMessage id="project.global.buttons.delete"/>
                                    <i className="fa-solid fa-trash-can mx-1"></i>
                                </Link>

                                <Link to={`/reviews/edit-review/${userReview.id}`} className="btn btn-primary">
                                    <FormattedMessage id="project.global.buttons.edit"/>
                                    <i className="fa-solid fa-pen mx-1"></i>
                                </Link>
                            </div>
                        </div>
                    </div>
                </div>
            ))}


        </div>
    )
};

UserReviews.propTypes ={
    userReviews: PropTypes.array.isRequired
};

export default UserReviews;