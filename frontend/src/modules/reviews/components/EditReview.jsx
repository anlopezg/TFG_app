import React, { useState, useEffect } from 'react';
import {useParams, useNavigate, useLocation} from 'react-router-dom';
import ReactStars from 'react-stars';
import * as actions from '../actions.js';
import {useDispatch} from "react-redux";
import Errors from "../../common/components/Errors.jsx";
import {FormattedMessage} from "react-intl";
import PropTypes from "prop-types";

const EditReview = () => {

    const location = useLocation();
    const review = location.state?.userReview;

    const navigate = useNavigate();
    const dispatch = useDispatch();
    const [backendErrors, setBackendErrors] = useState("");
    let form;

    const [rating, setRating] = useState(review.rating);
    const [comment, setComment] = useState(review.comment);

    console.log("Review", review);
    const handleSubmit = event => {
        event.preventDefault();

        if(form.checkValidity()) {
            dispatch(actions.editReview({
                id: review.id,
                    rating: rating,
                    comment: comment

                }, () => navigate('/reviews/find-user-reviews'),
                errors => setBackendErrors(errors)
            ));
        } else{
            setBackendErrors(null);
            form.classList.add('was-validated');
        }


    };

    return (
        <div className="row justify-content-center m-3">
            <Errors errors={backendErrors} onClose={() => setBackendErrors(null)}/>

            <div className="card shopping-card col-md-6">
                <h2 className="mt-3 retro card-title">
                    <FormattedMessage id="project.reviews.edit.title"/>
                </h2>

                <div className="card-body">
                    <form ref={node => form = node}
                          className="needs-validation" noValidate
                          onSubmit={(e)=> handleSubmit(e)}>

                        <div className="form-group row my-2">
                            <label htmlFor="rating" className="col-md-12 col-form-label text-muted">
                                <FormattedMessage id="project.reviews.edit.rating"/>
                            </label>

                            <div className="col-md-12">
                                <ReactStars
                                    value={rating}
                                    count={5}
                                    half={false}
                                    onChange={newRating => setRating(newRating)}
                                    size={24}
                                    color1={"#f6f6ee"}
                                    color2={"#DE7C7C"}
                                />
                            </div>
                        </div>

                        <div className="form-group row my-2">
                            <label htmlFor="comment" className="col-md-12 col-form-label text-muted">
                                <FormattedMessage id="project.reviews.edit.comment"/>
                            </label>
                            <div className="col-md-12">
                                <textarea
                                    name="comment"
                                    value={comment}
                                    onChange={e => setComment(e.target.value)}
                                    rows="4"
                                    className="form-control"
                                    maxLength="200"
                                    required
                                />
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                        </div>

                        <div className="form-group row justify-content-center">
                            <div className="col-md-6 mt-4">
                                <button type="submit" className="btn button-coral bold-label">
                                    <FormattedMessage id="project.global.buttons.save"/>
                                </button>
                            </div>
                        </div>
                    </form>

                </div>

            </div>

        </div>
    );
};

EditReview.propTypes ={
    userReview: PropTypes.array.isRequired
};

export default EditReview;
