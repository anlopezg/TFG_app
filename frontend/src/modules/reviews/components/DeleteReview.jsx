import React from 'react';
import { useDispatch } from 'react-redux';
import {Link, useNavigate, useParams} from 'react-router-dom';
import * as actions from '../actions';
import {FormattedMessage} from "react-intl";

const DeleteReview = () => {

    const {id} = useParams();
    const dispatch = useDispatch();
    const navigate = useNavigate();

    console.log("Review id", id);

    const handleDelete = () => {
        dispatch(actions.deleteReview(
            id,
            () => navigate('/reviews/find-user-reviews')
        ));
    };



    return (
        <div className="row justify-content-center ">
            <div className="col-md-6 ">
                <div className="card shopping-card card-body ">
                    <div className="justify-content-between align-items-center">

                        <h2 className="card-title retro">
                            <FormattedMessage id="project.reviews.delete.title"/>
                        </h2>
                        <p className="text-center">
                            <FormattedMessage id="project.reviews.delete.sure"/>
                        </p>
                    </div>

                    <div className="mt-4 mx-5 d-flex justify-content-between">
                        <Link className="btn button-lilac" to="/reviews/find-user-reviews" style={{ textTransform: 'uppercase' }}>
                            <FormattedMessage id="project.global.buttons.cancel" />
                        </Link>

                        <button onClick={() => handleDelete()} type="submit" className="btn button-lilac" style={{ textTransform: 'uppercase' }}>
                            <FormattedMessage id="project.global.buttons.ok" />
                        </button>
                    </div>
                </div>
            </div>


        </div>
    );
};

export default DeleteReview;
