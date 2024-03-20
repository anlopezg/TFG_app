import {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import {useNavigate, useParams} from "react-router-dom";

import * as actions from "../actions.js";
import * as selectors from "../selectors.js";
import * as userSelectors from "../../users/selectors.js";

const ManagePattern=()=>{

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const {id} = useParams();
    const pattern = useSelector(selectors.getPattern);
    const user = useSelector(userSelectors.getUser);


    useEffect(() => {
        const patternId = Number(id);
        if(!Number.isNaN(patternId)){
            dispatch(actions.findPatternById(user.userName, patternId));
        }


    }, [id, dispatch]);

    useEffect(() => {

        //Only when a pattern has been found, navigate to the page
        if (pattern) {
            const patternId = Number(id);
            if (!Number.isNaN(patternId)) {
                navigate(`/publications/edit-pattern/${patternId}`);
            }
        }
    }, [pattern, id, navigate]);

    return null;
};

export default ManagePattern;