import {useSelector, useDispatch} from 'react-redux';
import {FormattedMessage} from 'react-intl';
import {useNavigate} from 'react-router-dom';

import {Errors} from '../../common';
import * as actions from '../actions';
import * as selectors from '../selectors';
import {CountryDropdown, RegionDropdown} from "react-country-region-selector";
import {useState} from "react";
import BackLink from "../../common/components/BackLink.jsx";

const UpdateProfile = () => {

    const user = useSelector(selectors.getUser);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const [userName, setUserName] = useState(user.userName);
    const [email, setEmail]  = useState(user.email);
    const [firstName, setFirstName] = useState(user.firstName);
    const [language, setLanguage] = useState(user.language);
    const [country, setCountry] = useState(user.country);
    const [region, setRegion] = useState(user.region);
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
                    region: region.trim(),
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

            <BackLink />
            <div className="mt-4 mb-4 container d-flex justify-content-center align-items-center">
                <div className="card shopping-card min-width-card">
                    <div className="card-header coral">
                        <h2 className="retro">
                            <FormattedMessage id="project.users.UpdateProfile.title"/>
                        </h2>
                    </div>


                    <div className="card-body">
                        <form ref={node => form = node}
                              className="needs-validation" noValidate onSubmit={e => handleSubmit(e)}>

                            <div className="text-center">
                                <div className="italic-message">
                                    <FormattedMessage id="project.global.form.updateProfile"/>
                                </div>
                            </div>

                            <div className="form-group ">
                                <label className="col-form-label bold-label">
                                    <FormattedMessage id="project.global.fields.userName" />
                                </label>
                                <input type="text" id="userName" className="form-control"
                                       value={userName}
                                       onChange={e => setUserName(e.target.value)}
                                       autoFocus
                                       required />
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required' />
                                </div>
                            </div>
                            <div className="form-group mt-3">
                                <label className="col-form-label bold-label">
                                    <FormattedMessage id="project.global.fields.email" />
                                </label>
                                <input type="email" id="email" className="form-control"
                                       value={email}
                                       onChange={e => setEmail(e.target.value)}
                                       required />
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required' />
                                </div>
                            </div>

                            <div className="form-group mt-3">
                                <label className="col-form-label bold-label">
                                    <FormattedMessage id="project.global.fields.firstName" />
                                </label>
                                <input type="text" id="firstName" className="form-control"
                                       value={firstName}
                                       onChange={e => setFirstName(e.target.value)}
                                       required />
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required' />
                                </div>
                            </div>

                            <div className="form-group mt-3">
                                <label className="col-form-label bold-label">
                                    <FormattedMessage id="project.global.fields.language" />
                                </label>
                                <input type="text" id="language" className="form-control"
                                       value={language}
                                       onChange={e => setLanguage(e.target.value)}
                                       required />
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required' />
                                </div>
                            </div>

                            <div className="form-group row">
                                <div className=" col-md-6">
                                    <label className="col-form-label bold-label">
                                        <FormattedMessage id="project.global.fields.country" />
                                    </label>
                                    <CountryDropdown
                                        value={country}
                                        onChange={(val) => setCountry(val)}
                                        defaultOptionLabel={country}
                                        id="country" className="form-control form-select" />
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required' />
                                    </div>
                                </div>

                                <div className="form-group col-md-6">
                                    <label className="col-form-label bold-label">
                                        <FormattedMessage id="project.global.fields.region" />
                                    </label>
                                    <RegionDropdown
                                        disableWhenEmpty={true}
                                        country={country}
                                        value={region}
                                        onChange={(val) => setRegion(val)}
                                        id="region" className="form-control form-select"
                                        defaultOptionLabel={region} />
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required' />
                                    </div>
                                </div>
                            </div>

                            <div className="form-group row">
                                <div className="col-md-6">
                                    <label className="col-form-label bold-label">
                                        <FormattedMessage id="project.global.fields.crochetLevel" />
                                    </label>
                                    <select id="crochetLevel" className="form-control form-select" value={crochetLevel}
                                            onChange={e => setCrochetLevel(e.target.value)}
                                            required>
                                        <option value="0">
                                            <FormattedMessage id="project.global.fields.level.none" /></option>
                                        <option value="1">
                                            <FormattedMessage id="project.global.fields.level.beginner" /></option>
                                        <option value="2">
                                            <FormattedMessage id="project.global.fields.level.intermediate" /></option>
                                        <option value="3">
                                            <FormattedMessage id="project.global.fields.level.advanced" /></option>
                                    </select>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required' />
                                    </div>
                                </div>

                                <div className="form-group col-md-6">
                                    <label className="col-form-label bold-label">
                                        <FormattedMessage id="project.global.fields.knitLevel" />
                                    </label>
                                    <select id="knitLevel" className="form-control form-select" value={knitLevel}
                                            onChange={e => setKnitLevel(e.target.value)}
                                            required>
                                        <option value="0">
                                            <FormattedMessage id="project.global.fields.level.none" /></option>
                                        <option value="1">
                                            <FormattedMessage id="project.global.fields.level.beginner" /></option>
                                        <option value="2">
                                            <FormattedMessage id="project.global.fields.level.intermediate" /></option>
                                        <option value="3">
                                            <FormattedMessage id="project.global.fields.level.advanced" /></option>
                                    </select>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required' />
                                    </div>
                                </div>
                            </div>

                            <div className="form-group mt-3">
                                <label className="col-form-label bold-label">
                                    <FormattedMessage id="project.global.fields.bio" />
                                </label>
                                <textarea id="bio" className="form-control" rows="2"
                                          value={bio}
                                          maxLength={200}
                                          onChange={e => setBio(e.target.value)}
                                />
                            </div>

                            <div className="form-group row justify-content-center">
                                <div className="col-md-6 mt-4">
                                    <button type="submit" className="btn button-pink text-uppercase bold-label">
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
