import React, { useState, useEffect } from 'react';
import {useParams, useNavigate, useLocation} from 'react-router-dom';
import ReactStars from 'react-stars';
import * as actions from '../actions.js';
import {useDispatch} from "react-redux";
import Errors from "../../common/components/Errors.jsx";
import {FormattedMessage} from "react-intl";
import {BackLink, ProductLink} from "../../common/index.js";


const AddReview = () => {

    const location = useLocation();
    const productId = location.state?.productId;
    const productName = location.state?.productName;

    const navigate = useNavigate();
    const dispatch = useDispatch();
    const [backendErrors, setBackendErrors] = useState("");
    let form;

    const [rating, setRating] = useState("");
    const [comment, setComment] = useState("");

    const handleSubmit = event => {
        event.preventDefault();

        if(form.checkValidity()) {
            dispatch(actions.publishReview({
                    productId:productId,
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

            <div className="mb-3">
                <BackLink/>
            </div>

            <div className="card shopping-card col-md-6">
                <h2 className="mt-3 retro card-title">
                    <FormattedMessage id="project.reviews.add.header"/>
                </h2>
                <p className="text-muted text-center">
                    <FormattedMessage id="project.reviews.add.product"/>
                    <ProductLink id={productId} title={productName}/>
                </p>

                <div className="card-body">
                    <form ref={node => form = node}
                          className="needs-validation" noValidate
                          onSubmit={(e)=> handleSubmit(e)}>

                        <div className="form-group row my-1">
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
                                    color1={"#D2d2d2"}
                                    color2={"#E6895C"}
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

                        <div className="form-group row justify-content-center align-items-center">
                            <div className="col-md-6 mt-4">
                                <button type="submit" className="btn button-coral bold-label mx-4">
                                    <FormattedMessage id="project.reviews.add.publish"/>
                                    <i className="fa-solid fa-upload mx-2"></i>
                                </button>
                            </div>
                        </div>
                    </form>

                </div>

            </div>

        </div>
    );
};


export default AddReview;