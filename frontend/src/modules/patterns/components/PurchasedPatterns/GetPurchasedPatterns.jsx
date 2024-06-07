import * as actions from "../../actions.js";
import * as selectors from "../../selectors.js";
import {useDispatch, useSelector} from "react-redux";
import {useEffect} from "react";
import {FormattedMessage} from "react-intl";
import PurchasedPatternsResult from "./PurchasedPatternsResult.jsx";

const GetPurchasedPatterns =() =>{

    const purchasedPatterns = useSelector(selectors.getPurchasedPatterns);
    const dispatch = useDispatch();

    useEffect(() => {

        dispatch(actions.getPurchasedPatterns());

        console.log("Purchased patterns", purchasedPatterns);

        return () => {
            dispatch(actions.clearPurchasedPatternsSearch());
        };

    }, [dispatch]);



    if(!purchasedPatterns){
        return null;
    }

    if(purchasedPatterns === undefined || purchasedPatterns.length === 0){
        return (
            <div>
                <div className="alert alert-info" role="alert">
                    <FormattedMessage id='project.pattern.purchased.notFound'/>
                </div>
            </div>
        )
    }

    return (
        <div>
            <div className="mt-2 mb-3">
                <h2 className="retro">
                    <FormattedMessage id="project.pattern.purchased.heading"/>
                </h2>
            </div>

            <PurchasedPatternsResult patterns={purchasedPatterns}/>

        </div>
    )

}

export default GetPurchasedPatterns;