import {useState} from 'react';
import {useSelector, useDispatch} from 'react-redux';
import {FormattedMessage} from 'react-intl';
import {useNavigate} from 'react-router-dom';

import {Errors} from '../../common';
import * as actions from '../actions';
import * as selectors from '../selectors';

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

            <div className="mt-4 mb-4 container justify-content-center align-items-center">
                <div className="card bg-light mb-3">
                    <h2 className="card-header back-color-pink">
                        <FormattedMessage id="project.users.ChangePassword.title"/>
                    </h2>
                    <div className="card-body back-color-grey">
                        <form ref={node => form = node}
                            className="needs-validation" noValidate onSubmit={e => handleSubmit(e)}>
                            <div className="form-group row justify-content-center">
                                <label htmlFor="oldPassword" className="col-md-4 col-form-label bold-label">
                                    <FormattedMessage id="project.users.ChangePassword.fields.oldPassword"/>
                                </label>
                                <div className="col-md-4">
                                    <input type="password" id="oldPassword" className="form-control"
                                        value={oldPassword}
                                        onChange={e => setOldPassword(e.target.value)}
                                        autoFocus
                                        required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>
                            <div className="form-group row justify-content-center">
                                <label htmlFor="newPassword" className="col-md-4 col-form-label bold-label">
                                    <FormattedMessage id="project.users.ChangePassword.fields.newPassword"/>
                                </label>
                                <div className="col-md-4">
                                    <input type="password" id="newPassword" className="form-control"
                                        value={newPassword}
                                        onChange={e => setNewPassword(e.target.value)}
                                        required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>
                            <div className="form-group row justify-content-center">
                                <label htmlFor="confirmNewPassword" className="col-md-4 col-form-label bold-label">
                                    <FormattedMessage id="project.users.ChangePassword.fields.confirmNewPassword"/>
                                </label>
                                <div className="col-md-4">
                                    <input ref={node => confirmNewPasswordInput = node}
                                        type="password" id="confirmNewPassword" className="form-control"
                                        value={confirmNewPassword}
                                        onChange={e => handleConfirmNewPasswordChange(e)}
                                        required/>
                                    <div className="invalid-feedback">
                                        {passwordsDoNotMatch ?
                                            <FormattedMessage id='project.global.validator.passwordsDoNotMatch'/> :
                                            <FormattedMessage id='project.global.validator.required'/>}

                                    </div>
                                </div>
                            </div>
                            <div className="form-group row">
                                <div className="offset-md-3 col-md-1 ml-auto mt-3 d-flex justify-content-end">
                                    <button type="submit" className="btn button-light-pink extra-bold-label">
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
