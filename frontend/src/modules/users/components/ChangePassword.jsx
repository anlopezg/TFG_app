import {useState} from 'react';
import {useSelector, useDispatch} from 'react-redux';
import {FormattedMessage} from 'react-intl';
import {useNavigate} from 'react-router-dom';

import {Errors} from '../../common';
import * as actions from '../actions';
import * as selectors from '../selectors';
import BackLink from "../../common/components/BackLink.jsx";

const ChangePassword = () => {

    const user = useSelector(selectors.getUser);
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [oldPassword, setOldPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [confirmNewPassword, setConfirmNewPassword] = useState('');
    const [backendErrors, setBackendErrors] = useState(null);
    const [passwordsDoNotMatch, setPasswordsDoNotMatch] = useState(false);
    let form;
    let confirmNewPasswordInput;

    const handleSubmit = event => {

        event.preventDefault();

        if (form.checkValidity() && checkConfirmNewPassword()) {

            dispatch(actions.changePassword(user.id, oldPassword, newPassword,
                () => navigate('/'),
                errors => setBackendErrors(errors)));

        } else {

            setBackendErrors(null);
            form.classList.add('was-validated');
            
        }

    }

    const checkConfirmNewPassword = () => {

        if (newPassword !== confirmNewPassword) {

            confirmNewPasswordInput.setCustomValidity('error');
            setPasswordsDoNotMatch(true);

            return false;

        } else {
            return true;
        }

    }

    const handleConfirmNewPasswordChange = event => {

        confirmNewPasswordInput.setCustomValidity('');
        setConfirmNewPassword(event.target.value);
        setPasswordsDoNotMatch(false);

    }

    return (
        <div>
            <Errors errors={backendErrors} onClose={() => setBackendErrors(null)}/>
            <div className="mx-5">
                <BackLink/>
            </div>
            <div className="mt-4 mb-4 container d-flex justify-content-center align-items-center">
                <div className="card mb-3 card shopping-card min-width-card">
                    <div className="card-header coral">
                        <h2 className="retro ">
                            <FormattedMessage id="project.users.ChangePassword.title"/>
                        </h2>
                    </div>

                    <div className="card-body">
                        <form ref={node => form = node}
                            className="needs-validation" noValidate onSubmit={e => handleSubmit(e)}>


                            <div className="form-group ">
                                <label htmlFor="oldPassword" className="col-form-label bold-label">
                                    <FormattedMessage id="project.users.ChangePassword.fields.oldPassword" />
                                </label>
                                <input type="password" id="oldPassword" className="form-control"
                                       value={oldPassword}
                                       onChange={e => setOldPassword(e.target.value)}
                                       autoFocus
                                       required />
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required' />
                                </div>
                            </div>

                            <div className="form-group ">
                                <label htmlFor="newPassword" className="col-form-label bold-label">
                                    <FormattedMessage id="project.users.ChangePassword.fields.newPassword" />
                                </label>
                                <input type="password" id="newPassword" className="form-control"
                                       value={newPassword}
                                       onChange={e => setNewPassword(e.target.value)}
                                       autoFocus
                                       required />
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required' />
                                </div>
                            </div>

                            <div className="form-group ">
                                <label htmlFor="confirmNewPassword" className="col-form-label bold-label">
                                    <FormattedMessage id="project.users.ChangePassword.fields.confirmNewPassword" />
                                </label>
                                <input ref={node => confirmNewPasswordInput = node}
                                    type="password" id="confirmNewPassword" className="form-control"
                                       value={confirmNewPassword}
                                       onChange={e => handleConfirmNewPasswordChange(e)}
                                       required />
                                <div className="invalid-feedback">
                                    {passwordsDoNotMatch ?
                                        <FormattedMessage id='project.global.validator.passwordsDoNotMatch'/> :
                                        <FormattedMessage id='project.global.validator.required'/>}

                                </div>
                            </div>

                            <div className="form-group row justify-content-center">
                                <div className="col-md-6 mt-4">
                                    <button type="submit" className="btn button-pink bold-label text-uppercase">
                                        <FormattedMessage id="project.global.buttons.save"/>
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>



        </div>
    );

}

export default ChangePassword;
