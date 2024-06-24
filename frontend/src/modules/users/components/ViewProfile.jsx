import { useDispatch, useSelector} from "react-redux";
import * as selectors from "../selectors.js";
import {FormattedMessage} from "react-intl";
import {Link} from "react-router-dom";
import {useEffect} from "react";
import * as actions from '../actions.js';
import GetStripeAccount from "./Stripe/GetStripeAccount.jsx";


const ViewProfile = () => {

    const user = useSelector(selectors.getUser);
    const dispatch = useDispatch();
    const seller = useSelector(selectors.isSeller);
    const accountId = useSelector(selectors.getAccountId);


    useEffect(() => {
        if(seller && accountId){
            dispatch(actions.getStripeAccount(accountId));
        }

        return () => {
            dispatch(actions.clearStripeAccount());
        };

    }, [seller, accountId, dispatch]);



    const getLevelName = (level) => {
        switch (level){
            case 0:
                return  <FormattedMessage id="project.global.fields.level.none"/>;
            case 1:
                return <FormattedMessage id="project.global.fields.level.beginner"/>;
            case 2:
                return <FormattedMessage id="project.global.fields.level.intermediate"/>;
            case 3:
                return <FormattedMessage id="project.global.fields.level.advanced"/>;
        }
    }

    return(
        <div className="mt-4 mb-4 container">
            <div className="d-flex justify-content-center">
                <div className="card shopping-card min-width-card">
                    <div className="card-header coral">
                        <h2 className="retro">
                            <FormattedMessage id="project.users.ProfileInfo.title" />
                        </h2>
                    </div>

                    <div className="card-body">
                        <div className="form-group">
                            <label className="col-form-label bold-label">
                                <FormattedMessage id="project.global.fields.userName" />
                            </label>
                            <div className="form-control-plaintext border rounded p-2">
                                {user.userName}
                            </div>
                        </div>

                        <div className="form-group mt-3">
                            <label className="col-form-label bold-label">
                                <FormattedMessage id="project.global.fields.email" />
                            </label>
                            <div className="form-control-plaintext border rounded p-2">
                                {user.email}
                            </div>
                        </div>

                        <div className="form-group mt-3">
                            <label className="col-form-label bold-label">
                                <FormattedMessage id="project.global.fields.firstName" />
                            </label>
                            <div className="form-control-plaintext border rounded p-2">
                                {user.firstName}
                            </div>
                        </div>
                        <div className="form-group mt-3">
                            <label className="col-form-label bold-label">
                                <FormattedMessage id="project.global.fields.language" />
                            </label>
                            <div className="form-control-plaintext border rounded p-2">
                                {user.language}
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-6">
                                <div className="form-group mt-3">
                                    <label className="col-form-label bold-label">
                                        <FormattedMessage id="project.global.fields.country" />
                                    </label>
                                    <div className="form-control-plaintext border rounded p-2">
                                        {user.country}
                                    </div>
                                </div>
                            </div>
                            <div className="col-md-6">
                                <div className="form-group mt-3">
                                    <label className="col-form-label bold-label">
                                        <FormattedMessage id="project.global.fields.region" />
                                    </label>
                                    <div className="form-control-plaintext border rounded p-2">
                                        {user.region}
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-6">
                                <div className="form-group mt-3">
                                    <label className="col-form-label bold-label">
                                        <FormattedMessage id="project.global.fields.crochetLevel" />
                                    </label>
                                    <div className="form-control-plaintext border rounded p-2">
                                        {getLevelName(user.crochetLevel)}
                                    </div>
                                </div>
                            </div>
                            <div className="col-md-6">
                                <div className="form-group mt-3">
                                    <label className="col-form-label bold-label">
                                        <FormattedMessage id="project.global.fields.knitLevel" />
                                    </label>
                                    <div className="form-control-plaintext border rounded p-2">
                                        {getLevelName(user.knitLevel)}
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div className="form-group mt-3">
                            <label className="col-form-label bold-label">
                                <FormattedMessage id="project.global.fields.bio" />
                            </label>
                            <div className="form-control-plaintext border rounded p-2">
                                {user.bio}
                            </div>
                        </div>

                        <div className="row justify-content-center">
                            <div className="col-md-12 mt-4">
                                <Link className="btn button-pink text-uppercase bold-label" to="/users/update-profile">
                                    <FormattedMessage id="project.users.UpdateProfile.title" />
                                </Link>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <br/>

            {seller && accountId && (
                <div className="mt-4">
                    <GetStripeAccount />
                </div>
            )}
        </div>
    )
}

export default ViewProfile;

