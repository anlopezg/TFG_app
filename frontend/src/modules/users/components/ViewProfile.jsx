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
        <div className="mt-4 mb-4 container justify-content-center align-items-center">

            <div className="card mb-3">
                <h2 className="retro card-header back-color-pink">
                    <FormattedMessage id="project.users.ProfileInfo.title"/>
                </h2>
                <div className="card-body">
                    <div className="row justify-content-center">
                        <div className="col-md-4">
                            <label htmlFor="userName" className="col-form-label bold-label">
                                <FormattedMessage id="project.global.fields.userName"/>
                            </label>
                        </div>
                        <div className="col-md-4 col-form-label">
                            <p>{user.userName}</p>
                        </div>
                    </div>

                    <div className="row justify-content-center">
                        <div className="col-md-4 ">
                            <label htmlFor="email" className="bold-label col-form-label">
                                <FormattedMessage id="project.global.fields.email"/>
                            </label>
                        </div>
                        <div className="col-md-4 col-form-label">
                            <p>{user.email}</p>
                        </div>
                    </div>

                    <div className="row justify-content-center">
                        <div className="col-md-4 ">
                            <label htmlFor="firstName" className="bold-label col-form-label">
                                <FormattedMessage id="project.global.fields.firstName"/>
                            </label>
                        </div>
                        <div className="col-md-4 col-form-label">
                            <p>{user.firstName}</p>
                        </div>
                    </div>

                    <div className="row justify-content-center">
                        <div className="col-md-4 ">
                            <label htmlFor="language" className="bold-label col-form-label">
                                <FormattedMessage id="project.global.fields.language"/>
                            </label>
                        </div>
                        <div className="col-md-4 col-form-label">
                            <p>{user.language}</p>
                        </div>
                    </div>

                    <div className="row justify-content-center">
                        <div className="col-md-4 ">
                            <label htmlFor="country" className="bold-label col-form-label">
                                <FormattedMessage id="project.global.fields.country"/>
                            </label>
                        </div>
                        <div className="col-md-4 col-form-label">
                            <p>{user.country}</p>
                        </div>
                    </div>

                    <div className="row justify-content-center">
                        <div className="col-md-4 ">
                            <label htmlFor="region" className="bold-label col-form-label">
                                <FormattedMessage id="project.global.fields.region"/>
                            </label>
                        </div>
                        <div className="col-md-4 col-form-label">
                            <p>{user.region}</p>
                        </div>
                    </div>


                    <div className="row justify-content-center">
                        <div className="col-md-4 ">
                            <label htmlFor="crochetlevel" className="bold-label col-form-label">
                                <FormattedMessage id="project.global.fields.crochetLevel"/>
                            </label>
                        </div>
                        <div className="col-md-4 col-form-label">
                            <p>{getLevelName(user.crochetLevel)}</p>
                        </div>
                    </div>

                    <div className="row justify-content-center">
                        <div className="col-md-4 ">
                            <label htmlFor="knitlevel" className="bold-label col-form-label">
                                <FormattedMessage id="project.global.fields.knitLevel"/>
                            </label>
                        </div>
                        <div className="col-md-4 col-form-label">
                            <p>{getLevelName(user.knitLevel)}</p>
                        </div>
                    </div>

                    <div className="row justify-content-center">
                        <div className="col-md-4 ">
                            <label htmlFor="bio" className="bold-label col-form-label">
                                <FormattedMessage id="project.global.fields.bio"/>
                            </label>
                        </div>
                        <div className="col-md-4 col-form-label">
                            <p>{user.bio}</p>
                        </div>
                    </div>




                    <div className="row justify-content-center">
                        <div className="col-md-6 mt-4">
                            <Link className="btn button-pink extra-bold-label" to="/users/update-profile">
                                <FormattedMessage id="project.users.UpdateProfile.title"/>
                            </Link>
                        </div>

                    </div>
                </div>
            </div>

            {seller && accountId ? (
                <div className="row justify-content-center">
                    <GetStripeAccount/>
                </div>
            ):(
                <div>

                </div>
            )}


        </div>
    )
}

export default ViewProfile;

