import {useSelector, useDispatch} from 'react-redux';
import {FormattedMessage} from 'react-intl';
import {useNavigate} from 'react-router-dom';

import {Errors} from '../../common';
import * as actions from '../actions';
import * as selectors from '../selectors';
import {CountryDropdown} from "react-country-region-selector";
import {useState} from "react";

const UpdateProfile = () => {

    const user = useSelector(selectors.getUser);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const [userName, setUserName] = useState(user.userName);
    const [email, setEmail]  = useState(user.email);
    const [firstName, setFirstName] = useState(user.firstName);
    const [language, setLanguage] = useState(user.language);
    const [country, setCountry] = useState(user.country);
    const [crochetLevel, setCrochetLevel] = useState(user.crochetLevel);
    const [knitLevel, setKnitLevel] = useState(user.knitLevel);
    const [bio, setBio] = useState(user.bio);

    const [backendErrors, setBackendErrors] = useState(null);
    let form;

    const handleSubmit = event => {

        event.preventDefault();

        if (form.checkValidity()) {
            
            dispatch(actions.updateProfile(
                {id: user.id,
                    userName: userName.trim(),
                    email: email.trim(),
                    firstName: firstName.trim(),
                    language: language.trim(),
                    country: country.trim(),
                    crochetLevel: crochetLevel,
                    knitLevel: knitLevel,
                    bio: bio.trim()
                },
                () => navigate('/users/view-profile'),
                errors => setBackendErrors(errors)));

        } else {

            setBackendErrors(null);
            form.classList.add('was-validated');

        }

    }


    return (
        <div>
            <Errors errors={backendErrors} onClose={() => setBackendErrors(null)}/>

            <div className="mt-4 mb-4 container justify-content-center align-items-center">
                <div className="card">
                    <h2 className="card-header">
                        <FormattedMessage id="project.users.UpdateProfile.title"/>
                    </h2>

                    <div className="card-body">
                        <form ref={node => form = node}
                              className="needs-validation" noValidate onSubmit={e => handleSubmit(e)}>
                            <div className="form-group row justify-content-center">
                                <label htmlFor="userName" className="col-md-4 col-form-label  bold-label">
                                    <FormattedMessage id="project.global.fields.userName"/>
                                </label>
                                <div className="col-md-4">
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

                            <div className="form-group row justify-content-center">
                                <label htmlFor="email" className="col-md-4 col-form-label  bold-label">
                                    <FormattedMessage id="project.global.fields.email"/>
                                </label>
                                <div className="col-md-4">
                                    <input type="email" id="email" className="form-control"
                                           value={email}
                                           onChange={e => setEmail(e.target.value)}
                                           required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>

                            <div className="form-group row justify-content-center">
                                <label htmlFor="firstName" className="col-md-4 col-form-label bold-label">
                                    <FormattedMessage id="project.global.fields.firstName"/>
                                </label>
                                <div className="col-md-4">
                                    <input type="text" id="firstName" className="form-control"
                                           value={firstName}
                                           onChange={e => setFirstName(e.target.value)}
                                           autoFocus
                                           required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>

                            <div className="form-group row justify-content-center">
                                <label htmlFor="language" className="col-md-4 col-form-label bold-label">
                                    <FormattedMessage id="project.global.fields.language"/>
                                </label>
                                <div className="col-md-4">
                                    <input type="text" id="language" className="form-control"
                                           value={language}
                                           onChange={e => setLanguage(e.target.value)}
                                           autoFocus
                                           required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>

                            <div className="form-group row justify-content-center">
                                <label htmlFor="country" className="col-md-4 col-form-label bold-label">
                                    <FormattedMessage id="project.global.fields.country"/>
                                </label>
                                <div className="col-md-4">
                                    <CountryDropdown
                                        value={country}
                                        onChange={(val) => setCountry(val)}
                                        defaultOptionLabel={country}
                                        id="country" className="form-control custom-country-dropdown"/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>

                            <div className="form-group row justify-content-center">
                                <label htmlFor="crochetLevel" className="col-md-4 col-form-label bold-label">
                                    <FormattedMessage id="project.global.fields.crochetLevel"/>
                                </label>
                                <div className="col-md-4">
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
                            </div>
                            <div className="form-group row justify-content-center">
                                <label htmlFor="knitLevel" className="col-md-4 col-form-label bold-label">
                                    <FormattedMessage id="project.global.fields.knitLevel"/>
                                </label>
                                <div className="col-md-4">
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

                            <div className="form-group row justify-content-center">
                                <label htmlFor="bio" className="col-md-4 col-form-label bold-label">
                                    <FormattedMessage id="project.global.fields.bio"/>
                                </label>
                                <div className="col-md-4">
                                    <textarea  id="bio" className="form-control" rows="2"
                                               value={bio}
                                               maxLength={200}
                                               onChange={e => setBio(e.target.value)}
                                    />

                                </div>
                            </div>

                            <div className="form-group row justify-content-center">
                                <div className="col-md-6 mt-4">
                                    <button type="submit" className="btn button-pink extra-bold-label">
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

export default UpdateProfile;
