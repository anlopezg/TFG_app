import {useState} from 'react';
import {useDispatch} from 'react-redux';
import {FormattedMessage} from 'react-intl';
import {useNavigate} from 'react-router-dom';
import PropTypes from 'prop-types';

import {CountryDropdown, RegionDropdown} from 'react-country-region-selector';

import {Errors} from '../../common';
import * as actions from '../actions';

const PurchaseForm = ({shoppingCartId}) => {

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [postalAddress, setPostalAddress] = useState('');
    const [locality, setLocality] = useState('');
    const [region, setRegion] = useState('');
    const [country, setCountry] = useState('');
    const [postalCode, setPostalCode] = useState('');
    const [backendErrors, setBackendErrors] = useState(null);
    let form;


    const handleSubmit = (event) => {

        event.preventDefault();

        if (form.checkValidity()) {

            dispatch(actions.purchase(shoppingCartId,
                postalAddress.trim(),
                locality.trim(),
                region.trim(),
                country.trim(),
                postalCode.trim(),
                navigate('/shopping/purchase/payment'),
                errors => setBackendErrors(errors)));

        } else {
            setBackendErrors(null);
            form.classList.add('was-validated');
        }

    }

    return (

        <div>
            <Errors errors={backendErrors}
                    onClose={() => setBackendErrors(null)}/>

            <form ref={node => form = node}
                  className="needs-validation" noValidate
                  onSubmit={(e) => handleSubmit(e)}>
            <div className="card shopping-card">
                <div className="card-body">
                    <h3 className="retro text-center">
                        <FormattedMessage id="project.shopping.Purchase.address"/>
                    </h3>
                    <p className="small muted text-center it">
                        <FormattedMessage id="project.shopping.Purchase.address.complete"/>
                    </p>
                            <div className="form-group row">
                                <label htmlFor="postalAddress" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.global.fields.postalAddress"/>
                                </label>
                                <div className="col-md-12 mb-3">
                                    <input type="text" id="postalAddress" className="form-control"
                                           value={postalAddress}
                                           onChange={e => setPostalAddress(e.target.value)}
                                           autoFocus
                                           required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>

                            <div className="form-group row">
                                <label htmlFor="country" className="col-form-label bold-label">
                                    <FormattedMessage id="project.global.fields.country"/>
                                </label>
                                <div className="col-md-12 mb-3">
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

                            <div className="form-group row">
                                <label htmlFor="region" className="col-form-label bold-label">
                                    <FormattedMessage id="project.global.fields.region"/>
                                </label>
                                <div className="col-md-12 mb-3">
                                    <RegionDropdown
                                        disableWhenEmpty={true}
                                        country={country}
                                        value={region}
                                        onChange={(val) => setRegion(val)}

                                        id="region" className="form-control custom-country-dropdown"
                                        defaultOptionLabel={<FormattedMessage id="project.global.fields.select.region"/>}
                                    />

                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>

                            <div className="form-group row">
                                <label htmlFor="locality" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.global.fields.locality"/>
                                </label>
                                <div className="col-md-12 mb-3">
                                    <input type="text" id="locality" className="form-control"
                                           value={locality}
                                           onChange={e => setLocality(e.target.value)}
                                           autoFocus
                                           required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>

                            <div className="form-group row">
                                <label htmlFor="postalCode" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.global.fields.postalCode"/>
                                </label>
                                <div className="col-md-12 mb-3">
                                    <input type="text" id="postalCode" className="form-control"
                                           value={postalCode}
                                           onChange={e => setPostalCode(e.target.value)}
                                           required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>
                    </div>
                </div>


                <div className="form-group row mt-4">
                    <div className="col-md-12 d-flex justify-content-center">
                        <button type="submit" className="btn button-lime">
                            <FormattedMessage id="project.shopping.BuyForm.payment"/>
                        </button>
                    </div>
                </div>
            </form>
        </div>

    );

}

PurchaseForm.propTypes = {
    shoppingCartId: PropTypes.number.isRequired
};

export default PurchaseForm;