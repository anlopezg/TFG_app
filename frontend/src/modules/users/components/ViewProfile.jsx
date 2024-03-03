import {useSelector} from "react-redux";
import * as selectors from "../selectors.js";
import {FormattedMessage} from "react-intl";
import {Link} from "react-router-dom";

const ViewProfile = () => {

    const user = useSelector(selectors.getUser);

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

            <div className="card bg-light mb-3">
                <h2 className="card-header back-color-pink">
                    <FormattedMessage id="project.users.ProfileInfo.title"/>
                </h2>
                <div className="card-body back-color-grey">
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

                    <div className="row">
                        <div className="offset-md-3 ml-auto mr-3 d-flex justify-content-end ">
                            <Link className="btn button-pink extra-bold-label" to="/users/update-profile">
                                <FormattedMessage id="project.users.UpdateProfile.title"/>
                            </Link>
                        </div>

                    </div>
                </div>
            </div>

        </div>
    )
}

export default ViewProfile;

