import * as actions from "../../users/actions.js";
import {useDispatch} from "react-redux";
import {useNavigate} from 'react-router-dom';
import {useEffect, useState} from "react";

const BecomesSellerStripe = () =>{


    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();
    const dispatch = useDispatch();

    /*
    useEffect(() => {
        dispatch(actions.userBecomesSeller(
            (url) => {
                window.location.href = url; // Redirect to Stripe url
            },
            (error) => {
                setError(error);
                setLoading(false);
            }
        ));
    }, [dispatch]);*/

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error.message}</div>;
    }

    return null;

}

export default BecomesSellerStripe;