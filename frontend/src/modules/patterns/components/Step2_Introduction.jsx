import React, { useState } from 'react';
import {FormattedMessage} from "react-intl";

const Step2_Introduction = ({ data, onChange }) => {

    const handleChange = (e) => {
        const { name, value } = e.target;
        onChange({ [name]: value });
    };

    const handleTimeChange = (e) => {
        const { name, value } = e.target;
        const newTimeData = {
            ...data,
            [name]: value,
            time: `${data.timeValue} ${data.timeUnit}`
        };
        if (name === 'timeValue') {
            newTimeData.timeValue = value;
        } else if (name === 'timeUnit') {
            newTimeData.timeUnit = value;
        }
        onChange(newTimeData);
    };

    return (
        <div>
            <div className="container d-flex justify-content-center align-items-center">

                    <div className="card-body">
                        <div className="text-center">
                            <div className="framed-title disabled bold-label">
                                <FormattedMessage id="project.products.Pattern.detailedInfo"/>
                            </div>
                        </div>
                        <div className="mt-2 mb-3 text-center">
                            <div className="italic-message small">
                                <FormattedMessage id="project.products.Pattern.detailedInfo.message"/>
                            </div>
                        </div>

                        <div className="form-group row">
                            <label htmlFor="introduction" className="col-md-12 col-form-label bold-label">
                                <FormattedMessage id="project.products.Pattern.introduction"/>
                            </label>
                            <div className="col-md-12">
                                    <textarea id="introduction" className="form-control" rows="2"
                                              name="introduction"
                                              value={data.introduction}
                                              maxLength="500"
                                              onChange={handleChange}
                                              required/>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                            <p className="small muted italic-message">
                                <FormattedMessage id="project.products.Pattern.introduction.desc"/>
                            </p>
                        </div>

                        <div className="form-group row">
                            <label htmlFor="notes" className="col-md-12 col-form-label bold-label">
                                <FormattedMessage id="project.products.Pattern.notes"/>
                            </label>
                            <div className="col-md-12">
                                    <textarea id="notes" className="form-control" rows="2"
                                              name="notes"
                                              value={data.notes}
                                              maxLength="500"
                                              onChange={handleChange}
                                    />
                            </div>
                            <p className="small muted  italic-message">
                                <FormattedMessage id="project.products.Pattern.notes.desc"/>
                            </p>
                        </div>

                        <br/>
                        


                        <br/>

                        <div className="form-group row">
                            <div className="col-md-6">
                                <label htmlFor="abbreviations" className=" col-form-label bold-label">
                                    <FormattedMessage id="project.products.Pattern.abbreviations"/>
                                </label>
                                <select id="abbreviations" className="form-select" name="abbreviations" value={data.abbreviations}
                                        onChange={handleChange} required>
                                    <option value="ES">
                                        <FormattedMessage id="project.products.Pattern.standard.spanish"/></option>
                                    <option value="CUSTOM">
                                        <FormattedMessage id="project.products.Pattern.standard.custom"/></option>
                                    <option value="US">
                                        <FormattedMessage id="project.products.Pattern.standard.usa"/></option>

                                </select>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                            <div className="col-md-6">
                                <label htmlFor="specialAbbreviations" className=" col-form-label bold-label">
                                    <FormattedMessage id="project.products.Pattern.special.abbreviations"/>
                                </label>
                                <textarea
                                    id="specialAbbreviations" className="form-control" rows="2"
                                    name="specialAbbreviations"
                                    value={data.specialAbbreviations}
                                    maxLength="200"
                                    onChange={handleChange}>

                                    </textarea>
                            </div>
                        </div>
                    </div>
            </div>

        </div>
    );
};

export default Step2_Introduction;
