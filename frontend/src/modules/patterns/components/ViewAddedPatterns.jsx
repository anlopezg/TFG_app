import {Link} from 'react-router-dom';
import {FormattedMessage} from "react-intl";
import {useDispatch, useSelector} from "react-redux";

import * as actions from "../actions.js";
import * as selectors from "../selectors.js";

import AddedPatterns from "./AddedPatterns.jsx";
import {Pager} from "../../common/index.js";
import {useEffect} from "react";

const ViewAddedPatterns = () => {

    const patternSearch = useSelector(selectors.getPatternSearch);
    const dispatch = useDispatch();

    useEffect(() => {

        dispatch(actions.findAddedPatterns({page: 0}));

        return () => dispatch(actions.clearAddedPatternSearch());

    }, [dispatch]);


    if(!patternSearch){
        return null;
    }

    return (

        <div className="mt-4 ms-auto container justify-content-center align-items-center">

            <div className="back-color-blue card-body">
                <div className="d-flex justify-content-between align-items-center">
                    <div>
                        <h3 className="pt-3 ms-5">
                            <FormattedMessage id="project.products.AddedPattern.heading"/>
                        </h3>
                    </div>
                    <div className="col-md-3 me-5">
                        <Link className="btn button-dark-blue bold-label" to="/publications/create-pattern">
                            <FormattedMessage id="project.products.CreatePattern.heading"/>
                        </Link>
                    </div>
                </div>


                <div className="p-5">
                    {patternSearch && patternSearch.result && patternSearch.result.items.length > 0 ? (
                        <div>
                            <AddedPatterns patterns={patternSearch.result.items}/>
                            <Pager back={{
                                enabled: patternSearch.criteria.page >= 1,
                                onClick:() => dispatch(actions.previousFindAddedPatternsResultPage(patternSearch.criteria))}}
                                   next={{
                                       enabled: patternSearch.result.existMoreItems,
                                       onClick: () => dispatch(actions.nextFindAddedPatternsResultPage(patternSearch.criteria))}}
                            />
                        </div>
                    ):(
                        <div className="alert back-color-grey" role="alert">
                            <FormattedMessage id="project.products.Pattern.empty"/>
                        </div>
                    )}
                </div>

            </div>






        </div>
    )
}

export default ViewAddedPatterns;