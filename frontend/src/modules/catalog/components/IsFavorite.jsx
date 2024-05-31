import {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import * as actions from "../actions.js";
import * as favoriteActions from "../actions.js";
import users from "../../users/index.js";
import Heart from "react-heart";
import {useNavigate} from "react-router-dom";

const IsFavorite = ({productId}) => {

    const dispatch = useDispatch();
    const user = useSelector(users.selectors.getUser);
    const loggedIn = useSelector(users.selectors.isLoggedIn);
    const [backendErrors, setBackendErrors] = useState(null);
    const [isLiked, setIsLiked] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        // Check state of Favorite
        dispatch(actions.findFavorite(productId,
            favorite => setIsLiked(favorite.liked),
            errors => setBackendErrors(errors)
        ));
    }, [productId]);

    const handleFavoriteClick = () => {
        if (loggedIn) {
            dispatch(favoriteActions.markAsFavorite(
                {userId: user.id,
                    productId: toNumber(productId),
                    liked: true},
                () => setIsLiked(!isLiked),
                errors => setBackendErrors(errors)
            ));
        } else {
            navigate('/users/login');
        }
    };

    const toNumber = value => value.length > 0 ? Number(value) : null;


    return (
        <div className="mt-3" style={{ width: "2rem" }}>
            <Heart isActive={isLiked} onClick={ handleFavoriteClick} activeColor="#DE7C7C"/>
        </div>
    )

}

export default IsFavorite;