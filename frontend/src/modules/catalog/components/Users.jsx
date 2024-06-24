import PropTypes from "prop-types";
import {Form} from "react-router-dom";
import {FormattedMessage} from "react-intl";
import {UserLink} from "../../common/index.js";

const Users = ({users}) => {

    console.log("", users);

    return (
        <div className="container user-result">
            {users.map(user =>
                <div key={user.id} className="row mb-3 justify-content-center">

                    <div className="col-md-8 ">
                        <div className="card">
                            <div className="row ">

                                <div className="col-md-4">
                                    <img className="img-fluid " src="/src/profile_icon_crochet.png" alt="Profile icon"/>
                                </div>

                                <div className="col-md-6">
                                    <div className="card-body">
                                        <h5 className="card-header">
                                            <i className="fa-solid fa-user-tag mx-1"></i>
                                            <UserLink id={user.id} username={user.userName}/>
                                        </h5>
                                        <p className="card-text ">
                                            <small className="text-muted">
                                                {user.region}, {user.country}
                                            </small>
                                        </p>
                                        <p className="card-text">{user.bio}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            )}

            </div>

    );
}

Users.propTypes = {
    users: PropTypes.array.isRequired
};

export default Users;