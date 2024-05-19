import {useDispatch, useSelector} from "react-redux";
import {useEffect} from "react";
import * as actions from "../actions.js";
import * as selectors from "../selectors.js";
import {FormattedMessage} from "react-intl";
import {Pager} from "../../common/index.js";
import UserReviews from "./UserReviews.jsx";

const FindUserReviews = () => {


    const userReviewSearch = useSelector(selectors.getUserReviewSearch);
    const dispatch = useDispatch();

    useEffect(() => {

        dispatch(actions.findUserReviews({page:0}));

        return () => dispatch(actions.clearUserReviewSearch());
    }, [dispatch]);

    if(!userReviewSearch){
        return null;
    }


    return(
        <div>
            <div className="mt-2 mb-3">
                <h2 className="retro">
                    <FormattedMessage id="project.reviews.User.header"/>
                </h2>
            </div>

            <div>
                {userReviewSearch && userReviewSearch.result && userReviewSearch.result.items.length>0 ?(
                    <div>
                        <UserReviews userReviews={userReviewSearch.result.items}/>
                        <Pager
                            back={{
                                enabled: userReviewSearch.criteria.page >= 1,
                                onClick: () => dispatch(actions.previousFindUserReviewsResultPage(userReviewSearch.criteria))}}
                            next={{
                                enabled: userReviewSearch.result.existMoreItems,
                                onClick: () => dispatch(actions.nextFindUserReviewsResultPage(userReviewSearch.criteria))}}/>
                    </div>

                ):(
                    <div className="alert alert-info" role="alert">
                        <FormattedMessage id='project.reviews.User.NotFound'/>
                    </div>

                    )}

            </div>
        </div>

    )
}

export default FindUserReviews;