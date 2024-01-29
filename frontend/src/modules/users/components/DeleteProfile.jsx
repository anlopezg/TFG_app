import {useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {Link, useNavigate} from 'react-router-dom';
import * as actions from '../actions';
import * as selectors from "../selectors.js";
import {Errors} from "../../common/index.js";
import {FormattedMessage} from "react-intl";

const DeleteProfile = () => {

    const user = useSelector(selectors.getUser);
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [backendErrors, setBackendErrors] = useState(null);
    let form;

    const handleSubmit = event => {
        event.preventDefault();

        if(form.checkValidity()){
            dispatch(actions.deleteProfile(user.id,
                ()=> navigate('/'),
                errors => setBackendErrors(errors)));
        }else{

            setBackendErrors(null);
            form.classList.add('was-validated');
        }
    }

    const handleDeleteProfile = () =>{
        dispatch(actions.deleteProfile(user.id,
            () => navigate('/'),
            errors => setBackendErrors(errors)));

    }

    console.log('user id: ', user.id);

    return(
        <div>
            <Errors errors={backendErrors} onClose={() => setBackendErrors(null)}/>
            <div className="mt-4 mb-4 container d-flex justify-content-center align-items-center">
                <div className="card bg-light mb-3 ">
                    <h2 className="card-header back-color-pink">
                        <FormattedMessage id="project.users.DeleteProfile.title"/>
                    </h2>
                    <div className="card-body back-color-grey">

                        <div className="text-center back-color-grey">
                            <div className="italic-message">
                                <FormattedMessage id="project.users.DeleteProfile.message"/>
                            </div>
                        </div>
                    </div>

                    <div className="card-footer back-color-grey">
                        <form ref={node => form = node}
                              className="needs-validation" noValidate
                              onSubmit={e => handleSubmit(e)}>

                            <div className="form-row">
                                <div className="offset-md-3 col-md-2 d-flex justify-content-start">
                                    <Link className="btn button-light-pink" to="/users/view-profile">
                                        <FormattedMessage id="project.global.buttons.cancel"/>
                                    </Link>
                                </div>
                                <div className="offset-md-3 col-md-2 ml-auto d-flex justify-content-end">
                                    <button type="submit" className="btn button-pink bold-label"
                                            onClick={() => handleDeleteProfile()}>
                                        <FormattedMessage id="project.global.buttons.ok"/>
                                    </button>
                                </div>

                            </div>

                        </form>
                    </div>
                </div>
            </div>

        </div>
    )

}

export default DeleteProfile;