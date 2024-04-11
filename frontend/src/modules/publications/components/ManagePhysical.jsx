import {useDispatch, useSelector} from "react-redux";
import {useNavigate, useParams} from "react-router-dom";
import * as selectors from "../selectors.js";
import {useEffect} from "react";
import * as actions from "../actions.js";

const ManagePhysical= () =>{

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const {id} = useParams();
    const physical = useSelector(selectors.getPhysical);


    useEffect(() => {
        const physicalId = Number(id);
        if(!Number.isNaN(physicalId)){
            dispatch(actions.findPhysicalById(physicalId));
        }


    }, [id, dispatch]);

    useEffect(() => {

        //Only when a physical product has been found, navigate to the page
        if (physical) {
            const physicalId = Number(id);
            if (!Number.isNaN(physicalId)) {
                navigate(`/publications/edit-physical/${physicalId}`);
            }
        }
    }, [physical, id, navigate]);

    return null;

};

export default ManagePhysical;