import {Pager} from "../../common/index.js";
import {useSelector, useDispatch} from "react-redux";

import * as selectors from '../selectors';
import * as actions from '../actions';
import {FormattedMessage} from "react-intl";
import {Users} from "../index.js";


const FindUsersResult = ()=>{

    const userSearch = useSelector(selectors.getUserSearch);
    const dispatch = useDispatch();

    if(!userSearch){
        return null;
    }

    if(userSearch.result.items.length === 0){

        return (
            <div className="alert alert-danger" role="alert">
                <FormattedMessage id='project.catalog.FindUsersResult.noUsersFound'/>
            </div>
        );
    }

    return(
        <div>
            <Users users={userSearch.result.items}/>
            <Pager
                back={{
                    enabled: userSearch.criteria.page >= 1,
                    onClick: () => dispatch(actions.previousFindUsersResultPage(userSearch.criteria))}}
                next={{
                    enabled: userSearch.result.existMoreItems,
                    onClick: () => dispatch(actions.nextFindUsersResultPage(userSearch.criteria))}}/>
        </div>
    )
}

export  default FindUsersResult;