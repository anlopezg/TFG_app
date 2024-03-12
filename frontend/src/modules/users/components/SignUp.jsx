import {useState} from 'react';
import {useDispatch} from 'react-redux';
import {FormattedMessage} from 'react-intl';
import {useNavigate} from 'react-router-dom';

import { CountryDropdown} from 'react-country-region-selector';

import {Errors} from '../../common';
import * as actions from '../actions';

const SignUp = () => {

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [userName, setUserName] = useState('');
    const [email, setEmail]  = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [firstName, setFirstName] = useState('');
    const [language, setLanguage] = useState('');
    const [country, setCountry] = useState(null);
    const [crochetLevel, setCrochetLevel] = useState('');
    const [knitLevel, setKnitLevel] = useState('');
    const [bio, setBio] = useState('');


    const [backendErrors, setBackendErrors] = useState(null);
    const [passwordsDoNotMatch, setPasswordsDoNotMatch] = useState(false);
    let form;
    let confirmPasswordInput;

    const handleSubmit = event => {

        event.preventDefault();

        if (form.checkValidity() && checkConfirmPassword()) {
            
            dispatch(actions.signUp(
                {userName: userName.trim(),
                    email: email.trim(),
                    password: password,
                    firstName: firstName.trim(),
                    language: language.trim(),
                    country: country.trim(),
                    crochetLevel: crochetLevel,
                    knitLevel: knitLevel,
                    bio: bio.trim()

                },
                () => navigate('/'),
                errors => setBackendErrors(errors),
                () => {
                    navigate('/users/login');
                    dispatch(actions.logout());
                }
            ));
            

        } else {

            setBackendErrors(null);
            form.classList.add('was-validated');

        }

    }

    const checkConfirmPassword = () => {

        if (password !== confirmPassword) {

            confirmPasswordInput.setCustomValidity('error');
            setPasswordsDoNotMatch(true);

            return false;

        } else {
            return true;
        }

    }

    const handleConfirmPasswordChange = value => {

        confirmPasswordInput.setCustomValidity('');
        setConfirmPassword(value);
        setPasswordsDoNotMatch(false);
    
    }

    return (
        <div>
            <Errors errors={backendErrors} onClose={() => setBackendErrors(null)}/>
            <div className="mt-4 mb-4 container d-flex justify-content-center align-items-center">
                <div className="card">
                    <h2 className="card-header">
                        <FormattedMessage id="project.users.SignUp.title"/>
                    </h2>
                    <div className="card-body">
                        <form ref={node => form = node}
                            className="needs-validation" noValidate
                            onSubmit={e => handleSubmit(e)}>

                            <div className="text-center">
                                <div className="italic-message">
                                    <FormattedMessage id="project.global.form.introduction"/>
                                </div>
                            </div>

                            <div className="form-group row">
                                <label htmlFor="userName" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.global.fields.userName"/>
                                </label>
                                <div className="col-md-12">
                                    <input type="text" id="userName" className="form-control"
                                        value={userName}
                                        onChange={e => setUserName(e.target.value)}
                                        autoFocus
                                        required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label htmlFor="email" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.global.fields.email"/>
                                </label>
                                <div className="col-md-12">
                                    <input type="email" id="email" className="form-control"
                                           value={email}
                                           onChange={e => setEmail(e.target.value)}
                                           required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.email'/>
                                    </div>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label htmlFor="password" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.global.fields.password"/>
                                </label>
                                <div className="col-md-12">
                                    <input type="password" id="password" className="form-control"
                                        value={password}
                                        onChange={e => setPassword(e.target.value)}
                                        required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label htmlFor="confirmPassword" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.users.SignUp.fields.confirmPassword"/>
                                </label>
                                <div className="col-md-12">
                                    <input ref={node => confirmPasswordInput = node}
                                        type="password" id="confirmPassword" className="form-control"
                                        value={confirmPassword}
                                        onChange={e => handleConfirmPasswordChange(e.target.value)}
                                        required/>
                                    <div className="invalid-feedback">
                                        {passwordsDoNotMatch ?
                                            <FormattedMessage id='project.global.validator.passwordsDoNotMatch'/> :
                                            <FormattedMessage id='project.global.validator.required'/>}
                                    </div>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label htmlFor="firstName" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.global.fields.firstName"/>
                                </label>
                                <div className="col-md-12">
                                    <input type="text" id="firstName" className="form-control"
                                        value={firstName}
                                        onChange={e => setFirstName(e.target.value)}
                                        required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>

                            <div className="container m-0 p-0">
                                <div className="form-group row ">
                                    <div className="col-md-6">
                                        <label htmlFor="language" className="col-form-label bold-label">
                                            <FormattedMessage id="project.global.fields.language"/>
                                        </label>

                                        <input type="text" id="language" className="form-control"
                                        value={language}
                                        onChange={e => setLanguage(e.target.value)}
                                        required/>
                                        <div className="invalid-feedback">
                                            <FormattedMessage id='project.global.validator.required'/>
                                        </div>
                                    </div>

                                    <div className="col-md-6">
                                        <label htmlFor="country" className="col-form-label bold-label">
                                            <FormattedMessage id="project.global.fields.country"/>
                                        </label>

                                        <CountryDropdown
                                            value={country}
                                            onChange={(val) => setCountry(val)}
                                            defaultOptionLabel={<FormattedMessage id="project.global.fields.select.country"/>}
                                            id="country" className="form-control custom-country-dropdown"/>
                                        <div className="invalid-feedback">
                                            <FormattedMessage id='project.global.validator.required'/>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div className="container m-0 p-0">
                                <div className="form-group row ">
                                    <div className="col-md-6">
                                        <label htmlFor="crochetLevel" className=" col-form-label bold-label">
                                            <FormattedMessage id="project.global.fields.crochetLevel"/>
                                        </label>

                                        <select id="crochetLevel" className="form-control" value={crochetLevel}
                                                onChange={e => setCrochetLevel(e.target.value)}
                                                required>
                                            <option value="0">
                                                <FormattedMessage id="project.global.fields.level.none"/></option>
                                            <option value="1">
                                                <FormattedMessage id="project.global.fields.level.beginner"/></option>
                                            <option value="2">
                                                <FormattedMessage id="project.global.fields.level.intermediate"/></option>
                                            <option value="3">
                                                <FormattedMessage id="project.global.fields.level.advanced"/></option>
                                        </select>
                                        <div className="invalid-feedback">
                                            <FormattedMessage id='project.global.validator.required'/>
                                        </div>

                                    </div>

                                    <div className="col-md-6">
                                        <label htmlFor="knitLevel" className="col-form-label bold-label">
                                            <FormattedMessage id="project.global.fields.knitLevel"/>
                                        </label>

                                        <select id="knitLevel" className="form-control" value={knitLevel}
                                                onChange={e => setKnitLevel(e.target.value)}
                                                required>
                                            <option value="0">
                                                <FormattedMessage id="project.global.fields.level.none"/></option>
                                            <option value="1">
                                                <FormattedMessage id="project.global.fields.level.beginner"/></option>
                                            <option value="2">
                                                <FormattedMessage id="project.global.fields.level.intermediate"/></option>
                                            <option value="3">
                                                <FormattedMessage id="project.global.fields.level.advanced"/></option>
                                        </select>
                                        <div className="invalid-feedback">
                                            <FormattedMessage id='project.global.validator.required'/>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div className="form-group row">
                                <label htmlFor="bio" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.global.fields.bio"/>
                                </label>
                                <div className="col-md-12">
                                    <textarea  id="bio" className="form-control" rows="2"
                                           value={bio}
                                           maxLength={200}
                                           onChange={e => setBio(e.target.value)}
                                           />

                                </div>
                            </div>


                            <div className="form-group row justify-content-center">
                                <div className="col-md-6 mt-4">
                                    <button type="submit" className="btn button-pink bold-label">
                                        <FormattedMessage id="project.users.SignUp.title"/>
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

export default SignUp;
