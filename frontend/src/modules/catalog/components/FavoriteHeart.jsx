import React, { useEffect, useState } from 'react';
import {useDispatch, useSelector} from 'react-redux';
import * as actions from '../actions.js';
import Heart from "react-heart";
import users from "../../users/index.js";
import * as selectors from '../selectors.js';

const FavoriteHeart = ({ productId }) => {

    const [active, setActive] = useState(false);
    const dispatch = useDispatch();

    const user = useSelector(users.selectors.getUser);
    const favorite = useSelector(selectors.getFavorite);
    const [backendErrors, setBackendErrors] = useState(null);

    useEffect(() => {

        dispatch(actions.findFavorite(Number(productId),

            () => {
                if(favorite && typeof favorite.isLiked !== 'undefined'){
                    setActive(favorite.isLiked)
                }
            },
            errors => setBackendErrors(errors)
        ));
    }, [dispatch, productId, user]);


    const handleHeartClick = () => {
        const newLikedState = !active;

        if (newLikedState) {
            dispatch(actions.markAsFavorite({
                    userId: user.id,
                    productId: Number(productId),
                    isLiked: true
                }, () => setActive(true),
                errors => setBackendErrors(errors) ));
        } else {
            dispatch(actions.unmarkAsFavorite(Number(productId),
                () => setActive(false),
                errors => setBackendErrors(errors)
            ));
        }
    };


    return (
        <div>

            <div className="heart-container">
                <Heart isActive={active} activeColor="#DE7C7C" onClick={handleHeartClick}/>
            </div>
        </div>

    );
};


export default FavoriteHeart;
